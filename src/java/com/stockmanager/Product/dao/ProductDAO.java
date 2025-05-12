/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.Product.dao;

import com.stockmanager.Product.model.Product;
import java.util.List;
        

/**
 *
 * @author sanuja
 */
public interface ProductDAO {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(String productId);
    int getProductCount();
    Product getProduct(String productId);
    List<Product> getAllProducts(); 
}
