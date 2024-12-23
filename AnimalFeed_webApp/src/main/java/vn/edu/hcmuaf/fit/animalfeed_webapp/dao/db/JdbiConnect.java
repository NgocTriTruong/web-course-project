package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.sql.SQLException;
import java.util.List;

public class JdbiConnect {
    static String url = "jdbc:mysql://" + DBProperties.host() + ":" + DBProperties.port() + "/" + DBProperties.dbname() + "?" + DBProperties.option();

    static Jdbi jdbi;

    public static Jdbi getJdbi() {
        if (jdbi == null) makeConnect();
        return jdbi;
    }

    private static void makeConnect() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(url);
        ds.setUser(DBProperties.username());
        ds.setPassword(DBProperties.password());

        try {
            ds.setUseCompression(true);
            ds.setAutoReconnect(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Sử dụng ds (MysqlDataSource) để tạo đối tượng Jdbi
        jdbi = Jdbi.create(ds);
    }

//    public static void main(String[] args) {
//        Jdbi jdbi = getJdbi();
//        List<Product>products = jdbi.withHandle(handle -> {
//            return handle.createQuery("select * from products").mapToBean(Product.class).list();
//        });
//        System.out.println(products);
//    }
}
