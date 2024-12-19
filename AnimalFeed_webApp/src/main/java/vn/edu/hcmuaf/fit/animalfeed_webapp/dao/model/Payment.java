package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {
    private int id;
    private Date payDate;
    private int method;
    private int orderId;
    private int userId;

    public Payment() {}

    public Payment(int id, Date payDate, int method, int orderId, int userId) {
        this.id = id;
        this.payDate = payDate;
        this.method = method;
        this.orderId = orderId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
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

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", payDate=" + payDate +
                ", method=" + method +
                ", orderId=" + orderId +
                ", userId=" + userId +
                '}';
    }
}
