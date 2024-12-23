package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.util.Date;

public class Product {
    private int id, catId, quantity, discountId;
    private String name, description, status, img, brandName;
    private double price;
    private Date createDate;

    public Product() {}

    public Product(int id, int catId, String name, double price, String description, int quantity, String status, String img, Date createDate,String brandName) {
        this.id = id;
        this.catId = catId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.status = status;
        this.img = img;
        this.createDate = createDate;
        this.brandName = brandName;
    }
}
