package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.DBConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
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


    public static void main(String[] args) {

    }

}
