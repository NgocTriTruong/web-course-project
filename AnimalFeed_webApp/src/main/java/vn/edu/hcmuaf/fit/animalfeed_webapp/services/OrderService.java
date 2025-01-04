package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.OrderDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.OrderDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;

public class OrderService {
    static OrderDao orderDao = new OrderDao();
    static OrderDetailDao orderDetailDao = new OrderDetailDao();

    public void insertOrder(Order order) {
        orderDao.insertOrder(order);
    }

    public void insertOrderDetail(OrderDetail orderDetail) {
        orderDetailDao.insertOrderDetail(orderDetail);
    }
}
