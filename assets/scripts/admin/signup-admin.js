// Form validation script
function validateForm() {
    let username = document.getElementById('username').value;
    let email = document.getElementById('email').value;
    let password = document.getElementById('password').value;
    let confirmPassword = document.getElementById('confirm-password').value;

    // Kiem tra 2 mat khau co giong nhau khong ?
    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return false; // Prevent form submission
    }

    // Kiem tra email co dung fomat khong?
    let emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!emailRegex.test(email)) {
        alert("Vui lòng nhập địa chỉ email hợp lệ.");
        return false;
    }

    // Kiem tra mat khau phai co  (hon 8kisi tu, 1 chu hoa, 1 so)
    let passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    if (!passwordRegex.test(password)) {
        alert("\n" + "Mật khẩu phải dài ít nhất 8 ký tự và chứa ít nhất một chữ cái và một số.");
        return false;
    }

    // If everything is valid, allow form submission
    return true;
}
