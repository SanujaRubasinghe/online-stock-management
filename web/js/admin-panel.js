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

function toggleUserDropdown() {
    document.getElementById("user-dropdown").classList.toggle("show");
}

window.onclick = function(e) {
    if(!e.target.matches(".user-profile-dropbtn")) {
        let dropdown = document.getElementById("user-dropdown");
        if (dropdown.classList.contains("show")) {
            dropdown.classList.remove("show");
        }
    }
};


function updateOnlineStatus() {
    fetch('OnlineUsersServlet')
            .then(response => response.json())
            .then(onlineUsers => {
                document.querySelectorAll('.user-status').forEach(statusElement => {
                    let username = statusElement.dataset.username;
                    if (onlineUsers.includes(username)) {
                        statusElement.textContent = 'Online';
                        statusElement.style.color = 'green';
                    } else {
                        statusElement.textContent = 'Offline';
                        statusElement.style.color = 'red';
                    }
                })
            .catch(error => console.error('Error fetching online users: ', error));
    });
}
