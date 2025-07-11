<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Đăng ký</title>
    <!-- Bootstrap CSS -->
    <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" rel="stylesheet"/>
    <!-- Font Awesome để thêm icon -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Google Fonts - Poppins -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <!-- reCAPTCHA -->
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/register_user.css">

</head>
<body>
<div class="container">
    <nav aria-label="breadcrumb">
        <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="<%= request.getContextPath() %>/home" class="home-link">Trang chủ</a></li>
            <li class="breadcrumb-item active" aria-current="page">Đăng ký tài khoản</li>
        </ol>
    </nav>
    <div class="form-section">
        <h2>Đăng Ký Tài Khoản</h2>
        <p class="text-center">Tạo tài khoản mới để bắt đầu</p>
        <div class="success-message" id="successMessage">Đăng ký thành công! Vui lòng đăng nhập.</div>

        <!-- Thông báo lỗi -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Kiểm tra khóa -->
        <c:if test="${sessionScope.lockUntil != null && System.currentTimeMillis() < sessionScope.lockUntil}">
            <div class="alert alert-danger" id="lockMessage">
                Trang đăng ký bị khóa do nhập sai mã quá 5 lần. Vui lòng thử lại sau
                <span id="remainingTime"></span> phút!
            </div>
            <script>
                const lockUntil = ${sessionScope.lockUntil};
                function updateRemainingTime() {
                    const now = Date.now();
                    const remaining = Math.max(0, Math.ceil((lockUntil - now) / 1000 / 60));
                    document.getElementById("remainingTime").innerText = remaining;
                    if (remaining === 0) {
                        window.location.reload();
                    }
                }
                updateRemainingTime();
                setInterval(updateRemainingTime, 60000); // Cập nhật mỗi phút
            </script>
        </c:if>

        <!-- Form đăng ký -->
        <form id="registrationForm" method="POST" novalidate style="${sessionScope.lockUntil != null && System.currentTimeMillis() < sessionScope.lockUntil ? 'display:none;' : ''}">
            <div class="mb-3">
                <i class="fas fa-user"></i>
                <input class="form-control" id="id_username" type="text" name="username" maxlength="100" placeholder="Tên tài khoản *" required>
                <div class="error-message" id="usernameError">Vui lòng nhập tên tài khoản</div>
            </div>
            <div class="mb-3">
                <i class="fas fa-envelope"></i>
                <input class="form-control" id="id_email" type="email" name="email" placeholder="Email (Gmail) *" required>
                <div class="error-message" id="emailError">Vui lòng nhập email Gmail hợp lệ</div>
            </div>
            <div class="mb-3">
                <i class="fas fa-lock"></i>
                <input class="form-control" id="id_password1" type="password" name="password" maxlength="100" placeholder="Mật khẩu *" required>
                <div class="error-message" id="password1Error">Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ cái và số</div>
            </div>
            <div class="mb-3">
                <i class="fas fa-lock"></i>
                <input class="form-control" id="id_password2" type="password" name="confirmPassword" maxlength="100" placeholder="Nhập lại mật khẩu *" required>
                <div class="error-message" id="password2Error">Mật khẩu không khớp</div>
            </div>
            <div class="mb-3">
                <div class="g-recaptcha" data-sitekey="6LciYeoqAAAAALAiQBkhttpGondAfDMVtaUCiHGW"></div>
            </div>
            <div class="text-center">
                <button class="btn btn-primary" type="button" id="sendCodeBtn">Gửi mã xác minh</button>
            </div>
        </form>

        <!-- Form xác minh mã -->
        <form id="verifyForm" style="display: none;" method="POST">
            <div class="mb-3">
                <i class="fas fa-key"></i>
                <input class="form-control" id="id_code" type="text" name="code" placeholder="Nhập mã xác minh *" required>
                <div class="error-message" id="codeError">Mã xác minh không hợp lệ</div>
            </div>
            <div class="text-center">
                <button class="btn btn-primary" type="submit" id="verifyCodeBtn">Xác minh & Đăng ký</button>
            </div>
        </form>

        <a href="<%= request.getContextPath() %>/login" class="login-link">Đã có tài khoản? Đăng nhập</a>
    </div>
</div>

<!-- Modal for Loading Indicator -->
<div class="modal fade" id="loadingModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content" style="background: transparent; border: none; box-shadow: none;">
            <div class="spinner-border text-primary" role="status" style="width: 3rem; height: 3rem;">
                <span class="visually-hidden">Loading...</span>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById("sendCodeBtn").addEventListener("click", function() {
        const form = document.getElementById("registrationForm");
        const email = document.getElementById("id_email").value;
        const username = document.getElementById("id_username").value;
        const password1 = document.getElementById("id_password1").value;
        const password2 = document.getElementById("id_password2").value;
        const recaptchaResponse = grecaptcha.getResponse();

        let isValid = true;
        if (!username) {
            document.getElementById("usernameError").style.display = "block";
            isValid = false;
        } else {
            document.getElementById("usernameError").style.display = "none";
        }
        if (!/^[a-zA-Z0-9._%+-]+@gmail\.com$/.test(email)) {
            document.getElementById("emailError").style.display = "block";
            isValid = false;
        } else {
            document.getElementById("emailError").style.display = "none";
        }
        if (!password1 || password1.length < 8 || !/[a-zA-Z]/.test(password1) || !/\d/.test(password1)) {
            document.getElementById("password1Error").style.display = "block";
            isValid = false;
        } else {
            document.getElementById("password1Error").style.display = "none";
        }
        if (password1 !== password2) {
            document.getElementById("password2Error").style.display = "block";
            isValid = false;
        } else {
            document.getElementById("password2Error").style.display = "none";
        }
        if (!recaptchaResponse) {
            alert("Vui lòng xác nhận bạn không phải là robot!");
            isValid = false;
        }

        if (isValid) {
            const loadingModal = new bootstrap.Modal(document.getElementById("loadingModal"));
            loadingModal.show();

            fetch("<%= request.getContextPath() %>/send-code", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "email=" + encodeURIComponent(email)
            }).then(response => {
                loadingModal.hide();
                if (response.ok) {
                    sessionStorage.setItem("regUsername", username);
                    sessionStorage.setItem("regEmail", email);
                    sessionStorage.setItem("regPassword", password1);
                    document.getElementById("registrationForm").style.display = "none";
                    document.getElementById("verifyForm").style.display = "block";
                } else {
                    response.text().then(text => alert(text || "Có lỗi khi gửi mã xác minh!"));
                }
            });
        }
    });

    document.getElementById("verifyForm").addEventListener("submit", function(event) {
        event.preventDefault();
        const code = document.getElementById("id_code").value;
        const loadingModal = new bootstrap.Modal(document.getElementById("loadingModal"));
        loadingModal.show();

        fetch("<%= request.getContextPath() %>/verify-code", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "code=" + encodeURIComponent(code)
        }).then(response => {
            if (response.ok) {
                const username = sessionStorage.getItem("regUsername");
                const email = sessionStorage.getItem("regEmail");
                const password = sessionStorage.getItem("regPassword");
                fetch("<%= request.getContextPath() %>/register", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: "username=" + encodeURIComponent(username) +
                        "&email=" + encodeURIComponent(email) +
                        "&password=" + encodeURIComponent(password) +
                        "&g-recaptcha-response=" + grecaptcha.getResponse()
                }).then(regResponse => {
                    loadingModal.hide();
                    if (regResponse.ok) {
                        document.getElementById("successMessage").style.display = "block";
                        setTimeout(() => window.location.href = "<%= request.getContextPath() %>/home", 2000);
                    } else {
                        regResponse.text().then(text => alert("Đăng ký thất bại: " + text));
                    }
                });
            } else {
                loadingModal.hide();
                response.text().then(text => {
                    document.getElementById("codeError").innerText = text;
                    document.getElementById("codeError").style.display = "block";
                    if (text.includes("khóa trong 10 phút")) {
                        window.location.reload(); // Tải lại trang để hiển thị thông báo khóa
                    }
                });
            }
        });
    });
</script>
</body>
</html>