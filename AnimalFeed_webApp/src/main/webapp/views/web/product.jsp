<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Web Project</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/product.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/font-awesome/all.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

<%--  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>--%>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/login.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/signup.css">

  <!-- scrollToTopBtn -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/scrollToTopBtn.css">
  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/scrollToTopBtn.js" defer></script>

  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js"></script>

</head>
<body>
<%@ include file="layout/header.jsp" %>
  <main style="margin-top: 65px;" class="bg-light">
    <section id="background-section">
      <h1 class="outline">SẢN PHẨM</h1>
    </section>

    <section class="w-75 d-flex justify-content-center align-items-center me-auto ms-auto mt-4 mb-4 p-3" id="introduction-section">
      <span class="align-content-center h3" id="production-text">
        Chúng tôi luôn lấy chất lượng sản phẩm làm giá trị cốt lõi cho sự phát triển của mình, cam kết đưa ra thị trường những sản phẩm có chất lượng cao và đảm bảo an toàn cho vật nuôi và an toàn cho cộng đồng.
      </span>
    </section>

    <c:forEach var="ca" items="${categoriesData}">
      <c:choose>
        <c:when test="${ca.id % 2 != 0}">
          <section class="w-100 d-flex justify-content-between align-items-center">
            <img src="${ca.img}" class="image_dv_number" alt="${ca.name}" width="360px" height="360px" style="margin-left: 732px; margin-bottom: 22px;">

            <div class="heo-section d-flex mb-4">
              <div class="d-flex w-100 justify-content-around mt-4">
                <div id="productPigCarousel${ca.id}" class="carousel slide" data-bs-ride="carousel">
                  <div class="carousel-inner">

                    <c:forEach var="p" items="${productsData}">
                      <c:if test="${p.cat_id == ca.id}">
                        <div class="carousel-item ${p == productsData[0] ? 'active' : ''}">
                          <div class="d-flex justify-content-center gap-4">
                            <div class="card">
                              <img src="${p.img}" class="card-img-top" alt="${p.name}">
                              <div class="card-body text-center">
                                <h5 class="card-title">${p.name}</h5>
                                <p class="card-text">${p.description}</p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </c:if>
                    </c:forEach>
                  </div>
                  <!-- Điều hướng carousel -->
                  <button class="carousel-control-prev" type="button" data-bs-target="#productPigCarousel${ca.id}" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                  </button>
                  <button class="carousel-control-next" type="button" data-bs-target="#productPigCarousel${ca.id}" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                  </button>
                </div>
              </div>
            </div>

            <div class="flex-column text-start me-5 pe-5">
              <h2 class="text-dark">${ca.name}</h2>
              <a href="list-product?categoryId=${ca.id}" class="btn custom-btn" style="background-color: #fcae18; font-size: 17px; border-radius: 30px;">Xem tất cả</a>
            </div>
          </section>
        </c:when>
        <c:otherwise>
          <section class="w-100 d-flex justify-content-between align-items-center">
            <div class="flex-column text-end ms-5 ps-5">
              <h2 class="text-dark">${ca.name}</h2>
              <a href="list-product?categoryId=${ca.id}" class="btn custom-btn" style="background-color: #fcae18; font-size: 17px; border-radius: 30px;">Xem tất cả</a>
            </div>

            <div class="ga-section d-flex mb-4">
              <div class="d-flex w-100 justify-content-around mt-4">
                <div id="productChickenCarousel${ca.id}" class="carousel slide" data-bs-ride="carousel">
                  <div class="carousel-inner">

                    <c:forEach var="p" items="${productsData}">
                      <c:if test="${p.cat_id == ca.id}">
                        <div class="carousel-item ${p == productsData[0] ? 'active' : ''}">
                          <div class="d-flex justify-content-center gap-4">
                            <div class="card">
                              <img src="${p.img}" class="card-img-top" alt="${p.name}">
                              <div class="card-body text-center">
                                <h5 class="card-title">${p.name}</h5>
                                <p class="card-text">${p.description}</p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </c:if>
                    </c:forEach>
                  </div>
                </div>
              </div>
              <!-- Điều hướng carousel -->
              <button class="carousel-control-prev" type="button" data-bs-target="#productPigCarousel${ca.id}" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button class="carousel-control-next" type="button" data-bs-target="#productPigCarousel${ca.id}" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>
            <img src="${ca.img}" class="image_dv_number" alt="${ca.name}" width="360px" height="360px" style="margin-left: 400px; margin-bottom: 22px;">
          </section>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </main>

<%@ include file="layout/near_footer.jsp" %>
<%@ include file="layout/footer.jsp" %>

<!-- scrollToTopBtn -->
<button id="scrollToTopBtn"><i class="fa-solid fa-chevron-up"></i></button>

<script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
</body>
</html>