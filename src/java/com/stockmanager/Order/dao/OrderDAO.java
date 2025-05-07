/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.stockmanager.Order.dao;

import com.stockmanager.Order.model.Order;
import com.stockmanager.Order.model.OrderItem;
import java.util.List;
        

/**
 *
 * @author sanuja
 */
public interface OrderDAO {
    void addOrder(Order order);
    void addOrderItem(OrderItem orderItem);
    void updateOrder(Order order);
    void deleteOrder(int orderId);
    void modifyOrderStatus(int orderId, String orderStatus);
    int getNextOrderId();
    Order getOrderbyId(int orderId);
    List<Order> getAllOrders();
    List<OrderItem> getAllOrderItems(int orderId);
}
