package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.OrderDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.OrderDetailsWithProduct;
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
}
