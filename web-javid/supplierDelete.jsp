<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Delete Supplier</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .form-container {
            max-width: 600px;
            margin: 0 auto;
        }
        .or-divider {
            display: flex;
            align-items: center;
            margin: 1rem 0;
        }
        .or-divider::before, .or-divider::after {
            content: "";
            flex: 1;
            border-bottom: 1px solid #dee2e6;
        }
        .or-text {
            padding: 0 10px;
        }
    </style>
</head>
<body>
    <div class="container mt-5">
        <div class="form-container border p-4 rounded shadow">
            <h3 class="text-center mb-4">Delete Supplier</h3>
            <form id="deleteForm" action="delete" method="post" onsubmit="return confirmDelete()">
                <div class="mb-3">
                    <label for="supplierid" class="form-label">Supplier ID</label>
                    <input type="text" class="form-control" id="supplierid" name="supplierid" placeholder="Enter Supplier ID">
                </div>
                
                <div class="or-divider">
                    <span class="or-text">OR</span>
                </div>
                
                <div class="mb-3">
                    <label for="from-date" class="form-label">From Date</label>
                    <input type="date" class="form-control" id="from-date" name="from-date">
                </div>
                <div class="mb-3">
                    <label for="to-date" class="form-label">To Date</label>
                    <input type="date" class="form-control" id="to-date" name="to-date">
                </div>
                <button type="submit" class="btn btn-danger w-100">Delete Supplier</button>
            </form>
            <div id="message" class="mt-3 text-center"></div>
        </div>
    </div>

    <script>
        function confirmDelete() {
            const sid = document.getElementById('supplierid').value;
            const fromDate = document.getElementById('from-date').value;
            const toDate = document.getElementById('to-date').value;
            
            if (!sid && (!fromDate || !toDate)) {
                alert('Please enter either Supplier ID or both date range!');
                return false;
            }
            
            return confirm('Are you sure you want to delete these supplier records? This action cannot be undone.');
        }
    </script>
</body>
</html>