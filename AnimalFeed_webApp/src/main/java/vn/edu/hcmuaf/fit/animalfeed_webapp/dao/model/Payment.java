package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Payment implements Serializable {
    private int id;
    private LocalDateTime payDate;
    private String method;
    private int orderId;
    private int userId;
    private int status;

    public Payment() {}

    public Payment(int id, LocalDateTime payDate, String method, int orderId, int userId, int status) {
        this.id = id;
        this.payDate = payDate;
        this.method = method;
        this.orderId = orderId;
        this.userId = userId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", payDate=" + payDate +
                ", method=" + method +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", status=" + status +
                '}';
    }
}
