package com.example.midterm.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Order {
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Product category name should not be empty")
    private String category;
    @NotEmpty(message = "Manufacturer name should not be empty")
    private String manufacturer;
    @NotEmpty(message = "Volume of product should not be empty")
    private String volume;
    @Min(value = 0, message = "Price should be greater than 0")
    private int price;
    private int quantity;

    public Order(){

    }

    public Order(int id, String name, String category, String manufacturer, String volume, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.manufacturer = manufacturer;
        this.volume = volume;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public String getManufacturer() {
        return manufacturer;
    }
    public String getVolume() {
        return volume;
    }
    public int getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    public void setVolume(String volume) {
        this.volume = volume;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
