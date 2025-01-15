package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto;

public class ProductWithDiscountDTO {
    private int id;
    private String img;
    private String name;
    private String description;
    private double price;
    private int percentage; // Tỷ lệ giảm giá
    private double discountedPrice; // Giá sau giảm
    private int daysLeft; // Số ngày còn lại của sản phẩm
    private int cat_id;

    public ProductWithDiscountDTO() {
    }

    public ProductWithDiscountDTO(int id, String img, String name, String description, double price, int percentage, double discountedPrice, int daysLeft, int cat_id) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.description = description;
        this.price = price;
        this.percentage = percentage;
        this.discountedPrice = discountedPrice;
        this.daysLeft = daysLeft;
        this.cat_id = cat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    @Override
    public String toString() {
        return "ProductWithDiscountDTO{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", percentage=" + percentage +
                ", discountedPrice=" + discountedPrice +
                ", daysLeft=" + daysLeft +
                ", cat_id=" + cat_id +
                '}';
    }
}
