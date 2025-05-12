<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Product</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-container {
            max-width: 800px;
            margin: 20px auto;
            background-color: #f8f9fa;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }
        .form-header {
            background-color: #0d6efd;
            color: white;
            padding: 15px;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
        }
        .required-field::after {
            content: " *";
            color: red;
        }
        .section-title {
            color: #0d6efd;
            border-bottom: 2px solid #0d6efd;
            padding-bottom: 5px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container py-4">
        <!-- Success/Error Messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show">
                ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show">
                ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="form-container">
            <div class="form-header">
                <h3 class="text-center mb-0">Add New Product</h3>
            </div>
            
            <div class="p-4">
                <form id="productForm" action="AddProductServlet" method="post" enctype="multipart/form-data">
                    <!-- Basic Product Information -->
                    <div class="mb-4">
                        <h5 class="section-title">Product Information</h5>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="product_id" class="form-label required-field">Product ID</label>
                                <input type="text" class="form-control" id="product_id" name="product_id" 
                                       pattern="PRD-\d{4}" title="Format: PRD-XXXX (e.g., PRD-1001)" required
                                       value="${param.product_id}">
                                <div class="form-text">Format: PRD-XXXX (e.g., PRD-1001)</div>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <label for="product_name" class="form-label required-field">Product Name</label>
                                <input type="text" class="form-control" id="product_name" name="product_name" required
                                       value="${param.product_name}">
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="category" class="form-label required-field">Category</label>
                                <select class="form-select" id="category" name="category" required>
                                    <option value="" disabled selected>Select Category</option>
                                    <option value="Food" ${param.category == 'Food' ? 'selected' : ''}>Food</option>
                                    <option value="Mechanical Parts" ${param.category == 'Mechanical Parts' ? 'selected' : ''}>Mechanical Parts</option>
                                    <option value="Electronics" ${param.category == 'Electronics' ? 'selected' : ''}>Electronics</option>
                                    <option value="Fasteners" ${param.category == 'Fasteners' ? 'selected' : ''}>Fasteners</option>
                                    <option value="Tools" ${param.category == 'Tools' ? 'selected' : ''}>Tools</option>
                                    <option value="Lubricants" ${param.category == 'Lubricants' ? 'selected' : ''}>Lubricants</option>
                                </select>
                            </div>
                            
                            <div class="col-md-6 mb-3">
                                <label for="supplier_id" class="form-label">Supplier</label>
                                <select class="form-select" id="supplier_id" name="supplier_id">
                                    <option value="" selected>No Supplier</option>
                                    <c:forEach items="${suppliers}" var="supplier">
                                        <option value="${supplier.supplier_id}" ${param.supplier_id == supplier.supplier_id ? 'selected' : ''}>
                                            ${supplier.supplier_name} (${supplier.supplier_id})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        
                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description" rows="2">${param.description}</textarea>
                        </div>
                    </div>
                    
                    <!-- Pricing & Inventory -->
                    <div class="mb-4">
                        <h5 class="section-title">Pricing & Inventory</h5>
                        <div class="row">
                            <div class="col-md-4 mb-3">
                                <label for="unit_price" class="form-label required-field">Unit Price ($)</label>
                                <input type="number" step="0.01" min="0" class="form-control" id="unit_price" 
                                       name="unit_price" required value="${param.unit_price}">
                            </div>
                            
                            <div class="col-md-4 mb-3">
                                <label for="stock" class="form-label required-field">Stock Quantity</label>
                                <input type="number" min="0" class="form-control" id="stock" name="stock" required
                                       value="${param.stock}">
                            </div>
                            
                            <div class="col-md-4 mb-3">
                                <label for="reorder_level" class="form-label">Reorder Level</label>
                                <input type="number" min="0" class="form-control" id="reorder_level" 
                                       name="reorder_level" value="${param.reorder_level}">
                            </div>
                        </div>
                    </div>
                    
                    <!-- Product Image -->
                    <div class="mb-4">
                        <h5 class="section-title">Product Image</h5>
                        <div class="mb-3">
                            <label for="product_image" class="form-label">Upload Product Image</label>
                            <input class="form-control" type="file" id="product_image" name="product_image" accept="image/*">
                            <div class="form-text">Recommended size: 500x500px, Max 2MB (JPEG/PNG)</div>
                        </div>
                    </div>
                    
                    <!-- Form Actions -->
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                        <button type="reset" class="btn btn-secondary me-md-2">Reset Form</button>
                        <button type="submit" class="btn btn-primary">Add Product</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Client-side validation
        document.getElementById('productForm').addEventListener('submit', function(event) {
            const unitPrice = parseFloat(document.getElementById('unit_price').value);
            const stock = parseInt(document.getElementById('stock').value);
            const reorderLevel = parseInt(document.getElementById('reorder_level').value) || 0;
            
            if (isNaN(unitPrice) {
                alert('Please enter a valid unit price');
                event.preventDefault();
                return;
            }
            
            if (unitPrice < 0) {
                alert('Unit price cannot be negative');
                event.preventDefault();
                return;
            }
            
            if (isNaN(stock)) {
                alert('Please enter a valid stock quantity');
                event.preventDefault();
                return;
            }
            
            if (stock < 0) {
                alert('Stock quantity cannot be negative');
                event.preventDefault();
                return;
            }
            
            if (reorderLevel < 0) {
                alert('Reorder level cannot be negative');
                event.preventDefault();
                return;
            }
            
            if (reorderLevel > 0 && stock < reorderLevel) {
                if (!confirm('Stock quantity is below the reorder level. Continue anyway?')) {
                    event.preventDefault();
                    return;
                }
            }
        });
    </script>
</body>
</html>