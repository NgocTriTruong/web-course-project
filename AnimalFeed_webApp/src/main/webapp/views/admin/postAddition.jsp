<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Thêm bài viết</title>
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
    <div class="container px-4 ">
        <a href="${pageContext.request.contextPath}/post-management" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
            <i class="fas fa-angle-left"></i> Quay lại
        </a>
        <div class="mb-3 bg_green p-2">
            <span class="text-white h5">Thông tin bài viết</span>
        </div>
        <form action="${pageContext.request.contextPath}/post-add" method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="col-md-4">
                    <div class="mb-3">
                        <label for="username" class="form-label style_18"><b>Tiêu đề</b></label>
                        <i class="fas fa-pen-to-square ms-2"></i>
                        <input type="text" class="form-control" id="username" name="username"
                               placeholder="Nhập tiêu đề..." value="${param.username}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="mb-3">
                        <label for="fullname" class="form-label style_18"><b>Người tạo</b></label>
                        <i class="fas fa-user ms-2"></i>
                        <input type="text" class="form-control" id="fullname" name="fullname"
                               placeholder="Nhập người tạo..." value="${param.fullname}" required>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="mb-3">
                        <div>
                            <label class="form-label style_18"><b>Trạng thái</b></label>
                            <i class="fas fa-toggle-on ms-2"></i>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="active" value="1" ${param.status == '1' || param.status == null ? 'checked' : ''}>
                            <label class="form-check-label" for="active">Hiển thị</label>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="inactive" value="0" ${param.status == '0' ? 'checked' : ''}>
                            <label class="form-check-label" for="inactive">Ẩn</label>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="mb-3">
                        <label for="avatar" class="form-label style_18"><b>Ảnh bài viết</b></label>
                        <i class="fas fa-image ms-2"></i>
                        <input class="form-control" type="file" id="avatar" name="avatar">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="mb-3">
                    <label for="shortDescription" class="form-label style_18"><b>Mô tả ngắn</b></label>
                    <input type="text" class="form-control" id="shortDescription" name="shortDescription"
                           placeholder="Nhập mô tả ngắn..." value="${param.shortDescription}" required>
                </div>
                <div class="mb-3">
                    <label for="content" class="form-label style_18"><b>Nội dung</b></label>
                    <textarea class="form-control" id="content" name="content" rows="10"
                              placeholder="Nhập nội dung..." required>${param.content}</textarea>
                </div>
            </div>

            <button type="submit" class="btn bg_green fw-bold text-white">Thêm mới</button>
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