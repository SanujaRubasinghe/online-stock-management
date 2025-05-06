/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.Order.dao;

import com.stockmanager.Order.model.Order;
import com.stockmanager.util.LoggerUtil;

import java.sql.*;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sanuja
 */
public class OrderDAOImpl implements OrderDAO {
    private static final Logger logger = LoggerUtil.getLogger(OrderDAOImpl.class);
    private DataSource dataSource;
    
    public OrderDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (customer_name, order_date, total_amount) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, order.getCustomerName());
            stmt.setString(2, order.getOrderDate());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.executeUpdate();
            
            logger.log(Level.INFO, "Order inserted: {0}", order.getCustomerName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to insert order.", e);
        }
    }
    
    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET customer_name=?, order_date=?, total_amount=? WHERE order_id=?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, order.getCustomerName());
            stmt.setString(2, order.getOrderDate());
            stmt.setDouble(3, order.getTotalAmount());
            stmt.setInt(4, order.getOrderId());
            stmt.executeUpdate();
            
            logger.log(Level.INFO, "Order updated: ID {0}", order.getOrderId());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error updating the order.",e);
        }
    }
    
    @Override
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id=?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, orderId);
            stmt.executeUpdate();
            
            logger.log(Level.INFO, "Order deleted ID {0}", orderId);
            
        } catch(SQLException e) {
            logger.log(Level.SEVERE, "Order deletion failed.",e);
        }
    }
    
    @Override
    public Order getOrderbyId(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM order WHERE order_id=?";
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            ResultSet result = stmt.executeQuery();
            
            if (result.next()) {
                int orderID = result.getInt("order_id");
                String customerName = result.getString("customer_name");
                String orderDate = result.getString("order_date");
                double totalAmount = result.getDouble("total_amount");
                
                order = new Order(orderID, customerName, orderDate, totalAmount);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to fetch order by id:",e);
        }
        
        return order;
    }
    
    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery(sql);
            
            while (results.next()) {
                int orderId = results.getInt("order_id");
                String customerName = results.getString("customer_name");
                String orderDate = results.getString("order_date");
                double totalAmount = results.getDouble("total_amount");
                
                Order order = new Order(orderId, customerName, orderDate, totalAmount);
                
                orders.add(order);
            }
            
            logger.log(Level.INFO, "All orders fetched");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all orders", e);
        }
        
        return orders;
    }
}
