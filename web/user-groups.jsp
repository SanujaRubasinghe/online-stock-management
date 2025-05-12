<%-- 
    Document   : user-groups
    Created on : Mar 30, 2025, 9:20:09 AM
    Author     : sanuja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Manage User Groups</title>
    </head>
    <body>
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            
            <div class="content-container">
                <div class="content-heading">
                    <div class="content-title">GROUPS</div>
                    <a href="add-group-form.jsp" class="add-btn">ADD GROUP</a>
                </div>
                <div class="content-body">
                    <table>
                        <tr>
                            <th>Group Name</th>
                            <th>Access Level</th>
                            <th>Date Created</th>
                            <th>Actions</th>
                        </tr>
                        
                        <c:forEach var="group" items="${userGroupList}">
                            <tr>
                                <td>${group.getGroupName()}</td>
                                <td><div class="access-level ${group.getAccessLevel()}">${group.getAccessLevel()}</div></td>
                                <td>${group.getCreateDate()}</td>
                                <td><a href="delete-user-group?id=${group.getId()}" class="delete-btn">Delete</a></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            
        </div>
        
        
        <script src="./js/admin-panel.js"></script>
    </body>
</html>
