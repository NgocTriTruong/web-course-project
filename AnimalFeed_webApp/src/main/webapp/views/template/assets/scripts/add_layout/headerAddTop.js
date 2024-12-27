window.addEventListener("scroll", addStickyClass);

var header = document.getElementById("header-placeholder"); // lấy phần tử header theo ID
var sticky = header.offsetTop; // lấy vị trí của header

function addStickyClass() {
    if (window.pageYOffset > sticky) {
        header.classList.add("sticky"); // thêm lớp sticky khi cuộn quá vị trí của header
    } else {
        header.classList.remove("sticky"); // loại bỏ lớp sticky khi cuộn lên trên
    }
}