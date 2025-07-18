<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Chính Sách - Phúc Lợi</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/tuyendung.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">
  <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js" defer></script>
  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>
  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js"></script>

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
<main style="margin-top: 100px;">
  <div class="tuyendung">
    <!-- Banner Section -->
    <div class="background-section">
      <h1 class="outline">TUYỂN DỤNG</h1>
    </div>

    <!-- Header Section -->
    <header class="header-tuyendung">
      <nav class="navbar-tuyendung">
        <a href="#policies">Chính Sách - Phúc Lợi</a>
        <a href="#job-listings">Vị trí tuyển dụng</a>
      </nav>
    </header>

    <!-- "Chính Sách - Phúc Lợi" Header -->
    <h2 id="policies">Chính Sách - Phúc Lợi</h2>

    <!-- Phúc lợi công ty -->
    <section class="benefits">
      <div class="benefit-card">
        <img src="${pageContext.request.contextPath}/views/template/assets/images/icons_tuyendung/baohiem.png" alt="Bảo hiểm" class="benefit-icon">
        <h3>Bảo hiểm, trợ cấp</h3>
        <p>Tại VINAFEED, sức khỏe của bạn là điều chúng tôi quan tâm hàng đầu. Với các chế độ bảo hiểm được cung cấp, bạn có thể yên tâm khi hầu hết các bệnh sẽ được hỗ trợ.</p>
      </div>
      <div class="benefit-card">
        <img src="${pageContext.request.contextPath}/views/template/assets/images/icons_tuyendung/luongthuong.png" alt="Lương, thưởng" class="benefit-icon">
        <h3>Lương, thưởng</h3>
        <p>Người lao động được hưởng chế độ lương, thưởng theo quy chế rõ ràng. Người lao động có thể nhận phụ cấp, trợ cấp và các đãi ngộ theo tiêu chuẩn và địa điểm công việc.</p>
      </div>
      <div class="benefit-card">
        <img src="${pageContext.request.contextPath}/views/template/assets/images/icons_tuyendung/phattrien.png" alt="Đào tạo và phát triển" class="benefit-icon">
        <h3>Đào tạo và phát triển</h3>
        <p>Đào tạo và phát triển nguồn nhân lực là ưu tiên hàng đầu của ban lãnh đạo. Công ty hỗ trợ các khóa học nâng cao chuyên môn nghiệp vụ, quản lý, lãnh đạo cấp quốc tế.</p>
      </div>
    </section>

    <!-- "Vị Trí Ứng Tuyển" Header -->
    <h2 id="job-listings">Vị Trí Ứng Tuyển</h2>

    <!-- Tuyển dụng -->
    <section class="job-listings" style="max-width: 1050px; margin: 0 auto;">
      <p>Để đáp ứng nhân sự cho nhà máy thức ăn chăn nuôi Vina, hiện nay chúng tôi đang có nhu cầu tuyển dụng nhân sự cho các vị trí. Để biết thêm thông tin về từng vị trí vui lòng liên hệ sdt bên dưới từng vị trí để ứng tuyển.</p>

      <div class="search-bar">
        <input type="text" id="searchInput" placeholder="Từ khóa tìm kiếm">
        <select id="locationFilter">
          <option value="">Chọn địa điểm làm việc</option>
          <option value="Tỉnh Tây Ninh">Tỉnh Tây Ninh</option>
          <option value="Tỉnh Đồng Nai">Tỉnh Đồng Nai</option>
          <option value="Tỉnh Hà Nam">Tỉnh Hà Nam</option>
          <option value="Thành Phố Hồ Chí Minh">Thành Phố Hồ Chí Minh</option>
          <option value="Tỉnh Long An">Tỉnh Long An</option>
          <option value="Tỉnh Kiên Giang">Tỉnh Kiên Giang</option>
          <option value="Tỉnh Cà Mau">Tỉnh Cà Mau</option>
          <option value="Tỉnh Tiền Giang">Tỉnh Tiền Giang</option>
        </select>
        <button onclick="searchJobs()">Tìm nhanh</button>
      </div>

      <table class="job-table" id="jobTable">
        <thead>
        <tr>
          <th>Vị trí</th>
          <th>Nơi làm việc</th>
          <th>Liên hệ</th>
        </tr>
        </thead>
        <tbody id="jobList">
        <!-- Hiển thị danh sách công việc từ database -->
        <c:forEach var="job" items="${jobList}">
          <tr>
            <td>${job.position}</td>
            <td>${job.location}</td>
            <td>${job.phone}</td>
          </tr>
        </c:forEach>
        </tbody>
      </table>

      <div class="pagination" id="pagination"></div>
    </section>
  </div>
</main>
<%@ include file="layout/near_footer.jsp" %>
<%@ include file="layout/footer.jsp" %>
<script src="${pageContext.request.contextPath}/views/template/assets/scripts/tuyendung.js"></script>
</body>
</html>
