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

  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/forgot_password.css">

  <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
  <df-messenger
          intent="WELCOME"
          chat-title="VinaFeed_chat"
          agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264"
          language-code="vi"
  ></df-messenger>

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