/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
let rowCount = 1;

function addProductRow() {
    const container = document.getElementById("product-container");
    const row = document.createElement("div");
    row.classList.add('product-row');
    row.innerHTML = `
        <div style="margin-bottom: 10px;">
            <input type="text" name="productCode" placeholder="Product Code" onblur="fetchProductDetails(this)" />
            <input type="text" name="productName" placeholder="Name" readonly />
            <input type="text" name="price" placeholder="Price" readonly />
            <input type="number" name="quantity" placeholder="Qty" value="1" min="1" />
            <button type="button" onclick="removeProductRow(this)" class="delete-product-row-btn">X</button>
        </div>    
    `;
    
    container.appendChild(row);
    rowCount++;
}

function removeProductRow(button) {
    const row = button.closest(".product-row");
    if (row) row.remove();
}

function fetchProductDetails(input) {
    const code = input.value.trim();
    const row = input.parentElement;
    
    if (code !== "") {
        fetch(`ProductController?action=getById&id=${code}`)
                .then(response => response.json())
                .then(data => {
                    if (data) {
                        row.querySelector('[name="productName"]').value = data.name;
                        row.querySelector('[name="price"]').value = data.price;
                    } else {
                        alert("Product not found");
                    }                
        });
    }
    
    window.onload = function() {
        addProductRow();
    };
}
