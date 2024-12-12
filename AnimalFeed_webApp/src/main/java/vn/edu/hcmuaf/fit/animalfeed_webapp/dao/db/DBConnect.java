package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {
    static String url = "jdbc:mysql://" + DBProperties.host() + ":" + DBProperties.port() + "/" + DBProperties.dbname() + "?" + DBProperties.option();
    static Connection conn;

    public static Statement get() {
        try {
            if (conn == null || conn.isClosed())  makeConnect();
            return conn.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
           return null;
        }
    }

    private static void makeConnect() throws ClassNotFoundException, SQLException {
        Class.forName("conn.mysql.cj.jdbc.Driver");
        conn= DriverManager.getConnection(url, DBProperties.username(), DBProperties.password());
    }
}
