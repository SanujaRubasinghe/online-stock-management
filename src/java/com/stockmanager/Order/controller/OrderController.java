/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.stockmanager.Order.controller;

import com.stockmanager.Order.model.Order;
import com.stockmanager.Order.model.OrderItem;
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
            case "view-order-items":
                int orderId = Integer.parseInt(request.getParameter("orderid"));
                List<OrderItem> orderItemList = orderService.fetchAllOrderItems(orderId);
                request.setAttribute("orderItemList", orderItemList);
                request.setAttribute("orderId", orderId);
                request.getRequestDispatcher("/order-items.jsp").forward(request, response);
                break;
            case "cancel-order":
                orderId = Integer.parseInt(request.getParameter("orderid"));
                orderService.setOrderStatus(orderId, "cancelled");
                response.sendRedirect("OrderController");
                break;
            case "complete-order":
                orderId = Integer.parseInt(request.getParameter("orderid"));
                orderService.setOrderStatus(orderId, "delivered");
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
       
        String customer = request.getParameter("customer");
        String orderStatus = request.getParameter("orderStatus");
        
        String[] productCodes = request.getParameterValues("productCode");
        String[] productNames = request.getParameterValues("productName");
        String[] productPrices = request.getParameterValues("price");
        String[] productQuants = request.getParameterValues("quantity");
        
        double totalOrderValue = 0.0;
        double totalOrderItemValue;
        
        
        switch (action) {
            case "add-order":
                for (int i = 0; i < productCodes.length; i++) {
                    totalOrderValue += Double.parseDouble(productPrices[i]) * Double.parseDouble(productQuants[i]);
                }
                orderService.createOrder(new Order(
                        customer, orderStatus, totalOrderValue
                ));
                int orderId = orderService.nextOrderId();
                for (int i=0; i < productCodes.length; i++) {
                    totalOrderItemValue = Double.parseDouble(productQuants[i]) * Double.parseDouble(productPrices[i]);
                    
                    orderService.createOrderItem(new OrderItem(
                            orderId,
                            productCodes[i],
                            productNames[i],
                            Integer.parseInt(productQuants[i]),
                            Double.parseDouble(productPrices[i]),
                            totalOrderItemValue        
                    ));
                }
               
                response.sendRedirect("OrderController");
                break;
            case "update-order":
                break;
            default:
                response.sendRedirect("OrderController");
        }       
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
