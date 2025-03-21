package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto;

public class ProductDTO {
    private int id;
    private String name;

    public ProductDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters và setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}