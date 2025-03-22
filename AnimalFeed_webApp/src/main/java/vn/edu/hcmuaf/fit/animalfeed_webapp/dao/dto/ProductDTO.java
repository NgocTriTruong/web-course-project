package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto;

public class ProductDTO {
    private int id;
    private String name;
    private double price; // Thêm trường price

    public ProductDTO(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters và setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}