<%-- 
    Document   : side-bar
    Created on : Mar 27, 2025, 6:01:59 PM
    Author     : sanuja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="side-bar">
    <div class="menu">
        <div class="menu-heading">Inventory System</div>
        <div class="item"><a href="DashboardController">Dashboard</a></div>
        <div class="item">
            <a class="sub-btn">User Management<span class="dropdown"></span></a>
            <div class="sub-menu">
                <a href="show-users">Manage Users</a>
                <a href="user-groups">Manage Groups</a>
            </div>
        </div>
        <div class="item"><a href="ProductController">Product Management</a></div>
        <div class="item">
            <a href="OrderController">Order Management</a>
        </div>
        <div class="item"><a href="#">Supplier Management</a></div>

    </div>
</div>

