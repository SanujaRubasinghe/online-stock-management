/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.StockManagement.products;
/**
 *
 * @author MMM JAVID
 */
public class Product {
    private String product_id;
    private String product_name;
    private String category;
    private double Unit_Price;
    private int Stock;
    private boolean Status;
    
    public Product(){
        
    }
    
    public Product(String product_id, String product_name, String category, double Unit_Price, int Stock, boolean Status){
        this.product_id = product_id;
        this.product_name = product_name;
        this.category = category;
        this.Unit_Price = Unit_Price;
        this.Stock = Stock;
        this.Status = Status;
    }
    
    // Getters and setters for all fields
    public String getProduct_id() { return product_id; }
    public void setProduct_id(String product_id) { this.product_id = product_id; }
    
    public String getProduct_name() { return product_name; }
    public void setProduct_name(String product_name) { this.product_name = product_name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public double getUnit_Price() { return Unit_Price; }
    public void setUnit_Price(double Unit_Price) { this.Unit_Price = Unit_Price; }
    
    public int getStock() { return Stock; }
    public void setStock(int Stock) { this.Stock = Stock; }
    
    public boolean isStatus() { return Status; }
    public void setStatus(boolean Status) { this.Status = Status; }
}