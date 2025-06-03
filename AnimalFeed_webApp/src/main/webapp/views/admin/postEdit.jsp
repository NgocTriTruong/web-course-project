<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
  <meta http-equiv="x-ua-compatible" content="ie=edge"/>
  <title>Chỉnh sửa bài viết</title>
  <!-- Bootstrap CSS -->
  <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
  <!-- Font Awesome -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

  <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>

  <!-- js add header -->
  <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
</head>

<body>
<!-- Start your project here-->
<!--Main Navigation-->
<%@ include file="layout/header.jsp" %>

<!--Main Navigation-->

<!--Main layout-->
<main class="mb-5" style="margin-top: 100px;">

  <!-- Container for demo purpose -->
  <div class="container px-4">
    <a href="${pageContext.request.contextPath}/post-management" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
      <i class="fas fa-angle-left"></i> Quay lại
    </a>
    <div class="mb-3 bg_green p-2">
      <span class="text-white h5">Chỉnh sửa bài viết</span>
    </div>
    <c:if test="${not empty errorMessage}">
      <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/post-edit" method="post" enctype="multipart/form-data">
      <input type="hidden" name="id" value="${post.id}">
      <div class="row">
        <div class="col-md-4">
          <div class="mb-3">
            <label for="username" class="form-label style_18"><b>Tiêu đề</b></label>
            <i class="fas fa-pen-to-square ms-2"></i>
            <input type="text" class="form-control" id="username" name="username"
                   placeholder="Nhập tiêu đề..." value="${post.title}" required>
          </div>
        </div>
        <div class="col-md-4">
          <div class="mb-3">
            <label for="fullname" class="form-label style_18"><b>Người tạo</b></label>
            <i class="fas fa-user ms-2"></i>
            <input type="text" class="form-control" id="fullname" name="fullname"
                   placeholder="Nhập người tạo..." value="${post.userId}" readonly>
          </div>
        </div>
        <div class="col-md-4">
          <div class="mb-3">
            <div>
              <label class="form-label style_18"><b>Trạng thái</b></label>
              <i class="fas fa-toggle-on ms-2"></i>
            </div>
            <div class="form-check-inline">
              <input class="form-check-input" type="radio" name="status" id="active" value="1" ${post.status == 1 ? 'checked' : ''}>
              <label class="form-check-label" for="active">Hiển thị</label>
            </div>
            <div class="form-check-inline">
              <input class="form-check-input" type="radio" name="status" id="inactive" value="0" ${post.status == 0 ? 'checked' : ''}>
              <label class="form-check-label" for="inactive">Ẩn</label>
            </div>
          </div>
        </div>
        <div class="col-md-4">
          <div class="mb-3">
            <label for="avatar" class="form-label style_18"><b>Ảnh bài viết</b></label>
            <i class="fas fa-image ms-2"></i>
            <input class="form-control" type="file" id="avatar" name="avatar">
            <c:if test="${not empty post.img}">
              <small class="form-text text-muted">Ảnh hiện tại: ${post.img}</small>
            </c:if>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="mb-3">
          <label for="shortDescription" class="form-label style_18"><b>Mô tả ngắn</b></label>
          <input type="text" class="form-control" id="shortDescription" name="shortDescription"
                 placeholder="Nhập mô tả ngắn..." value="${param.shortDescription != null ? param.shortDescription : ''}" required>
        </div>
        <div class="mb-3">
          <label for="content" class="form-label style_18"><b>Nội dung</b></label>
          <textarea class="form-control" id="content" name="content" rows="10"
                    placeholder="Nhập nội dung..." required>${post.content}</textarea>
        </div>
      </div>

      <button type="submit" class="btn bg_green fw-bold text-white">Cập nhật</button>
    </form>
  </div>
  <!-- Container for demo purpose -->
</main>
<!--Main layout-->

<footer class="bottom-0 w-100 text-center py-2 bg-light">
  <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>
<script>
  CKEDITOR.replace('content');
</script>
</html>