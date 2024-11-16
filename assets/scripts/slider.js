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
    let slidesSecond = document.querySelectorAll(".slide-show-2 .list-products .item");
    let currentIndexSecond = 0;
    let slideIntervalSecond;

    function showSlideSecond(index) {
        slidesSecond.forEach((slide, i) => {
            slide.classList.remove("active"); // Hide all slides
        });
        slidesSecond[index].classList.add("active"); // Show the current slide
    }

    function nextSlideSecond() {
        currentIndexSecond = (currentIndexSecond + 1) % slidesSecond.length;
        showSlideSecond(currentIndexSecond);
    }

    function changeSlideSecond(direction) {
        // Clear the interval to pause automatic sliding when a button is clicked
        clearInterval(slideIntervalSecond);

        // Update the slide index
        currentIndexSecond = (currentIndexSecond + direction + slidesSecond.length) % slidesSecond.length;
        showSlideSecond(currentIndexSecond);

        // Restart the interval for automatic sliding after a short delay
        slideIntervalSecond = setInterval(nextSlideSecond, 5000);
    }

    // Initial slide display
    showSlideSecond(currentIndexSecond);

    // Set the interval for automatic sliding
    slideIntervalSecond = setInterval(nextSlideSecond, 5000);

    // Attach button events
    window.changeSlideSecond = changeSlideSecond; // Expose the function to the global scope
});



