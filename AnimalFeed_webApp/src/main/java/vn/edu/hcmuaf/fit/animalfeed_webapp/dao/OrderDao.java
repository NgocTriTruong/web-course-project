package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.UserStats;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Method to insert a new order
    public int insertOrder(Order order) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO orders (status, address, shipper_id, total_price, total_quantity, user_id, ship_price, order_date, ship_date, shipping_code) VALUES (:status, :address, :shipper_id, :total_price, :total_quantity, :user_id, :ship_price, :order_date, :ship_date, :shipping_code)")
                        .bind("status", order.getStatus())
                        .bind("address", order.getAddress())
                        .bind("shipper_id", order.getShipperId())
                        .bind("total_price", order.getTotalPrice())
                        .bind("total_quantity", order.getTotalQuantity())
                        .bind("user_id", order.getUserId())
                        .bind("ship_price", order.getShippingPrice())
                        .bind("order_date", order.getOrderDate())
                        .bind("ship_date", order.getShippingDate())
                        .bind("shipping_code", order.getShippingCode())
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

    // Lấy doanh thu theo tháng trong năm
    public List<Object[]> getMonthlyRevenue(int year){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT MONTH(order_date) AS month, SUM(total_price) AS total_revenue " +
                                "FROM orders WHERE YEAR(order_date) = :year " +
                                "GROUP BY MONTH(order_date)")
                        .bind("year", year)
                        .map((rs, ctx) -> new Object[]{rs.getInt("month"), rs.getDouble("total_revenue")})
                        .list()
        );
    }

    // Lấy doanh thu theo ngày trong tháng
    public List<Object[]> getMonthlyRevenueInYear(int year, int month){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT DAY(order_date) AS day, SUM(total_price) AS total_revenue " +
                                "FROM orders WHERE MONTH(order_date) = :month AND YEAR(order_date) = :year " +
                                "GROUP BY DAY(order_date)")
                        .bind("year", year)
                        .bind("month", month)
                        .map((rs, ctx) -> new Object[]{rs.getInt("day"), rs.getDouble("total_revenue")})
                        .list()
        );
    }

    //lấy thống kê trạng thái đơn hàng theo tháng
    public List<Object[]> getOrderStatusStatsInMonthYear(int month, int year) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT status, COUNT(*) AS count " +
                                "FROM orders WHERE MONTH(order_date) = :month AND YEAR(order_date) = :year " +
                                "GROUP BY status")
                        .bind("month", month)
                        .bind("year", year)
                        .map((rs, ctx) -> new Object[]{rs.getString("status"), rs.getInt("count")})
                        .list()
        );
    }

    //lấy thống kê trạng thái đơn hàng theo năm
    public List<Object[]> getOrderStatusStatsInYear(int year) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT status, COUNT(*) AS count " +
                                "FROM orders WHERE YEAR(order_date) = :year " +
                                "GROUP BY status")
                        .bind("year", year)
                        .map((rs, ctx) -> new Object[]{rs.getString("status"), rs.getInt("count")})
                        .list()
        );
    }
}