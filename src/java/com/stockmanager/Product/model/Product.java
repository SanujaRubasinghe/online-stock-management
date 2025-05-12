/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.Product.model;

/**
 *
 * @author sanuja
 */
public class Product {
    private String productCode;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private int stockLowLimit;
    
    public Product() {}

    public Product(String productCode, String name, String description, double price, int quantity, int stockLowLimit) {
        this.productCode = productCode;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.stockLowLimit = stockLowLimit;
    }

    public int getStockLowLimit() {
        return stockLowLimit;
    }

    public void setStockLowLimit(int stockLowLimit) {
        this.stockLowLimit = stockLowLimit;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
