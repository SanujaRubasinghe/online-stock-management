<%-- 
    Document   : order-items
    Created on : May 7, 2025, 1:13:21 PM
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
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/order-items.css" >

        <title>Dashboard</title>
    </head>
    <body>
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            
            <div class="content-container">
                <div class="content-heading">
                    <div class="content-title">ORDER #${orderId}</div>
                </div>
                <div class="content-body">
                    <div class="order-item-list">
                        <table>
                            <tr>
                                <th>Product Code</th>
                                <th>Product Name</th>
                                <th>Unit Price (Rs)</th>
                                <th>Quantity</th>
                            </tr>
                            <c:forEach var="temp" items="${orderItemList}">
                                <tr>
                                    <td>${temp.getProductCode()}</td>
                                    <td>${temp.getProductName()}</td>
                                    <td>${temp.getUnitPrice()}</td>
                                    <td>${temp.getQuantity()}</td>                                
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="order-summary">
                        <c:forEach var="temp" items="${orderItemList}">
                            <div class="order-item">
                                <span>${temp.getProductName()} x ${temp.getQuantity()}</span>
                                <span>Rs.${temp.getTotalPrice()}</span>
                            </div>
                        </c:forEach>
                        <div class="order-action">
                            <a href="OrderController?action=cancel-order&orderid=${orderId}" class="cancel-btn">Cancel</a>
                            <a href="OrderController?action=complete-order&orderid=${orderId}" class="deliver-btn">Delivered</a>
                        </div>
                    </div>
                </div>
                
            </div>
            
        </div>
        
        <script src="./js/admin-panel.js"></script>
        
    </body>
</html>
