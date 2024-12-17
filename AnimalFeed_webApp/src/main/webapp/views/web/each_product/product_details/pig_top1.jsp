<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/signup.css">
  
    <!-- scrollToTopBtn -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/scrollToTopBtn.css">
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/scrollToTopBtn.js" defer></script>
</head>
<body>
<div><%@ include file="../../layout/header.jsp" %></div>
<main class="chi_tiet_product" style="margin-top: 105px;">
    <div class="container">
        <div class="content text-center pb-5">
            <div class="row">
                <div class="col-md-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_01.png" alt="" width="350px" height="100%"
                         style="object-fit: cover;">
                </div>
                <div class="col-md-1"></div>
                <div class="col-md-6 text-start">
                    <div class="h1 mt-5 mb-3" style="color:#94b83d;">TOP 01</div>
                    <div class="h5 mt-3 mb-3">Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi</div>
                    <div class="line_red"></div>
                    <div class="d-flex mt-3 mb-3">
                        <div class="h5 fw-bold p_bold text-start">Giá:</div>
                        <div class="p text-start text-secondary price_sale me-4"
                             style="font-size: 18px; margin-left: -95px">
                            <del> 400.000 <u>đ</u></del>
                            <span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                        <div class="h4 text-danger">320.000 vnđ</div>
                    </div>
                    <div class="line_gray"></div>
                    <div class="d-flex mt-2">
                        <div class="p fw-bold p_bold">Mã SP:</div>
                        <div class="p">TOP01</div>
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
                    <div class="button d-flex mt-3 ms-5">
                        <div class="btn_h order d-flex justify-content-center">
                            <div style="padding-top: 3px;">
                                <i class="fa-solid fa-cart-plus"></i>
                            </div>
                            <div class="h5 text_order">Thêm vào giỏ hàng</div>
                        </div>
                        <div class="btn_h call d-flex justify-content-center">
                            <div class="h5 text_call">Mua ngay</div>
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
                <button id="toggle-button1" onclick="toggleDescription('description-content1', 'toggle-button1')">+
                </button>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="description-content1" style="display: none;" class="content mt-3">
            <div class="p text-start mb-2">
                + HAPPY 340 dùng cho gà siêu thịt từ 01 đến 21 ngày tuổi.
            </div>
            <div class="p text-start mb-2">
                + Sản phẩm giàu dinh dưỡng, giúp tăng cường, hỗ trợ hệ miễn dịch trên gia cầm non; cân bằng acid amin,
                bổ sung men tiêu hóa; bổ sung ccas sản phẩm có nguồn gốc tự nhiên, kích thích tính thèm ăn.
            </div>
            <div class="p text-start mb-2">
                + Sử dụng HAPPY 340 giúp gà con phát triển, tăng trọng tốt, giảm tỷ lệ hao hụt đầu con, giảm còi cọc;
                tăng tỷ lệ đồng đều trên đàn gà. Phân gà khô, giảm mùi hôi chuồng.
            </div>
            <div class="p text-start mb-2">
                + HAPPY 340 là thức ăn hỗn hợp dạng hạt, với dinh dưỡng được phân bổ đồng đều trong hạt thức ăn. Gà ăn
                không bị dính mỏ. Ít rơi vãi, tiết kiệm chi phí thức ăn. Không làm dơ nước uống.
            </div>
            <div class="p text-start mb-2">
                + Sản phẩm có chứa kháng sinh phòng bệnh viêm ruột hoại tử cho gà.
            </div>
            <div class="p text-start mb-5">
                + Màu sắc thay đổi không ảnh hưởng đến chất lượng sản phẩm. Không sử dụng các chất cấm theo quy định
                hiện hành.
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="container">
            <div class="content d-flex between mt-4">
                <div class="h3">Thành phần dinh dưỡng</div>
                <button id="toggle-button2" onclick="toggleDescription('description-content2', 'toggle-button2')">+
                </button>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="description-content2" style="display: none;" class="content mt-3">
            <div class="p text-start mb-2">
                + Protein thô tối thiểu: 21%
            </div>
            <div class="p text-start mb-2">
                + Độ ẩm tối đa: 14%
            </div>
            <div class="p text-start mb-2">
                + Năng lượng trao đổi tối thiểu: 2.950 kcal/kg
            </div>
            <div class="p text-start mb-2">
                + Xơ thô tối đa: 6%
            </div>
            <div class="p text-start mb-2">
                + Canxi trung bình từ 0,6 – 1,2%
            </div>
            <div class="p text-start mb-2">
                + P tổng số từ 0,4 – 1,2%
            </div>
            <div class="p text-start mb-2">
                + Lysine tổng số nhỏ nhất: 0,95%
            </div>
            <div class="p text-start mb-2">
                + Methionine + Cystine tổng số nhỏ nhất: 0,73%
            </div>
            <div class="p text-start mb-2">
                + Threonine tổng số tối thiểu: 0,61%
            </div>
            <div class="p text-start mb-5">
                + Khoáng tổng số tối đa: 15%.
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="container">
            <div class="content d-flex between mt-4">
                <div class="h3">Thành phần nguyên liệu</div>
                <button id="toggle-button3" onclick="toggleDescription('description-content3', 'toggle-button3')">+
                </button>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="description-content3" style="display: none;" class="content mt-3">
            <div class="p text-start mb-2">
                + Bắp
            </div>
            <div class="p text-start mb-2">
                + Tấm
            </div>
            <div class="p text-start mb-2">
                + Cám gạo
            </div>
            <div class="p text-start mb-2">
                + Cám mì
            </div>
            <div class="p text-start mb-2">
                + Bột cá
            </div>
            <div class="p text-start mb-2">
                + Khô dầu đậu nành
            </div>
            <div class="p text-start mb-2">
                + DCP
            </div>
            <div class="p text-start mb-2">
                + Chất acid hóa
            </div>
            <div class="p text-start mb-2">
                + Các acid amin
            </div>
            <div class="p text-start mb-2">
                + Men tiêu hóa
            </div>
            <div class="p text-start mb-2">
                + Các chất bổ sung khoáng
            </div>
            <div class="p text-start mb-5">
                + Vitamin.....
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="container">
            <div class="content d-flex between mt-4">
                <div class="h3">Hướng dẫn sử dụng</div>
                <button id="toggle-button4" onclick="toggleDescription('description-content4', 'toggle-button4')">+
                </button>
            </div>
        </div>
    </div>

    <div class="container">
        <div id="description-content4" style="display: none;" class="content mt-3">
            <div class="p text-start mb-2">
                + Sản phẩm dùng cho gà siêu thịt từ 1 ngày tuổi đến 21 ngày tuổi.
            </div>
            <div class="p text-start mb-2">
                + Số lần cho ăn: 3 - 4 lần/ngày.
            </div>
            <div class="p text-start mb-2">
                + Cung cấp đầy đủ nước sạch, mát cho gà uống tự do
            </div>
            <div class="p text-start mb-2">
                + Gà đạt trọng lượng bình quân: 300 – 360 gram/con
            </div>
            <div class="p text-start mb-2">
                + Không cần pha trộn thêm các loại thực liệu khác.
            </div>
            <div class="p text-start mb-5">
                + Tùy từng trại chăn nuôi và chủng loại heo có thể thay đổi cho phù hợp.
            </div>
        </div>
    </div>

    <div class="container-fluid bg-light">
        <div class="container">
            <div class="content text-center">
                <div class="h2 fw-bold mb-3 mt-5 pt-3">SẢN PHẨM LIÊN QUAN</div>
            </div>
            <div class="slide-show-1 mt-4 carousel slide" data-bs-ride="carousel" data-bs-interval="3000">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <div class="list-products d-flex justify-content-center">
                            <div class="product-card">
                                <div class="product-img">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_01.png" alt="TOP 01" height="250px"
                                         width="200px">
                                </div>
                                <div class="h5 text-h">TOP 01</div>
                                <div class="p pb-4 text-p">Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi</div>
                            </div>
                            <div class="product-card">
                                <div class="product-img">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_01.png" alt="TOP 01" height="250px"
                                         width="200px">
                                </div>
                                <div class="h5 text-h">TOP 01</div>
                                <div class="p pb-4 text-p">Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi</div>
                            </div>
                            <div class="product-card">
                                <div class="product-img">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_01.png" alt="TOP 01" height="250px"
                                         width="200px">
                                </div>
                                <div class="h5 text-h">TOP 01</div>
                                <div class="p pb-4 text-p">Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi</div>
                            </div>
                        </div>
                    </div>
                    <div class="carousel-item">
                        <div class="list-products d-flex justify-content-center">
                            <div class="product-card">
                                <div class="product-img">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_02.png" alt="TOP 02" height="250px"
                                         width="200px">
                                </div>
                                <div class="h5 text-h">TOP 02</div>
                                <div class="p pb-4 text-p">Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi</div>
                            </div>
                            <div class="product-card">
                                <div class="product-img">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_02.png" alt="TOP 02" height="250px"
                                         width="200px">
                                </div>
                                <div class="h5 text-h">TOP 02</div>
                                <div class="p pb-4 text-p">Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi</div>
                            </div>
                            <div class="product-card">
                                <div class="product-img">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_02.png" alt="TOP 02" height="250px"
                                         width="200px">
                                </div>
                                <div class="h5 text-h">TOP 02</div>
                                <div class="p pb-4 text-p">Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi</div>
                            </div>
                        </div>
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target=".carousel" data-bs-slide="prev">
                    &#10094;
                </button>
                <button class="carousel-control-next" type="button" data-bs-target=".carousel" data-bs-slide="next">
                    &#10095;
                </button>
            </div>
        </div>
    </div>
</main>
<div><%@ include file="../../layout/footer.jsp" %></div>

    <!-- Login -->
    <div id="login" class ="login">
        <div id ="login_container">
            <div class="login_close">
                <img src="${pageContext.request.contextPath}/views/template/assets/images/logo/close.png" alt="">
            </div>
      
            <div class="login_header">
                <div class="login_logo_shop">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/header/logo_vina.png" alt="Logo Shop">
                </div>
                <div class="login_header_text">
                    <h3 style="font-size: 24px;">Đăng nhập tài khoản</h3>
                </div>
            </div>
            <div class="login_body">
                <div class="form-floating mb-3 mt-3">
                    <input type="text" class="form-control" id="contact" placeholder="Nhập số điện thoại/email" name="contact" required autocomplete="off"> 
                    <label for="contact">Nhập số điện thoại<span class="req">*</span></label>
                </div>
                <div class="form-floating mb-3 mt-3">
                    <input type="password" class="form-control" id="pwd" placeholder="Nhập mật khẩu" name="password" required autocomplete="off"> 
                    <label for="password">Nhập mật khẩu<span class="req">*</span></label>
                </div>
                <p class="login_forgot"><a href="#">Quên mật khẩu?</a></p>
      
                <button type="submit" class="login_button_submit">Đăng nhập</button>
                <div class="login_split">
                    <p>Hoặc</p>
                </div>
                <div class="login_social">
                    <a href="" class="social_btn login_social_gg">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/social/google.png" alt="">
                        <p>Google</p>
                    </a>
                    <a href="" class="social_btn login_social_zl">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/social/zalo.png" alt="">
                        <p>Zalo</p>
                    </a>
                </div>
            </div>
            <div class="login_footer">
                <p>Bạn chưa có tài khoản? <a href="#" id="go-to-signup">Đăng ký ngay</a></p>
            </div>
        </div>
      </div>
      
      <!-- sign up -->
      <div id="signup" class ="signup">
        <div id="signup_container">
            <div class="signup_close">
                <img src="${pageContext.request.contextPath}/views/template/assets/images/logo/close.png" alt="">
            </div>
      
            <div class="signup_header">
                <div class="signup_logo_shop">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/header/logo_vina.png" alt="Logo Shop">
                </div>
                <div class="signup_header_text">
                    <h3 style="font-size: 24px;">Đăng ký tài khoản</h3>
                </div>
            </div>
            <div class="signup_body">
                <div class="form-floating mb-2 mt-2">
                    <input type="text" class="form-control" id="name" placeholder="Nhập họ và tên" name="Name" required autocomplete="off"> 
                    <label for="password">Nhập họ và tên<span class="req">*</span></label>
                </div>
      
                <div class="form-floating mb-2 mt-2">
                    <input type="text" class="form-control" id="contact" placeholder="Nhập số điện thoại/email" name="Contact" required autocomplete="off"> 
                    <label for="contact">Nhập số điện thoại<span class="req">*</span></label>
                </div>
                <div class="form-floating mb-2 mt-2">
                    <input type="password" class="form-control" id="pwd" placeholder="Nhập mật khẩu" name="password" required autocomplete="off"> 
                    <label for="password">Nhập mật khẩu<span class="req">*</span></label>
                </div>
              
                <div class="form-floating mb-2 mt-2">
                    <input type="password" class="form-control" id="pwd" placeholder="Nhập lại mật khẩu" name="password again" required autocomplete="off"> 
                    <label for="password again">Nhập lại mật khẩu<span class="req">*</span></label>
                </div>
      
                <div class="signup_okay">
                    <p>Bằng việc đăng ký này, bạn đã chấp nhận các chính sách của VINAFEED</p>
                </div>
                <button type="submit" class="signup_button_submit">Đăng ký</button>
                <div class="signup_split">
                    <p>Hoặc</p>
                </div>
                <div class="signup_social">
                    <a href="" class="social_btn signup_social_gg">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/social/google.png" alt="">
                        <p>Google</p>
                    </a>
                    <a href="" class="social_btn signup_social_zl">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/social/zalo.png" alt="">
                        <p>Zalo</p>
                    </a>
                </div>
            </div>
            <div class="signup_footer">
                <p>Bạn đã có tài khoản? <a href="#" id="go-to-login">Đăng nhập ngay</a></p>
            </div>
        </div>
      </div>
      
      <!-- scrollToTopBtn -->
      <button id="scrollToTopBtn"><i class="fa-solid fa-chevron-up"></i></button>

<script>
    function toggleDescription(contentId, buttonId) {
        var content = document.getElementById(contentId);
        var button = document.getElementById(buttonId);

        if (content.style.display === "none") {
            content.style.display = "block";
            button.textContent = "-";
            button.style.fontSize = "70px";
            button.style.height = "33px";
            button.style.lineHeight = "33px";
        } else {
            content.style.display = "none";
            button.textContent = "+";
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

        function showSlide(index) {
            slides.forEach((slide, i) => {
                slide.classList.remove("active");  // Hide all slides
            });
            slides[index].classList.add("active");  // Show the current slide
        }

        function nextSlide() {
            currentIndex = (currentIndex + 1) % slides.length;
            showSlide(currentIndex);
        }

        function changeSlide(direction) {
            // Clear the interval to pause automatic sliding when a button is clicked
            clearInterval(slideInterval);

            // Update the slide index
            currentIndex = (currentIndex + direction + slides.length) % slides.length;
            showSlide(currentIndex);

            // Restart the interval for automatic sliding after a short delay
            slideInterval = setInterval(nextSlide, 5000);
        }

        // Initial slide display
        showSlide(currentIndex);

        // Set the interval for automatic sliding
        slideInterval = setInterval(nextSlide, 5000);

        // Attach button events
        window.changeSlideProducts = changeSlide;  // Expose the function to the global scope
    });
</script>

</body>
</html>