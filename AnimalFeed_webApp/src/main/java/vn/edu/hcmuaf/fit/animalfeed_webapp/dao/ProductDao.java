package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Handle;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.DBConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProductDao {

    public static List<Product> getAll() {
        Handle handle = DBConnect.get().open();
        ArrayList<Product> listProduct = new ArrayList<>();

        listProduct = (ArrayList<Product>) handle.createQuery("SELECT * FROM products")
                .map((rs, ctx) -> {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setCategoryId(rs.getInt("cat_Id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));
                    product.setDescription(rs.getString("description"));
                    product.setQuantity(rs.getInt("quantity"));
                    product.setStatus(rs.getInt("status"));
                    product.setImage(rs.getString("img"));
                    product.setCreateDate(rs.getDate("create_date"));
                    return product;
                });
        return listProduct;

    }

    public static void main(String[] args) {
        getAll();
    }

//    public Product getById(int id) {
//        Statement statement = DBConnect.get();
//        ResultSet re = null;
//        try {
//            re =  statement.executeQuery("SELECT * FROM products WHERE id = " + id);
//            if (re.next()) {
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
//            return null;
//
//        } catch (SQLException e) {
//            return null;
//        }
//    }

}
