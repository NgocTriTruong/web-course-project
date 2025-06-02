<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
<%--    <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6oIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" rel="stylesheet"/>--%>

    <!-- Font Awesome để thêm icon -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

    <!-- reCAPTCHA -->
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/login_user.css">
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
                <i class="fas fa-envelope"></i> <!-- Thay icon điện thoại bằng icon email -->
                <input type="email" class="form-control" id="id_email" name="email" placeholder="Email (Gmail)" required>
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
        <a href="https://www.facebook.com/v22.0/dialog/oauth?client_id=10059135720792572&redirect_uri=https://animalsfeeds.online/login-facebook" class="btn btn-info">
            <i class="fab fa-facebook me-2"></i> Đăng nhập với Facebook
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

            // Kiểm tra email
            const email = form.querySelector('input[name="email"]').value.trim();
            if (email === '') {
                errorDiv.innerText = 'Vui lòng nhập email';
                errorDiv.style.display = 'block';
                isValid = false;
            } else if (!/^[a-zA-Z0-9._%+-]+@gmail\.com$/.test(email)) {
                errorDiv.innerText = 'Vui lòng nhập email Gmail hợp lệ (ví dụ: example@gmail.com)';
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