package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Product implements Serializable {
    private int id;
    private int cat_id;
    private String name;
    private double price;
    private String description;
    private int quantity;
    private int status;
    private String img;
    private LocalDate createDate;
    private int discountId;
    private String brandName;

    public Product() {
    }

    public Product(int id, int cat_id, String name, double price, String description, int quantity, int status, String img, LocalDate createDate, int discountId, String brandName) {
        this.id = id;
        this.cat_id = cat_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
        this.img = img;
        this.createDate = createDate;
        this.discountId = discountId;
        this.brandName = brandName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", cat_id=" + cat_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                ", img='" + img + '\'' +
                ", createDate=" + createDate +
                ", discountId=" + discountId +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
