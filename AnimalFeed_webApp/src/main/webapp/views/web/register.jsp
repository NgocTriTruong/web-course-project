<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="utf-8"/>
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <title>Đăng ký</title>
    <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" rel="stylesheet"/>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/views/template/assets/css/register.css">
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
        <h2>ĐĂNG KÝ TÀI KHOẢN</h2>
        <p class="text-center">Nếu chưa có tài khoản vui lòng đăng ký tại đây</p>
        <div class="success-message" id="successMessage">Đăng ký thành công!</div>
        <form action="<%= request.getContextPath() %>/register" method="POST" id="registrationForm" novalidate>
            <div class="mb-3">
                <input class="form-control" id="id_username" type="text" name="username" maxlength="100" placeholder="Tên tài khoản *" required>
                <div class="error-message" id="usernameError">Lỗi tên tài khoản</div>
            </div>
            <div class="mb-3">
                <input class="form-control" id="id_phone" type="tel" name="phone" maxlength="15" placeholder="Số điện thoại *" required>
                <div class="error-message" id="phoneError">Lỗi số điện thoại</div>
            </div>
            <div class="mb-3">
                <input class="form-control" id="id_password1" type="password" name="password" maxlength="100" placeholder="Mật khẩu *" required>
                <div class="error-message" id="password1Error">Lỗi mật khẩu</div>
            </div>
            <div class="mb-3">
                <input class="form-control" id="id_password2" type="password" name="confirmPassword" maxlength="100" placeholder="Nhập lại mật khẩu *" required>
                <div class="error-message" id="password2Error">Mật khẩu không khớp</div>
            </div>
            <div class="text-center">
                <button class="btn btn-primary" type="submit">ĐĂNG KÝ</button>
            </div>
        </form>
        <a href="<%= request.getContextPath() %>/login" class="login-link">Bạn đã có tài khoản? Đăng nhập</a>
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

<!-- Bootstrap JS and dependencies -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-kQtW33rZJAHjgefvhyyzcGF3vI0Txkzl5M7G7rvB/JF9QFJzUawmGJlfyep7EJiF" crossorigin="anonymous"></script>
<!-- Font Awesome -->
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<script src="<%= request.getContextPath() %>/views/template/assets/scripts/register.js"></script>
</body>
</html>
