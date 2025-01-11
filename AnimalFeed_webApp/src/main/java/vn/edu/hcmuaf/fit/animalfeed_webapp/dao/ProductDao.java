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

    public List<Product> getAll() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> handle.createQuery("select * from products").mapToBean(Product.class).list());
    }

    public Product getById(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where id = :id")
                        .bind("id", id)
                        .mapToBean(Product.class).findOne().orElse(null));
    }

    // Lay product theo id danh muc
    public List<Product> getByCatId(int categoryId) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where cat_id = :categoryId")
                        .bind("categoryId", categoryId)
                        .mapToBean(Product.class).list());
    }

    //Dem so luong product trong db
    public int getTotalProduct() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select count(*) from products")
                        .mapTo(Integer.class).findOne().orElse(0));
    }

    //phan trang product
    public List<Product> getProductByPage(int page, int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where cat_id = :id order by id limit :end offset :start")
                        .bind("id", id)
                        .bind("start", (page - 1) * 16)
                        .bind("end", 16)
                        .mapToBean(Product.class).list());
    }

    //Them product
    public void insertProduct(Product product) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO products (cat_id, name, price, description, quantity, status, img, create_date) "
                        + "VALUES (:catId, :name, :price, :description, :quantity, :status, :img, :createDate)").bindBean(product).execute());
    }

    //xoa product
    public void deleteProduct(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        jdbi.useHandle(handle ->
                handle.createUpdate("DELETE FROM products WHERE id = :id")
                        .bind("id", id).execute());
    }

    //sua product
    public void updateProduct(Product product) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE products SET name = :name, price = :price, description = :description WHERE id = :id")
                        .bindBean(product).execute());
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
                WHERE p.discount_id != 1
                """;
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .mapToBean(ProductWithDiscountDTO.class).list());
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
