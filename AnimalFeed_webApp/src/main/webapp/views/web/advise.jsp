<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tư vấn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/tu_van.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

    <!-- Bootstrap -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/tu_van.css"></link>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>
    
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

<%@ include file="layout/header.jsp" %>
<main style="margin-top: 90px;">
    <div class="tu_van">
        <div class="background-section">
            <h1 class="outline">TƯ VẤN KỸ THUẬT</h1>
        </div>

        <div class="container-fluid bg-light">
            <div class="container">
                <div class="topic reverse">
                    <div class="image">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/chicken_2.jpg" alt="Chickens">
                    </div>
                    <div class="content">
                        <h2>CHUYÊN ĐỀ GÀ</h2>
                        <a href="advise-detail"><button>Xem tư vấn</button></a>
                    </div>
                </div>

                <div class="topic">
                    <div class="image">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/duck_2.jpg" alt="Ducks">
                    </div>
                    <div class="content">
                        <h2>CHUYÊN ĐỀ VỊT</h2>
                        <a href="${pageContext.request.contextPath}/views/web/advise_detail.jsp"><button>Xem tư vấn</button></a>
                    </div>
                </div>

                <div class="topic reverse">
                    <div class="image">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/pig_2.jpg" alt="Chickens">
                    </div>
                    <div class="content">
                        <h2>CHUYÊN ĐỀ HEO</h2>
                        <a href="${pageContext.request.contextPath}/views/web/advise_detail.jsp"><button>Xem tư vấn</button></a>
                    </div>
                </div>

                <div class="topic">
                    <div class="image">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/cow_2.jpg" alt="Ducks">
                    </div>
                    <div class="content">
                        <h2>CHUYÊN ĐỀ BÒ</h2>
                        <a href="${pageContext.request.contextPath}/views/web/advise_detail.jsp"><button>Xem tư vấn</button></a>
                    </div>
                </div>

                <div class="topic reverse">
                    <div class="image">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/shrimp.png" alt="Chickens">
                    </div>
                    <div class="content">
                        <h2>CHUYÊN ĐỀ TÔM</h2>
                        <a href="${pageContext.request.contextPath}/views/web/advise_detail.jsp"><button>Xem tư vấn</button></a>
                    </div>
                </div>

                <div class="topic">
                    <div class="image">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/fish_2.jpg" alt="Ducks">
                    </div>
                    <div class="content">
                        <h2>CHUYÊN ĐỀ CÁ</h2>
                        <a href="${pageContext.request.contextPath}/views/web/advise_detail.jsp"><button>Xem tư vấn</button></a>
                    </div>
                </div>

                <div class="topic reverse">
                    <div class="image">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/goat_2.jpg" alt="Chickens">
                    </div>
                    <div class="content">
                        <h2>CHUYÊN ĐỀ DÊ</h2>
                        <a href="${pageContext.request.contextPath}/views/web/advise_detail.jsp"><button>Xem tư vấn</button></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<%@ include file="layout/near_footer.jsp" %>
<%@ include file="layout/footer.jsp" %>

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
      
                <button type="submit" id="login-submit" class="login_button_submit">Đăng nhập</button>
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


<script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>

</body>
</html>