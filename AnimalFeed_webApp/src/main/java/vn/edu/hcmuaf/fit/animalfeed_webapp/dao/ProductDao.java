package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.DBConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProductDao {
    private ActionLogDao actionLogDao = new ActionLogDao();

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
                        .bind("status", "1")
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

    // Lay product theo id danh muc -sản phẩm mới
    public List<Product> getByCatIdOfNewProduct(int categoryId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where cat_id = :categoryId and status = :status and create_date >= DATE_SUB(CURDATE(), INTERVAL 10 DAY)")
                        .bind("categoryId", categoryId)
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .mapToBean(Product.class).list());
    }

    // Lay product theo id danh muc -top 10 sản phẩm bán chạy
    public List<Product> getByCatIdOfBestSelling(int categoryId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                        SELECT p.*
                        FROM products p
                        JOIN order_details od ON p.id = od.product_id
                        WHERE p.cat_id = :categoryId AND p.status = :status
                        GROUP BY p.id
                        ORDER BY SUM(od.quantity) DESC
                        LIMIT 10
                    """)
                        .bind("categoryId", categoryId)
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
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
                ActionLog actionLog = new ActionLog(userId, "CREATE", "PRODUCT", productId, "User " + userId + " created product " + productId, null, product.toString());
                actionLogDao.logAction(actionLog);
            });
        }

    }

    //xoa product
    public void deleteProduct(int productId, int userId) {
        Product deletedProduct = getProductByIdOfAdmin(productId);
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
                    // Ghi log hành động vào bảng action_log
                    ActionLog actionLog = new ActionLog(userId, "DELETE", "PRODUCT", productId, "User " + userId + " deleted product " + productId, deletedProduct.toString(), null);
                    actionLogDao.logAction(actionLog);
                }else {
                    throw new RuntimeException("Failed to delete product with ID: " + productId);
                }
            });
        }
    }

    //sua product
    public void updateProduct(int productId, Product product, int userId) {
        Product oldProduct  = getProductByIdOfAdmin(productId);
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
                    // Ghi log hành động vào bảng action_log
                    ActionLog actionLog = new ActionLog(userId, "UPDATE", "PRODUCT", productId, "User " + userId + " updated product " + productId, oldProduct.toString(), product.toString());
                    actionLogDao.logAction(actionLog);
                } else {
                    throw new RuntimeException("Failed to update product with ID: " + product.getId());
                }
            });
        }
    }

    //tự động cập nhật giam gia
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
            SELECT p.id, p.img, p.name, p.description, p.price, p.quantity, p.status, d.percentage, ROUND(p.price * (1 - d.percentage / 100), 2) AS discountedPrice
            FROM products p
            JOIN discounts d ON p.discount_id = d.id
            WHERE p.discount_id != 1 AND p.status = '1'
            """;
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .mapToBean(ProductWithDiscountDTO.class)
                        .list());
    }

    // Phân trang sản phẩm giảm giá
    public List<ProductWithDiscountDTO> getProductByPageOfDiscount(int page, int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        String query = """
            SELECT p.id, p.img, p.name, p.description, p.price, p.quantity, p.status, d.percentage, ROUND(p.price * (1 - d.percentage / 100), 2) AS discountedPrice
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
                        .bind("start", (page - 1) * 8)
                        .bind("end", 8)
                        .mapToBean(ProductWithDiscountDTO.class)  // Ánh xạ kết quả vào ProductWithDiscountDTO
                        .list());
    }

    //Hiển thị sản phẩm mới nhất
    public List<Product> getNewProduct(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where status = :status and cat_id = :id and create_date >= DATE_SUB(CURDATE(), INTERVAL 10 DAY) ORDER BY create_date DESC")
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .bind("id", id)
                        .mapToBean(Product.class).list());
    }

    // Phân trang sản phẩm mới nhất
    public List<Product> getProductByPageOfNewProduct(int page, int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where status = :status and cat_id = :id and discount_id != :discountId and create_date >= DATE_SUB(CURDATE(), INTERVAL 10 DAY) ORDER BY create_date DESC limit :end offset :start")
                        .bind("status", "1")  // Lấy sản phẩm có trạng thái 'active'
                        .bind("discountId", 1)  // Lấy sản phẩm không giảm giá
                        .bind("id", id)
                        .bind("start", (page - 1) * 8)
                        .bind("end", 8)
                        .mapToBean(Product.class).list());
    }

    // Hiển thị sản phẩm bán chạy nhất
    public List<Product> getBestSellingProducts(int categoryId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                SELECT p.*
                FROM products p
                JOIN order_details od ON p.id = od.product_id
                WHERE p.status = :status
                AND p.cat_id = :categoryId
                GROUP BY p.id
                ORDER BY SUM(od.quantity) DESC
                LIMIT 10
            """)
                        .bind("status", "1")  // Lọc sản phẩm có trạng thái 'active'
                        .bind("categoryId", categoryId)
                        .mapToBean(Product.class).list());
    }

    // Phân trang top 10 sản phẩm bán chạy nhất
    public List<Product> getProductByPageOfBestSelling(int page, int categoryId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                        SELECT p.*
                        FROM products p
                        JOIN order_details od ON p.id = od.product_id
                        WHERE p.status = :status
                        AND p.cat_id = :categoryId
                        GROUP BY p.id
                        ORDER BY SUM(od.quantity) DESC
                        LIMIT :end OFFSET :start
                    """)
                        .bind("status", "1")  // Lọc sản phẩm có trạng thái 'active'
                        .bind("categoryId", categoryId)
                        .bind("start", (page - 1) * 8)
                        .bind("end", 8)
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

    public Map<Integer, Integer> getProductSales() {
        Jdbi jdbi = JdbiConnect.getJdbi();

        // Truy vấn SQL
        List<Map<String, Object>> results = jdbi.withHandle(handle ->
                handle.createQuery("""
            SELECT product_id, SUM(quantity) AS soldquantity
            FROM order_details
            GROUP BY product_id
        """).mapToMap().list()
        );

        // Log kết quả trả về từ truy vấn
        System.out.println("Query results: " + results);

        Map<Integer, Integer> salesMap = new HashMap<>();
        for (Map<String, Object> row : results) {
            // Log từng row để kiểm tra
            System.out.println("Row data: " + row);

            // Sửa key thành đúng định dạng
            Object productIdObj = row.get("product_id");
            Object soldQuantityObj = row.get("soldquantity"); // Đảm bảo chữ thường

            try {
                if (productIdObj != null && soldQuantityObj != null) {
                    // Ép kiểu chính xác
                    Integer productId = ((Number) productIdObj).intValue();
                    Integer soldQuantity = ((Number) soldQuantityObj).intValue();
                    salesMap.put(productId, soldQuantity);
                } else {
                    // Log nếu gặp null
                    System.err.println("Null value encountered in product_id or soldQuantity: " + row);
                }
            } catch (ClassCastException e) {
                // Xử lý ngoại lệ khi ép kiểu
                System.err.println("ClassCastException encountered: " + row);
                e.printStackTrace();
            }
        }

        // Trả về Map kết quả
        return salesMap;
    }

    public List<Product> getRelatedProducts(int categoryId, int currentProductId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery("SELECT * FROM products WHERE cat_id = :categoryId AND id != :currentProductId AND status = :status LIMIT 6")
                            .bind("categoryId", categoryId)
                            .bind("currentProductId", currentProductId)
                            .bind("status", "1")
                            .mapToBean(Product.class)
                            .list()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list on error
        }
    }

    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        Map<Integer, Integer> salesData = productDao.getProductSales();
        System.out.println(salesData);
    }

    public Product getProductByName(String name) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        String trimmedName = name.trim();
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM products WHERE TRIM(LOWER(name)) LIKE TRIM(LOWER(:name))")
                        .bind("name", "%" + trimmedName + "%")
                        .mapToBean(Product.class)
                        .findFirst()
                        .orElse(null));
    }

    // Lấy danh sách sản phẩm bán chạy nhất trong năm
    public List<Object[]> getTopSellingProductsInYear(int limit, int year) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                        SELECT p.id, p.name, SUM(od.quantity) AS total_quantity
                        FROM products p
                        JOIN order_details od ON p.id = od.product_id
                        JOIN orders o ON od.order_id = o.id
                        WHERE YEAR(o.order_date) = :year
                        GROUP BY p.id, p.name
                        ORDER BY total_quantity DESC
                        LIMIT :limit
                    """)
                        .bind("year", year)
                        .bind("limit", limit)
                        .map((rs, ctx) -> new Object[]{rs.getInt("id"), rs.getString("name"), rs.getInt("total_quantity")})
                        .list()
        );
    }

    // Lấy danh sách sản phẩm bán chạy nhất trong tháng
    public List<Object[]> getTopSellingProducts(int limit, int year, int month){
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("""
                        SELECT p.id, p.name, SUM(od.quantity) AS total_quantity
                        FROM products p
                        JOIN order_details od ON p.id = od.product_id
                        JOIN orders o ON od.order_id = o.id
                        WHERE YEAR(o.order_date) = :year AND MONTH(o.order_date) = :month
                        GROUP BY p.id, p.name
                        ORDER BY total_quantity DESC
                        LIMIT :limit
                    """)
                        .bind("year", year)
                        .bind("month", month)
                        .bind("limit", limit)
                        .map((rs, ctx) -> new Object[]{rs.getInt("id"), rs.getString("name"), rs.getInt("total_quantity")})
                        .list()
        );
    }

    // Lấy số lượng tồn kho hiện tại của sản phẩm
    public int getInventoryQuantity(int productId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COALESCE(SUM(quantity), 0) FROM inventory_transactions WHERE product_id = :productId")
                        .bind("productId", productId)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(0));
    }
}