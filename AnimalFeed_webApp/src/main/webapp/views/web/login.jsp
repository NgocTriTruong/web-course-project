<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <%--    <link rel="stylesheet" href="<%= request.getContextPath() %>/views/template/assets/css/login.css">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link crossorigin="anonymous" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" rel="stylesheet"/>
    <title>Đăng nhập</title>
    <style>
        /* Cải tiến chung */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f7f9fc;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        /* Tạo hiệu ứng cho phần đăng nhập */
        .login-form-section {
            background-color: #ffffff;
            border-radius: 15px;
            box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            padding: 40px;
            animation: fadeIn 1s ease-in-out;
        }

        /* Hiệu ứng fade-in cho phần đăng nhập */
        @keyframes fadeIn {
            0% {
                opacity: 0;
                transform: translateY(-30px);
            }
            100% {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Tiêu đề */
        .login-form-section h2 {
            font-size: 2.4rem;
            font-weight: bold;
            color: #007bff;
            text-align: center;
            margin-bottom: 20px;
            letter-spacing: 1px;
        }

        /* Phần nhập liệu */
        .form-control {
            font-size: 16px;
            padding: 12px 15px;
            border-radius: 8px;
            border: 1px solid #ddd;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }

        /* Màu khi focus vào input */
        .form-control:focus {
            border-color: #007bff;
            box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
        }

        /* Nút đăng nhập */
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

        /* Hiệu ứng khi hover nút đăng nhập */
        .btn-primary:hover {
            background-color: #0056b3;
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(0, 123, 255, 0.3);
        }

        /* Liên kết đăng ký */
        .register-link {
            text-align: center;
            color: #007bff;
            font-size: 16px;
            text-decoration: none;
            display: block;
            margin-top: 20px;
            transition: color 0.3s ease, text-decoration 0.3s ease;
        }

        /* Hiệu ứng hover cho liên kết đăng ký */
        .register-link:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        /* Liên kết quên mk */
        .reset_pass-link {
            text-align: center;
            color: #007bff;
            font-size: 16px;
            text-decoration: none;
            display: block;
            margin-top: 20px;
            transition: color 0.3s ease, text-decoration 0.3s ease;
        }

        /* Hiệu ứng hover cho liên kết đăng ký */
        .reset_pass-link:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        /* Phần Navbar */
        .navbar {
            background-color: #007bff;
            padding: 15px 20px;
            display: flex;
            align-items: center;
            position: fixed;
            width: 100%;
            top: 0;
            left: 0;
            z-index: 1000;
        }

        .navbar a {
            color: white;
            font-size: 18px;
            text-decoration: none;
            margin-left: 20px;
        }

        .navbar a:hover {
            text-decoration: underline;
        }

        /* Tạo khoảng cách giữa các phần */
        .mb-3 {
            margin-bottom: 20px;
        }

        /* Phần thân của form, đảm bảo không bị kéo dài quá */
        .login-container {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
            min-height: 100vh;
            background-color: #f7f9fc;
        }

    </style>
</head>
<body>
<div class="login-container">
    <div class="login-form-section">
        <h2>Đăng nhập</h2>
        <p class="text-center">Vui lòng đăng nhập để truy cập tài khoản của bạn</p>
        <!-- Chuyển action tới servlet xử lý đăng nhập -->
        <form action="<%= request.getContextPath() %>/login" method="post" id="loginForm">
            <div class="mb-3">
                <input type="tel" class="form-control" id="id_phone" name="phone" placeholder="Nhập số điện thoại" required>
            </div>
            <div class="mb-3">
                <input class="form-control" id="id_password" placeholder="Nhập mật khẩu" name="password" type="password" aria-label="Mật khẩu" required />
            </div>
            <div class="text-center">
                <button class="btn btn-primary" type="submit">ĐĂNG NHẬP</button>
            </div>
        </form>
        <a href="<%= request.getContextPath() %>/register" class="register-link">Chưa có tài khoản? Đăng ký</a>
        <a href="<%= request.getContextPath() %>/reset_pass" class="reset_pass-link">Quên mật khẩu</a>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-kQtW33rZJAHjgefvhyyzcGF3vI0Txkzl5M7G7rvB/JF9QFJzUawmGJlfyep7EJiF" crossorigin="anonymous"></script>
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
</body>
</html>
