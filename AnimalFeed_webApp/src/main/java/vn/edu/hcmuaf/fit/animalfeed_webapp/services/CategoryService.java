package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CategoryDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;

import java.util.List;

public class CategoryService {
    private final CategoryDao categoryDao = new CategoryDao();

    public void insertCategory(Category category, int userId) {
        categoryDao.insert(category, userId);
    }

    public void updateCategoryStatus(Category category, int userId) {
        categoryDao.update(category, userId);
    }

    public void deleteCategory(int id, int userId) {
        categoryDao.delete(id, userId);
    }

    public Category getCategoryById(int id) {
        return categoryDao.getById(id);
    }

    public List<Category> getAll() {
        return categoryDao.getAll();
    }
}