package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Order implements Serializable {
    private int id;
    private int status;
    private String address;
    private int shipperId;
    private double totalPrice;
    private int totalQuantity;
    private int userId;
    private String customerName; // Added field for customer name
    private double shippingPrice;
    private LocalDateTime orderDate;
    private Date shippingDate;
    private String shippingCode;

    public Order() {}

    public Order(int id, int status, String address, int shipperId, double totalPrice, int totalQuantity, int userId, String customerName, double shippingPrice, LocalDateTime orderDate, Date shippingDate) {
        this.id = id;
        this.status = status;
        this.address = address;
        this.shipperId = shipperId;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.userId = userId;
        this.customerName = customerName;
        this.shippingPrice = shippingPrice;
        this.orderDate = orderDate;
        this.shippingDate = shippingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(double shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(String shippingCode) {
        this.shippingCode = shippingCode;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", status=" + status +
                ", address='" + address + '\'' +
                ", shipperId=" + shipperId +
                ", totalPrice=" + totalPrice +
                ", totalQuantity=" + totalQuantity +
                ", userId=" + userId +
                ", customerName='" + customerName + '\'' +
                ", shippingPrice=" + shippingPrice +
                ", orderDate=" + orderDate +
                ", shippingDate=" + shippingDate +
                ", shippingCode='" + shippingCode + '\'' +
                '}';
    }
}