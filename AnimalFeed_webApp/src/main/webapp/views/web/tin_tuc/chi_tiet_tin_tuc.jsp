<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${post.title}" /></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chi_tiet_tin_tuc.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/font-awesome/all.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/jquery-3.7.1.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>

    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger
            intent="WELCOME"
            chat-title="VinaFeed_chat"
            agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264"
            language-code="vi"
    ></df-messenger>
</head>
<body>
<div><%@ include file="../layout/header.jsp" %></div> <!-- Chỉ giữ ở đầu -->
<main class="main" style="margin-top: 115px;">
    <div class="container my-5 tin_tuc">
        <c:if test="${not empty post}">
            <!-- Header section -->
            <div class="text-center mb-4">
                <h1 class="display-4 text-start"><c:out value="${post.title}" /></h1>
                <p class="text-danger text-start">
                    <i class="fas fa-calendar-alt"></i> <f:formatDate value="${post.createDate}" pattern="EEEE, dd/MM/yyyy, HH:mm 'GMT+7'" />
                </p>
            </div>

            <!-- Article content section -->
            <div class="row w-100">
                <div class="bai_viet">
                    <p><c:out value="${post.content}" escapeXml="false" /></p>

                    <!-- Hiển thị ảnh nếu có -->
                    <c:if test="${not empty post.img}">
                        <div class="text-center my-4">
                            <img src="${pageContext.request.contextPath}${post.img}" alt="${post.title}" class="img-fluid">
                            <p class="text-muted img-label">Hình ảnh minh họa</p>
                        </div>
                    </c:if>

                    <!-- Thêm các đoạn nội dung bổ sung nếu có (tùy chọn) -->
                </div>
            </div>

            <div class="text-center my-4">
                <p>Chia sẻ:</p>
                <a href="#" id="facebook">
                    <i class="fa-brands fa-facebook-f"></i>
                </a>
                <a href="#" id="twiter">
                    <i class="fa-brands fa-twitter"></i>
                </a>
            </div>
        </c:if>
        <c:if test="${empty post}">
            <p class="text-center">Bài viết không tồn tại hoặc đã bị xóa.</p>
        </c:if>

        <!-- Related Articles section -->
        <div id="newsCarousel" class="carousel slide related-news mb-5" data-bs-ride="carousel">
            <div class="carousel-inner w-75 ms-auto me-auto">
                <div class="carousel-item active">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="news-card">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news2.jpg" alt="News 1">
                                <div class="card-body">
                                    <h5 class="card-title">Vinafeed Group Được Vinh Danh Thương Hiệu Vàng Nông Nghiệp...</h5>
                                    <p class="card-text">Vinafeed Group vinh dự đạt giải Thương Hiệu Vàng Nông Nghiệp Việt Nam năm nay...</p>
                                    <a href="${pageContext.request.contextPath}/news/detail?id=2" class="text-danger">Xem chi tiết</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="news-card">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news3.jpg" alt="News 2">
                                <div class="card-body">
                                    <h5 class="card-title">Vinafeed Group Khẳng Định Vị Thế Trong Ngành Nông Nghiệp Công...</h5>
                                    <p class="card-text">Ngày 02/08 vừa qua, tại Hà Nội...</p>
                                    <a href="${pageContext.request.contextPath}/news/detail?id=3" class="text-danger">Xem chi tiết</a>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="news-card">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news4.jpg" alt="News 3">
                                <div class="card-body">
                                    <h5 class="card-title">Vinafeed Group Tiếp Tục Giữ Vững Danh Hiệu "Hàng Việt Nam Chất...</h5>
                                    <p class="card-text">Tối ngày 14/03 vừa qua lễ công bố "Hàng Việt Nam chất lượng cao năm 2024"...</p>
                                    <a href="${pageContext.request.contextPath}/news/detail?id=4" class="text-danger">Xem chi tiết</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Controls -->
            <button class="carousel-control-prev" type="button" data-bs-target="#newsCarousel" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#newsCarousel" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>
</main>
<div><%@ include file="../layout/footer.jsp" %></div> <!-- Chỉ giữ ở cuối -->

<script>
    $(document).ready(function() {
        // Initialize the carousel
        $('#newsCarousel').carousel({
            interval: 3000 // Set carousel interval (3 seconds per slide)
        });

        // Optional: Add click events for custom navigation or other jQuery-based interactions
        $('.carousel-control-prev').click(function() {
            $('#newsCarousel').carousel('prev');
        });

        $('.carousel-control-next').click(function() {
            $('#newsCarousel').carousel('next');
        });
    });
</script>
</body>
</html>