package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CategoryDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;

import java.util.List;

public class CategoryService {
    static CategoryDao categoryDao = new CategoryDao();

    //Them danh muc
    public void insertCategory(Category category) {
        categoryDao.insertCategory(category);
    }

    //Sua danh muc
    public void updateCategoryStatus(Category category) {
        categoryDao.updateCategoryStatus(category);
    }

    //Xoa danh muc
    public void deleteCategory(int id) {
        categoryDao.deleteCategory(id);
    }

    //Lay danh muc theo id
    public Category getCategoryById(int id) {
        return categoryDao.getCategoryById(id);
    }

    //Lay tat ca danh muc
    public List<Category> getAll() {
        return categoryDao.getAll();
    }
}
