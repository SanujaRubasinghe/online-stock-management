<%-- 
    Document   : admin-panel.jsp
    Created on : Mar 24, 2025, 10:33:47 AM
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
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/dashboard.css" >
        <title>Dashboard</title>
    </head>
    <body>
        <%@ include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@ include file="./partial/nav-bar.jsp" %>

            <!-- Dashboard content -->
            <div class="dashboard-container">
                 <div class="dashboard-header">
            <h1>Welcome to the Dashboard</h1>
            <p>Manage your users, products, orders, and suppliers from here</p>
        </div>
        
        <!-- Dashboard Cards -->
        <div class="dashboard-cards">
            
            <!-- Users Card -->
            <div class="dashboard-card users-card">
                <div class="card-header">Users</div>
                <div class="card-body">
                    <p>3</p> <!-- This is the number, will animate using JS -->
                </div>
                <a href="show-users" class="view-more-btn">View More</a>
            </div>

            <!-- Products Card -->
            <div class="dashboard-card products-card">
                <div class="card-header">Products</div>
                <div class="card-body">
                    <p>${totalProducts}</p> <!-- This is the number, will animate using JS -->
                </div>
                <a href="ProductController" class="view-more-btn">View More</a>
            </div>

            <!-- Orders Card -->
            <div class="dashboard-card orders-card">
                <div class="card-header">Orders</div>
                <div class="card-body">
                    <p>3</p> <!-- This is the number, will animate using JS -->
                </div>
                <a href="OrderController" class="view-more-btn">View More</a>
            </div>

            <!-- Suppliers Card -->
            <div class="dashboard-card suppliers-card">
                <div class="card-header">Suppliers</div>
                <div class="card-body">
                    <p>30</p> <!-- This is the number, will animate using JS -->
                </div>
                <a href="#" class="view-more-btn">View More</a>
            </div>
            
        </div>
            </div>
        </div>

        <script src="./js/admin-panel.js"></script>
        <script src="./js/dashboard.js"></script>
    </body>
</html>
