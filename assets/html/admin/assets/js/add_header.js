document.addEventListener("DOMContentLoaded", function() {
    fetch("/assets/html/admin/layout/header.html")
        .then(response => response.text())
        .then(data => {
            document.getElementById("header-placeholder").innerHTML = data;
        })
        .catch(error => console.error("Error loading header:", error));
});