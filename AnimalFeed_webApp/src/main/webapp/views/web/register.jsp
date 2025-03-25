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

    <style>
        /* Reset mặc định và font */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #74ebd5 0%, #acb6e5 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            padding: 20px;
            overflow: hidden;
        }

        .container {
            position: relative;
            width: 100%;
            max-width: 700px;
            padding: 0;
        }

        .form-section {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
            padding: 40px 30px;
            animation: slideUp 0.8s ease-out;
            backdrop-filter: blur(10px);
        }

        @keyframes slideUp {
            0% {
                opacity: 0;
                transform: translateY(50px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        h2 {
            font-size: 2rem;
            font-weight: 600;
            color: #2c3e50;
            text-align: center;
            margin-bottom: 10px;
            letter-spacing: 1px;
        }

        p.text-center {
            font-size: 1rem;
            color: #7f8c8d;
            margin-bottom: 25px;
        }

        /* Breadcrumb */
        .breadcrumb {
            background: transparent;
            padding: 10px 0;
            margin-bottom: 20px;
            font-size: 0.9rem;
        }

        .breadcrumb-item a {
            color: #3498db;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .breadcrumb-item a:hover {
            color: #2980b9;
            text-decoration: underline;
        }

        .breadcrumb-item.active {
            color: #7f8c8d;
        }

        /* Input field */
        .form-control {
            font-size: 1rem;
            padding: 12px 15px 12px 40px; /* Để dành chỗ cho icon */
            border-radius: 10px;
            border: 1px solid #dfe6e9;
            background: #f9fbfc;
            transition: all 0.3s ease;
            position: relative;
        }

        .form-control:focus {
            border-color: #3498db;
            box-shadow: 0 0 12px rgba(52, 152, 219, 0.3);
            background: #fff;
        }

        .form-control::placeholder {
            color: #95a5a6;
        }

        .mb-3 {
            position: relative;
            margin-bottom: 20px;
        }

        .mb-3 i {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: #95a5a6;
            font-size: 1.1rem;
        }

        /* Nút đăng ký */
        .btn-primary {
            width: 100%;
            padding: 14px;
            font-size: 1.1rem;
            font-weight: 500;
            background: linear-gradient(90deg, #3498db, #2980b9);
            border: none;
            border-radius: 10px;
            color: #fff;
            text-transform: uppercase;
            letter-spacing: 1px;
            transition: all 0.4s ease;
        }

        .btn-primary:hover {
            background: linear-gradient(90deg, #2980b9, #1f618d);
            transform: translateY(-3px);
            box-shadow: 0 8px 20px rgba(52, 152, 219, 0.4);
        }

        /* Link đăng nhập */
        .login-link {
            text-align: center;
            color: #3498db;
            font-size: 0.95rem;
            text-decoration: none;
            display: block;
            margin-top: 20px;
            transition: all 0.3s ease;
        }

        .login-link:hover {
            color: #2980b9;
            text-decoration: underline;
        }

        /* Alert thông báo lỗi */
        .alert-danger {
            color: #fff;
            background: linear-gradient(90deg, #e74c3c, #c0392b);
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 0.9rem;
            box-shadow: 0 4px 15px rgba(231, 76, 60, 0.3);
        }

        /* Thông báo lỗi dưới input */
        .error-message {
            color: #e74c3c;
            font-size: 0.85rem;
            margin-top: 5px;
            display: none;
        }

        .form-control.invalid {
            border-color: #e74c3c;
            box-shadow: 0 0 8px rgba(231, 76, 60, 0.3);
        }

        /* Thông báo thành công */
        .success-message {
            color: #fff;
            background: linear-gradient(90deg, #2ecc71, #27ae60);
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 1rem;
            text-align: center;
            display: none;
            box-shadow: 0 4px 15px rgba(46, 204, 113, 0.3);
        }

        /* reCAPTCHA */
        .g-recaptcha {
            display: flex;
            justify-content: center;
            margin-bottom: 20px;
        }

        /* Responsive */
        @media (max-width: 576px) {
            .form-section {
                padding: 30px 20px;
                margin: 15px;
            }
            h2 {
                font-size: 1.8rem;
            }
            .form-control {
                font-size: 0.95rem;
            }
            .btn-primary {
                padding: 12px;
                font-size: 1rem;
            }
        }
    </style>
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

        <!-- Form đăng ký -->
        <form id="registrationForm" method="POST" novalidate>
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

        // Validate inputs
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
                    alert("Có lỗi khi gửi mã xác minh!");
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
                // Gửi yêu cầu đăng ký sau khi xác minh thành công
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
                });
            }
        });
    });
</script>
</body>
</html>