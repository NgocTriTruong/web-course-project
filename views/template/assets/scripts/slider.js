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
    // Select all menu links and product sections
    const productSections = document.querySelectorAll(".slide_show_2 .list-products");

    // Slider state variables
    let slidesSecond = [];
    let currentIndexSecond = 0;
    let slideIntervalSecond;

    // Function to show a specific slide
    function showSlideSecond(index) {
        slidesSecond.forEach((slide, i) => {
            slide.classList.remove("active"); // Hide all slides
        });
        if (slidesSecond[index]) {
            slidesSecond[index].classList.add("active"); // Show the current slide
        }
    }

    // Function to go to the next slide
    function nextSlideSecond() {
        if (slidesSecond.length === 0) return;
        currentIndexSecond = (currentIndexSecond + 1) % slidesSecond.length;
        showSlideSecond(currentIndexSecond);
    }

    // Function to change slide manually
    function changeSlideSecond(direction) {
        // Clear the interval to pause automatic sliding when a button is clicked
        clearInterval(slideIntervalSecond);

        // Update the slide index
        currentIndexSecond = (currentIndexSecond + direction + slidesSecond.length) % slidesSecond.length;
        showSlideSecond(currentIndexSecond);

        // Restart the interval for automatic sliding after a short delay
        slideIntervalSecond = setInterval(nextSlideSecond, 5000);
    }

    // Expose the changeSlideSecond function to the global scope for button onclick
    window.changeSlideSecond = changeSlideSecond;

    // Function to initialize the slider for a given section
    function initializeSlider(activeSection) {
        // Clear any existing interval
        clearInterval(slideIntervalSecond);

        // Get slides from the active section
        slidesSecond = activeSection.querySelectorAll(".item");
        currentIndexSecond = 0;

        // Show the first slide
        showSlideSecond(currentIndexSecond);

        // Start the interval for automatic sliding
        slideIntervalSecond = setInterval(nextSlideSecond, 5000);
    }

    // Initialize the slider for the initially active section
    const initialActive = document.querySelector(".list-products.active");
    if (initialActive) {
        initializeSlider(initialActive);
    } else if (productSections.length > 0) {
        // If no section is active by default, activate the first one
        productSections[0].classList.add("active");
        initializeSlider(productSections[0]);
    }
});




