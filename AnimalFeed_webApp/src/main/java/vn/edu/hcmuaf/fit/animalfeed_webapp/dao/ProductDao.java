package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.DBConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDao {

    public List<Product> getProducts() {
        Statement statement = DBConnect.get();
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<>();

        try {
            resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                // Extract data from the ResultSet and create Product objects
                int id = resultSet.getInt("id");
                int catId = resultSet.getInt("cat_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                String description = resultSet.getString("description");
                int quantity = resultSet.getInt("quantity");
                String status = resultSet.getString("status");
                String img = resultSet.getString("img");
                Date createDate = resultSet.getDate("create_date");
                String brandName = resultSet.getString("brand_name");

                // Add product to the list
                products.add(new Product(id, catId, name, price, description, quantity, status, img, createDate, brandName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
