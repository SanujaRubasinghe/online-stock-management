<%-- 
    Document   : editSupplier
    Created on : May 6, 2025, 7:24:46 PM
    Author     : MMM JAVID
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Supplier</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f6f8;
                margin: 0;
                padding: 0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }

            form {
                background-color: #ffffff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                width: 100%;
                max-width: 400px;
            }

            h2 {
                text-align: center;
                margin-bottom: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }

            input[type="text"],
            input[type="email"],
            input[type="number"] {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 6px;
                box-sizing: border-box;
                font-size: 14px;
            }

            input[type="submit"] {
                width: 100%;
                background-color: #4CAF50;
                color: white;
                padding: 12px;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
                transition: background-color 0.3s ease;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
            }
        </style>
    </head>
    <body>
        <form action="SupplierServlet?action=update" method="post">
            <input type="hidden" name="sid" value="${supplier.supplierId}" placeholder="${supplier.supplierId}"/>
            Name: <input type="text" name="name" value="${supplier.name}" placeholder="${supplier.name}"/><br/>
            Email: <input type="email" name="email" value="${supplier.email}" placeholder="${supplier.email}"/><br/>
            Phone: <input type="text" name="phone" value="${supplier.phone}" placeholder="${supplier.phone}"/><br/>
            Company: <input type="text" name="company" value="${supplier.company}" placeholder="${supplier.company}"/><br/>
            Category: <input type="text" name="category" value="${supplier.category}" placeholder="${supplier.category}"/><br/>
            Quantity: <input type="number" name="quantity" value="${supplier.quantity}" placeholder="${supplier.quantity}"/><br/>
            <input type="submit" value="Update Supplier" />
        </form>
    </body>
</html>
