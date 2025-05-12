package com.StockManagement.InventoryServlet;

import com.StockManagement.products.Product;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "InventoryServlet", urlPatterns = {"/InventoryServlet"})
public class InventoryServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/supplier_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "Safiya@123";
    
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        
        try {
            if (action != null) {
                switch (action) {
                    case "edit":
                        showEditForm(id, request, response);
                        break;
                    case "delete":
                        deleteProduct(id, response);
                        break;
                    case "add":
                        addProduct(request,response);
                        request.getRequestDispatcher("addProduct.jsp").forward(request, response);
                        break;
                    default:
                        listProducts(request, response);
                }
            } else {
                listProducts(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if (action != null && action.equals("update")) {
                updateProduct(request, response);
            } else if (action != null && action.equals("add")) {
                addProduct(request, response);
            } else {
                listProducts(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        List<Product> productList = new ArrayList<>();
        String searchQuery = request.getParameter("search");
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql;
            PreparedStatement pstmt;
            
            if (searchQuery != null && !searchQuery.isEmpty()) {
                sql = "SELECT * FROM supplier_products WHERE product_name LIKE ? OR category LIKE ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, "%" + searchQuery + "%");
                pstmt.setString(2, "%" + searchQuery + "%");
            } else {
                sql = "SELECT * FROM supplier_products";
                pstmt = conn.prepareStatement(sql);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
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
            }
            
            request.setAttribute("productList", productList);
            request.getRequestDispatcher("inventory.jsp").forward(request, response);
        }
    }

    private void showEditForm(String productId, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "SELECT * FROM supplier_products WHERE product_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, productId);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Product product = new Product();
                        product.setProduct_id(rs.getString("product_id"));
                        product.setProduct_name(rs.getString("product_name"));
                        product.setCategory(rs.getString("category"));
                        product.setUnit_Price(rs.getDouble("Unit_Price"));
                        product.setStock(rs.getInt("Stock"));
                        product.setStatus(rs.getBoolean("Status"));

                        request.setAttribute("product", product);
                        request.getRequestDispatcher("editProduct.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("InventoryServlet?error=Product+not+found");
                    }
                }
            }
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        boolean status = stock > 0;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "UPDATE supplier_products SET product_name=?, category=?, Unit_Price=?, Stock=?, Status=? WHERE product_id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, category);
                pstmt.setDouble(3, price);
                pstmt.setInt(4, stock);
                pstmt.setBoolean(5, status);
                pstmt.setString(6, id);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    response.sendRedirect("InventoryServlet?success=Product+updated+successfully");
                } else {
                    response.sendRedirect("InventoryServlet?error=Failed+to+update+product");
                }
            }
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        boolean status = stock > 0;

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "INSERT INTO supplier_products (product_name, category, Unit_Price, Stock, Status) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, name);
                pstmt.setString(2, category);
                pstmt.setDouble(3, price);
                pstmt.setInt(4, stock);
                pstmt.setBoolean(5, status);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    response.sendRedirect("InventoryServlet?success=Product+added+successfully");
                } else {
                    response.sendRedirect("InventoryServlet?error=Failed+to+add+product");
                }
            }
        }
    }

    private void deleteProduct(String productId, HttpServletResponse response) 
            throws ServletException, IOException, SQLException {
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS)) {
            String sql = "DELETE FROM supplier_products WHERE product_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, productId);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    response.sendRedirect("InventoryServlet?success=Product+deleted+successfully");
                } else {
                    response.sendRedirect("InventoryServlet?error=Failed+to+delete+product");
                }
            }
        }
    }
}