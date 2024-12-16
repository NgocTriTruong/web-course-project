<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Thêm shipper</title>
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

<%@ include file="layout/header.jsp" %>

<!--Main layout-->
<main class="mb-5" style="margin-top: 100px;">
    <!-- Container for demo purpose -->
    <div class="container px-4 ">
        <a href="shipperManagemet.jsp" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
            <i class="fas fa-angle-left"></i> Quay lại
        </a>
        <div class="mb-3 bg_green p-2">
            <span class="text-white h5">Thông tin shipper</span>
        </div>
        <form class="border p-5">
            <div class="row">
                <div class="col-md-4">
                    <label for="category" class="form-label style_18"><b>Tên của shipper</b></label>
                    <i class="fas fa-bars ms-2"></i>
                    <input type="text" class="form-control" id="category" name="category"
                           placeholder="Nhập họ và tên..." required>
                </div>
                <div class="col-md-4">
                    <label for="category" class="form-label style_18"><b>Số điện thoại của shipper</b></label>
                    <i class="fas fa-phone ms-2"></i>
                    <input type="text" class="form-control" id="category" name="category"
                           placeholder="Nhập số điện thoại..." required>
                </div>
                <div class="col-md-4">
                    <label for="category" class="form-label style_18"><b>Tiền lương</b></label>
                    <i class="fas fa-dollar ms-2"></i>
                    <input type="text" class="form-control" id="category" name="category"
                           placeholder="Nhập tiền lương..." required>
                </div>
                <div class="col-md-4 mt-3">
                    <div class="mb-3">
                        <div>
                            <label class="form-label style_18"><b>Trạng thái</b></label>
                            <i class="fas fa-toggle-on ms-2"></i>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="active" value="1" checked>
                            <label class="form-check-label" for="active">Đang làm việc</label>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="inactive" value="0">
                            <label class="form-check-label" for="inactive">Đã nghỉ làm</label>
                        </div>
                    </div>
                </div>

            </div>
            <!-- Repeat the pattern for other form elements -->
            <button type="submit" class="btn bg_green text-white fw-bold">Thêm mới</button>
        </form>

    </div>
    <!-- Container for demo purpose -->
</main>
<!--Main layout-->

<footer class="position-fixed bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>
</html>
