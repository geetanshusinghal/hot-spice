package com.hotspice.objects;

import com.hotspice.entities.Category;
import com.hotspice.entities.DishAttributes;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by Geetanshu on 28/08/16.
 */
public class DishRequest {

    private String categoryName;
    private String dishName;
    private String description;
    private Double price;
    private String unit;
    private String image;
    private List<DishAttributeRequest> attributes;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<DishAttributeRequest> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<DishAttributeRequest> attributes) {
        this.attributes = attributes;
    }
}
