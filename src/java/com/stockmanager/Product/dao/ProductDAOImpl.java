/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.Product.dao;

import com.stockmanager.Product.model.Product;
import com.stockmanager.util.LoggerUtil;
import java.sql.*;
import java.util.*;
import javax.sql.DataSource;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sanuja
 */
public class ProductDAOImpl implements ProductDAO {
    private static final Logger logger = LoggerUtil.getLogger(ProductDAOImpl.class);
    private DataSource dataSource;
    
    public ProductDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override 
    public void addProduct(Product product) {
        String sql = "insert into product(product_code, name, description, price, quantity_in_stock) values(?,?,?,?,?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getPrice());
            stmt.setInt(5, product.getQuantity());
            
            stmt.executeUpdate();
            logger.log(Level.INFO, "Product Inserted: ID {0}", product.getProductCode());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error inserting the Product.",e);
        }
    }
    
    @Override
    public void updateProduct(Product p) {
        String sql = "update product set name=?, description=?, price=?, quantity_in_stock=? where product_code=?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getDescription());
            stmt.setDouble(3, p.getPrice());
            stmt.setInt(4, p.getQuantity());
            stmt.setString(5, p.getProductCode());
            
            stmt.executeUpdate();
            
            logger.log(Level.INFO, "Product updated: ID {0}", p.getProductCode());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating the product.", e);
        }
    }
    
    @Override
    public void deleteProduct(String productCode) {
        String sql = "delete from product where product_code = ?";
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, productCode);
            
            stmt.executeUpdate();
            
            logger.log(Level.INFO, "Product deleted: ID {0}", productCode);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting the product.", e);
        }
    }
    
    @Override
    public Product getProduct(String productCode) {
        String sql = "select * from product where product_code=?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, productCode);
            
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                return new Product(
                        result.getString("product_code"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getDouble("price"),
                        result.getInt("quantity_in_stock")
                );
            }
                
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error retrieving product data.", e);
        }
        
        return null;
    }
    
    @Override
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from product";
        
        try(Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            
            ResultSet results = stmt.executeQuery(sql);
            
            while (results.next()) {
                productList.add(new Product(
                        results.getString("product_code"),
                        results.getString("name"),
                        results.getString("description"),
                        results.getDouble("price"),
                        results.getInt("quantity_in_stock")   
                ));
            }
                      
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all products,", e);
        }
        
        return productList;
    }
}
