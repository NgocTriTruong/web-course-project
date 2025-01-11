package vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model;

import java.io.Serializable;

public class ProductDetail implements Serializable {
    private int id;
    private int productId;
    private String detail_description;
    private String nutrition;
    private String ingredient;
    private String usage;

    public ProductDetail() {}

    public ProductDetail(int id, int productId, String detail_description, String nutrition, String ingredient, String usage) {
        this.id = id;
        this.productId = productId;
        this.detail_description = detail_description;
        this.nutrition = nutrition;
        this.ingredient = ingredient;
        this.usage = usage;
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

    public String getDetail_description() {
        return detail_description;
    }

    public void setDetail_description(String detail_description) {
        this.detail_description = detail_description;
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

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Override
    public String toString() {
        return "ProductDetail{" +
                "id=" + id +
                ", productId=" + productId +
                ", detail_description='" + detail_description + '\'' +
                ", nutrition='" + nutrition + '\'' +
                ", ingredient='" + ingredient + '\'' +
                ", usage='" + usage + '\'' +
                '}';
    }
}
