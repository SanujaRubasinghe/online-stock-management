<%-- 
    Document   : loginForm
    Created on : Mar 24, 2025, 10:21:59 AM
    Author     : sanuja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>
        
        <form action="LoginServlet" method="post">
            User Name: <input type="text" name="username" placeholder="User Name" required><br>
            Password: <input type="password" name="password" placeholder="Password" required><br>
            <input type="submit" value="Submit">            
        </form>
        
    </body>
</html>
