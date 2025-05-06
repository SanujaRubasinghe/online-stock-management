/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.stockmanager.Order.controller;

import com.stockmanager.Order.model.Order;
import com.stockmanager.Order.service.OrderService;
import java.util.List;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 *
 * @author sanuja
 */
public class OrderController extends HttpServlet {

    @Resource(name="jdbc/inventorydb")
    private DataSource dataSource;
    
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.orderService = new OrderService(dataSource);
    }
    
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) action = "list";
        
        switch (action) {
            case "new":
                request.getRequestDispatcher("/new_order.jsp").forward(request, response);
                break;
            case "edit":
                int id = Integer.parseInt(request.getParameter("id"));
                Order existingOrder = orderService.fetchOrderById(id);
                request.setAttribute("order", existingOrder);
                request.getRequestDispatcher("/edit_order.jsp").forward(request, response);
                break;
            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                orderService.removeOrder(deleteId);
                response.sendRedirect("OrderController");
                break;
            default:
                List<Order> orderList = orderService.fetchAllOrders();
                request.setAttribute("orderList", orderList);
                request.getRequestDispatcher("/list_orders.jsp").forward(request, response);
                break;
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
       
        int id = Integer.parseInt(request.getParameter("orderId"));
        String customerName = request.getParameter("customerName");
        String orderDate = request.getParameter("orderDate");
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
        
        Order order = new Order(id, customerName, orderDate, totalAmount);
        
        switch (action) {
            case "add-order":
                orderService.createOrder(order);
                break;
            case "update-order":
                orderService.modifyOrder(order);
            default:
                response.sendRedirect("OrderController");
        }       
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
