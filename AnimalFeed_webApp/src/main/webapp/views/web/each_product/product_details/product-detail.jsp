<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi-tiết-sản-phẩm</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/gioi_thieu.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/each_product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chi_tiet_product.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/signup.css">
  
    <!-- scrollToTopBtn -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/scrollToTopBtn.css">
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/scrollToTopBtn.js" defer></script>

    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger
            intent="WELCOME"
            chat-title="VinaFeed_chat"
            agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264"
            language-code="vi"
    ></df-messenger>

</head>
<body>
<%@ include file="../../layout/header.jsp" %>

<main class="chi_tiet_product" style="margin-top: 105px;">
    <div class="container">
        <div class="content text-center pb-5">
            <div class="row">
                <jsp:useBean id="product" class="vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product" scope="request" />
                <div class="col-md-4">
                    <img src="${product.img}" alt="${product.name}" width="350px" height="100%"
                         style="object-fit: cover;">
                </div>
                <div class="col-md-1"></div>
                <div class="col-md-6 text-start">
                    <div class="h1 mt-5 mb-3" style="color:#94b83d;">${product.name}</div>
                    <div class="h5 mt-3 mb-3">${product.description}</div>
                    <div class="line_red"></div>
                    <div class="d-flex mt-3 mb-3">
                        <div class="h5 fw-bold p_bold text-start">Giá:</div>
                        <c:if test="${product.discountId > 1}">
                            <jsp:useBean id="discounts" scope="request" type="vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount"/>
                            <div class="p text-start text-secondary price_sale me-4"
                                 style="font-size: 18px; margin-left: -95px">
                                <del><f:formatNumber value="${product.price}"/> <u>đ</u></del>
                                <span style="color: red; font-size: 14px; margin-left: 5px;">-${discounts.percentage}%</span>
                            </div>
                            <div class="h4 text-danger"><f:formatNumber value="${product.price * (1 - discounts.percentage / 100)}"/> <u>đ</u></div>
                        </c:if>
                        <c:if test="${product.discountId == 1}">
                            <div class="h4 text-danger"><f:formatNumber value="${product.price}"/> <u>đ</u></div>
                        </c:if>

                    </div>
                    <div class="line_gray"></div>
                    <div class="d-flex mt-2">
                        <div class="p fw-bold p_bold">Mã SP:</div>
                        <div class="p">${product.name}</div>
                    </div>
                    <div class="d-flex mt-2">
                        <div class="p fw-bold p_bold text-start">Thị trường phân phối:</div>
                        <div class="p">Việt Nam</div>
                    </div>
                    <div class="d-flex mt-2">
                        <div class="p fw-bold p_bold text-start">Trọng lượng:</div>
                        <div class="p">25kg</div>
                    </div>
                    <div class="d-flex mt-2">
                        <div class="p fw-bold p_bold">Dành cho:</div>
                        <div class="p">
                            Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi
                        </div>
                    </div>
                    <div class="d-flex mt-2">
                        <div class="p fw-bold p_bold">Lượt mua (bao):</div>
                        <div class="p">2.341</div>
                    </div>
                    <div class="button d-flex mt-3">
                        <form action="${pageContext.request.contextPath}/add-cart" method="GET" style="display: inline;">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input type="hidden" name="quantity" value="${param.quantity != null ? param.quantity : 1}">
                            <button type="submit" class="btn_h order d-flex justify-content-center" style="border: none;">
                                <div style="padding-top: 3px;">
                                    <i class="fa-solid fa-cart-plus"></i>
                                </div>
                                <div class="h5 text_order">Thêm vào giỏ hàng</div>
                            </button>
                        </form>
                        <form action="${pageContext.request.contextPath}/order-confirm" method="GET" style="display: inline;">
                            <input type="hidden" name="productId" value="${product.id}">
                            <input type="hidden" name="quantity" value="${param.quantity != null ? param.quantity : 1}">
                            <button type="submit" class="btn_h call d-flex justify-content-center w-100" style="border: none;">
                                <div class="h5 text_call">Mua ngay</div>
                            </button>
                        </form>
                        <div class="quantity-input ms-3">
                            <input type="number" name="quantity" value="1" min="1" max="500"
                                   class="form-control h-100"
                                   onchange="updateQuantity(this.value)">
                        </div>
                    </div>
                    <div class="share d-flex mt-4">
                        <div class="p text-start me-4">Liên hệ:</div>
                        <div class="d-flex ms-2">
                            <div class="h5 me-3">012.3456789</div>
                            <a href="#"><i class="ti-facebook"></i></a>
                            <a href="#"><i class="ti-twitter"></i></a>
                            <a href="#"><i class="ti-instagram"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="container">
            <div class="content d-flex between mt-4">
                <div class="h3" style="padding-top: 6px;">Mô tả sản phẩm</div>
                <button id="toggle-button1" onclick="toggleDescription('description-content1', 'toggle-button1')">
                    <i class="fa-solid fa-chevron-down"></i>
                </button>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="description-content1" style="display: none;" class="content mt-3">
            <div class="p text-start mb-2">
                ${productDetail.detail_description}
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="container">
            <div class="content d-flex between mt-4">
                <div class="h3">Thành phần dinh dưỡng</div>
                <button id="toggle-button2" onclick="toggleDescription('description-content2', 'toggle-button2')">
                    <i class="fa-solid fa-chevron-down"></i>
                </button>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="description-content2" style="display: none;" class="content mt-3">
            <div class="p text-start mb-2">
                ${productDetail.nutrition}
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="container">
            <div class="content d-flex between mt-4">
                <div class="h3">Thành phần nguyên liệu</div>
                <button id="toggle-button3" onclick="toggleDescription('description-content3', 'toggle-button3')">
                    <i class="fa-solid fa-chevron-down"></i>
                </button>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="description-content3" style="display: none;" class="content mt-3">
            <div class="p text-start mb-2">
                ${productDetail.ingredient}
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="container">
            <div class="content d-flex between mt-4">
                <div class="h3">Hướng dẫn sử dụng</div>
                <button id="toggle-button4" onclick="toggleDescription('description-content4', 'toggle-button4')">
                    <i class="fa-solid fa-chevron-down"></i>
                </button>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="description-content4" style="display: none;" class="content mt-3">
            <div class="p text-start mb-2">
                ${productDetail.usage}
            </div>
        </div>
    </div>

    <div class="container-fluid bg-light">
        <div class="container">
            <div class="content text-center">
                <div class="h2 fw-bold mb-3 mt-5 pt-3">SẢN PHẨM LIÊN QUAN</div>
            </div>
            <c:choose>
                <c:when test="${not empty relatedProducts}">
                    <div class="slide-show-1 mt-4 carousel slide" data-bs-ride="carousel" data-bs-interval="3000">
                        <div class="carousel-inner">
                            <c:set var="count" value="0" />
                            <c:forEach var="related" items="${relatedProducts}">
                                <c:if test="${count % 3 == 0}">
                                    <div class="carousel-item ${count == 0 ? 'active' : ''}">
                                    <div class="list-products d-flex justify-content-center">
                                </c:if>

                                <div class="product-card mx-2">
                                    <a href="product-detail?pid=${related.id}" class="text-decoration-none text-dark">
                                        <div class="product-img">
                                            <img src="${related.img}" alt="${related.name}" height="250px" width="200px">
                                        </div>
                                        <div class="h5 text-h">${related.name}</div>
                                        <div class="p pb-2 text-p">${related.description}</div>
                                        <div class="h4 text-start ms-3" style="color: red;">
                                            <f:formatNumber value="${related.price}"/> <u>đ</u>
                                        </div>
                                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                            <i class="fa-solid fa-truck mt-1"></i>
                                            <p class="ms-2">2 -4 ngày</p>
                                        </div>
                                    </a>
                                </div>

                                <c:if test="${count % 3 == 2 || count == relatedProducts.size() - 1}">
                                    </div>
                                    </div>
                                </c:if>

                                <c:set var="count" value="${count + 1}" />
                            </c:forEach>
                        </div>

                        <button class="carousel-control-prev" type="button" data-bs-target=".carousel" data-bs-slide="prev">
                            &#10094;
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target=".carousel" data-bs-slide="next">
                            &#10095;
                        </button>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center mt-4 mb-4">
                        <p>Không có sản phẩm liên quan nào để hiển thị.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</main>

<%@ include file="../../layout/near_footer.jsp" %>
<%@ include file="../../layout/footer.jsp" %>

      <!-- scrollToTopBtn -->
      <button id="scrollToTopBtn"><i class="fa-solid fa-chevron-up"></i></button>

<script>
    function toggleDescription(contentId, buttonId) {
        var content = document.getElementById(contentId);
        var button = document.getElementById(buttonId);

        if (content.style.display === "none") {
            content.style.display = "block";
            button.innerHTML = '<i class="fa-solid fa-chevron-up"></i>';
            button.style.fontSize = "70px";
            button.style.height = "33px";
            button.style.lineHeight = "17px";
        } else {
            content.style.display = "none";
            button.innerHTML = '<i class="fa-solid fa-chevron-down"></i>';
            button.style.fontSize = "40px";
            button.style.height = "40px";
            button.style.lineHeight = "40px";
        }
    }
</script>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        let slides = document.querySelectorAll(".slide-show-1 .list-products");
        let currentIndex = 0;
        let slideInterval;

        // Kiểm tra xem slides có phần tử nào không
        if (slides.length === 0) {
            console.log("Không có sản phẩm liên quan để hiển thị slideshow.");
            return; // Thoát nếu không có slides
        }

        function showSlide(index) {
            slides.forEach((slide, i) => {
                slide.classList.remove("active");  // Xóa lớp active khỏi tất cả slides
            });
            slides[index].classList.add("active");  // Thêm lớp active cho slide hiện tại
        }

        function nextSlide() {
            currentIndex = (currentIndex + 1) % slides.length;
            showSlide(currentIndex);
        }

        function changeSlide(direction) {
            clearInterval(slideInterval); // Dừng interval khi người dùng tương tác
            currentIndex = (currentIndex + direction + slides.length) % slides.length;
            showSlide(currentIndex);
            slideInterval = setInterval(nextSlide, 5000); // Khởi động lại interval
        }

        // Hiển thị slide đầu tiên
        showSlide(currentIndex);

        // Thiết lập interval cho slideshow tự động
        slideInterval = setInterval(nextSlide, 5000);

        // Gắn sự kiện cho các nút điều hướng
        window.changeSlideProducts = changeSlide;
    });
</script>

<script>
    function updateQuantity(value) {
        document.querySelector('input[name="quantity"]').value = value;
        document.querySelector('form[action$="/add-cart"] input[name="quantity"]').value = value;
        document.querySelector('form[action$="/order-confirm"] input[name="quantity"]').value = value;
        console.log("Số lượng đã chọn: " + value);
    }
</script>

</body>
</html>