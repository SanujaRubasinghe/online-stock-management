/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.stockmanager.Order.dao;

import com.stockmanager.Order.model.Order;
import java.util.List;
        

/**
 *
 * @author sanuja
 */
public interface OrderDAO {
    void addOrder(Order order);
    void updateOrder(Order order);
    void deleteOrder(int orderId);
    Order getOrderbyId(int orderId);
    List<Order> getAllOrders();
}
