<%-- 
    Document   : add-group-form
    Created on : Mar 30, 2025, 12:41:44 PM
    Author     : sanuja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <link rel="stylesheet" href="./css/admin-panel/add-form.css" type="text/css">
        <title>Add Group</title>
    </head>
    <body>
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            
            <div class="content-container">
                <div class="content-heading">
                    <div class="content-title">ADD USER GROUP</div>
                </div>
                <div class="content-body">
                    <form action="add-group" method="post" class="add-form">
                        <label for="group-name">Group Name</label>
                        <input type="text" name="group-name" id="group-name" placeholder="Group Name" required>
                        <label for="access-levle">Access Level</label>
                        <select name="access-level">
                            <option>High</option>
                            <option>Medium</option>
                            <option>Low</option>
                        </select>
                        <input type="submit" value="Submit">
                    </form>
                </div>
            </div>
            
        </div>
        
        
        <script src="./js/admin-panel.js"></script>
    </body>
</html>
