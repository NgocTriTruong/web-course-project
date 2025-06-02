<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
  <meta http-equiv="x-ua-compatible" content="ie=edge"/>
  <title>Thêm báo cáo sự cố</title>
  <!-- Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

  <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>
  <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
</head>

<body>
<!--Main Navigation-->
<%@ include file="layout/header.jsp" %>

<!--Main layout-->
<main class="mb-5">
  <section class="mb-5 text-center text-md-start">
    <div class="p-5" style="height: 200px; background: linear-gradient(to right, hsl(78, 50%, 48%), hsl(78, 50%, 68%));"></div>
    <div class="container px-4">
      <div class="card shadow-0" style="margin-top: -100px;">
        <div class="card-body py-5 px-5">
          <div class="row gx-lg-4 align-items-center">
            <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
              <h1 class="">Thêm báo cáo sự cố</h1>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>

  <div class="container px-4">
    <!-- Hiển thị thông báo -->
    <c:if test="${not empty message}">
      <div class="alert alert-success alert-dismissible fade show" role="alert">
          ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
      </div>
    </c:if>

    <!-- Form thêm sự cố -->
    <div class="card">
      <div class="card-body">
        <form action="${pageContext.request.contextPath}/add-issue" method="post">
          <div class="row mb-3 product-row">
            <div class="col-md-3">
              <label class="form-label">Sản phẩm (ID hoặc tên)</label>
              <input type="text" class="form-control product-id" name="productIdOrName" required>
              <input type="hidden" class="product-id-hidden" name="productId">
            </div>
            <div class="col-md-3">
              <label class="form-label">Tên sản phẩm</label>
              <input type="text" class="form-control product-name" disabled>
            </div>
          </div>
          <div class="mb-4">
            <label for="reason" class="form-label">Lý do sự cố</label>
            <textarea class="form-control" id="reason" name="reason" rows="4" required></textarea>
          </div>
          <div class="mb-4">
            <label for="quantity" class="form-label">Số lượng</label>
            <input type="number" class="form-control" id="quantity" name="quantity" min="1" required/>
          </div>
          <div class="mb-4">
            <label for="status" class="form-label">Trạng thái</label>
            <select class="form-select" id="status" name="status" required>
              <option value="0">Chưa giải quyết</option>
              <option value="1">Đã giải quyết</option>
            </select>
          </div>
          <div class="d-flex justify-content-end">
            <button type="submit" class="btn bg_green text-white" id="submitBtn">Lưu</button>
            <a href="${pageContext.request.contextPath}/issueManagement" class="btn bg_yellow text-white ms-2">Hủy</a>
          </div>
        </form>
      </div>
    </div>
  </div>
</main>

<!-- Footer -->
<footer class="bottom-0 w-100 text-center py-2 bg-light">
  <p class="pt-3" style="color: rgba(0, 0, 0, 0.5);">©2024 Group-11</p>
</footer>

<script>
  document.addEventListener('input', function(e) {
    if (e.target.classList.contains('product-id')) {
      const productInput = e.target;
      const productRow = productInput.closest('.product-row');
      const productNameInput = productRow.querySelector('.product-name');
      const productId = productRow.querySelector('.product-id-hidden');
      const value = productInput.value.trim();

      if (value.length > 0) {
        fetch("${pageContext.request.contextPath}/api/product?id=" + encodeURIComponent(value))
                .then(response => response.json())
                .then(data => {
                  if (data.name && !data.error) {
                    productNameInput.value = data.name;
                    productId.value = data.id;
                  } else {
                    productNameInput.value = 'Không có sản phẩm';
                  }
                })
                .catch(error => {
                  console.error('Error fetching product:', error);
                  productNameInput.value = 'Không có sản phẩm';
                });
      } else {
        productNameInput.value = '';
      }
    }
  });
</script>
</body>
</html>