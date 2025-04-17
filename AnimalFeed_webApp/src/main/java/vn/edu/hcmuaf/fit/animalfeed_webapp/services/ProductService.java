package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.List;
import java.util.Map;

public class ProductService {

    private static final ProductDao productDao = new ProductDao();
    private final UserService userService = UserService.getInstance(); // Thêm UserService để kiểm tra quyền

    // Lấy tất cả product có trạng thái 'active'
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    // Lấy tất cả product
    public List<Product> getAllProductOfAdmin() {
        return productDao.getAllProductOfAdmin();
    }

    // Lấy sản phẩm có trạng thái 'active' theo id
    public Product getDetail(int id) {
        return productDao.getById(id);
    }

    // Lấy sản phẩm theo id
    public Product getProductByIdOfAdmin(int id) {
        return productDao.getProductByIdOfAdmin(id);
    }

    public Product getProductById(int id) {
        return productDao.getProductByIdOfAdmin(id);
    }

    // Lấy product theo id danh mục - sản phẩm bình thường
    public List<Product> getByCatId(int categoryId) {
        return productDao.getByCatId(categoryId);
    }

    // Lấy product theo id danh mục - sản phẩm giảm giá
    public List<Product> getByCatIdOfDiscount(int categoryId) {
        return productDao.getByCatIdOfDiscount(categoryId);
    }

    // Đếm số lượng product trong db
    public int getTotalProduct() {
        return productDao.getTotalProduct();
    }

    // Phân trang product
    public List<Product> getProductByPage(int page, int id) {
        return productDao.getProductByPage(page, id);
    }

    // Thêm product
    public void insertProduct(Product product, int userId) {
        // Kiểm tra quyền PRODUCT_MANAGEMENT
        if (!userService.hasPermission(userId, "PRODUCT_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thực hiện thao tác này.");
        }
        productDao.insertProduct(product, userId);
    }

    // Xóa product
    public void deleteProduct(int productID, int userId) {
        // Kiểm tra quyền PRODUCT_MANAGEMENT
        if (!userService.hasPermission(userId, "PRODUCT_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thực hiện thao tác này.");
        }
        productDao.deleteProduct(productID, userId);
    }

    // Sửa product
    public void updateProduct(Product product, int userId) {
        // Kiểm tra quyền PRODUCT_MANAGEMENT
        if (!userService.hasPermission(userId, "PRODUCT_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thực hiện thao tác này.");
        }
        productDao.updateProduct(product, userId);
    }

    // Cập nhật giảm giá
    public void updateDiscount() {
        productDao.updateDiscount();
    }

    // Lấy sản phẩm giảm giá
    public List<ProductWithDiscountDTO> getDiscountProduct() {
        return productDao.getDiscountProduct();
    }

    // Phân trang sản phẩm giảm giá
    public List<ProductWithDiscountDTO> getProductByPageOfDiscount(int page, int id) {
        return productDao.getProductByPageOfDiscount(page, id);
    }

    // Hiển thị sản phẩm mới nhất
    public List<Product> getNewProduct(int id) {
        return productDao.getNewProduct(id);
    }

    // Hiển thị sản phẩm bán chạy nhất
    public List<Product> getBestSellingProducts(int id) {
        return productDao.getBestSellingProducts(id);
    }

    // Lọc sản phẩm theo Brand
    public List<Product> getProductByBrand(String brand) {
        return productDao.getProductByBrand(brand);
    }

    // Lấy danh sách thương hiệu
    public List<String> getBrands() {
        return productDao.getBrands();
    }

    // Lấy danh sách sản phẩm theo bộ lọc
    public List<Product> getFilteredProducts(String brand, String priceOrder) {
        return productDao.getFilteredProducts(brand, priceOrder);
    }

    // Đếm số product search
    public int countSearchProducts(String keyword, Double minPrice, Double maxPrice, String description, int categoryId) {
        return productDao.countSearchProducts(keyword, minPrice, maxPrice, description, categoryId);
    }

    // Tìm kiếm sản phẩm, phân trang
    public List<Product> searchProducts(String keyword, Double minPrice, Double maxPrice, String description, int categoryId, int currentPage, int pageSize) {
        return productDao.searchProducts(keyword, minPrice, maxPrice, description, categoryId, currentPage, pageSize);
    }

    // Lấy sản phẩm liên quan (cùng category)
    public List<Product> getRelatedProducts(int categoryId, int currentProductId) {
        return productDao.getRelatedProducts(categoryId, currentProductId);
    }

    // Lượt bán
    public Map<Integer, Integer> getProductSales() {
        return productDao.getProductSales();
    }

    public Product getProductByName(String name) {
        return productDao.getProductByName(name);
    }
}