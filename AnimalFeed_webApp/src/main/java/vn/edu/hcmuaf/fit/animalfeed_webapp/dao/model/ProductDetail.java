package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;

public class ProductDetail implements Serializable {
    private int id;
    private int productId;
    private String description;
    private String nutrition;
    private String ingredient;
    private String instruction;

    public ProductDetail() {}

    public ProductDetail(int id, int productId, String description, String nutrition, String ingredient, String instruction) {
        this.id = id;
        this.productId = productId;
        this.description = description;
        this.nutrition = nutrition;
        this.ingredient = ingredient;
        this.instruction = instruction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", productId=" + productId +
                ", description='" + description + '\'' +
                ", nutrition='" + nutrition + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
