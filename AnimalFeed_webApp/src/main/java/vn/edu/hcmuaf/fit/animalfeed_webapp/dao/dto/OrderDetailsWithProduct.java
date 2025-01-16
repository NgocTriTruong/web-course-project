package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

public class OrderDetailsWithProduct {
    private OrderDetail orderDetail;
    private Product product;

    public OrderDetailsWithProduct(OrderDetail orderDetail, Product product) {
        this.orderDetail = orderDetail;
        this.product = product;
    }


    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
