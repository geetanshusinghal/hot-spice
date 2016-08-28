package com.hotspice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Geetanshu on 25/08/16.
 */
@Table(name = "dish_attributes")
@Entity
public class DishAttributes extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="dish_id")
    private Dish dish;

    @Column(name="attribute_name")
    private String attributeName;

    @Column(name="attribute_value")
    private String attributeValue;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
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


