package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.OrderDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.OrderDetailsWithProduct;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.UserStats;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.OrderDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private OrderDetailDao orderDetailDao = new OrderDetailDao();
    private UserService userService = new UserService();

    // Thêm đơn hàng (cho admin)
    public int insertOrder(Order order, int adminUserId) {
        if (!userService.hasPermission(adminUserId, "ORDER_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thêm đơn hàng (yêu cầu quyền ORDER_MANAGEMENT).");
        }
        return orderDao.insertOrder(order);
    }

    // Thêm đơn hàng (cho khách hàng)
    public int insertOrder(Order order) {
        return orderDao.insertOrder(order);
    }

    // Thêm chi tiết đơn hàng (cho admin)
    public void insertOrderDetails(OrderDetail orderDetail, int adminUserId) {
        if (!userService.hasPermission(adminUserId, "ORDER_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thêm chi tiết đơn hàng (yêu cầu quyền ORDER_MANAGEMENT).");
        }
        orderDetailDao.insertOrderDetail(orderDetail);
    }

    // Thêm chi tiết đơn hàng (cho khách hàng)
    public void insertOrderDetails(OrderDetail orderDetail) {
        orderDetailDao.insertOrderDetail(orderDetail);
    }

    public ArrayList<Order> getOrdersByUserId(int userId) throws SQLException, ClassNotFoundException {
        return orderDao.getOrdersByUserId(userId);
    }

    public Order getOrderById(int orderId) throws SQLException, ClassNotFoundException {
        return orderDao.getOrderById(orderId);
    }

    public ArrayList<OrderDetailsWithProduct> getOrderDetailsWithProductByOrderId(int orderId) {
        return orderDetailDao.getOrderDetailsWithProductByOrderId(orderId);
    }

    public void updateOrderStatus(int orderId, int newStatus) {
        orderDao.updateOrderStatus(orderId, newStatus);
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public void updateShippingDate(int orderId, LocalDateTime now) {
        orderDao.updateShippingDate(orderId, now);
    }

    public List<OrderDetail> getOrderDetails(int orderId) {
        return orderDetailDao.getODsByOrderId(orderId);
    }

    public List<Order> searchOrders(String searchTerm) {
        return null;
    }

    public int getTotalOrder() {
        return orderDao.getTotalOrder();
    }

    public double getTotalRevenue() {
        return orderDao.getTotalRevenue();
    }

    public List<UserStats> getUserStats() {
        return orderDao.getUserStats();
    }

    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    public void updateOrderDetails(int orderId, List<OrderDetail> orderDetails) {
        orderDetailDao.deleteOrderDetailsByOrderId(orderId);
        for (OrderDetail detail : orderDetails) {
            orderDetailDao.insertOrderDetail(detail);
        }
    }

    public Map<String, Double> getMonthlyRevenue(int year) {
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        List<Object[]> results = orderDao.getMonthlyRevenue(year);
        for (int i = 1; i <= 12; i++) {
            monthlyRevenue.put("Tháng " + i, 0.0);
        }
        for (Object[] result : results) {
            int month = (int) result[0];
            double revenue = (double) result[1];
            monthlyRevenue.put("Tháng " + month, revenue);
        }
        return monthlyRevenue;
    }

    public Map<String, Double> getMonthlyRevenueInYear(int year, int month) {
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        List<Object[]> results = orderDao.getMonthlyRevenueInYear(year, month);
        int daysInMonth = java.time.YearMonth.of(year, month).lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            monthlyRevenue.put("Ngày " + i, 0.0);
        }
        for (Object[] result : results) {
            int day = (int) result[0];
            double revenue = (double) result[1];
            monthlyRevenue.put("Ngày " + day, revenue);
        }
        return monthlyRevenue;
    }

    public Map<String, Integer> getOrderStatusStatsInYear(int year) {
        Map<String, Integer> orderStatusStats = new LinkedHashMap<>();
        List<Object[]> results = orderDao.getOrderStatusStatsInYear(year);
        orderStatusStats.put("Đang xử lý", 0);
        orderStatusStats.put("Đã giao hàng", 0);
        orderStatusStats.put("Đã hủy", 0);
        for (Object[] result : results) {
            String status = (String) result[0];
            int count = (int) result[1];
            orderStatusStats.put(status, count);
        }
        return orderStatusStats;
    }

    public Map<String, Integer> getOrderStatusStatsInMonthYear(int month, int year) {
        Map<String, Integer> orderStatusStats = new LinkedHashMap<>();
        List<Object[]> results = orderDao.getOrderStatusStatsInMonthYear(month, year);
        orderStatusStats.put("Đang xử lý", 0);
        orderStatusStats.put("Đã giao hàng", 0);
        orderStatusStats.put("Đã hủy", 0);
        for (Object[] result : results) {
            String status = (String) result[0];
            int count = (int) result[1];
            orderStatusStats.put(status, count);
        }
        return orderStatusStats;
    }

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        List<UserStats> userStats = orderService.getUserStats();
        for (UserStats userStat : userStats) {
            System.out.println(userStat);
        }
        Map<String, Double> monthlyRevenue = orderService.getMonthlyRevenue(2023);
        System.out.println("Doanh thu theo tháng: " + monthlyRevenue);
    }
}