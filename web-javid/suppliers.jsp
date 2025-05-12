<%-- 
    Document   : suppliers.jsp
    Created on : May 6, 2025, 10:50:03 AM
    Author     : MMM JAVID
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Supplier Management</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        /* CSS Styles */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f7fa;
            color: #333;
        }

        .container {
            width: 90%;
            margin: 20px auto;
        }

        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        h1 {
            color: #2c3e50;
            margin: 0;
        }

        .btn {
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s;
        }

        .btn-primary {
            background-color: #3498db;
            color: white;
        }

        .btn-primary:hover {
            background-color: #2980b9;
        }

        .btn-danger {
            background-color: #e74c3c;
            color: white;
        }

        .btn-danger:hover {
            background-color: #c0392b;
        }

        .btn-success {
            background-color: #2ecc71;
            color: white;
        }

        .btn-success:hover {
            background-color: #27ae60;
        }

        .btn-warning {
            background-color: #f39c12;
            color: white;
        }

        .btn-warning:hover {
            background-color: #d35400;
        }

        .supplier-table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .supplier-table th, .supplier-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        .supplier-table th {
            background-color: #3498db;
            color: white;
            font-weight: 600;
        }

        .supplier-table tr:hover {
            background-color: #f1f9ff;
        }

        .action-buttons {
            display: flex;
            gap: 5px;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 50%;
            border-radius: 5px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.3);
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover {
            color: black;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
        }

        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-actions {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
            margin-top: 20px;
        }

        .search-container {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
        }

        .search-container input {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .search-container button {
            padding: 10px 15px;
        }

        .status-active {
            color: chartreuse;
            font-weight: 600;
        }

        .status-inactive {
            color: red;
            font-weight: 600;
        }

        .pagination {
            display: flex;
            justify-content: center;
            margin-top: 20px;
            gap: 5px;
        }

        .pagination button {
            padding: 8px 12px;
            border: 1px solid #ddd;
            background-color: white;
            cursor: pointer;
            border-radius: 4px;
        }

        .pagination button.active {
            background-color: #3498db;
            color: white;
            border-color: #3498db;
        }

        .pagination button:hover:not(.active) {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><i class="fas fa-truck"></i> Supplier Management </h1>
            <button id="addSupplierBtn" class="btn btn-primary">
                <i class="fas fa-plus"></i><a style="text-decoration: none;" href="http://localhost:8081/Supplier_Management_System1/"> Add New Supplier </a>
            </button>
        </div>

        <!--<div class="search-container">
            <input type="text" id="searchInput" placeholder="Search suppliers...">
            <button class="btn btn-primary"><i class="fas fa-search"></i> Search</button>
        </div>-->

        <table class="supplier-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Contact</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody id="supplierTableBody">
                <s:forEach var="supplier" items="${supplierList}">
                    <tr>
                        <td>${supplier.supplierId}</td>
                        <td>${supplier.name}</td>
                        <td>${supplier.company}</td>
                        <td>${supplier.email}</td>
                        <td>${supplier.phone}</td>
                        <td class="${supplier.status ? 'status-active' : 'status-inactive'}">
                            ${supplier.status ? 'Active' : 'Inactive'}
                        </td>
                        <td class="action-buttons">
                            <a style="text-decoration: none;" href="SupplierServlet?action=edit&id=${supplier.supplierId}" class="btn btn-warning">Edit</a>
                                <i class="fas fa-edit"></i> Edit
                            </a>

                            <a style="text-decoration: none;" href="SupplierServlet?action=delete&id=${supplier.supplierId}" 
                               class="btn btn-danger btn-delete"
                               onclick="return confirm('Are you sure you want to delete this supplier?');">
                                <i class="fas fa-trash"></i> Delete
                            </a>
                        </td>
                    </tr>
                </s:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>