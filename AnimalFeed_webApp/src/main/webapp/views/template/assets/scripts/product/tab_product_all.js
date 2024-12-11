document.addEventListener("DOMContentLoaded", () => {
    // Lấy tất cả các sản phẩm và tabs bằng cách sử dụng CSS selector
    const products = document.querySelectorAll("[id^='product_all_']"); // Tìm tất cả các div có id bắt đầu với 'product_all_'
    const tabs = document.querySelectorAll("[id^='product-tab-']"); // Tìm tất cả các div có id bắt đầu với 'product-tab-'
    
    // Duyệt qua từng tab và gắn sự kiện click
    tabs.forEach((tab, index) => {
        tab.addEventListener("click", () => {
            // Xóa lớp 'product-active' ở tất cả các tab
            tabs.forEach((tabItem) => tabItem.classList.remove("product-active"));
            // Thêm lớp 'product-active' vào tab hiện tại
            tab.classList.add("product-active");

            // Duyệt qua tất cả các sản phẩm và chỉ hiển thị sản phẩm tương ứng với tab được chọn
            products.forEach((product, i) => {
                if (i === index) {
                    product.style.display = "block"; // Hiển thị sản phẩm tương ứng
                } else {
                    product.style.display = "none"; // Ẩn các sản phẩm còn lại
                }
            });
        });
    });
});

// more sale product
// more sale product
document.addEventListener("DOMContentLoaded", () => {
  const toggleButtons = document.querySelectorAll("[id^='toggle-product-']"); 
  const moreContents = document.querySelectorAll("[id^='more_product_']"); 

  toggleButtons.forEach((toggleButton, index) => {
    toggleButton.addEventListener("click", () => {
      const moreContent = moreContents[index];  // Lấy phần tử tương ứng với toggleButton

      if (moreContent.style.display === "block") {
        moreContent.style.display = "none";
        toggleButton.innerHTML = 'Xem tất cả <i class="ms-2 fa-solid fa-chevron-down"></i>';
      } else {
        moreContent.style.display = "block";
        toggleButton.innerHTML = 'Thu gọn <i class="ms-2 fa-solid fa-chevron-up"></i>';
      }
    });
  });
});


  // more text product
document.addEventListener("DOMContentLoaded", () => {
    const toggleButton = document.getElementById("toggle-button-text");
    const moreContent = document.getElementById("more_text_product");
  
    toggleButton.addEventListener("click", () => {
      if (moreContent.style.display === "block") {
        moreContent.style.display = "none";
        toggleButton.innerHTML = 'Xem thêm <i class="ms-2 fa-solid fa-chevron-down"></i>';
      } else {
        moreContent.style.display = "block";
        toggleButton.innerHTML = 'Thu gọn <i class="ms-2 fa-solid fa-chevron-up"></i>';
      }
    });
  });
  