package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.OrderDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.OrderDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;

public class OrderService {
    private OrderDao orderDao = new OrderDao();
    private OrderDetailDao orderDetailDao = new OrderDetailDao();

    public void insertOrder(Order order) {
        orderDao.insertOrder(order);
    }

    public void insertOrderDetails(OrderDetail orderDetail) {
        orderDetailDao.insertOrderDetail(orderDetail);
    }
}
