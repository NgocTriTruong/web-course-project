package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.DiscountDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

public class CartItem {
    private int id;
    private int userId;
    private int productId;
    private double total;
    private int quantity;
    private int status;
    private String name;
    private String img;
    private double price;        // Original price
    private double unitPrice;
    private String desc;
    private Product product;     // Store reference to product for discount info


    // Constructor
    public CartItem(CartDetail cartDetail, Product product) {
        this.id = cartDetail.getId();
        this.userId = cartDetail.getUserId();
        this.productId = cartDetail.getProductId();
        this.total = cartDetail.getTotal();
        this.quantity = cartDetail.getQuantity();
        this.status = cartDetail.getStatus();
        this.name = product.getName();
        this.img = product.getImg();
        this.price = product.getPrice();
        this.desc = product.getDescription();
        this.product = product;  // Store product reference
        if (product.getDiscountId() > 1) { // 1 is no discount
            DiscountDao discountDao = new DiscountDao();
            this.unitPrice = discountDao.calculateDiscountedPrice(price, product.getDiscountId());
        } else {
            this.unitPrice = price;
        }
    }

    public CartItem() {

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
        return this.unitPrice * this.quantity;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDesc() { return desc; }

    public void setDesc(String desc) { this.desc = desc; }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", total=" + total +
                ", quantity=" + quantity +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                '}';
    }
}