// Lấy phần tử nút
const scrollToTopBtn = document.getElementById("scrollToTopBtn");

// Lắng nghe sự kiện cuộn
window.addEventListener("scroll", function () {
    if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
        scrollToTopBtn.style.display = "flex";
    } else {
        scrollToTopBtn.style.display = "none";
    }
});

// Cuộn lên đầu trang khi nhấn vào nút
scrollToTopBtn.addEventListener("click", function () {
    window.scrollTo({top: 0, behavior: "smooth"});
});
