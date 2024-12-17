document.addEventListener("DOMContentLoaded", function() {
    fetch(`/AnimalFeed_webApp/views/web/layout/header.jsp`)
        .then(response => response.text())
        .then(data => {
            document.getElementById("header-placeholder").innerHTML = data;
            setupLoginEventListeners();
        })
        .catch(error => console.error("Error loading header:", error));
});

document.addEventListener("DOMContentLoaded", function() {
    fetch("/AnimalFeed_webApp/views/web/layout/near_footer.jsp")
        .then(response => response.text())
        .then(data => {
            document.getElementById("near-footer-placeholder").innerHTML = data;
        })
        .catch(error => console.error("Error loading near footer:", error));
});

document.addEventListener("DOMContentLoaded", function() {
    fetch("/AnimalFeed_webApp/views/web/layout/footer.jsp")
        .then(response => response.text())
        .then(data => {
            document.getElementById("footer-placeholder").innerHTML = data;
        })
        .catch(error => console.error("Error loading footer:", error));
});

function setupLoginEventListeners() {
    const login = document.getElementById("login");
    const signup = document.getElementById("signup");
    const userIcon = document.querySelector(".add_login");
    const closeLogin = document.querySelector(".login_close");
    const closeSignup = document.querySelector(".signup_close");

    // Mở modal login khi bấm vào user icon
    if (userIcon) {
        userIcon.addEventListener("click", () => {
            login.classList.add("open");
        });
    }

    // Đóng modal login
    if (closeLogin) {
        closeLogin.addEventListener("click", () => {
            login.classList.remove("open");
        });
    }

    // Đóng modal signup
    if (closeSignup) {
        closeSignup.addEventListener("click", () => {
            signup.classList.remove("open");
        });
    }

    // Hiển thị modal signup khi bấm vào "Đăng kí ngay" trong login
    const goToSignup = document.getElementById("go-to-signup");
    if (goToSignup) {
        goToSignup.addEventListener("click", (event) => {
            event.preventDefault(); // Ngừng hành động mặc định của link
            login.classList.remove("open"); // Ẩn modal login
            signup.classList.add("open"); // Hiển thị modal signup
        });
    }

    // Hiển thị modal login khi bấm vào "Đăng nhập ngay" trong signup
    const goToLogin = document.getElementById("go-to-login");
    if (goToLogin) {
        goToLogin.addEventListener("click", (event) => {
            event.preventDefault(); // Ngừng hành động mặc định của link
            signup.classList.remove("open"); // Ẩn modal signup
            login.classList.add("open"); // Hiển thị modal login
        });
    }

    // Đóng modal khi click bên ngoài modal content
    login.addEventListener("click", (event) => {
        if (event.target === login) {
            login.classList.remove("open");
        }
    });

    signup.addEventListener("click", (event) => {
        if (event.target === signup) {
            signup.classList.remove("open");
        }
    });
}