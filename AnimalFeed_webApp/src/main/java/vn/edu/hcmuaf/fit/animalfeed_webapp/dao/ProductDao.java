package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.DBConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProductDao {

    // Lấy sản phẩm có trạng thái 'active'
    public List<Product> getAll() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> handle.createQuery("select * FROM products WHERE status = :status AND discount_id = :discountId")
                .bind("status", "1")
                .bind("discountId", 1)  // Lấy sản phẩm không giảm giá
                .mapToBean(Product.class).list());
    }

    public List<Product> getAllProductOfAdmin() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> handle.createQuery("select * FROM products")
                .mapToBean(Product.class).list());
    }

    public Product getById(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where id = :id and status = :status")
                        .bind("id", id)
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .mapToBean(Product.class).findOne().orElse(null));
    }

    public Product getProductByIdOfAdmin(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where id = :id")
                        .bind("id", id)
                        .mapToBean(Product.class).findOne().orElse(null));
    }

    // Lay product theo id danh muc -sản phẩm không giảm giá
    public List<Product> getByCatId(int categoryId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where cat_id = :categoryId and status = :status and discount_id = :discountId")
                        .bind("categoryId", categoryId)
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .bind("discountId", 1)  // Lấy sản phẩm không giảm giá
                        .mapToBean(Product.class).list());
    }

    // Lay product theo id danh muc -sản phẩm giảm giá
    public List<Product> getByCatIdOfDiscount(int categoryId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where cat_id = :categoryId and status = :status and discount_id != :discountId")
                        .bind("categoryId", categoryId)
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .bind("discountId", 1)  // Lấy sản phẩm không giảm giá
                        .mapToBean(Product.class).list());
    }

    //Dem so luong product active trong db
    public int getTotalProduct() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select count(*) from products where status = :status")
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .mapTo(Integer.class).findOne().orElse(0));
    }

    //phan trang product
    public List<Product> getProductByPage(int page, int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where status = :status and cat_id = :id AND discount_id = :discountId order by id limit :end offset :start")
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .bind("id", id)
                        .bind("discountId", 1)
                        .bind("start", (page - 1) * 16)
                        .bind("end", 16)
                        .mapToBean(Product.class).list());
    }

    //Them product
    public void insertProduct(Product product, int userId) {
        //kiem tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(userId);

        if(isAdmin) {
            Jdbi jdbi = JdbiConnect.getJdbi();
            // Thực hiện thêm sản phẩm và ghi log
            jdbi.useTransaction(handle ->{

                    int productId = handle.createUpdate("INSERT INTO products (cat_id, name, price, description, quantity, img, create_date, discount_id) "
                            + "VALUES (:cat_id, :name, :price, :description, :quantity, :img, :createDate, :discountId)")
                            .bindBean(product)
                            .executeAndReturnGeneratedKeys("id").mapTo(Integer.class).one();

                // Ghi log hành động vào bảng action_log
                handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                        .bind("userId", userId)
                        .bind("actionType", "CREATE")
                        .bind("entityType", "PRODUCT")
                        .bind("entityId", productId)
                        .bind("description", "User " + userId + " created product " + productId)
                        .execute();
            });
        }

    }

    //xoa product
    public void deleteProduct(int productId, int userId) {
        //kiem tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(userId);

        if (isAdmin) {
            // Thực hiện xóa mềm sản phẩm và ghi log
            Jdbi jdbi = JdbiConnect.getJdbi();
            jdbi.useTransaction(handle -> {

                // Cập nhật trạng thái sản phẩm thành 'deleted' ( deleted = 0)
                int updatedRows = handle.createUpdate("UPDATE products SET status = :status WHERE id = :productId")
                        .bind("status", "0") // Trạng thái 'deleted'
                        .bind("productId", productId).execute();

                // Ghi log hành động vào bảng action_log
                if (updatedRows > 0) {
                    handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                            .bind("userId", userId)
                            .bind("actionType", "DELETE")
                            .bind("entityType", "PRODUCT")
                            .bind("entityId", productId)
                            .bind("description", "User " + userId + " deleted product " + productId)
                            .execute();
                }else {
                    throw new RuntimeException("Failed to delete product with ID: " + productId);
                }
            });
        }
    }

    //sua product
    public void updateProduct(Product product, int userId) {
        //kiem tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(userId);

        if (isAdmin){
            Jdbi jdbi = JdbiConnect.getJdbi();
            jdbi.useTransaction(handle -> {
                // Cập nhật thông tin sản phẩm và ghi log
                int updatedRows = handle.createUpdate("UPDATE products SET cat_id = :cat_id, name = :name, price = :price, description = :description, quantity = :quantity, status = :status, img = :img, discount_id =:discountId WHERE id = :id")
                        .bindBean(product).execute();

                // Ghi log hành động vào bảng action_log
                if (updatedRows > 0) {
                    handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                            .bind("userId", userId)
                            .bind("actionType", "UPDATE")
                            .bind("entityType", "PRODUCT")
                            .bind("entityId", product.getId())
                            .bind("description", "User " + userId + " updated product " + product.getId())
                            .execute();
                } else {
                    throw new RuntimeException("Failed to update product with ID: " + product.getId());
                }
            });
        }
    }

    //cập nhật giam gia
    public void updateDiscount() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        String updateQuery = """ 
                UPDATE products p
                JOIN discounts d
                    ON (DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), NOW()) <= 30 AND d.percentage = 50)
                    OR (DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), NOW()) <= 90 AND d.percentage = 25)
                    OR (DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), NOW()) <= 150 AND d.percentage = 15)
                    OR (DATEDIFF(DATE_ADD(p.create_date, INTERVAL 3 YEAR), NOW()) > 150 AND d.percentage = 0)
                SET p.discount_id = d.id;
                """;
        jdbi.useHandle(handle -> {
            int rowsUpdated = handle.createUpdate(updateQuery).execute();
            System.out.println(rowsUpdated + " products updated with discount_id.");
        });
    }

    //lấy sản phẩm giảm giá
    public List<ProductWithDiscountDTO> getDiscountProduct() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        String query = """
                SELECT p.id, p.img, p.name, p.description, p.price, d.percentage, ROUND(p.price * (1 - d.percentage / 100), 2) AS discountedPrice
                FROM products p
                JOIN discounts d ON p.discount_id = d.id
                WHERE p.discount_id != 1 and p.status = '1'
                """;
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .mapToBean(ProductWithDiscountDTO.class).list());
    }

    // Phân trang sản phẩm giảm giá
    public List<ProductWithDiscountDTO> getProductByPageOfDiscount(int page, int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        String query = """
            SELECT p.id, p.img, p.name, p.description, p.price, d.percentage, ROUND(p.price * (1 - d.percentage / 100), 2) AS discountedPrice
            FROM products p
            JOIN discounts d ON p.discount_id = d.id
            WHERE p.cat_id = :id AND p.discount_id != :discountId
            ORDER BY p.id
            LIMIT :end OFFSET :start
            """;
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .bind("id", id)
                        .bind("discountId", 1)  // Trả về các sản phẩm có discount khác 1
                        .bind("start", (page - 1) * 12)
                        .bind("end", 12)
                        .mapToBean(ProductWithDiscountDTO.class)  // Ánh xạ kết quả vào ProductWithDiscountDTO
                        .list());
    }


    //Hiển thị sản phẩm mới nhất
    public List<Product> getNewProduct() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where status = :status and create_date >= DATE_SUB(CURDATE(), INTERVAL 10 DAY) ORDER BY create_date DESC")
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .mapToBean(Product.class).list());
    }

    //Hiển thị sản phẩm bán chạy nhất
    public List<Product> getBestSellingProducts() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                SELECT p.*
                FROM products p
                JOIN order_details od ON p.id = od.product_id
                JOIN orders o ON od.order_id = o.id
                WHERE p.status = :status
                GROUP BY p.id
                ORDER BY o.total_quantity DESC
                LIMIT 10
            """)
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .mapToBean(Product.class).list());
    }

    //Loc sản phẩm theo Brand
    public List<Product> getProductByBrand(String brand) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where brand = :brand and status = :status and discount_id = :discountId")
                        .bind("brand", brand)
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .bind("discountId", 1)  // Lấy sản phẩm không giảm giá
                        .mapToBean(Product.class).list());
    }

    //lấy danh sách thương hiệu
    public List<String> getBrands() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select distinct brand_name from products where status = :status")
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .mapTo(String.class).list());
    }

    //lấy danh sách sản phẩm theo bộ lọc.
    public List<Product> getFilteredProducts(String brand, String priceOrder){
        Jdbi jdbi = JdbiConnect.getJdbi();
        String query = """
                SELECT * FROM products
                WHERE status = :status
                """;
        if (brand != null && !brand.isEmpty()) {
            query += " AND brand_name = :brandName";
        }

        if ("ascending".equals(priceOrder)) {
            query +=" ORDER BY price ASC";
        } else if ("descending".equals(priceOrder)) {
            query +=" ORDER BY price DESC";
        }

        String finalQuery = query;
        return jdbi.withHandle(handle ->
                handle.createQuery(finalQuery)
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .bind("brand_name", brand)
                        .bind("priceOrder", priceOrder)
                        .mapToBean(Product.class).list());
    }

    //đếm số product search
    public int countSearchProducts(String keyword, Double minPrice, Double maxPrice, String description, int categoryId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> {
            StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM products WHERE status = '1' ");

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.append(" AND name LIKE :keyword");
            }
            if (minPrice != null) {
                query.append(" AND price >= :minPrice");
            }
            if (maxPrice != null) {
                query.append(" AND price <= :maxPrice");
            }
            if (description != null && !description.trim().isEmpty()) {
                query.append(" AND description LIKE :description");
            }
            if (categoryId > 0) {
                query.append(" AND cat_id = :categoryId");
            }

            return handle.createQuery(query.toString())
                    .bind("keyword", "%" + keyword + "%")
                    .bind("minPrice", minPrice)
                    .bind("maxPrice", maxPrice)
                    .bind("description", "%" + description + "%")
                    .bind("categoryId", categoryId)
                    .mapTo(Integer.class)
                    .one();
        });
    }

    //tìm kiếm sản phẩm , phân trang
    public List<Product> searchProducts(String keyword, Double minPrice, Double maxPrice, String description, int categoryId, int currentPage, int pageSize) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> {
            StringBuilder query = new StringBuilder("SELECT * FROM products WHERE status = '1' ");

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.append(" AND name LIKE :keyword");
            }
            if (minPrice != null) {
                query.append(" AND price >= :minPrice");
            }
            if (maxPrice != null) {
                query.append(" AND price <= :maxPrice");
            }
            if (description != null && !description.trim().isEmpty()) {
                query.append(" AND description LIKE :description");
            }
            if (categoryId > 0) {
                query.append(" AND cat_id = :categoryId");
            }

            query.append(" LIMIT :limit OFFSET :offset");

            return handle.createQuery(query.toString())
                    .bind("keyword", "%" + keyword + "%")
                    .bind("minPrice", minPrice)
                    .bind("maxPrice", maxPrice)
                    .bind("description", "%" + description + "%")
                    .bind("categoryId", categoryId)
                    .bind("limit", pageSize)
                    .bind("offset", (currentPage - 1) * pageSize)
                    .mapToBean(Product.class)
                    .list();
        });
    }

    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        List<ProductWithDiscountDTO> products = productDao.getDiscountProduct();
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm giảm giá.");
        } else {
            for (ProductWithDiscountDTO product : products) {
                System.out.println(product);
            }
        }
    }

}
