package com.example.restaurantguide.jobs;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {

    private String name ;
    private String speciality ;
    private String description ;
    private String phone_number ;
    private double locationV;
    private double locationV1;
    ArrayList<Food> menu = new ArrayList<Food>();

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public double getLocationV1() {
        return locationV1;
    }

    public void setLocationV1(double locationV1) {
        this.locationV1 = locationV1;
    }

    public double getLocationV() {
        return locationV;
    }

    public void setLocationV(double locationV) {
        this.locationV = locationV;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Food> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<Food> menu) {
        this.menu = menu;
    }

    public Restaurant(String name, String speciality, String description,String phone, ArrayList<Food> menu,double locationV,double locationV1) {
        this.name = name;
        this.speciality = speciality;
        this.description = description;
        this.phone_number = phone;
        this.menu = menu;
        this.locationV = locationV;
        this.locationV1 = locationV1;
    }
}
