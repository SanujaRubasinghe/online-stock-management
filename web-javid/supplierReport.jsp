<%-- 
    Document   : supplierReport
    Created on : May 10, 2025, 11:23:18 PM
    Author     : MMM JAVID
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Supplier Management System</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f7fa;
            color: #333;
            line-height: 1.6;
        }
        .container {
            max-width: 1400px;
            margin: 0 auto;
            background-color: #fff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 25px;
            padding-bottom: 10px;
            border-bottom: 2px solid #ecf0f1;
        }
        h2 {
            color: #3498db;
            margin-top: 0;
        }
        .filter-section {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 6px;
            margin-bottom: 25px;
            border: 1px solid #e1e5eb;
        }
        .filter-row {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-bottom: 15px;
        }
        .filter-group {
            flex: 1;
            min-width: 200px;
        }
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #495057;
        }
        input, select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 14px;
        }
        input:focus, select:focus {
            border-color: #80bdff;
            outline: 0;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
        }
        button, .export-btn {
            background-color: #3498db;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            margin-right: 10px;
            transition: background-color 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }
        button:hover, .export-btn:hover {
            background-color: #2980b9;
        }
        #resetBtn {
            background-color: #6c757d;
        }
        #resetBtn:hover {
            background-color: #5a6268;
        }
        #exportPDF, .export-btn {
            background-color: #28a745;
        }
        #exportPDF:hover, .export-btn:hover {
            background-color: #218838;
        }
        #results {
            margin-top: 30px;
            overflow-x: auto;
        }
        #resultCount {
            margin-bottom: 15px;
            font-style: italic;
            color: #6c757d;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            font-size: 14px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
            min-width: 1200px;
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #e1e5eb;
        }
        th {
            background-color: #3498db;
            color: white;
            font-weight: 600;
            position: sticky;
            top: 0;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #e9ecef;
        }
        .action-buttons {
            margin-top: 25px;
            display: flex;
            justify-content: flex-end;
        }
        .price-cell {
            text-align: right;
        }
        .quantity-cell {
            text-align: center;
        }
        @media (max-width: 768px) {
            .filter-row {
                flex-direction: column;
                gap: 15px;
            }
            .filter-group {
                min-width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Supplier Management System</h1>
        
        <form action="SupplierFilter" method="get">
            <div class="filter-section">
                <h2>Filter Suppliers</h2>

                <div class="filter-row">
                    <div class="filter-group">
                        <label for="name">Name</label>
                        <input type="text" id="name" name="name" placeholder="Enter supplier name" 
                               value="${param.name}">
                    </div>

                    <div class="filter-group">
                        <label for="email">Email</label>
                        <input type="text" id="email" name="email" placeholder="Enter email"
                               value="${param.email}">
                    </div>
                    
                    <div class="filter-group">
                        <label for="companyName">Company Name</label>
                        <input type="text" id="companyName" name="companyName" placeholder="Enter company name"
                               value="${param.companyName}">
                    </div>
                    
                    <div class="filter-group">
                        <label for="category">Category</label>
                        <select id="category" name="category">
                            <option value="">All Categories</option>
                            <option value="Manufacturer" ${param.category == 'Manufacturer' ? 'selected' : ''}>Manufacturer</option>
                            <option value="Distributor" ${param.category == 'Distributor' ? 'selected' : ''}>Distributor</option>
                            <option value="Wholesaler" ${param.category == 'Wholesaler' ? 'selected' : ''}>Wholesaler</option>
                            <option value="Retailer" ${param.category == 'Retailer' ? 'selected' : ''}>Retailer</option>
                            <option value="Service Provider" ${param.category == 'Service Provider' ? 'selected' : ''}>Service Provider</option>
                        </select>
                    </div>
                </div>

                <div class="filter-row">
                    <div class="filter-group">
                        <label for="quantity">0 To Quantity</label>
                        <input type="number" id="quantity" name="quantity" placeholder="Enter quantity"
                               value="${param.quantity}" min="0">
                    </div>
                    
                    <div class="filter-group">
                        <label for="price">0.0 To Price</label>
                        <input type="number" id="price" name="price" placeholder="Enter price"
                               value="${param.price}" min="0" step="0.01">
                    </div>

                    <div class="filter-group">
                        <label for="dateFrom">Date From</label>
                        <input type="date" id="dateFrom" name="date-from" value="${param['date-from']}">
                    </div>

                    <div class="filter-group">
                        <label for="dateTo">Date To</label>
                        <input type="date" id="dateTo" name="date-to" value="${param['date-to']}">
                    </div>

                    <div class="filter-group" style="align-self: flex-end;">
                        <button type="submit" id="filterBtn">Apply Filters</button>
                        <button type="button" id="resetBtn" onclick="window.location.href='SupplierFilter'">Reset</button>
                    </div>
                </div>
            </div>
        </form>
        
        <div id="results">
            <h2>Filtered Results</h2>
            
            <% 
                ResultSet rs = (ResultSet) request.getAttribute("results");
                if (rs != null && !rs.isBeforeFirst()) {
            %>
                <div class="no-results" style="color: #dc3545; font-weight: 600; margin-bottom: 20px;">
                    No suppliers found matching your criteria.
                </div>
            <% } %>
            
            <table>
                <thead>
                    <tr>
                        <th>Supplier ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Company</th>
                        <th>Category</th>
                        <th>Address</th>
                        <th>Quantity</th>
                        <th>Price</th>
                        <th>Last Modified</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        if (rs != null) {
                            while (rs.next()) {
                    %>
                    <tr>
                        <td><%= rs.getString("sid") %></td>
                        <td><%= rs.getString("name") %></td>
                        <td><%= rs.getString("email") %></td>
                        <td><%= rs.getString("phone") %></td>
                        <td><%= rs.getString("company_name") %></td>
                        <td><%= rs.getString("category") %></td>
                        <td><%= rs.getString("address") %></td>
                        <td class="quantity-cell"><%= rs.getInt("Quantity") %></td>
                        <td class="price-cell"><%= String.format("%.2f", rs.getDouble("Price")) %></td>
                        <td><%= rs.getTimestamp("last_modified_time") %></td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
            
            <div class="action-buttons">
                <form id="exportForm" action="SupplierFilter" method="post" target="_blank">
                    <input type="hidden" name="name" value="${param.name}">
                    <input type="hidden" name="email" value="${param.email}">
                    <input type="hidden" name="companyName" value="${param.companyName}">
                    <input type="hidden" name="category" value="${param.category}">
                    <input type="hidden" name="quantity" value="${param.quantity}">
                    <input type="hidden" name="price" value="${param.price}">
                    <input type="hidden" name="date-from" value="${param['date-from']}">
                    <input type="hidden" name="date-to" value="${param['date-to']}">
                    <button type="submit" id="exportPDF" class="export-btn">Export to PDF</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
