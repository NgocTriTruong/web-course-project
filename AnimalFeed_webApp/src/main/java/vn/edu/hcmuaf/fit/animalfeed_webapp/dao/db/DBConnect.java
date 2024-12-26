package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db;

import org.jdbi.v3.core.Jdbi;

import java.sql.*;

public class DBConnect {
    static Jdbi jdbi = Jdbi.create("jdbc:mysql://" + DBProperties.host() + ":" + DBProperties.port() + "/" + DBProperties.dbname() + "?" + DBProperties.option(), DBProperties.username(), DBProperties.password());
    static Connection conn;

    public static Jdbi get() {
        return jdbi;
    }
}
