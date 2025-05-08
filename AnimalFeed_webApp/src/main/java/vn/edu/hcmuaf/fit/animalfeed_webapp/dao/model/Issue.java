package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Issue implements Serializable {
    private int id;
    private int userId;
    private int productId;
    private String reason;
    private int quantity;
    private int status;
    private LocalDateTime createDate;

    public Issue() {}

    public Issue(int id, int userId, int productId, String reason, int quantity, int status, LocalDateTime createDate) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.reason = reason;
        this.quantity = quantity;
        this.status = status;
        this.createDate = createDate;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", reason='" + reason + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                ", createDate=" + createDate +
                '}';
    }
}
