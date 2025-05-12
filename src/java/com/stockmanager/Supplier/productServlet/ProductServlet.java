/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.StockManagement.productServlet;

import com.StockManagement.products.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.*;
/**
 *
 * @author MMM JAVID
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> productList = new ArrayList<>();
        
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/supplier_db", "root", "Safiya@123")) {
            String sql = "SELECT * FROM supplier_products";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Product product = new Product();
                product.setProduct_id(rs.getString("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setCategory(rs.getString("category"));
                product.setUnit_Price(rs.getDouble("Unit_Price"));
                product.setStock(rs.getInt("Stock"));
                product.setStatus(rs.getBoolean("Status"));
                productList.add(product);
            }
            
            request.setAttribute("productList", productList);
            request.getRequestDispatcher("home.jsp").forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database error", e);
        }
    }
}