document.addEventListener("DOMContentLoaded", function () {
    let slides = document.querySelectorAll(".slide-show-1 .list-images div");
    let currentIndex = 0;
    let slideInterval;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.remove("active");  // Hide all slides
        });
        slides[index].classList.add("active");  // Show the current slide
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        showSlide(currentIndex);
    }

    function changeSlide(direction) {
        // Clear the interval to pause automatic sliding when a button is clicked
        clearInterval(slideInterval);

        // Update the slide index
        currentIndex = (currentIndex + direction + slides.length) % slides.length;
        showSlide(currentIndex);

        // Restart the interval for automatic sliding after a short delay
        slideInterval = setInterval(nextSlide, 5000);
    }

    // Initial slide display
    showSlide(currentIndex);

    // Set the interval for automatic sliding
    slideInterval = setInterval(nextSlide, 5000);

    // Attach button events
    window.changeSlideFirst = changeSlide;  // Expose the function to the global scope
});

document.addEventListener("DOMContentLoaded", function () {
    let slides = document.querySelectorAll(".slide-show-2 .list-products div");
    let currentIndex = 0;
    let slideInterval;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.remove("active");  // Hide all slides
            if (i === index) {
                slide.classList.add("active");  // Show the current slide
            }
        });
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        showSlide(currentIndex);
    }

    function changeSlide(direction) {
        // Clear the interval to pause automatic sliding when a button is clicked
        clearInterval(slideInterval);

        // Update the slide index
        currentIndex = (currentIndex + direction + slides.length) % slides.length;
        showSlide(currentIndex);

        // Restart the interval for automatic sliding after a short delay
        slideInterval = setInterval(nextSlide, 5000);
    }

    // Initial slide display
    showSlide(currentIndex);

    // Set the interval for automatic sliding
    slideInterval = setInterval(nextSlide, 5000);

    // Attach button events
    window.changeSlideSecond = changeSlide;  // Expose the function to the global scope
});


