package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class OrderDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Method to insert a new user
    public ArrayList<Order> getOrdersByUserId(int userId) throws SQLException, ClassNotFoundException {
        return (ArrayList<Order>) jdbi.withHandle(handle -> handle.createQuery("select * from orders where user_id = :userId")
                .bind("userId", userId)
                .mapToBean(Order.class)
                .list()
        );
    }

    public int insertOrder(Order order) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO orders (status, address, shipper_id, total_price, total_quantity, user_id, ship_price, order_date, ship_date) VALUES (:status, :address, :shipper_id, :total_price, :total_quantity, :user_id, :ship_price, :order_date, :ship_date)")
                        .bind("status", order.getStatus())
                        .bind("address", order.getAddress())
                        .bind("shipper_id", order.getShipperId())
                        .bind("total_price", order.getTotalPrice())
                        .bind("total_quantity", order.getTotalQuantity())
                        .bind("user_id", order.getUserId())
                        .bind("ship_price", order.getShippingPrice())
                        .bind("order_date", order.getOrderDate())
                        .bind("ship_date", order.getShippingDate())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }

    // Method to update user information
    public void updateOrderStatus(int id, int newStatus) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE orders SET status = :newStatus WHERE id = :id")
                        .bind("newStatus", newStatus)
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsUpdated);
    }

    // Method to delete a user by username
    public void deleteOrder(int id) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM orders WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsDeleted);
    }

    // Method to list all users
    public ArrayList<Order> getAllOrders() {
        return (ArrayList<Order>) jdbi.withHandle(handle -> handle.createQuery("select * from orders").mapToBean(Order.class).list());
    }

    public Order getOrderById(int orderId) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from orders where id = :orderId")
                .bind("orderId", orderId)
                .mapToBean(Order.class)
                .findFirst()  // This returns an Optional<Order>
                .orElse(null));  // This returns null if no order is found
    }

    public void updateShippingDate(int orderId, LocalDateTime now) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE orders SET ship_date = :now WHERE id = :orderId")
                        .bind("now", now)
                        .bind("orderId", orderId)
                        .execute()
        );
        System.out.println(rowsUpdated);
    }
}
