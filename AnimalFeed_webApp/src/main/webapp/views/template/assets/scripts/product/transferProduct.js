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

// Thêm vào cuối file JavaScript
function scrollToSection(sectionId) {
    const element = document.getElementById(sectionId);
    if (element) {
        element.scrollIntoView({ behavior: 'smooth' });
    }
}

// Sửa lại các link phân trang và category
document.querySelectorAll('.category-link').forEach(link => {
    link.addEventListener('click', function (e) {
        e.preventDefault();
        const href = this.getAttribute('href');
        window.location.href = href + '#sale-products';
    });
});