package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    public Category getCategoryById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from categories where id = :id").bind("id", id).mapToBean(Category.class).findOne().orElse(null));
    }

    public void insertCategory(Category category) {
        int rowsInserted = jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO categories (name, img, status) VALUES (:name, :img, :status)")
                        .bind("name", category.getName())
                        .bind("img", category.getImg())
                        .bind("status", category.getStatus())
                        .execute()
        );
        System.out.println(rowsInserted);
    }

    // Method to update user information
    public void updateCategoryStatus(int status, int id) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE categories SET status = :status WHERE id = :id")
                        .bind("status", status)
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsUpdated);
    }

    // Method to delete a user by username
    public void deleteCategory(int id) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM categories WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsDeleted);
    }

    // Method to list all users
    public ArrayList<Category> getAllCategories() {
        return (ArrayList<Category>) jdbi.withHandle(handle -> handle.createQuery("select * from categories").mapToBean(Category.class).list());
    }

    public static void main(String[] args) {
        CategoryDao catDao = new CategoryDao();
        List<Category> cats = catDao.getAllCategories();
        for (Category cat : cats) {
            System.out.println(cat);
        }

    }
}
