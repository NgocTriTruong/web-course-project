function selectCategory(button, categoryId) {
    // Lấy tất cả các nút
    var buttons = document.querySelectorAll('.product_button_icon');

    // Loại bỏ lớp 'selected' khỏi tất cả các nút
    buttons.forEach(function(btn) {
        btn.classList.remove('selected');
    });

    // Thêm lớp 'selected' vào nút được chọn
    button.classList.add('selected');

    // Chuyển hướng trang với categoryId
    window.location.href = '${pageContext.request.contextPath}/list-product?categoryId=' + categoryId;
}