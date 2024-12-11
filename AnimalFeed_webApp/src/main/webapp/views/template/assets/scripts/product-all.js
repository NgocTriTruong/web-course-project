// Chọn phần tử menu thả xuống và các nút
const productSelect = document.getElementById("product-select"); // ID của menu thả xuống
const buttons = document.querySelectorAll(".button"); // Chọn tất cả các nút

// Xử lý sự kiện khi người dùng thay đổi lựa chọn
productSelect.addEventListener("change", function () {
    const selectedProduct = productSelect.value;

    // Thay đổi nội dung hiển thị hoặc xử lý logic khác dựa trên lựa chọn
    if (selectedProduct === "CA_TRA") {
        alert("Bạn đã chọn Cá Tra!");
    } else if (selectedProduct === "CA_DIEU_HONG") {
        alert("Bạn đã chọn Cá Điêu Hồng!");
    }
    // Thêm các lựa chọn khác nếu cần
});

// Xử lý sự kiện khi nhấn vào các nút
buttons.forEach(button => {
    button.addEventListener("click", function () {
        const type = button.classList.contains("heo") ? "heo" :
            button.classList.contains("bo") ? "bò" :
                button.classList.contains("tom") ? "tôm" : "cá";
        alert("Bạn đã chọn thức ăn cho " + type + "!");
    });
});
