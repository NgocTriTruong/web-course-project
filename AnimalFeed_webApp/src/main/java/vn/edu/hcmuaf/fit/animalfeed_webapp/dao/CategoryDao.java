package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.JdbiException;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    public Category getCategoryById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from categories where id = :id")
                .bind("id", id).mapToBean(Category.class)
                .findOne().orElse(null));
    }

    //them danh muc
    public void insertCategory(Category category) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO categories (name, img, status) VALUES (:name, :img, :status)")
                        .bindBean(category)
                        .execute()
        );
    }

    // sua danh muc
    public void updateCategoryStatus(Category category) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE categories SET name = :name, img = :img, status = :status WHERE id = :id")
                        .bindBean(category)
                        .execute()
        );
    }

    // xoa danh muc
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
