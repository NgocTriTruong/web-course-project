package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.UserStats;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Method to insert a new order
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

    // Method to get orders by user ID
    public ArrayList<Order> getOrdersByUserId(int userId) throws SQLException, ClassNotFoundException {
        return (ArrayList<Order>) jdbi.withHandle(handle -> handle.createQuery("select * from orders where user_id = :userId")
                .bind("userId", userId)
                .mapToBean(Order.class)
                .list()
        );
    }

    // Method to update order status
    public void updateOrderStatus(int id, int newStatus) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE orders SET status = :newStatus WHERE id = :id")
                        .bind("newStatus", newStatus)
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsUpdated);
    }

    // Method to delete an order by ID
    public void deleteOrder(int id) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM orders WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsDeleted);
    }

    // Method to list all orders
    public List<Order> getAllOrders() {
        return jdbi.withHandle(handle -> handle.createQuery("select * from orders").mapToBean(Order.class).list());
    }

    // Method to get an order by ID
    public Order getOrderById(int orderId) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from orders where id = :orderId")
                .bind("orderId", orderId)
                .mapToBean(Order.class)
                .findFirst()
                .orElse(null));
    }

    // Method to update shipping date
    public void updateShippingDate(int orderId, LocalDateTime now) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE orders SET ship_date = :now WHERE id = :orderId")
                        .bind("now", now)
                        .bind("orderId", orderId)
                        .execute()
        );
        System.out.println(rowsUpdated);
    }

    // Total number of orders
    public int getTotalOrder() {
        return jdbi.withHandle(handle -> handle.createQuery("select count(*) from orders")
                .mapTo(int.class)
                .one());
    }

    // Total revenue
    public int getTotalRevenue() {
        return jdbi.withHandle(handle -> handle.createQuery("select sum(total_price) from orders")
                .mapTo(int.class)
                .one());
    }

    // Get user statistics
    public List<UserStats> getUserStats() {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT u.full_name, u.phone, " +
                                        "COUNT(o.id) AS total_orders, " +
                                        "SUM(od.quantity) AS total_products_ordered, " +
                                        "SUM(o.total_price) AS total_amount_to_pay " +
                                        "FROM users u " +
                                        "JOIN orders o ON u.id = o.user_id " +
                                        "JOIN order_details od ON o.id = od.order_id " +
                                        "GROUP BY u.id, u.full_name, u.phone "
                        )
                        .mapToBean(UserStats.class)
                        .list()
        );
    }

    // Thêm phương thức updateOrder
    public void updateOrder(Order order) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("""
                        UPDATE orders
                        SET address = :address, status = :status, total_price = :totalPrice, total_quantity = :totalQuantity
                        WHERE id = :id
                        """)
                        .bind("address", order.getAddress())
                        .bind("status", order.getStatus())
                        .bind("totalPrice", order.getTotalPrice())
                        .bind("totalQuantity", order.getTotalQuantity())
                        .bind("id", order.getId())
                        .execute()
        );
        System.out.println("Rows updated: " + rowsUpdated);
    }

    public static void main(String[] args) {
        OrderDao orderDao = new OrderDao();
        List<UserStats> userStats = orderDao.getUserStats();
        for (UserStats userStat : userStats) {
            System.out.println(userStat);
        }
    }
}