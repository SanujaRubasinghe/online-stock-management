<%-- 
    Document   : products
    Created on : May 6, 2025, 9:27:25 AM
    Author     : sanuja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="true" %>
<%@page import="javax.servlet.http.HttpSession" %>
<%@page import="com.stockmanager.User.controller.SessionListener" %>
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
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/product-management.css">
        <title>Products</title>
    </head>
    <body>
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            
            <div class="content-container">
                <div class="content-heading">
                    <div class="content-title">PRODUCTS</div>
                    <a href="ProductController?action=add-product" class="add-btn">ADD PRODUCT</a>
                </div>
                <div class="content-body">
                    <table>
                <tr>
                    <th>Product Code</th>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="tempProduct" items="${products}">
                    <tr>
                        <td>${tempProduct.getProductCode()}</td>
                        <td>${tempProduct.getName()}</td>
                        <td>${tempProduct.getDescription()}</td>
                        <td>${tempProduct.getPrice()}</td>
                        <td>${tempProduct.getQuantity()}</td>
                        <td>
                            <a href="ProductController?action=edit-product&id=${tempProduct.getProductCode()}" class="update-btn">Update</a> | 
                            <a href="ProductController?action=delete-product&id=${tempProduct.getProductCode()}" class="delete-btn">Delete</a>
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
