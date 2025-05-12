/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


//const checkboxes = document.querySelectorAll(".productCheckbox");
//const deleteBtn = document.getElementById("deleteSelectedBtn");
//
//checkboxes.forEach(cb => {
//    cb.addEventListener('change', () => {
//        const anyChecked = Array.from(checkboxes).some(box => box.checked);
//        deleteBtn.style.display = anyChecked ? 'inline-block' : 'none';
//    });
//});

document.addEventListener("DOMContentLoaded", function () {
    const masterCheckbox = document.getElementById("selectAll");
    const checkboxes = document.querySelectorAll(".productCheckbox");
    const deleteBtn = document.getElementById("deleteSelectedBtn");
    
    const searchInput = document.getElementById('productSearch');
    const rows = document.querySelectorAll('table tbody tr');
     const noResultsRow = document.getElementById('noResultsMessageRow');

    function updateDeleteBtn() {
        const anyChecked = Array.from(checkboxes).some(cb => cb.checked);
        deleteBtn.style.display = anyChecked ? 'inline-block' : 'none';
    }

    masterCheckbox.addEventListener("change", function () {
        checkboxes.forEach(cb => cb.checked = masterCheckbox.checked);
        updateDeleteBtn();
    });

    checkboxes.forEach(cb => {
        cb.addEventListener("change", function () {
            const allChecked = Array.from(checkboxes).every(cb => cb.checked);
            masterCheckbox.checked = allChecked;
            updateDeleteBtn();
        });
    });
    
    searchInput.addEventListener('input', function () {
        const query = this.value.toLowerCase();
        let matchCount = 0;

        rows.forEach(row => {
            const text = row.textContent.toLowerCase();
            const isMatch = text.includes(query);
            row.style.display = isMatch ? '' : 'none';
            if (isMatch) matchCount++;
        });

        noResultsRow.style.display = matchCount === 0 ? '' : 'none';
    });
});