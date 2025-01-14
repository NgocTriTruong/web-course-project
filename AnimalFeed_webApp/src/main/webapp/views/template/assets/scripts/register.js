
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

document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('registrationForm');
    const errorDiv = document.querySelector('.alert');

    form.addEventListener('submit', function(event) {
        let isValid = true;

        // Reset các thông báo lỗi
        const errorMessages = form.querySelectorAll('.error-message');
        errorMessages.forEach(function(errorMessage) {
            errorMessage.style.display = 'none';
        });

        // Kiểm tra trường "username"
        const username = form.querySelector('input[name="username"]').value;
        if (username === '') {
            form.querySelector('#usernameError').style.display = 'block';
            isValid = false;
        }

        // Kiểm tra trường "phone"
        const phone = form.querySelector('input[name="phone"]').value;
        if (phone === '') {
            form.querySelector('#phoneError').style.display = 'block';
            isValid = false;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu
        const password = form.querySelector('input[name="password"]').value;
        const confirmPassword = form.querySelector('input[name="confirmPassword"]').value;
        if (password !== confirmPassword) {
            form.querySelector('#password2Error').style.display = 'block';
            isValid = false;
        }

        // Nếu có lỗi, hiển thị thông báo lỗi và ngừng gửi form
        if (!isValid) {
            errorDiv.style.display = 'block'; // Hiển thị thông báo lỗi
            event.preventDefault(); // Ngừng gửi form
        }
    });
});
