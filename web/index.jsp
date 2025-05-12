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
        <link rel="stylesheet" href="./css/login-page.css"/>
        <title>Login Page</title>
    </head>
    <body>
        <div class="login-form-container">
            <div class="heading">Inventory System</div>
            <form action="LoginServlet" method="post">
                <input type="text" name="username" placeholder="User Name" required><br>
                <input type="password" name="password" placeholder="Password" required><br>
                <input type="submit" value="Log In">            
            </form>
        </div>
        
    </body>
</html>
