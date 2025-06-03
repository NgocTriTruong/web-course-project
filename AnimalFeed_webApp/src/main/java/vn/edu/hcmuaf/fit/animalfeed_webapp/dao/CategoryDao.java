package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.util.List;

public class CategoryDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();
    private ActionLogDao actionLogDao = new ActionLogDao();
    private UserService userService = UserService.getInstance();

    public Category getById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM categories WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(Category.class)
                        .findOne()
                        .orElse(null));
    }

    public void insert(Category category, int userId) {
        if (userService.hasPermission(userId, "CATEGORY_MANAGEMENT")) {
            jdbi.useTransaction(handle -> {
                int categoryId = handle.createUpdate("INSERT INTO categories (name, img, status) VALUES (:name, :img, :status)")
                        .bindBean(category)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one();

                ActionLog actionLog = new ActionLog();
                actionLog.setUser_id(userId);
                actionLog.setAction_type("CREATE");
                actionLog.setEntity_type("CATEGORY");
                actionLog.setEntity_id(categoryId);
                actionLog.setDescription("User " + userId + " created category " + category.getName());
                actionLog.setBefore_data("null");
                actionLog.setAfter_data(category.toString());
                actionLogDao.logAction(actionLog);
            });
        } else {
            throw new RuntimeException("User does not have CATEGORY_MANAGEMENT permission");
        }
    }

    public void update(Category category, int userId) {
        if (userService.hasPermission(userId, "CATEGORY_MANAGEMENT")) {
            jdbi.useTransaction(handle -> {
                Category oldCategory = getById(category.getId());
                if (oldCategory == null) {
                    throw new IllegalArgumentException("Danh mục không tồn tại.");
                }

                int updatedRows = handle.createUpdate("UPDATE categories SET name = :name, img = :img, status = :status WHERE id = :id")
                        .bindBean(category)
                        .execute();

                if (updatedRows > 0) {
                    ActionLog actionLog = new ActionLog();
                    actionLog.setUser_id(userId);
                    actionLog.setAction_type("UPDATE");
                    actionLog.setEntity_type("CATEGORY");
                    actionLog.setEntity_id(category.getId());
                    actionLog.setDescription("User " + userId + " updated category " + category.getName());
                    actionLog.setBefore_data(oldCategory.toString());
                    actionLog.setAfter_data(category.toString());
                    actionLogDao.logAction(actionLog);
                } else {
                    throw new RuntimeException("Failed to update category with ID: " + category.getId());
                }
            });
        } else {
            throw new RuntimeException("User does not have CATEGORY_MANAGEMENT permission");
        }
    }

    public void delete(int id, int userId) {
        if (userService.hasPermission(userId, "CATEGORY_MANAGEMENT")) {
            jdbi.useTransaction(handle -> {
                Category deletedCategory = getById(id);
                if (deletedCategory == null) {
                    throw new IllegalArgumentException("Danh mục không tồn tại.");
                }

                int updatedRows = handle.createUpdate("UPDATE categories SET status = :status WHERE id = :id")
                        .bind("status", 0)
                        .bind("id", id)
                        .execute();

                if (updatedRows > 0) {
                    ActionLog actionLog = new ActionLog();
                    actionLog.setUser_id(userId);
                    actionLog.setAction_type("DELETE");
                    actionLog.setEntity_type("CATEGORY");
                    actionLog.setEntity_id(id);
                    actionLog.setDescription("User " + userId + " deleted category " + deletedCategory.getName());
                    actionLog.setBefore_data(deletedCategory.toString());
                    actionLog.setAfter_data("null");
                    actionLogDao.logAction(actionLog);
                } else {
                    throw new RuntimeException("Không thể xóa danh mục với ID: " + id);
                }
            });
        } else {
            throw new RuntimeException("User does not have CATEGORY_MANAGEMENT permission");
        }
    }

    public List<Category> getAll() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM categories WHERE status = :status")
                        .bind("status", 1)
                        .mapToBean(Category.class)
                        .list());
    }
}