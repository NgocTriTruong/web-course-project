<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
        <c:if test="${not empty posts and posts.size() > 0}">
            <div class="trending-news row">
                <div class="col-md-8 mb-4">
                    <c:set var="mainPost" value="${posts[0]}" />
                    <a href="${pageContext.request.contextPath}/news/detail?id=${mainPost.id}">
                        <div class="card news-card">
                            <img src="${not empty mainPost.img ? pageContext.request.contextPath.concat(mainPost.img) : pageContext.request.contextPath.concat('/views/template/assets/images/default.jpg')}" class="card-img-top" alt="${mainPost.title}">
                            <div class="main-news-title card-img-overlay d-flex align-items-end">
                                <h5 class="main-card-title text-white p-2 w-100">${mainPost.title}</h5>
                            </div>
                        </div>
                    </a>
                </div>

                <c:if test="${posts.size() > 1}">
                    <div class="col-md-4 mb-4">
                        <div class="card news-card mb-3 h-100 border-0">
                            <c:set var="secondPost" value="${posts[1]}" />
                            <a href="${pageContext.request.contextPath}/news/detail?id=${secondPost.id}">
                                <img src="${not empty secondPost.img ? pageContext.request.contextPath.concat(secondPost.img) : pageContext.request.contextPath.concat('/views/template/assets/images/default.jpg')}" class="card-img-top" alt="${secondPost.title}">
                                <div class="card-body">
                                    <p class="post-date text-danger"><i class="fas fa-calendar-alt"></i> <f:formatDate value="${secondPost.createDate}" pattern="dd/MM/yyyy" /></p>
                                    <h5 class="card-title mt-2">${secondPost.title}</h5>
                                    <p class="card-text">${secondPost.content}</p>
                                </div>
                            </a>
                        </div>
                    </div>
                </c:if>
            </div>
        </c:if>

        <div class="news-container">
            <c:forEach var="post" items="${posts}" begin="2" varStatus="loop">
                <div class="w-100 col-md-4 mb-4">
                    <div class="card news-card d-flex flex-row mb-3 h-100 border-0">
                        <img src="${not empty post.img ? pageContext.request.contextPath.concat(post.img) : pageContext.request.contextPath.concat('/views/template/assets/images/default.jpg')}" class="sub-news-image" alt="${post.title}" style="width: 150px; height: auto;">
                        <div class="sub-card-body d-flex flex-column ms-3 p-4 row-gap-3">
                            <p class="post-date text-danger mb-1">
                                <i class="fas fa-calendar-alt"></i> <f:formatDate value="${post.createDate}" pattern="dd/MM/yyyy" />
                            </p>
                            <h5 class="card-title mt-2">${post.title}</h5>
                            <p class="card-text">${post.content}</p>
                            <a href="${pageContext.request.contextPath}/news/detail?id=${post.id}" class="news-detail-btn btn mt-2">Xem chi tiết</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <div class="pagination">
        <c:if test="${totalPages > 1}">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <button class="page-button active" data-page="${i}" data-category="tin_tuc">${i}</button>
                    </c:when>
                    <c:otherwise>
                        <button class="page-button" data-page="${i}" data-category="tin_tuc">${i}</button>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </c:if>
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