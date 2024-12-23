package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;

import java.sql.SQLException;
import java.util.*;

public class OrderDao {
    // Method to insert a new user
    public Order getUserById(int id) throws SQLException, ClassNotFoundException {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> handle.createQuery("select * from orders where id = :id").bind("id", id).mapToBean(Order.class).findOne().orElse(null));
    }

    // Method to update user information
    public void updateOrderStatus(int status, int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE orders SET status = :status WHERE id = :id")
                        .bind("status", status)
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsUpdated);
    }

    // Method to delete a user by username
    public void deleteOrder(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM orders WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsDeleted);
    }

    // Method to list all users
    public ArrayList<Order> getAllOrders() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return (ArrayList<Order>) jdbi.withHandle(handle -> handle.createQuery("select * from orders").mapToBean(Order.class).list());
    }
}
