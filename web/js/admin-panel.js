/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
//document.addEventListener("DOMContentLoaded", function() {
//    document.querySelectorAll('.sub-btn').forEach(function(button) {
//        button.addEventListener('click', function() {
//            let subMenu = this.nextElementSibling;
//            let dropDownIcon = this.querySelector(".dropdown");
//            
//            if (subMenu && subMenu.classList.contains('sub-menu')) {
//                if (subMenu.style.maxHeight) {
//                    subMenu.style.maxHeight = null;
//                    dropDownIcon.classList.remove("rotate");
//                } else {
//                    subMenu.style.maxHeight = subMenu.scrollHeight + "px";
//                    dropDownIcon.classList.add("rotate");
//                }
//            }
//        });
//    });
//});

document.addEventListener('DOMContentLoaded', () => {
        const subBtns = document.querySelectorAll('.sub-btn');

        subBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                const item = btn.closest('.item');
                item.classList.toggle('open');
            });
        });
});

//function toggleUserDropdown() {
//    document.getElementById("user-dropdown").classList.toggle("show");
//}
//
//window.onclick = function(e) {
//    if(!e.target.matches(".user-profile-dropbtn")) {
//        let dropdown = document.getElementById("user-dropdown");
//        if (dropdown.classList.contains("show")) {
//            dropdown.classList.remove("show");
//        }
//    }
//};

    // Toggle dropdown
    function toggleUserDropdown() {
        const dropdown = document.getElementById("user-dropdown");
        dropdown.style.display = (dropdown.style.display === "block") ? "none" : "block";
    }

    // Close dropdown when clicking outside
    document.addEventListener('click', function (e) {
        const dropdown = document.getElementById("user-dropdown");
        const button = document.querySelector(".user-profile-dropbtn");

        if (!button.contains(e.target) && !dropdown.contains(e.target)) {
            dropdown.style.display = "none";
        }
    });

    // Update current time
    function updateTime() {
        const now = new Date();
        const timeString = now.toLocaleTimeString();
        document.getElementById("current-time").textContent = timeString;
    }

    // Run clock every second
    setInterval(updateTime, 1000);
    updateTime();

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
