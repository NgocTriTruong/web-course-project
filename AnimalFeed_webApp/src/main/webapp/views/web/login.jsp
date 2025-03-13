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

    <!-- reCAPTCHA -->
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f7f9fc;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: #333;
        }

        .login-form-section {
            background-color: #ffffff;
            border-radius: 15px;
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            padding: 40px;
            animation: fadeIn 1s ease-in-out;
        }

        @keyframes fadeIn {
            0% { opacity: 0; transform: translateY(-30px); }
            100% { opacity: 1; transform: translateY(0); }
        }

        h2 {
            font-size: 2.4rem;
            font-weight: bold;
            color: #007bff;
            text-align: center;
            margin-bottom: 20px;
        }

        .form-control {
            font-size: 16px;
            padding: 12px 15px;
            border-radius: 8px;
            border: 1px solid #ddd;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
        }

        .btn-primary {
            width: 100%;
            border-radius: 8px;
            padding: 12px;
            font-size: 16px;
            background-color: #007bff;
            border: none;
            color: white;
            transition: all 0.3s ease;
            cursor: pointer;
        }

        .btn-primary:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(0, 123, 255, 0.3);
        }

        .register-link, .reset_pass-link {
            text-align: center;
            color: #007bff;
            font-size: 16px;
            text-decoration: none;
            display: block;
            margin-top: 20px;
            transition: color 0.3s ease, text-decoration 0.3s ease;
        }

        .register-link:hover, .reset_pass-link:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        .alert {
            color: white;
            background-color: #dc3545;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
            display: none;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="login-form-section">
        <h2>Đăng nhập</h2>
        <p class="text-center">Vui lòng đăng nhập để truy cập tài khoản của bạn</p>

        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Form đăng nhập -->
        <form action="<%= request.getContextPath() %>/login" method="post" id="loginForm">
            <div class="mb-3">
                <input type="tel" class="form-control" id="id_phone" name="phone" placeholder="Nhập số điện thoại" required>
            </div>
            <div class="mb-3">
                <input class="form-control" id="id_password" name="password" type="password" placeholder="Nhập mật khẩu" required />
            </div>

            <!-- Google reCAPTCHA -->
            <div class="g-recaptcha mb-3" data-sitekey="6LciYeoqAAAAALAiQBkhttpGondAfDMVtaUCiHGW"></div>

            <div class="text-center">
                <button class="btn btn-primary" type="submit">ĐĂNG NHẬP</button>
            </div>
        </form>

        <a href="<%= request.getContextPath() %>/register" class="register-link">Chưa có tài khoản? Đăng ký</a>
        <a href="<%= request.getContextPath() %>/forgot-password" class="reset_pass-link">Quên mật khẩu?</a>
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
