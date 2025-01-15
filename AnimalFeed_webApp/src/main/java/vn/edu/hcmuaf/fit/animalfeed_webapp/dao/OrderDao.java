package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;

import java.sql.SQLException;
import java.util.*;

public class OrderDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Method to insert a new user
    public Order getUserById(int id) throws SQLException, ClassNotFoundException {
        return jdbi.withHandle(handle -> handle.createQuery("select * from orders where id = :id").bind("id", id).mapToBean(Order.class).findOne().orElse(null));
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
    public void updateOrderStatus(int status, int id) {
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
}
