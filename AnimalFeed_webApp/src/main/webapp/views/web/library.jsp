<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Thư viện</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/thu_vien.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">
</head>
<body>
<%@ include file="layout/header.jsp" %>

<main style="margin-top: 105px;">
  <div class="thu-vien">
    <section id="thu-vien-banner">
      <div class="overlay"></div> <!-- Lớp phủ mờ -->
      <h1 class="thu-vien-content">Thư Viện</h1>
    </section>

    <section class="templates-library bg-light">
      <div class="thu-vien-2">
        <h2>Hãy cùng nhau khám phá thư viện của chúng tôi</h2>
        <div class="features">
          <!-- Feature Cards -->
          <div class="feature-card">
            <img src="${pageContext.request.contextPath}/views/template/assets/images/thu_vien/img.png" alt="Modern Designs" class="feature-image">
            <h3>Tham quan Vina đại lý của Nguyễn Minh Thái</h3>
          </div>
          <div class="feature-card">
            <img src="${pageContext.request.contextPath}/views/template/assets/images/thu_vien/img_2.png" alt="Easy Customization" class="feature-image">
            <h3>Khóa tập huấn luyện "Quản lý nhà máy thức ăn chăn nuôi" tại Thái Lan</h3>
          </div>
          <div class="feature-card">
            <img src="${pageContext.request.contextPath}/views/template/assets/images/thu_vien/img_3.png" alt="High-Quality Graphics" class="feature-image">
            <h3>Chuyến tham quan Vina của đại lý Bạch Thị Thu Hương</h3>
          </div>
          <div class="feature-card">
            <img src="${pageContext.request.contextPath}/views/template/assets/images/thu_vien/img_4.png" alt="Multiple Formats" class="feature-image">
            <h3>Chuyến tham quan Vina của đại lý Khu vực Hậu Giang</h3>
          </div>
          <div class="feature-card">
            <img src="${pageContext.request.contextPath}/views/template/assets/images/thu_vien/img_5.png" alt="Industry-Specific" class="feature-image">
            <h3>Chuyến tham quan Vina của đại lý Hoàng Thị Kim Anh</h3>
          </div>
          <div class="feature-card">
            <img src="${pageContext.request.contextPath}/views/template/assets/images/thu_vien/img_6.png" alt="Responsive Layouts" class="feature-image">
            <h3>Tham quan Vina đại lý của Nguyễn Thị Hoa</h3>
          </div>
        </div>
        <div class="videos">
          <h2>Video nổi bật</h2>
          <div class="video-container">
            <iframe src="https://player.vimeo.com/video/555611952" width="560" height="315" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
          </div>
        </div>
      </div>
    </section>
  </div>
</main>
<%@ include file="layout/near_footer.jsp" %>
<%@ include file="layout/footer.jsp" %>

<script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js" defer></script>
<script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>
<script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js" defer></script>
<script>
  document.querySelectorAll('.feature-card').forEach(card => {
    card.addEventListener('mouseenter', () => {
      card.style.transform = 'rotateY(10deg) rotateX(10deg)';
    });

    card.addEventListener('mouseleave', () => {
      card.style.transform = 'rotateY(0deg) rotateX(0deg)';
    });
  });
</script>
</body>
</html>
