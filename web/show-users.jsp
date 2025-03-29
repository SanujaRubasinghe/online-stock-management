<%-- 
    Document   : show-users
    Created on : Mar 27, 2025, 4:02:08 PM
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
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/user-management.css" >
        <title>User Management</title>
    </head>
    <body>
        
        <%@ include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            
            <div class="content-container">
                <div class="content-heading">
                    <div class="content-title">USERS</div>
                    <a href="add-user-form.html" class="add-user-btn">ADD USER</a>
                </div>
                <div class="content-body">
                    <table>
                <tr>
                    <th>Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>User Name</th>
                    <th>Role</th>
                    <th>Last Login</th>
                    <th>Actions</th>
                </tr>
                <c:forEach var="tempUser" items="${userList}">
                    <tr>
                        <td>${tempUser.getId()}</td>
                        <td>${tempUser.getFirstName()}</td>
                        <td>${tempUser.getLastName()}</td>
                        <td>${tempUser.getUserName()}</td>
                        <td>${tempUser.getRole()}</td>
                        <td>${tempUser.getLoginDate()}</td>
                        <td><a href="#">Update</a> | <a href="delete-user?id=${tempUser.getId()}"+>Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
                </div>
            </div>
            
        </div>
        <script src="./js/admin-panel.js"></script>
    </body>
</html>
