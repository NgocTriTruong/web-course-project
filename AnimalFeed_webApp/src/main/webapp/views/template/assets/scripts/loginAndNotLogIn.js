document.addEventListener("DOMContentLoaded", () => {
    const notLoggedIn = document.getElementById("not-logged-in");
    const loggedIn = document.getElementById("logged-in");

    // Kiểm tra trạng thái đăng nhập
    const isLoggedIn = "${sessionScope.user != null}"; // Sử dụng EL để kiểm tra user trong session

    if (isLoggedIn === "true") {
        notLoggedIn.style.display = "none";
        loggedIn.style.display = "block";
    } else {
        notLoggedIn.style.display = "block";
        loggedIn.style.display = "none";
    }
});