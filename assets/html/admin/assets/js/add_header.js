document.addEventListener("DOMContentLoaded", function() {
    fetch("/assets/html/admin/layout/header.html")
        .then(response => response.text())
        .then(data => {
            document.getElementById("header-placeholder").innerHTML = data;
        })
        .catch(error => console.error("Error loading header:", error));
});

document.addEventListener("DOMContentLoaded", function() {
    // Lấy đường dẫn hiện tại
    const currentPath = window.location.pathname;
    
    // Tìm các liên kết trong thanh bên
    const sidenavLinks = document.querySelectorAll(".sidenav-link");
    
    sidenavLinks.forEach((link) => {
      if (link.getAttribute("href") === currentPath) {
        link.classList.add("active");
      }
    });
});

