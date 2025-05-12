<%-- 
    Document   : test
    Created on : Mar 22, 2025, 7:19:24 AM
    Author     : sanuja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Role</th>
            </tr>
            
            <c:forEach var="tempUser" items="${test_list}">
                <tr>
                    <td>${tempUser.id}</td>
                    <td>${tempUser.firstName}</td>
                    <td>${tempUser.lastName}</td>
                    <td>${tempUser.role}</td>
                </tr>
            </c:forEach>
        </table>
        <a href="add-user-form.html">Add new User</a>
    </body>
</html>
