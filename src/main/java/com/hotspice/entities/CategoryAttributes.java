package com.hotspice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Geetanshu on 25/08/16.
 */
@Table(name = "category_attributes")
@Entity
public class CategoryAttributes extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @Column(name = "attribute_name")
    private String attributeName;

    @Column(name = "description")
    private String description;

    @Column(name = "is_mandatory")
    private boolean isMandatory;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "validation")
    private String validation;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
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
