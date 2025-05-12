<%-- 
    Document   : edit-product
    Created on : May 6, 2025, 9:34:33 AM
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
<html>
    <head>
        <title>Update Product</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="./css/admin-panel/add-form.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="./css/admin-panel/admin-panel.css" >
    </head>
    <body>
        
        <%@include file="./partial/side-bar.jsp" %>
        
        <div id="content">
            <%@include file="./partial/nav-bar.jsp" %>
            
            <div class="content-container">
                <div class="content-heading">
                    <div class="content-title">UPDATE PRODUCT</div>
                </div>
                <div class="content-body">
                    
                        <form action="ProductController?action=update" method="post" class="add-form">
                            <label>Product Code</label>
                            <input 
                                type="text" 
                                name="product-code" 
                                placeholder="Product Code"
                                value="${product.getProductCode()}"
                                required />
                            <label>Product Name</label>
                            <input 
                                type="text" 
                                name="name" 
                                placeholder="Product Name"
                                value="${product.getName()}"
                                required />
                            <label>Description</label>
                            <input 
                                type="text" 
                                name="desc" 
                                placeholder="Description"
                                value="${product.getDescription()}"
                                required />
                            <label>Price</label>
                            <input 
                                type="number" 
                                name="price" 
                                placeholder="Price" 
                                min="0" 
                                value="${product.getPrice()}"
                                required />
                            <label>Quantity</label>
                            <input 
                                type="number" 
                                name="quantity" 
                                placeholder="Quantity" 
                                min="0" 
                                value="${product.getQuantity()}"
                                required />
                            <label>Stock Low Alert Threshold</label>
                            <input 
                                type="number" 
                                name="stock-low-limit" 
                                placeholder="Stock Low Alert Threshold" 
                                min="0" 
                                value="${product.getStockLowLimit()}"
                                required />
                            <input type="submit" value="Submit">
                        </form>
                </div>
            </div>
        </div>
        
        <script src="./js/admin-panel.js"></script>
    </body>
</html>