<!DOCTYPE html>
<!--
Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Html.html to edit this template
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("index.jsp");
        return;
    }
%>
<html>
    <head>
        <title>Add User</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/admin-panel.css" >
        <link rel="stylesheet" href="./css/admin-panel/add-form.css" type="text/css">
    </head>
    <body>
        
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            
            <div class="content-container">
                <div class="content-heading">
                    <div class="content-title">ADD USER</div>
                </div>
                <div class="content-body">
                    
                        <form action="add-user" method="post" class="add-form">
                            <label>First Name</label>
                            <input type="text" name="firstName" placeholder="First Name" required />
                            <label>Last Name</label>
                            <input type="text" name="lastName" placeholder="Last Name" required />
                            <label>User Name</label>
                            <input type="text" name="userName" placeholder="User Name" required />
                            <label>Password</label>
                            <input type="password" name="password" placeholder="Password" required />
                            <label>User Group</label>
                            <select name="role">
                                <c:forEach var="group" items="${userGroupList}">
                                    <option>${group.getGroupName()}</option>
                                </c:forEach>
                            </select>
                            <input type="submit" value="Submit">
                        </form>
                </div>
            </div>
        </div>
        
        <script src="./js/admin-panel.js"></script>
    </body>
</html>
