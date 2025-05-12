package com.StockManagement.supplier_d;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;

@WebServlet("/delete")
public class Supplier_d extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/supplier_db";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Safiya@123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String sid = request.getParameter("supplierid");
        String from_date = request.getParameter("from-date");
        String to_date = request.getParameter("to-date");
        
        String message = "";
        int deletedCount = 0;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
                
                if (sid != null && !sid.trim().isEmpty()) {
                    deletedCount += deleteBySupplierId(connection, sid);
                }
                
                if (from_date != null && to_date != null && 
                    !from_date.trim().isEmpty() && !to_date.trim().isEmpty()) {
                    deletedCount += deleteByDateRange(connection, from_date, to_date);
                }
                
                if (deletedCount > 0) {
                    message = "Successfully deleted " + deletedCount + " record(s).";
                } else {
                    message = "No records found matching your criteria.";
                }
                
            } catch (SQLException e) {
                message = "Database error: " + e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                message = "Error: " + e.getMessage();
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            message = "JDBC Driver not found.";
            e.printStackTrace();
        }
        
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/supplierDelete.jsp");
        dispatcher.forward(request, response);
    }
    
    private int deleteBySupplierId(Connection connection, String sid) throws SQLException {
        String sql = "DELETE FROM supplier_c WHERE sid = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, sid);
            return pstmt.executeUpdate();
        }
    }
    
    private int deleteByDateRange(Connection connection, String fromDateStr, String toDateStr) 
            throws SQLException, java.text.ParseException {
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fromDate = formatter.parse(fromDateStr);
        java.util.Date toDate = formatter.parse(toDateStr);
        
        String sql = "DELETE FROM supplier_c WHERE last_modified_time BETWEEN ? AND ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setTimestamp(1, new Timestamp(fromDate.getTime()));
            pstmt.setTimestamp(2, new Timestamp(toDate.getTime()));
            return pstmt.executeUpdate();
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("supplierDelete.jsp");
    }
}