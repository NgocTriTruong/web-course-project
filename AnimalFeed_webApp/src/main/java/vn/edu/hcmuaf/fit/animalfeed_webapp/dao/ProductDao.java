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
//        Statement statement = DBConnect.get();
//        ResultSet re = null;
//        ArrayList<Product> listProduct = new ArrayList<>();
//
//        try {
//            re =  statement.executeQuery("SELECT * FROM products");
//            while (re.next()) {
//                System.out.println(re.getInt("id")
//                        + "; " + re.getInt("cat_Id")
//                        + "; " + re.getString("name")
//                        + "; " + re.getDouble("price")
//                        + "; " + re.getString("description")
//                        + "; " + re.getInt("quantity")
//                        + ";"  + re.getInt("status")
//                        + "; " + re.getString("img")
//                        + "; " + re.getDate("create_date"));
//            }
//            return listProduct;
//
//        } catch (SQLException e) {
//            return listProduct;
//        }
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->  handle.createQuery("select * from products").mapToBean(Product.class).list());
    }

    public Product getById(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where id = :id")
                        .bind("id", id)
                        .mapToBean(Product.class).findOne().orElse(null));
    }

    //Dem so luong product tỏng db
    public int getTotalProduct(){
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select count(*) from products")
                        .mapTo(Integer.class).findOne().orElse(0));
    }

    //phan trang theo category
    public List<Product> getProductByPage(int page, int id){
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from products where cat_id = :id order by id limit :end offset :start")
                        .bind("id", id)
                        .bind("start", (page-1)*16)
                        .bind("end", 16)
                        .mapToBean(Product.class).list());
    }

    public static void main(String[] args) {
//        ProductDao productDao = new ProductDao();
//        List<Product> products = productDao.getProductByPage(1, 1);
//        for (Product product : products) {
//            System.out.println(product);
//        }

        ProductDao productDao = new ProductDao();
        List<Product> products = productDao.getAll();
        for (Product product : products) {
            System.out.println(product);
        }

    }

}
