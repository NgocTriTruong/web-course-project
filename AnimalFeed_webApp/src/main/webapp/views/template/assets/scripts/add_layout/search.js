document.addEventListener("DOMContentLoaded", function () {
    const searchToggle = document.getElementById("search-toggle");
    const searchFormContainer = document.getElementById("search-form-container");

    searchToggle.addEventListener("click", function (e) {
        e.preventDefault();
        const isVisible = searchFormContainer.style.display === "block";
        searchFormContainer.style.display = isVisible ? "none" : "block";
    });

    // Đóng form khi click ra ngoài
    document.addEventListener("click", function (e) {
        if (!searchFormContainer.contains(e.target) && e.target !== searchToggle) {
            searchFormContainer.style.display = "none";
        }
    });
    searchFormContainer.addEventListener("click", function (e) {
        e.stopPropagation();
    });

    // Debug form submission
    const form = searchFormContainer.querySelector("form");
    form.addEventListener("submit", function (e) {
        console.log("Form submitted with:", new FormData(form));
    });
});