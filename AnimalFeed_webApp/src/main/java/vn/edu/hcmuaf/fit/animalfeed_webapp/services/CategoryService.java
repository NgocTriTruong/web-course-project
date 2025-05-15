package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CategoryDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;

import java.util.List;

public class CategoryService {
    static CategoryDao categoryDao = new CategoryDao();
    private final UserService userService = UserService.getInstance(); // Thêm UserService để kiểm tra quyền

    //Them danh muc
    public void insertCategory(Category category, int userId) {
        // Kiểm tra quyền PRODUCT_MANAGEMENT
        if (!userService.hasPermission(userId, "CATEGORY_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thực hiện thao tác này.");
        }
        categoryDao.insertCategory(category, userId);
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


    public static void main(String[] args) {
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.getAll();
        for (Category category : categories) {
            System.out.println(category);
        }
    }
}
