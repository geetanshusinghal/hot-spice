package com.hotspice.objects;

import javax.persistence.Column;

/**
 * Created by Geetanshu on 28/08/16.
 */
public class DishAttributeRequest {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
