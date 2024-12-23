package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.DBConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ProductDao {

    public List<Product> getAll() {
        Statement statement = DBConnect.get();
        ResultSet re = null;
        ArrayList<Product> listProduct = new ArrayList<>();

        try {
            re =  statement.executeQuery("SELECT * FROM products");
            while (re.next()) {
                System.out.println(re.getInt("id")
                        + "; " + re.getInt("cat_Id")
                        + "; " + re.getString("name")
                        + "; " + re.getDouble("price")
                        + "; " + re.getString("description")
                        + "; " + re.getInt("quantity")
                        + ";"  + re.getInt("status")
                        + "; " + re.getString("img")
                        + "; " + re.getDate("create_date"));
            }
            return listProduct;

        } catch (SQLException e) {
            return listProduct;
        }
    }

    public Product getById(int id) {
        Statement statement = DBConnect.get();
        ResultSet re = null;
        try {
            re =  statement.executeQuery("SELECT * FROM products WHERE id = " + id);
            if (re.next()) {
                System.out.println(re.getInt("id")
                        + "; " + re.getInt("cat_Id")
                        + "; " + re.getString("name")
                        + "; " + re.getDouble("price")
                        + "; " + re.getString("description")
                        + "; " + re.getInt("quantity")
                        + ";"  + re.getInt("status")
                        + "; " + re.getString("img")
                        + "; " + re.getDate("create_date"));
            }
            return null;

        } catch (SQLException e) {
            return null;
        }
    }

}
