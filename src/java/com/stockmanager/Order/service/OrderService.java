/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockmanager.Order.service;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.stockmanager.Order.dao.OrderDAO;
import com.stockmanager.Order.dao.OrderDAOImpl;
import com.stockmanager.Order.model.Order;
import com.stockmanager.Order.model.OrderItem;
import java.util.List;

/**
 *
 * @author shanuka
 */
public class OrderService {
       
    private OrderDAO orderDAO;
    
    public OrderService(DataSource dataSource) {
        this.orderDAO = new OrderDAOImpl(dataSource);
    }
    
    public void createOrder(Order order) {
        orderDAO.addOrder(order);
    }
    
    public void createOrderItem(OrderItem orderItem) {
        orderDAO.addOrderItem(orderItem);
    }
    
    public void modifyOrder(Order order) {
        orderDAO.updateOrder(order);
    }
    
    public void removeOrder(int orderId) {
        orderDAO.deleteOrder(orderId);
    }
    
    public void setOrderStatus(int orderId, String orderStatus) {
        orderDAO.modifyOrderStatus(orderId, orderStatus);
    }
    
    public Order fetchOrderById(int orderId) {
        return orderDAO.getOrderbyId(orderId);
    }
    
    public List<Order> fetchAllOrders() {
        return orderDAO.getAllOrders();
    }
    
    public List<OrderItem> fetchAllOrderItems(int orderId) {
        return orderDAO.getAllOrderItems(orderId);
    }
    
    public int nextOrderId() {
        return orderDAO.getNextOrderId();
    }
}
