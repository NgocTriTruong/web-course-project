<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Sign Up</title>
  <!-- Bootstrap CSS -->
  <link href="../../bootstrap/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="../../css/signup_admin.css">


</head>
<body class="bg-dark">

<div class="d-flex justify-content-center align-items-center min-vh-100">
  <div class="signup-admin rounded-3 p-4 position-relative" style="width: 400px; background-color: rgba(255, 255, 255, 0.13); backdrop-filter: blur(10px); box-shadow: 0 0 40px rgba(8, 7, 16, 0.6);">
    <h3 class="text-center text-white mb-4">Admin Sign Up</h3>

    <form id="signup-form" onsubmit="return validateForm()">
      <div class="mb-3">
        <label for="username" class="form-label text-white">Username</label>
        <input type="text" class="form-control" id="username" placeholder="Enter Username" required>
      </div>
      <div class="mb-3">
        <label for="email" class="form-label text-white">Email</label>
        <input type="email" class="form-control" id="email" placeholder="Enter Email" required>
      </div>
      <div class="mb-3">
        <label for="password" class="form-label text-white">Password</label>
        <input type="password" class="form-control" id="password" placeholder="Enter Password" required>
      </div>
      <div class="mb-3">
        <label for="confirm-password" class="form-label text-white">Confirm Password</label>
        <input type="password" class="form-control" id="confirm-password" placeholder="Confirm Password" required>
      </div>
      <button type="submit" class="btn btn-light w-100">Sign Up</button>
    </form>

    <!-- Link to Login admin page -->
    <div class="text-center mt-3">
      <p class="text-white">Already have an account? <a href="../../html/admin/login_admin.html" class="text-decoration-none text-primary">Login</a></p>
    </div>
  </div>
</div>

<!-- Bootstrap JS -->
<script src="../../bootstrap/bootstrap.bundle.min.js"></script>
<script src="../../scripts/admin/signup-admin.js"></script>
</body>
</html>
