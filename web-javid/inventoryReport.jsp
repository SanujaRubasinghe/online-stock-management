<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Report Generator</title>
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
            max-width: 1200px;
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
        .status-active {
            color: #28a745;
            font-weight: 600;
        }
        .status-inactive {
            color: #dc3545;
            font-weight: 600;
        }
        @media (max-width: 768px) {
            .filter-row {
                flex-direction: column;
                gap: 15px;
            }
            .filter-group {
                min-width: 100%;
            }
            table {
                display: block;
                overflow-x: auto;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Product Report Generator</h1>
        
        <form action="InventoryFilter" method="get">
            <div class="filter-section">
                <h2>Filter Products</h2>

                <div class="filter-row">
                    <div class="filter-group">
                        <label for="productId">Product ID</label>
                        <input type="text" id="productId" name="productId" placeholder="Enter product ID" 
                               value="${param.productId}">
                    </div>

                    <div class="filter-group">
                        <label for="productName">Product Name</label>
                        <input type="text" id="productName" name="productName" placeholder="Enter product name"
                               value="${param.productName}">
                    </div>
                    
                    <div class="filter-group">
                        <label for="Stock">0 To Stock</label>
                        <input type="number" id="Stock" name="Stock" placeholder="Enter stock quantity"
                               value="${param.Stock}" min="0">
                    </div>
                    
                    <div class="filter-group">
                        <label for="Unit_Price">0.0 To Unit Price</label>
                        <input type="number" id="Unit_Price" name="Unit_Price" placeholder="Enter unit price"
                               value="${param.Unit_Price}" min="0" step="0.01">
                    </div>

                    <div class="filter-group">
                        <label for="category">Category</label>
                        <select id="category" name="category">
                            <option value="">All Categories</option>
                            <option value="Food" ${param.category == 'Food' ? 'selected' : ''}>Food</option>
                            <option value="Mechanical Parts" ${param.category == 'Mechanical Parts' ? 'selected' : ''}>Mechanical Parts</option>
                            <option value="Fasteners" ${param.category == 'Fasteners' ? 'selected' : ''}>Fasteners</option>
                            <option value="Tools" ${param.category == 'Tools' ? 'selected' : ''}>Tools</option>
                            <option value="Lubricants" ${param.category == 'Lubricants' ? 'selected' : ''}>Lubricants</option>
                        </select>
                    </div>
                </div>

                <div class="filter-row">
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
                        <button type="reset" id="resetBtn" onclick="window.location.href='InventoryFilter'">Reset</button>
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
                    No products found matching your criteria.
                </div>
            <% } %>
            
            <table>
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Category</th>
                        <th>Stock</th>
                        <th>Unit Price</th>
                        <th>Added Date</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        //if (rs != null) {
                            while (rs.next()) {
                    %>
                    <tr>
                        <td><%= rs.getString("product_id") %></td>
                        <td><%= rs.getString("product_name") %></td>
                        <td><%= rs.getString("category") %></td>
                        <td><%= rs.getInt("Stock") %></td>
                        <td><%= String.format("%.2f", rs.getDouble("Unit_Price")) %></td>
                        <td><%= rs.getTimestamp("added_date") %></td>
                        <td class="<%= rs.getInt("Status") == 1 ? "status-active" : "status-inactive" %>">
                            <%= rs.getInt("Status") == 1 ? "Active" : "Inactive" %>
                        </td>
                    </tr>
                    <%
                            }
                        //}
                    %>
                </tbody>
            </table>
            
            <div class="action-buttons">
                <form id="exportForm" action="InventoryFilter" method="post" target="_blank">
                    <input type="hidden" name="productId" value="${param.productId}">
                    <input type="hidden" name="productName" value="${param.productName}">
                    <input type="hidden" name="category" value="${param.category}">
                    <input type="hidden" name="Stock" value="${param.Stock}">
                    <input type="hidden" name="Unit_Price" value="${param.Unit_Price}">
                    <input type="hidden" name="date-from" value="${param['date-from']}">
                    <input type="hidden" name="date-to" value="${param['date-to']}">
                    <button type="submit" id="exportPDF" class="export-btn">Export to PDF</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>