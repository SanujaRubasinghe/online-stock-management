<%-- 
    Document   : list_orders
    Created on : May 2, 2025, 7:23:20 PM
    Author     : sanuja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/admin-panel.css" >
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/order-management.css">
        <title>Orders</title>
    </head>
    <body>
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            <div class="content-container">
                <div class="content-heading">
                    <div class="content-title">ORDERS</div>
                    <a href="OrderController?action=new" class="add-btn">ADD ORDER</a>
                </div>
                <div class="content-body">
                    <table>
                        <tr>
                            <th>Order ID</th>
                            <th>Customer Name</th>
                            <th>Order Date</th>
                            <th>Order Value (Rs)</th>
                            <th>Order Status</th>
                            <th>Actions</th>
                        </tr>
                        <c:forEach var="tempOrder" items="${orderList}">
                            <tr>
                                <td>${tempOrder.getOrderId()}</td>
                                <td>${tempOrder.getCustomerName()}</td>
                                <td>${tempOrder.getOrderDate()}</td>
                                <td>${tempOrder.getTotalAmount()}</td>
                                <td>
                                    <span class="${tempOrder.getOrderStatus()}">${tempOrder.getOrderStatus()}</span>
                                </td>
                                <td>
                                    <a href="OrderController?action=view-order-items&orderid=${tempOrder.getOrderId()}" class="view-btn">View Order</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            
        </div>
        
        <script src="./js/admin-panel.js"></script>
        
    </body>
</html>
