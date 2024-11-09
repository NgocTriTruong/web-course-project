document.addEventListener("DOMContentLoaded", function() {
    fetch("/assets/html/layout/header.html")
        .then(response => response.text())
        .then(data => {
            document.getElementById("header-placeholder").innerHTML = data;
        })
        .catch(error => console.error("Error loading header:", error));
});
document.addEventListener("DOMContentLoaded", function() {
    fetch("/assets/html/layout/footer.html")
        .then(response => response.text())
        .then(data => {
            document.getElementById("footer-placeholder").innerHTML = data;
        })
        .catch(error => console.error("Error loading header:", error));
});