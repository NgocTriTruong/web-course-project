package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;

public class CartDetail implements Serializable {
    private int id;
    private int userId;
    private int productId;
    private double total;
    private int quantity;
    private int status;

    public CartDetail() {}

    public CartDetail(int id, int userId, int productId, double total, int quantity, int status) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.total = total;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CartDetail{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", total=" + total +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
