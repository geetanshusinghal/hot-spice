package com.hotspice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Geetanshu on 25/08/16.
 */
@Table(name = "category")
@Entity
public class Category extends BaseEntity {

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, mappedBy="category", fetch=FetchType.EAGER)
    private List<CategoryAttributes> categoryAttributes;


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CategoryAttributes> getCategoryAttributes() {
        return categoryAttributes;
    }

    public void setCategoryAttributes(List<CategoryAttributes> categoryAttributes) {
        this.categoryAttributes = categoryAttributes;
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
