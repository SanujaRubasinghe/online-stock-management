<%-- 
    Document   : new_order
    Created on : May 2, 2025, 7:17:29 PM
    Author     : Shanuka
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
        <title>Add Order Page</title>
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/admin-panel.css" >
        <script src="./js/new-order.js"></script>
    </head>
    <body>
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            
            <h2>Place New Order</h2>
                <form action="order" method="post">
                    <input type="text" name="customer" placeholder="Customer Name" required />
                    <input type="date" name="date" required />
                    <div id="product-container"></div>
                    <button type="button" onclick="addProductRow()">Add Product</button><br><br>
                    <button type="submit">Place Order</button>
                </form>     
        </div>
            
        <script src="./js/admin-panel.js"></script>
    </body>
</html>
