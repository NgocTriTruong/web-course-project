<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tin tức</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/tin_tuc.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/font-awesome/all.css" />
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/jquery-3.7.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <!-- scrollToTopBtn -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/scrollToTopBtn.css">
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/scrollToTopBtn.js" defer></script>

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
<%@ include file="../layout/header.jsp" %> <!-- Include header -->
<main class="d-block tin_tuc mb-5" style="margin-top: 90px;">
    <section id="background-section">
        <h1 class="outline">TIN TỨC</h1>
    </section>

    <div class="container my-5">
        <div class="trending-news row">
            <div class="col-md-8 mb-4">
                <a href="${pageContext.request.contextPath}/views/web/tin_tuc/chi_tiet_tin_tuc.jsp">
                    <div class="card news-card">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news1.jpg" class="card-img-top" alt="News Image">
                        <div class="main-news-title card-img-overlay d-flex align-items-end">
                            <h5 class="main-card-title text-white p-2 w-100">Vinafeed Group Được Vinh Danh Tại Giải Thưởng Doanh Nghiệp Xuất Sắc Châu Á - APEA 2024</h5>
                        </div>
                    </div>
                </a>
            </div>

            <div class="col-md-4 mb-4">
                <div class="card news-card mb-3 h-100 border-0">
                    <a href="${pageContext.request.contextPath}/views/web/tin_tuc/chi_tiet_tin_tuc.jsp">
                        <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news2.jpg" class="card-img-top" alt="News Image">
                        <div class="card-body">
                            <p class="post-date text-danger"><i class="fas fa-calendar-alt"></i> 06/08/2024</p>
                            <h5 class="card-title mt-2">Vinafeed Group Khẳng Định Vị Thế Trong Ngành Nông Nghiệp Công Nghệ Cao Năm 2024</h5>
                            <p class="card-text">Ngày 02/08 vừa qua, tại Hà Nội, Công ty cổ phần Báo cáo Đánh giá Việt Nam (Vietnam Report) phối hợp cùng Báo VietNamNet, chính thức tổ chức Lễ công bố "TOP 10 ...</p>
                        </div>
                    </a>
                </div>
            </div>
        </div>

        <div class="news-container">
            <div class="w-100 col-md-4 mb-4">
                <div class="card news-card d-flex flex-row mb-3 h-100 border-0">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news1.jpg" class="sub-news-image" alt="News Image" style="width: 150px; height: auto;">
                    <div class="sub-card-body d-flex flex-column ms-3 p-4 row-gap-3">
                        <p class="post-date text-danger mb-1">
                            <i class="fas fa-calendar-alt"></i> 15/10/2024
                        </p>
                        <h5 class="card-title mt-2">
                            Vinafeed Group Được Vinh Danh Tại Giải Thưởng Doanh Nghiệp Xuất Sắc Châu Á - APEA 2024
                        </h5>
                        <p class="card-text">Tối ngày 3/10 vừa qua, tại Lễ trao giải The Asia Pacific Enterprise Awards (APEA) 2024 do Hiệp hội Doanh nghiệp Châu Á - Enterprise Asia tổ chức tại TP.HCM, Vinafeed Group ...</p>
                        <a href="${pageContext.request.contextPath}/views/web/tin_tuc/chi_tiet_tin_tuc.jsp" class="news-detail-btn btn btn-primary mt-2">Xem chi tiết</a>
                    </div>
                </div>
            </div>

            <div class="w-100 col-md-4 mb-4">
                <div class="card news-card d-flex flex-row mb-3 h-100 border-0">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news2.jpg" class="sub-news-image" alt="News Image" style="width: 150px; height: auto;">
                    <div class="sub-card-body d-flex flex-column ms-3 p-4 row-gap-3">
                        <p class="post-date text-danger mb-1">
                            <i class="fas fa-calendar-alt"></i> 06/08/2024
                        </p>
                        <h5 class="card-title mt-2">
                            Vinafeed Group Khẳng Định Vị Thế Trong Nghành Nông Nghiệp Công Nghệ Cao Năm 2024
                        </h5>
                        <p class="card-text">Ngày 02/08 vừa qua, tại Hà Nội, Công ty cổ phần Báo cáo Đánh giá Việt Nam (Vietnam Report) phối hợp cùng Báo VietNamNet, chính thức tổ chức Lễ công bố "TOP 10 ...</p>
                        <a href="${pageContext.request.contextPath}/views/web/tin_tuc/chi_tiet_tin_tuc.jsp" class="news-detail-btn btn btn-primary mt-2">Xem chi tiết</a>
                    </div>
                </div>
            </div>

            <div class="w-100 col-md-4 mb-4">
                <div class="card news-card d-flex flex-row mb-3 h-100 border-0">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news1.jpg" class="sub-news-image" alt="News Image" style="width: 150px; height: auto;">
                    <div class="sub-card-body d-flex flex-column ms-3 p-4 row-gap-3">
                        <p class="post-date text-danger mb-1">
                            <i class="fas fa-calendar-alt"></i> 15/10/2024
                        </p>
                        <h5 class="card-title mt-2">
                            Vinafeed Group Được Vinh Danh Tại Giải Thưởng Doanh Nghiệp Xuất Sắc Châu Á - APEA 2024
                        </h5>
                        <p class="card-text">Tối ngày 3/10 vừa qua, tại Lễ trao giải The Asia Pacific Enterprise Awards (APEA) 2024 do Hiệp hội Doanh nghiệp Châu Á - Enterprise Asia tổ chức tại TP.HCM, Vinafeed Group ...</p>
                        <a href="${pageContext.request.contextPath}/views/web/tin_tuc/chi_tiet_tin_tuc.jsp" class="news-detail-btn btn btn-primary mt-2">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="pagination">
        <button class="page-button" data-page="1" data-category="tin_tuc">1</button>
        <button class="page-button" data-page="2" data-category="tin_tuc">2</button>
    </div>

</main>
<%@ include file="../layout/footer.jsp" %> <!-- Include footer -->

<!-- scrollToTopBtn -->
<button id="scrollToTopBtn"><i class="fa-solid fa-chevron-up"></i></button>

<!-- Optional JavaScript -->
<!-- Bootstrap Bundle with Popper -->
<script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/views/template/assets/scripts/pagination.js"></script>
</body>
</html>
