document.getElementById("login-submit").addEventListener("click", function () {
    // Lấy giá trị từ các input
    const contact = document.getElementById("contact").value.trim();
    const password = document.getElementById("pwd").value.trim();
  
    // Tài khoản cố định để kiểm tra (giả lập)
    const validUser = "0797534971"; // username
    const validPassword = "123"; // password
  
    // Kiểm tra dữ liệu
    if (contact === "" || password === "") {
      alert("Vui lòng nhập đầy đủ thông tin!");
      return;
    }
  
    // So khớp với thông tin cố định
    if (contact === validUser && password === validPassword) {
      // Lưu trạng thái đăng nhập
      localStorage.setItem("loggedIn", "true");
      alert("Đăng nhập thành công!");
  
      // Đóng form đăng nhập
      document.getElementById("login").style.display = "none";
  
      // Cập nhật giao diện
      checkLoginStatus();
    } else {
      alert("Thông tin đăng nhập không chính xác!");
    }
  });
  
  // Hàm kiểm tra trạng thái đăng nhập
  function checkLoginStatus() {
    const isLoggedIn = localStorage.getItem("loggedIn");
    if (isLoggedIn === "true") {
      document.getElementById("not-logged-in").style.display = "none";
      document.getElementById("logged-in").style.display = "block";
    } else {
      document.getElementById("not-logged-in").style.display = "block";
      document.getElementById("logged-in").style.display = "none";
    }
  }
  
  // Gắn sự kiện cho nút đăng xuất
  document.getElementById("logout").addEventListener("click", function () {
    localStorage.removeItem("loggedIn");
    checkLoginStatus();
  });
  
  // Kiểm tra trạng thái khi tải trang
  document.addEventListener("DOMContentLoaded", checkLoginStatus);

  document.addEventListener("DOMContentLoaded", function () {
    const isLoggedIn = localStorage.getItem("loggedIn");
    if (isLoggedIn === "true") {
      document.getElementById("not-logged-in").style.display = "none";
      document.getElementById("logged-in").style.display = "block";
    } else {
      document.getElementById("not-logged-in").style.display = "block";
      document.getElementById("logged-in").style.display = "none";
    }
  });
  