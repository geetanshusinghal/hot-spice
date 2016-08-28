package com.hotspice.objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by Geetanshu on 26/08/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryRequest {

    private long id;
    private String categoryName;
    private String description;
    private List<Attribute> attributes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}