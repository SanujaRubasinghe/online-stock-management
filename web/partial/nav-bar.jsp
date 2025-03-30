<%-- 
    Document   : nav-bar
    Created on : Mar 27, 2025, 7:37:24 PM
    Author     : sanuja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <nav class="nav-bar">
        <div class="user-profile">
            <button class="user-profile-dropbtn" onclick="toggleUserDropdown()">👤<%= session.getAttribute("username") %>▾</button>
            <div class="user-dropdown" id="user-dropdown">
                <a href="#">Profile</a>
                <a href="logout"><span style="color:red">Log Out</span></a>
            </div>
        </div>
    </nav>
</html>
