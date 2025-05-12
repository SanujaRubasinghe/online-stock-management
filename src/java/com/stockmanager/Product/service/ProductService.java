/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.Product.service;

import com.stockmanager.Product.model.Product;
import com.stockmanager.Product.dao.ProductDAO;
import com.stockmanager.Product.dao.ProductDAOImpl;
import java.util.List;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 *
 * @author sanuja
 */
public class ProductService {
        
    private ProductDAO productDAO;
    
    public ProductService(DataSource dataSource) {
        this.productDAO = new ProductDAOImpl(dataSource);
    }
    
    public void save(Product p) {
        productDAO.addProduct(p);
    }
    
    public void update(Product p) {
        productDAO.updateProduct(p);
    }
    
    public void delete(String productCode) {
        productDAO.deleteProduct(productCode);
    }
    
    public int getCount() {
        return productDAO.getProductCount();
    }
    
    public Product getById(String productCode) {
        return productDAO.getProduct(productCode);
    }
    
    public List<Product> getAll() {
        return productDAO.getAllProducts();
    }
}
