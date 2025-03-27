/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
document.addEventListener("DOMContentLoaded", function() {
    document.querySelectorAll('.sub-btn').forEach(function(button) {
        button.addEventListener('click', function() {
            let subMenu = this.nextElementSibling;
            let dropDownIcon = this.querySelector(".dropdown");
            
            if (subMenu && subMenu.classList.contains('sub-menu')) {
                if (subMenu.style.maxHeight) {
                    subMenu.style.maxHeight = null;
                    dropDownIcon.classList.remove("rotate");
                } else {
                    subMenu.style.maxHeight = subMenu.scrollHeight + "px";
                    dropDownIcon.classList.add("rotate");
                }
            }
        });
    });
});
