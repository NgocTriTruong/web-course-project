package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.Date;
import java.util.List;


public class CategoryDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();
    private ActionLogDao actionLogDao = new ActionLogDao();

    public Category getCategoryById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from categories where id = :id")
                .bind("id", id).mapToBean(Category.class)
                .findOne().orElse(null));
    }

    // Thêm danh mục
    public void insertCategory(Category category, int userId) {
        boolean isAdmin = UserDao.checkIfAdmin(userId);
        if (isAdmin) {
            Jdbi jdbi = JdbiConnect.getJdbi();
            // Thực hiện thêm sản phẩm và ghi log
            jdbi.useTransaction(handle -> {
                int categoryId = handle.createUpdate("INSERT INTO categories (name, img, status) VALUES (:name, :img, :status)")
                        .bindBean(category)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one();

                ActionLog actionLog = new ActionLog(userId, "CREATE", "CATEGORY", categoryId, "User " + userId + " created product " + category, null, category.toString());
                actionLogDao.logAction(actionLog);
            });
        }
    }

    // Sửa danh mục
    public void updateCategoryStatus(Category category, int userId) {
        jdbi.useTransaction(handle -> {
            // Lấy dữ liệu trước khi chỉnh sửa
            Category oldCategory = getCategoryById(category.getId());
            if (oldCategory == null) {
                throw new IllegalArgumentException("Danh mục không tồn tại.");
            }

            String beforeData = oldCategory.toString();

            // Cập nhật danh mục
            handle.createUpdate("UPDATE categories SET name = :name, img = :img, status = :status WHERE id = :id")
                    .bindBean(category)
                    .execute();

            // Ghi log
            ActionLog actionLog = new ActionLog();
            actionLog.setUser_id(userId);
            actionLog.setAction_type("UPDATE");
            actionLog.setEntity_type("CATEGORY");
            actionLog.setEntity_id(category.getId());
            actionLog.setCreated_at(new Date());
            actionLog.setDescription("User " + userId + " updated category " + category.getName());
            actionLog.setBefore_data(beforeData);
            actionLog.setAfter_data(category.toString());
            actionLogDao.logAction(actionLog);
        });
    }

    // Xóa danh mục
    public void deleteCategory(int id, int userId) {
        // Lấy thông tin danh mục trước khi xóa
        Category deletedCategory = getCategoryById(id);

        // Kiểm tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(userId);

        if (!isAdmin) {
            throw new SecurityException("User " + userId + " không có quyền xóa danh mục");
        }

        if (deletedCategory == null) {
            throw new IllegalArgumentException("Danh mục không tồn tại.");
        }

        // Thực hiện xóa mềm danh mục và ghi log
        Jdbi jdbi = JdbiConnect.getJdbi();
        jdbi.useTransaction(handle -> {
            // Cập nhật trạng thái danh mục thành 'deleted' (status = 0)
            int updatedRows = handle.createUpdate("UPDATE categories SET status = :status WHERE id = :id")
                    .bind("status", 0) // Trạng thái 'deleted'
                    .bind("id", id)
                    .execute();

            // Ghi log hành động nếu xóa thành công
            if (updatedRows > 0) {
                ActionLog actionLog = new ActionLog(
                        userId,
                        "DELETE",
                        "CATEGORY",
                        id,
                        "User " + userId + " deleted category " + deletedCategory.getName(),
                        deletedCategory.toString(),
                        null
                );
                actionLogDao.logAction(actionLog);
            } else {
                throw new RuntimeException("Không thể xóa danh mục với ID: " + id);
            }
        });
    }

    public List<Category> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("select * from categories")
                .mapToBean(Category.class).list());
    }

    public static void main(String[] args) {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getAll();
        for (Category category : categories) {
            System.out.println(category);
        }
    }
}