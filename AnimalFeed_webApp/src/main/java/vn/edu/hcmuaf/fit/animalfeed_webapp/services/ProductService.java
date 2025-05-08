package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.LinkedHashMap;
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


    // Lay product theo id danh muc - sản phẩm bán chạy
    public List<Product> getByCatIdOfBestSelling(int categoryId) {
        return productDao.getByCatIdOfBestSelling(categoryId);
    }

    //Lay product theo id danh muc - sản phẩm mới
    public List<Product> getByCatIdOfNewProduct(int categoryId) {
        return productDao.getByCatIdOfNewProduct(categoryId);
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
// Sửa product
    public void updateProduct(int productId, Product product, int userId) {
        // Kiểm tra quyền PRODUCT_MANAGEMENT
        if (!userService.hasPermission(userId, "PRODUCT_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thực hiện thao tác này.");
        }
        productDao.updateProduct(productId, product, userId);
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

    // Lấy danh sách sản phẩm bán chạy nhất
    public Map<String, Integer> getTopSellingProductsInYear(int limit, int year) {
        List<Object[]> results = productDao.getTopSellingProductsInYear(limit, year);
        Map<String, Integer> topSellingProducts = new LinkedHashMap<>();

        for (Object[] result : results) {
            String productName = (String) result[1];
            int quantitySold = ((Number) result[2]).intValue();
            topSellingProducts.put(productName, quantitySold);
        }

        return topSellingProducts;
    }

    // Lấy danh sách sản phẩm bán chạy nhất theo tháng
    public Map<String, Integer> getTopSellingProducts(int limit, int year, int month) {
        List<Object[]> results = productDao.getTopSellingProducts(limit, year, month);
        Map<String, Integer> topSellingProducts = new LinkedHashMap<>();

        for (Object[] result : results) {
            String productName = (String) result[1];
            int quantitySold = ((Number) result[2]).intValue();
            topSellingProducts.put(productName, quantitySold);
        }

        return topSellingProducts;

    //Phân trang sản phẩm mới
    public List<Product> getProductByPageOfNewProduct(int page, int id) {
        return productDao.getProductByPageOfNewProduct(page, id);
    }

    //hiển thị sản phẩm bán chạy nhất
    public List<Product> getBestSellingProducts(int id) {
        return productDao.getBestSellingProducts(id);

    }

    //Phân trang sản phẩm bán chạy
    public List<Product> getProductByPageOfBestSelling(int page, int id) {
        return productDao.getProductByPageOfBestSelling(page, id);
    }

    //Loc sản phẩm theo Brand
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
    // Lấy số lượng tồn kho hiện tại của sản phẩm
    public int getInventoryQuantity(int productId) {
        return productDao.getInventoryQuantity(productId);
    }

    //Lấy danh sách sản phẩm bán chạy theo trang
    public List<Product> getProductByPageOfBestSelling(int page, int categoryId) {
        return productDao.getProductByPageOfBestSelling(page, categoryId);
    }

    public List<Product> getByCatIdOfBestSelling(int categoryId) {
        return productDao.getByCatIdOfBestSelling(categoryId);
    }

    public List<Product> getProductByPageOfNewProduct(int page, int categoryId) {
        return productDao.getProductByPageOfNewProduct(page, categoryId);
    }

    public List<Product> getByCatIdOfNewProduct(int categoryId) {
        return productDao.getByCatIdOfNewProduct(categoryId);
    }

    public List<Product> getBestSellingProducts(int categoryId) {
        return productDao.getBestSellingProducts(categoryId);
    }
}