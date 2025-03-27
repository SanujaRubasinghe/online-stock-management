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

        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content"><h1>Admin Panel</h1></div>
        
        <script src="./js/admin-panel.js"></script>
        
    </body>
</html>
