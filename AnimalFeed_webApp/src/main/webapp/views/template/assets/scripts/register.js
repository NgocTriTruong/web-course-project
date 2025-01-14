
document.getElementById("registrationForm").addEventListener("submit", function(event) {
    // Ngừng gửi form nếu có lỗi
    event.preventDefault();

    // Lấy giá trị các trường đầu vào
    var username = document.getElementById("id_username").value;
    var phone = document.getElementById("id_phone").value;
    var password1 = document.getElementById("id_password1").value;
    var password2 = document.getElementById("id_password2").value;

    // Biến trạng thái kiểm tra
    var valid = true;

    // Xử lý kiểm tra tên tài khoản (username)
    if (username === "") {
        document.getElementById("usernameError").style.display = "block";
        document.getElementById("id_username").classList.add("invalid");
        valid = false;
    } else {
        document.getElementById("usernameError").style.display = "none";
        document.getElementById("id_username").classList.remove("invalid");
    }

    // Xử lý kiểm tra số điện thoại (phone)
    var phoneRegex = /^0\d{9}$/;
    if (!phoneRegex.test(phone)) {
        document.getElementById("phoneError").style.display = "block";
        document.getElementById("id_phone").classList.add("invalid");
        valid = false;
    } else {
        document.getElementById("phoneError").style.display = "none";
        document.getElementById("id_phone").classList.remove("invalid");
    }

    // Xử lý kiểm tra mật khẩu (password1)
    var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{8,}$/;
    if (!passwordRegex.test(password1)) {
        document.getElementById("password1Error").style.display = "block";
        document.getElementById("id_password1").classList.add("invalid");
        valid = false;
    } else {
        document.getElementById("password1Error").style.display = "none";
        document.getElementById("id_password1").classList.remove("invalid");
    }

    // Xử lý kiểm tra mật khẩu nhập lại (password2)
    if (password1 !== password2) {
        document.getElementById("password2Error").style.display = "block";
        document.getElementById("id_password2").classList.add("invalid");
        valid = false;
    } else {
        document.getElementById("password2Error").style.display = "none";
        document.getElementById("id_password2").classList.remove("invalid");
    }

    // Nếu tất cả kiểm tra hợp lệ, gửi form (hoặc thực hiện đăng ký)
    if (valid) {
        document.getElementById("registrationForm").submit();
    }
});