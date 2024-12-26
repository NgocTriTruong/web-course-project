package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;

import java.util.List;

public class CategoryDao {
    private Jdbi jdbi = JdbiConnect.getJdbi();

    public List<Category> getAll() {
        return jdbi.withHandle(handle -> handle.createQuery("select * from categories").mapToBean(Category.class).list());
    }

    public Category getById(int id) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from categories where id = :id")
                .bind("id", id).mapToBean(Category.class)
                .findOne().orElse(null));
    }

    public static void main(String[] args) {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.getAll();
        for (Category category : categories) {
            System.out.println(category);
        }
    }
}
