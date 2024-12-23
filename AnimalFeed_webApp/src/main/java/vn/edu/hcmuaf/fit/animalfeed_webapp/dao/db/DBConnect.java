package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db;

import java.sql.*;

public class DBConnect {
    static String url = "jdbc:mysql://" + DBProperties.host() + ":" + DBProperties.port() + "/" + DBProperties.dbname() + "?" + DBProperties.option();
    static Connection conn;

    public static Statement get() {
        try {
            if (conn == null || conn.isClosed()) makeConnect();
            return conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    private static void makeConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, DBProperties.username(), DBProperties.password());
    }

    public static void main(String[] args) throws SQLException {
        Statement statement =  get();
        ResultSet re = statement.executeQuery("SELECT * FROM products");

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
    }
}
