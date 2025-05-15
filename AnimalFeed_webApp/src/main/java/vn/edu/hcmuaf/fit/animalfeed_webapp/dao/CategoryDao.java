package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

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
    public void updateCategoryStatus(Category category) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE categories SET name = :name, img = :img, status = :status WHERE id = :id")
                        .bindBean(category)
                        .execute()
        );
    }

    // Xóa danh mục
    public void deleteCategory(int id) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM categories WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
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