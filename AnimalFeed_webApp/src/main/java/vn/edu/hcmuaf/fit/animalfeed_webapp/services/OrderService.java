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

    public int insertOrder(Order order) {
        return orderDao.insertOrder(order);
    }

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

    // Tổng đơn đặt hàng
    public int getTotalOrder() {
        return orderDao.getTotalOrder();
    }

    // Tổng doanh thu
    public double getTotalRevenue() {
        return orderDao.getTotalRevenue();
    }

    // Định nghĩa một phương thức để lấy thông tin người dùng và thống kê
    public List<UserStats> getUserStats() {
        return orderDao.getUserStats();
    }

    // Cập nhật thông tin đơn hàng
    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    // Cập nhật danh sách chi tiết đơn hàng (xóa cũ và thêm mới)
    public void updateOrderDetails(int orderId, List<OrderDetail> orderDetails) {
        // Xóa các chi tiết đơn hàng cũ
        orderDetailDao.deleteOrderDetailsByOrderId(orderId);

        // Thêm các chi tiết đơn hàng mới
        for (OrderDetail detail : orderDetails) {
            orderDetailDao.insertOrderDetail(detail);
        }
    }

    // Lấy thống kê doanh thu theo tháng trong năm
    public Map<String, Double> getMonthlyRevenue(int year){
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        // Giả sử bạn có DAO để lấy dữ liệu từ database
        List<Object[]> results = orderDao.getMonthlyRevenue(year);

        // Khởi tạo tất cả các tháng với doanh thu = 0
        for (int i = 1; i <= 12; i++) {
            monthlyRevenue.put("Tháng " + i, 0.0);
        }

        // Cập nhật doanh thu thực tế
        for (Object[] result : results) {
            int month = (int) result[0];
            double revenue = (double) result[1];
            monthlyRevenue.put("Tháng " + month, revenue);
        }

        return monthlyRevenue;
    }

    // Lấy thống kê doanh thu theo ngày trong tháng
    public Map<String, Double> getMonthlyRevenueInYear(int year, int month){
        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
        // Giả sử bạn có DAO để lấy dữ liệu từ database
        List<Object[]> results = orderDao.getMonthlyRevenueInYear(year, month);
        // Khởi tạo tất cả các ngày trong tháng với doanh thu = 0
        int daysInMonth = java.time.YearMonth.of(year, month).lengthOfMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            monthlyRevenue.put("Ngày " + i, 0.0);
        }
        // Cập nhật doanh thu thực tế
        for (Object[] result : results) {
            int day = (int) result[0];
            double revenue = (double) result[1];
            monthlyRevenue.put("Ngày " + day, revenue);
        }
        return monthlyRevenue;
    }

    // Lấy thống kê trạng thái đơn hàng trong năm
    public Map<String, Integer> getOrderStatusStatsInYear(int year) {
        Map<String, Integer> orderStatusStats = new LinkedHashMap<>();
        // Giả sử bạn có DAO để lấy dữ liệu từ database
        List<Object[]> results = orderDao.getOrderStatusStatsInYear(year);
        // Khởi tạo tất cả các trạng thái với số lượng = 0
        orderStatusStats.put("Đang xử lý", 0);
        orderStatusStats.put("Đã giao hàng", 0);
        orderStatusStats.put("Đã hủy", 0);
        // Cập nhật số lượng thực tế
        for (Object[] result : results) {
            String status = (String) result[0];
            int count = (int) result[1];
            orderStatusStats.put(status, count);
        }
        return orderStatusStats;
    }

    // Lấy thống kê trạng thái đơn hàng trong tháng
    public Map<String, Integer> getOrderStatusStatsInMonthYear(int month, int year) {
        Map<String, Integer> orderStatusStats = new LinkedHashMap<>();
        // Giả sử bạn có DAO để lấy dữ liệu từ database
        List<Object[]> results = orderDao.getOrderStatusStatsInMonthYear(month, year);
        // Khởi tạo tất cả các trạng thái với số lượng = 0
        orderStatusStats.put("Đang xử lý", 0);
        orderStatusStats.put("Đã giao hàng", 0);
        orderStatusStats.put("Đã hủy", 0);
        // Cập nhật số lượng thực tế
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

        // Lấy thống kê doanh thu theo tháng
        Map<String, Double> monthlyRevenue = orderService.getMonthlyRevenue(2023);
        System.out.println("Doanh thu theo tháng: " + monthlyRevenue);

    }
}