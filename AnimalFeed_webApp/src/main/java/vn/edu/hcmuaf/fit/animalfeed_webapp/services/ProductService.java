package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.*;

public class ProductService {

    static ProductDao productDao = new ProductDao();

    //Lay tat ca product có trạng thái 'active'
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    //Lay tat ca product
    public List<Product> getAllProductOfAdmin() {
        return productDao.getAllProductOfAdmin();
    }

    //Lấy sản phẩm có trạng thái 'active' theo id
    public Product getDetail(int id) {
        return productDao.getById(id);
    }

    //Lấy sản phẩm theo id
    public Product getProductByIdOfAdmin(int id) {
        return productDao.getProductByIdOfAdmin(id);
    }

    public Product getProductById(int id) {
        return productDao.getProductByIdOfAdmin(id);
    }

    //Lay product theo id danh muc - sản phẩm bình thường
    public List<Product> getByCatId(int categoryId) {
        return productDao.getByCatId(categoryId);
    }

    // Lay product theo id danh muc -sản phẩm giảm giá
    public List<Product> getByCatIdOfDiscount(int categoryId) {
        return productDao.getByCatIdOfDiscount(categoryId);
    }

    //Dem so luong product trong db
    public int getTotalProduct(){
        return productDao.getTotalProduct();
    }

    //phan trang product
    public List<Product> getProductByPage(int page, int id){
        return productDao.getProductByPage(page, id);
    }

    //Them product
    public void insertProduct(Product product, int userId) {
        productDao.insertProduct(product, userId);
    }

    //xoa product
    public void deleteProduct(int productID, int userId) {
        productDao.deleteProduct(productID, userId);
    }

    //sua product
    public void updateProduct(int productId, Product product, int userId) {
        productDao.updateProduct(productId , product, userId);
    }

    //cập nhật giảm giá
    public void updateDiscount() {
        productDao.updateDiscount();
    }

    //lấy sản phẩm giảm giá
    public List<ProductWithDiscountDTO> getDiscountProduct() {
        return productDao.getDiscountProduct();
    }

    // Phân trang sản phẩm giảm giá
    public List<ProductWithDiscountDTO> getProductByPageOfDiscount(int page, int id) {
        return productDao.getProductByPageOfDiscount(page, id);
    }

    //Hiển thị sản phâm mới nhất
    public List<Product> getNewProduct(int id) {
        return productDao.getNewProduct(id);
    }

    //hiển thị sản phẩm bán chạy nhất
    public List<Product> getBestSellingProducts(int id) {
        return productDao.getBestSellingProducts(id);
    }

    //Loc sản phẩm theo Brand
    public List<Product> getProductByBrand(String brand) {
        return productDao.getProductByBrand(brand);
    }

    //lấy danh sách thương hiệu
    public List<String> getBrands() {
        return productDao.getBrands();
    }

    //lấy danh sách sản phẩm theo bộ lọc.
    public List<Product> getFilteredProducts(String brand, String priceOrder) {
        return productDao.getFilteredProducts(brand, priceOrder);
    }

    //đếm số product search
    public int countSearchProducts(String keyword, Double minPrice, Double maxPrice, String description, int categoryId) {
        return productDao.countSearchProducts(keyword, minPrice, maxPrice, description, categoryId);
    }

    //tìm kiếm sản phẩm , phân trang
    public List<Product> searchProducts(String keyword, Double minPrice, Double maxPrice, String description, int categoryId, int currentPage, int pageSize) {
        return productDao.searchProducts(keyword, minPrice, maxPrice, description, categoryId, currentPage, pageSize);
    }

    //Lấy sản phẩm liên quan (cùng category)
    public List<Product> getRelatedProducts(int categoryId, int currentProductId) {
        return productDao.getRelatedProducts(categoryId, currentProductId);
    }

    //lượt bán
    public Map<Integer, Integer> getProductSales() {
        return productDao.getProductSales();
    }

    public Product getProductByName(String name) {
        return productDao.getProductByName(name);
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
    }

    // Lấy số lượng tồn kho hiện tại của sản phẩm
    public int getInventoryQuantity(int productId) {
        return productDao.getInventoryQuantity(productId);
    }
}
