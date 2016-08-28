package com.hotspice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Geetanshu on 25/08/16.
 */
@Table(name = "dish")
@Entity
public class Dish extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "unit")
    private String unit;

    @Column(name = "image")
    private String image;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="dish", fetch=FetchType.EAGER)
    private List<DishAttributes> dishAttributes;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public List<DishAttributes> getDishAttributes() {
        return dishAttributes;
    }

    public void setDishAttributes(List<DishAttributes> dishAttributes) {
        this.dishAttributes = dishAttributes;
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

    @PrePersist
    void prePersist() {
        this.setCreatedDate(new Date());
        this.setUpdatedDate(new Date());
    }

    @PreUpdate
    void preUpdate() {
        this.setUpdatedDate(new Date());
    }
}
