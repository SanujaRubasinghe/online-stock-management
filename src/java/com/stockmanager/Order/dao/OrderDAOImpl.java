/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.Order.dao;

import com.stockmanager.Order.model.Order;
import com.stockmanager.Order.model.OrderItem;
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
        String sql = "INSERT INTO orders (customer_name, total_amount) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, order.getCustomerName());
            stmt.setDouble(2, order.getTotalAmount());
            stmt.executeUpdate();
            
            logger.log(Level.INFO, "Order inserted: {0}", order.getCustomerName());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to insert order.", e);
        }
    }
    
    @Override
    public void addOrderItem(OrderItem orderItem) {
        String sql = "insert into order_items(orderID, product_code, product_name, quantity, unit_price, total_price) values(?,?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, orderItem.getOrderId());
            stmt.setString(2, orderItem.getProductCode());
            stmt.setString(3, orderItem.getProductName());
            stmt.setInt(4, orderItem.getQuantity());
            stmt.setDouble(5, orderItem.getUnitPrice());
            stmt.setDouble(6, orderItem.getTotalPrice());
            
            stmt.executeUpdate();
            
            logger.log(Level.INFO, "OrderItem inserted {0}", orderItem.getProductName());
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to insert order item.", e);
            e.printStackTrace();
        }
    }
    
    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE orders SET customer_name=?, order_date=?, order_status=?, total_amount=? WHERE order_id=?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, order.getCustomerName());
            stmt.setString(2, order.getOrderDate());
            stmt.setString(3, order.getOrderStatus());
            stmt.setDouble(4, order.getTotalAmount());
            stmt.setInt(5, order.getOrderId());
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
                String orderStatus = result.getString("order_status");
                double totalAmount = result.getDouble("total_amount");
                
                order = new Order(orderID, customerName, orderDate, orderStatus, totalAmount);
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
                String orderStatus = results.getString("order_status");
                double totalAmount = results.getDouble("total_amount");
                
                Order order = new Order(orderId, customerName, orderDate, orderStatus, totalAmount);
                
                orders.add(order);
            }
            
            logger.log(Level.INFO, "All orders fetched");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching all orders", e);
        }
        
        return orders;
    }
    
    @Override
    public int getNextOrderId() {
        String sql = "select * from orders order by order_id desc limit 1";
        int nextOrderId = 0;
        try (Connection conn = dataSource.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            
            if (result.next()) {
                nextOrderId = result.getInt("order_id");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching last order id", e);
        }
        
        return nextOrderId;
    }
    
    @Override
    public List<OrderItem> getAllOrderItems(int orderId) {
        String sql = "select * from order_items where orderID=?";
        List<OrderItem> list = new ArrayList<>();
        
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            
            ResultSet results = stmt.executeQuery();
            
            while (results.next()) {
                OrderItem orderItem = new OrderItem(
                        results.getInt("itemID"),
                        results.getInt("orderID"),
                        results.getString("product_code"),
                        results.getString("product_name"),
                        results.getInt("quantity"),
                        results.getDouble("unit_price"),
                        results.getDouble("total_price")
                );
                
                list.add(orderItem);
            }
            
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching order items.", e);
        }
        
        return list;
    }
    
    @Override
    public void modifyOrderStatus(int orderId, String orderStatus) {
        String sql = "update orders set order_status=? where order_id=?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, orderStatus);
            stmt.setInt(2, orderId);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            
        }
    }
}
