<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Quên mật khẩu</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css">
  <style>
    .card {
      max-width: 500px;
      margin: 0 auto;
      margin-top: 100px;
    }
    .hidden {
      display: none;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="card shadow">
    <div class="card-header bg-primary text-white">
      <h3 class="card-title mb-0">Quên mật khẩu</h3>
    </div>
    <div class="card-body">
      <!-- Step 1: Email Form -->
      <div id="emailStep">
        <p class="mb-3">Vui lòng nhập email để nhận mã xác nhận</p>
        <div class="mb-3">
          <label for="email" class="form-label">Email</label>
          <input type="email" class="form-control" id="email" required>
        </div>
        <div class="mb-3">
          <button type="button" id="sendCodeBtn" class="btn btn-primary">Gửi mã xác nhận</button>
        </div>
        <div id="emailMessage" class="alert alert-danger hidden"></div>
      </div>

      <!-- Step 2: Verification Code Form -->
      <div id="verificationStep" class="hidden">
        <p class="mb-3">Mã xác nhận đã được gửi tới email của bạn. Vui lòng kiểm tra và nhập mã.</p>
        <div class="mb-3">
          <label for="verificationCode" class="form-label">Mã xác nhận</label>
          <input type="text" class="form-control" id="verificationCode" required>
        </div>
        <div class="mb-3">
          <button type="button" id="verifyCodeBtn" class="btn btn-primary">Xác nhận</button>
        </div>
        <div id="verificationMessage" class="alert alert-danger hidden"></div>
      </div>

      <!-- Step 3: Reset Password Form -->
      <div id="resetPasswordStep" class="hidden">
        <p class="mb-3">Nhập mật khẩu mới của bạn</p>
        <div class="mb-3">
          <label for="newPassword" class="form-label">Mật khẩu mới</label>
          <input type="password" class="form-control" id="newPassword" required>
        </div>
        <div class="mb-3">
          <label for="confirmPassword" class="form-label">Xác nhận mật khẩu</label>
          <input type="password" class="form-control" id="confirmPassword" required>
        </div>
        <div class="mb-3">
          <button type="button" id="resetPasswordBtn" class="btn btn-primary">Đặt lại mật khẩu</button>
        </div>
        <div id="passwordMessage" class="alert alert-danger hidden"></div>
      </div>

      <!-- Step 4: Success Message -->
      <div id="successStep" class="hidden">
        <div class="alert alert-success">
          <p class="mb-2">Mật khẩu đã được đặt lại thành công!</p>
          <a href="login" class="btn btn-primary">Đăng nhập</a>
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