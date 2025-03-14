<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <title>Quên mật khẩu</title>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
  <!-- Font Awesome để thêm icon -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <!-- Google Fonts - Poppins -->
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

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
      max-width: 500px;
      padding: 0;
    }

    .card {
      background: rgba(255, 255, 255, 0.95);
      border-radius: 20px;
      box-shadow: 0 10px 40px rgba(0, 0, 0, 0.15);
      animation: slideUp 0.8s ease-out;
      backdrop-filter: blur(10px);
      margin: 0;
      border: none;
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

    .card-header {
      background: linear-gradient(90deg, #3498db, #2980b9);
      border-radius: 20px 20px 0 0;
      padding: 15px 20px;
      text-align: center;
    }

    .card-title {
      font-size: 1.8rem;
      font-weight: 600;
      color: #fff;
      margin: 0;
      letter-spacing: 1px;
    }

    .card-body {
      padding: 30px;
    }

    p.mb-3 {
      font-size: 1rem;
      color: #7f8c8d;
      text-align: center;
      margin-bottom: 20px;
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

    .form-label {
      font-size: 0.95rem;
      color: #2c3e50;
      margin-bottom: 5px;
    }

    /* Nút bấm */
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

    /* Alert thông báo */
    .alert-danger {
      color: #fff;
      background: linear-gradient(90deg, #e74c3c, #c0392b);
      padding: 12px;
      border-radius: 8px;
      margin-bottom: 20px;
      font-size: 0.9rem;
      box-shadow: 0 4px 15px rgba(231, 76, 60, 0.3);
    }

    .alert-success {
      color: #fff;
      background: linear-gradient(90deg, #2ecc71, #27ae60);
      padding: 15px;
      border-radius: 8px;
      margin-bottom: 0;
      font-size: 1rem;
      text-align: center;
      box-shadow: 0 4px 15px rgba(46, 204, 113, 0.3);
    }

    .alert-success .btn-primary {
      margin-top: 10px;
      padding: 10px 20px;
      font-size: 1rem;
    }

    /* Ẩn/hiện các bước */
    .hidden {
      display: none;
    }

    /* Responsive */
    @media (max-width: 576px) {
      .card {
        padding: 20px;
      }
      .card-title {
        font-size: 1.5rem;
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
  <div class="card shadow">
    <div class="card-header bg-primary text-white">
      <h3 class="card-title mb-0">Quên Mật Khẩu</h3>
    </div>
    <div class="card-body">
      <!-- Step 1: Email Form -->
      <div id="emailStep">
        <p class="mb-3">Nhập email để nhận mã xác nhận</p>
        <div class="mb-3">
          <label for="email" class="form-label">Email</label>
          <i class="fas fa-envelope"></i>
          <input type="email" class="form-control" id="email" required>
        </div>
        <div class="mb-3">
          <button type="button" id="sendCodeBtn" class="btn btn-primary">Gửi Mã Xác Nhận</button>
        </div>
        <div id="emailMessage" class="alert alert-danger hidden"></div>
      </div>

      <!-- Step 2: Verification Code Form -->
      <div id="verificationStep" class="hidden">
        <p class="mb-3">Nhập mã xác nhận đã gửi tới email của bạn</p>
        <div class="mb-3">
          <label for="verificationCode" class="form-label">Mã xác nhận</label>
          <i class="fas fa-key"></i>
          <input type="text" class="form-control" id="verificationCode" required>
        </div>
        <div class="mb-3">
          <button type="button" id="verifyCodeBtn" class="btn btn-primary">Xác Nhận</button>
        </div>
        <div id="verificationMessage" class="alert alert-danger hidden"></div>
      </div>

      <!-- Step 3: Reset Password Form -->
      <div id="resetPasswordStep" class="hidden">
        <p class="mb-3">Nhập mật khẩu mới của bạn</p>
        <div class="mb-3">
          <label for="newPassword" class="form-label">Mật khẩu mới</label>
          <i class="fas fa-lock"></i>
          <input type="password" class="form-control" id="newPassword" required>
        </div>
        <div class="mb-3">
          <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
          <i class="fas fa-lock"></i>
          <input type="password" class="form-control" id="confirmPassword" required>
        </div>
        <div class="mb-3">
          <button type="button" id="resetPasswordBtn" class="btn btn-primary">Đặt Lại Mật Khẩu</button>
        </div>
        <div id="passwordMessage" class="alert alert-danger hidden"></div>
      </div>

      <!-- Step 4: Success Message -->
      <div id="successStep" class="hidden">
        <div class="alert alert-success">
          <p class="mb-2">Mật khẩu đã được đặt lại thành công!</p>
          <a href="login" class="btn btn-primary">Đăng Nhập</a>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script>
  $(document).ready(function() {
    // Step 1: Send verification code
    $("#sendCodeBtn").click(function() {
      const email = $("#email").val();
      if (!email) {
        $("#emailMessage").text("Vui lòng nhập email").removeClass("hidden");
        return;
      }

      $.ajax({
        url: "forgot-password",
        type: "POST",
        data: { email: email },
        success: function() {
          $("#emailStep").addClass("hidden");
          $("#verificationStep").removeClass("hidden");
        },
        error: function(xhr) {
          $("#emailMessage").text(xhr.responseText || "Có lỗi xảy ra. Vui lòng thử lại.").removeClass("hidden");
        }
      });
    });

    // Step 2: Verify code
    $("#verifyCodeBtn").click(function() {
      const code = $("#verificationCode").val();
      if (!code) {
        $("#verificationMessage").text("Vui lòng nhập mã xác nhận").removeClass("hidden");
        return;
      }

      $.ajax({
        url: "verify-code",
        type: "POST",
        data: { code: code },
        success: function() {
          $("#verificationStep").addClass("hidden");
          $("#resetPasswordStep").removeClass("hidden");
        },
        error: function(xhr) {
          $("#verificationMessage").text(xhr.responseText || "Mã xác nhận không đúng hoặc đã hết hạn").removeClass("hidden");
        }
      });
    });

    // Step 3: Reset password
    $("#resetPasswordBtn").click(function() {
      const newPassword = $("#newPassword").val();
      const confirmPassword = $("#confirmPassword").val();

      if (!newPassword || !confirmPassword) {
        $("#passwordMessage").text("Vui lòng nhập đầy đủ thông tin").removeClass("hidden");
        return;
      }

      if (newPassword !== confirmPassword) {
        $("#passwordMessage").text("Mật khẩu xác nhận không khớp").removeClass("hidden");
        return;
      }

      $.ajax({
        url: "reset-password",
        type: "POST",
        data: { password: newPassword },
        success: function() {
          $("#resetPasswordStep").addClass("hidden");
          $("#successStep").removeClass("hidden");
        },
        error: function(xhr) {
          $("#passwordMessage").text(xhr.responseText || "Có lỗi xảy ra. Vui lòng thử lại.").removeClass("hidden");
        }
      });
    });
  });
</script>
</body>
</html>