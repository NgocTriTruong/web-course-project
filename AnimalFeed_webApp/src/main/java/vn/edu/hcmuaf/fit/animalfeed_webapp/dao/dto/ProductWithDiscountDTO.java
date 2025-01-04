package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto;

public class ProductWithDiscountDTO {
    private int id;
    private String name;
    private String description;
    private double price;
    private double discountedPrice; // Giá sau giảm
    private double percentage; // Tỷ lệ giảm giá
    private int daysLeft; // Số ngày còn lại của sản phẩm
    private String img;

    public ProductWithDiscountDTO(int id, String name, String description, double price, double discountedPrice, double percentage, int daysLeft, String img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discountedPrice = discountedPrice;
        this.percentage = percentage;
        this.daysLeft = daysLeft;
        this.img = img;
    }

    public ProductWithDiscountDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "ProductWithDiscountDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                ", percentage=" + percentage +
                ", daysLeft=" + daysLeft +
                ", img='" + img + '\'' +
                '}';
    }
}
