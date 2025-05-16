<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
  <meta http-equiv="x-ua-compatible" content="ie=edge"/>
  <title>Chỉnh sửa danh mục</title>
  <!-- Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">
  <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>
  <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
</head>

<body>
<%@ include file="layout/header.jsp" %>

<!--Main layout-->
<main class="mb-5" style="margin-top: 100px;">
  <div class="container px-4">
    <!-- Hiển thị thông báo lỗi hoặc thành công nếu có -->
    <% String error = (String) session.getAttribute("error");
      if (error != null) { %>
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      <%= error %>
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% session.removeAttribute("error"); %>
    <% } %>

    <% String message = (String) session.getAttribute("message");
      if (message != null) { %>
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      <%= message %>
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    <% session.removeAttribute("message"); %>
    <% } %>

    <a href="${pageContext.request.contextPath}/category-management-admin" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
      <i class="fas fa-angle-left"></i> Quay lại
    </a>
    <div class="mb-3 bg_green p-2">
      <span class="text-white h5">Chỉnh sửa danh mục</span>
    </div>
    <form class="border p-5" method="post" enctype="multipart/form-data">
      <input type="hidden" name="categoryId" value="${category.id}">
      <div class="row">
        <div class="col-md-4">
          <label for="category" class="form-label style_18"><b>Loại sản phẩm</b></label>
          <i class="fas fa-bars ms-2"></i>
          <input type="text" class="form-control" id="category" name="category"
                 value="${category.name}" placeholder="Nhập loại sản phẩm..." required>
        </div>
        <div class="col-md-4">
          <div class="mb-3">
            <label for="avatar" class="form-label style_18"><b>Ảnh minh họa</b></label>
            <i class="fas fa-image ms-2"></i>
            <input class="form-control" type="file" id="avatar" name="avatar" accept="image/*">
            <small class="form-text text-muted">Ảnh hiện tại: ${category.img}</small>
          </div>
        </div>
        <div class="col-md-4">
          <div class="mb-3">
            <div>
              <label class="form-label style_18"><b>Trạng thái</b></label>
              <i class="fas fa-toggle-on ms-2"></i>
            </div>
            <div class="form-check-inline">
              <input class="form-check-input" type="radio" name="status" id="active" value="1" ${category.status == 1 ? 'checked' : ''}>
              <label class="form-check-label" for="active">Hoạt động</label>
            </div>
            <div class="form-check-inline">
              <input class="form-check-input" type="radio" name="status" id="inactive" value="0" ${category.status == 0 ? 'checked' : ''}>
              <label class="form-check-label" for="inactive">Ngưng hoạt động</label>
            </div>
          </div>
        </div>
      </div>
      <button type="submit" class="btn bg_green text-white fw-bold">Cập nhật</button>
    </form>
  </div>
</main>
<!--Main layout-->

<footer class="position-fixed bottom-0 w-100 text-center py-2 bg-light">
  <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>
</body>
</html>