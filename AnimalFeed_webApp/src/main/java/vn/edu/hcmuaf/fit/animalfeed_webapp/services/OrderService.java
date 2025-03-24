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
import java.util.List;

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

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        List<Order> orders = orderService.getAllOrders();
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}