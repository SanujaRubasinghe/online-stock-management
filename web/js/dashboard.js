/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

// Function to animate the count-up effect
const counters = document.querySelectorAll('.card-body p');

const countUp = (element, target) => {
    let count = 0;
    const duration = 1000; // Duration in milliseconds (2 seconds)
    const startTime = performance.now();

    const easeInOut = (t) => {
        return t < 0.5 ? 2 * t * t : -1 + (4 - 2 * t) * t;
    };

    const updateCounter = () => {
        const elapsed = performance.now() - startTime;
        const progress = Math.min(elapsed / duration, 1); // Ensure the progress doesn't exceed 1
        const easeProgress = easeInOut(progress);

        count = Math.floor(target * easeProgress);
        element.textContent = count;

        if (progress < 1) {
            requestAnimationFrame(updateCounter); // Continue animating
        }
    };

    updateCounter();
};

// Trigger count-up for each card
window.addEventListener('load', () => {
    counters.forEach(counter => {
        const target = parseInt(counter.textContent);
        countUp(counter, target);
    });
});
