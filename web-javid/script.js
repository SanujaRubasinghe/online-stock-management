/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function check(){
    event.preventDefault();
    let name = document.getElementById("name");
    let email = document.getElementById("email");
    let phone = document.getElementById("phone");
    let company = document.getElementById("company");
    let experience = document.getElementById("experience");
    let category = document.getElementById("category");
    let address = document.getElementById("address");
    let quantity = document.getElementById("category");
    
    console.log(address.value);
    if(name.value === ''){
        name.style.border = '1px solid red';
        alert("Name is Required");
    }
    
    const phoneregexp = /^07[0-9]-[0-9]{7}$/;
    if(!phoneregexp.test(phone.value)){
        alert("Phone number is empty or Invalid Format");
        phone.style.border = '1px solid red';
    }
    
    if (category.value === '') {
        alert("Please select a category.");
        category.focus();
        return;
    }

    if (address.value === '') {
        alert("Address is required.");
        address.focus();
        return;
    }

    if (quantity.value === '' || quantity.value <= 0) {
        alert("Quantity must be a positive number.");
        quantity.focus();
        return;
    }
    
};