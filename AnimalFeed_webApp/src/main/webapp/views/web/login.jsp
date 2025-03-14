<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6oIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" rel="stylesheet"/>

    <!-- Font Awesome để thêm icon -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

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
            overflow: hidden;
        }

        .login-container {
            position: relative;
            width: 100%;
            max-width: 450px;
            padding: 20px;
        }

        .login-form-section {
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

        .mb-3 {
            position: relative;
        }

        .mb-3 i {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: #95a5a6;
            font-size: 1.1rem;
        }

        /* Nút đăng nhập */
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

        /* Nút Google */
        .btn-danger {
            display: block;
            width: 100%;
            padding: 12px;
            font-size: 1rem;
            font-weight: 500;
            background: linear-gradient(90deg, #e74c3c, #c0392b);
            border: none;
            border-radius: 10px;
            color: #fff;
            text-align: center;
            margin-top: 20px;
            transition: all 0.4s ease;
            text-decoration: none;
        }

        .btn-danger:hover {
            background: linear-gradient(90deg, #c0392b, #a93226);
            transform: translateY(-3px);
            box-shadow: 0 8px 20px rgba(231, 76, 60, 0.4);
            color: #fff;
        }

        /* Link đăng ký và quên mật khẩu */
        .register-link, .reset_pass-link {
            text-align: center;
            color: #3498db;
            font-size: 0.95rem;
            text-decoration: none;
            display: block;
            margin-top: 15px;
            transition: all 0.3s ease;
        }

        .register-link:hover, .reset_pass-link:hover {
            color: #2980b9;
            text-decoration: underline;
        }

        /* Alert thông báo lỗi */
        .alert {
            color: #fff;
            background: linear-gradient(90deg, #e74c3c, #c0392b);
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 0.9rem;
            box-shadow: 0 4px 15px rgba(231, 76, 60, 0.3);
            display: none;
        }

        /* reCAPTCHA */
        .g-recaptcha {
            display: flex;
            justify-content: center;
        }

        /* Responsive */
        @media (max-width: 576px) {
            .login-form-section {
                padding: 30px 20px;
                margin: 15px;
            }
            h2 {
                font-size: 1.8rem;
            }
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="login-form-section">
        <h2>Đăng Nhập</h2>
        <p class="text-center">Truy cập tài khoản của bạn ngay</p>

        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Form đăng nhập -->
        <form action="<%= request.getContextPath() %>/login" method="post" id="loginForm">
            <div class="mb-3">
                <i class="fas fa-phone"></i>
                <input type="tel" class="form-control" id="id_phone" name="phone" placeholder="Số điện thoại" required>
            </div>
            <div class="mb-3">
                <i class="fas fa-lock"></i>
                <input class="form-control" id="id_password" name="password" type="password" placeholder="Mật khẩu" required />
            </div>

            <!-- Google reCAPTCHA -->

            <div class="g-recaptcha mb-3" data-sitekey="6LciYeoqAAAAALAiQBkhttpGondAfDMVtaUCiHGW"></div>

            <div class="text-center">
                <button class="btn btn-primary" type="submit">Đăng Nhập</button>
            </div>
        </form>

        <a href="<%= request.getContextPath() %>/register" class="register-link">Chưa có tài khoản? Đăng ký ngay</a>
        <a href="<%= request.getContextPath() %>/forgot-password" class="reset_pass-link">Quên mật khẩu?</a>

        <a href="<%= request.getContextPath() %>/login-google" class="btn btn-danger">
            <i class="fab fa-google me-2"></i> Đăng nhập với Google
        </a>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        const form = document.getElementById('loginForm');
        const errorDiv = document.querySelector('.alert');

        form.addEventListener('submit', function(event) {
            let isValid = true;

            errorDiv.style.display = 'none'; // Ẩn thông báo lỗi cũ

            // Kiểm tra số điện thoại
            const phone = form.querySelector('input[name="phone"]').value.trim();
            if (phone === '') {
                errorDiv.innerText = 'Vui lòng nhập số điện thoại';
                errorDiv.style.display = 'block';
                isValid = false;
            } else if (!/^\d{10,11}$/.test(phone)) {
                errorDiv.innerText = 'Số điện thoại không hợp lệ (10-11 số)';
                errorDiv.style.display = 'block';
                isValid = false;
            }

            // Kiểm tra mật khẩu
            const password = form.querySelector('input[name="password"]').value.trim();
            if (password === '') {
                errorDiv.innerText = 'Vui lòng nhập mật khẩu';
                errorDiv.style.display = 'block';
                isValid = false;
            }

            // Kiểm tra reCAPTCHA
            const recaptchaResponse = grecaptcha.getResponse();
            if (recaptchaResponse.length === 0) {
                errorDiv.innerText = 'Vui lòng xác minh reCAPTCHA';
                errorDiv.style.display = 'block';
                isValid = false;
            }

            // Nếu có lỗi, ngăn gửi form
            if (!isValid) {
                event.preventDefault();
            }
        });
    });
</script>
</body>
</html>