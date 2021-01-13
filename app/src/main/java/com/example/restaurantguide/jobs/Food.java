package com.example.restaurantguide.jobs;

import java.io.Serializable;

public class Food implements Serializable {
    private String name ;
    private double price;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Food(String name,String description, double price) {
        this.name = name;
        this.description=description;
        this.price = price;
    }
}
