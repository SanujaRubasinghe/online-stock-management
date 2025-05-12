<%-- 
    Document   : home
    Created on : Apr 22, 2025, 8:58:46 AM
    Author     : MMM JAVID
--%>
<%-- Add this at the top of your JSP --%>
<%-- Corrected taglib directive --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Supplier Management System</title>
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2980b9;
            --accent-color: #e74c3c;
            --light-gray: #f5f5f5;
            --dark-gray: #333;
            --border-color: #ddd;
        }
        
        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        body {
            background-color: #f9f9f9;
            color: var(--dark-gray);
            line-height: 1.6;
        }
        
        .container {
            display: flex;
            min-height: 100vh;
        }
        
        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            color: white;
            padding: 20px 0;
        }
        
        .logo {
            padding: 0 20px 20px;
            border-bottom: 1px solid rgba(255,255,255,0.1);
            margin-bottom: 20px;
        }
        
        .logo h1 {
            font-size: 1.5rem;
        }
        
        .nav-menu li a {
            color: inherit;
            text-decoration: none;
        }
        
        .nav-item {
            padding: 12px 20px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        
        .nav-item:hover {
            background-color: rgba(255,255,255,0.1);
        }
        
        .nav-item.active {
            background-color: var(--primary-color);
            border-left: 4px solid var(--accent-color);
        }
        
        .main-content {
            flex: 1;
            padding: 20px;
        }
        
        .products-table tbody tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .products-table tbody tr:hover {
            background-color: #f1f1f1;
        }

        .products-table tbody td {
            padding: 12px 15px;
            border-bottom: 1px solid var(--border-color);
        }
        
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 15px;
            border-bottom: 1px solid var(--border-color);
        }
        
        .page-title h2 {
            color: var(--dark-gray);
            font-size: 1.8rem;
        }
        
        .user-actions {
            display: flex;
            gap: 15px;
        }
        
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background-color: var(--primary-color);
            color: white;
        }
        
        .btn-primary:hover {
            background-color: var(--secondary-color);
        }
        
        .btn-danger {
            background-color: var(--accent-color);
            color: white;
        }
        
        .supplier-card {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            padding: 25px;
            margin-bottom: 30px;
        }
        
        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid var(--border-color);
        }
        
        .card-title {
            font-size: 1.3rem;
            color: var(--dark-gray);
        }
        
        .supplier-info {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        
        .info-group {
            margin-bottom: 15px;
        }
        
        .info-label {
            font-weight: 600;
            color: #666;
            margin-bottom: 5px;
            font-size: 0.9rem;
        }
        
        .info-value {
            font-size: 1rem;
            padding: 8px 0;
            border-bottom: 1px solid var(--border-color);
        }
        
        .products-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        .products-table th, 
        .products-table td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid var(--border-color);
        }
        
        .products-table th {
            background-color: var(--light-gray);
            font-weight: 600;
        }
        
        .products-table tr:hover {
            background-color: rgba(0,0,0,0.02);
        }
        
        .ul li a {
            text-decoration: none;
        }
        
        .status {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 0.8rem;
            font-weight: 500;
        }
        
        .status-active {
            background-color: #d4edda;
            color: #155724;
        }
        
        .status-inactive {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .action-icons {
            display: flex;
            gap: 10px;
        }
        
        .action-icon {
            cursor: pointer;
            color: #666;
            transition: color 0.3s;
        }
        
         .supplier-photo {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #3498db;
            margin-bottom: 20px;
        }
        
        .action-icon:hover {
            color: var(--primary-color);
        }
        
        #addSupplier{
            background-color: green;
        }
        
        @media (max-width: 768px) {
            .container {
                flex-direction: column;
            }
            
            .sidebar {
                width: 100%;
            }
            
            .supplier-info {
                grid-template-columns: 1fr;
            }
        }
        
        .supplier-title {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .supplier-rating {
            background-color: #ffc107;
            color: #856404;
            padding: 3px 8px;
            border-radius: 4px;
            font-size: 0.8rem;
        }

        .contact-role {
            font-size: 0.8rem;
            color: #666;
            margin-left: 8px;
        }

        .phone-ext {
            font-size: 0.8rem;
            color: #666;
            margin-left: 8px;
        }

        .contact-method {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }

        .contact-method span {
            background-color: #f0f0f0;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 0.8rem;
        }

        .supplier-actions {
            display: flex;
            gap: 10px;
            margin-top: 20px;
            padding-top: 15px;
            border-top: 1px solid var(--border-color);
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Sidebar Navigation -->
        <div class="sidebar">
            <div class="logo">
                <h1>Momeris</h1>
            </div>
            <ul class="nav-menu">
                <li class="nav-item active">Dashboard</li>
                <li class="nav-item"><a style="text-decoration: none" href="http://localhost:8081/Supplier_Management_System1/SupplierServlet">Suppliers</a></li>
                <li class="nav-item"><a style="text-decoration: none" href="http://localhost:8081/Supplier_Management_System1/InventoryServlet">Inventory</a></li>
                <li class="nav-item"><a href="http://localhost:8081/Supplier_Management_System1/SupplierFilter">Reports<a></li>
            </ul>
        </div>
        
        <!-- Main Content -->
        <div class="main-content">
            <div class="header">
                <div class="page-title">
                    <h2>Supplier Details</h2>
                    <p>View and manage supplier information</p>
                </div>
                <div class="user-actions">
                    <button class="btn btn-primary" id="addSupplier">Add Supplier</button>
                    <script>
                        document.getElementById("addSupplier").addEventListener("click",function(){
                            window.location.href = "http://localhost:8081/Supplier_Management_System1/";
                        });
                    </script>
                    <button class="btn btn-primary" id="editSupplier" >Edit Supplier</button>
                    <script>
                        document.getElementById("editSupplier").addEventListener("click",function(){
                            window.location.href = "http://localhost:8081/Supplier_Management_System1/SupplierServlet";
                        });
                    </script>
                    <button class="btn btn-danger" id="deleteSupplier">Delete Supplier</button>
                    <script>
                        document.getElementById("deleteSupplier").addEventListener("click",function(){
                            window.location.href = "http://localhost:8081/Supplier_Management_System1/supplierDelete.jsp";
                        });
                    </script>
                </div>
            </div>
            <!-- Supplier Information Card -->
            <div class="supplier-card">
                <div class="card-header">
                    <div class="supplier-title">
                        <h3 class="card-title">JD Groups.</h3>
                        <div class="supplier-rating">
                            ★★★★☆ (4.2/5)
                        </div>
                    </div>
                    <span class="status status-active">Premium Partner</span>
                </div>

               <img class="supplier-photo" src="Tom.jpg" alt="Supplier Photo">
                <div class="supplier-info">
                    <div>
                        <div class="info-group">
                            <div class="info-label">Supplier Manager</div>
                            <div class="info-value">GTECH-2022</div>
                        </div>
                        <div class="info-group">
                            <div class="info-label">Contact Person</div>
                            <div class="info-value">
                                <span>Liam Neesam</span>
                                <span class="contact-role">(Supplier Manager)</span>
                            </div>
                        </div>
                        <div class="info-group">
                            <div class="info-label">Email</div>
                            <div class="info-value">
                                <a style="text-decoration: none;" href="mailto:javidmushtan@gmail.com">TomCruise@gmail.com</a>
                            </div>
                        </div>
                        <div class="info-group">
                            <div class="info-label">Phone</div>
                            <div class="info-value">
                                <span>+94 78-3056418</span>
                                <span class="phone-ext">Ext. 78</span>
                            </div>
                        </div>
                        <div class="info-group">
                            <div class="info-label">Payment Terms</div>
                            <div class="info-value">Net 30</div>
                        </div>
                    </div>

                    <div>
                        <div class="info-group">
                            <div class="info-label">Address</div>
                            <div class="info-value">
                                <div>2500 Araliya Mawatha</div>
                                <div>Suite 400</div>
                                <div>Matara, ON M5V 3M5</div>
                                <div>Sri Lanka</div>
                            </div>
                        </div>
                        <div class="info-group">
                            <div class="info-label">Primary Contact Method</div>
                            <div class="info-value">
                                <div class="contact-method">
                                    <span>📧 Email</span>
                                    <span>📞 Phone</span>
                                    <span>💬 WhatsApp</span>
                                </div>
                            </div>
                        </div>
                        <div class="info-group">
                            <div class="info-label">Contact Details</div>
                            <div class="info-value">
                                <div>Start: Jan 05, 1990</div>
                                <div>Renewal: Jan 14, 2024</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="supplier-actions" style="padding-left: 47%;">
                    <button class="btn btn-primary" id="contact">View Contact</button>
                    <script>
                        document.getElementById('contact').addEventListener('click',function(){
                            window.location.href = "https://wa.link/uo3znq";
                        });
                    </script>
                </div>
            </div>
            
            <!-- Supplier Products Table -->
            <div class="supplier-card">
                <div class="card-header">
                    <h3 class="card-title">Supplied Products</h3>
                    <button class="btn btn-primary">Add Product</button>
                </div>
                
                <table class="products-table">
                    <thead>
                        <tr>
                            <th>Product ID</th>
                            <th>Product Name</th>
                            <th>Category</th>
                            <th>Unit Price</th>
                            <th>Stock</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${productList}">
                            <tr>
                                <td>${product.getProduct_id()}</td>
                                <td>${product.getProduct_name()}</td>
                                <td>${product.getCategory()}</td>
                                <td>$${product.getUnit_Price()}</td>
                                <td>${product.getStock()}</td>
                                <td>
                                    <span class="status ${product.isStatus() ? 'status-active' : 'status-inactive'}">
                                        ${product.isStatus() ? 'In Stock' : 'Out of Stock'}
                                    </span>
                                </td>
                                <td class="action-icons">
                                    <span class="action-icon" name="product_edit"><a href="http://localhost:8081/Supplier_Management_System1/InventoryServlet">✏<a>️</span>
                                                <span class="action-icon" name="product_delete"><a href="http://localhost:8081/Supplier_Management_System1/InventoryServlet">🗑<a>️</span>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <script>
        
        document.getElementById('editSupplier').addEventListener('click', function() {
            alert('Edit supplier functionality would open a form here');
        });
        
        document.getElementById('deleteSupplier').addEventListener('click', function() {
            if(confirm('Are you sure you want to delete this supplier?')) {
                alert('Supplier deleted (this is a demo)');
            }
        });
    </script>
</body>
</html>
