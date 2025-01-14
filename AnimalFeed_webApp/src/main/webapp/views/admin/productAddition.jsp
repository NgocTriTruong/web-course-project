<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Thêm sản phẩm</title>
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
    <div class="container px-4">
        <a href="productManagement.jsp" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
            <i class="fas fa-angle-left"></i> Quay lại
        </a>

        <div class="mb-3 bg_green p-2 bg_green">
            <span class="text-white h5">Thông tin sản phẩm</span>
        </div>

        <form class="border p-5">
            <!-- Thông tin cơ bản -->
            <div class="row mb-3">
                <div class="col-md-4">
                    <label for="category" class="form-label style_18"><b>Loại sản phẩm</b></label>
                    <i class="fas fa-bars ms-2"></i>
                    <select class="form-select" id="category" name="category" required>
                        <option value="" disabled selected>Chọn danh mục</option>
                        <option value="1">Thức ăn cho heo</option>
                        <option value="1">Thức ăn cho gà</option>
                        <option value="1">Thức ăn cho vịt</option>
                        <option value="1">Thức ăn cho bò</option>
                        <option value="1">Thức ăn cho tôm</option>
                        <option value="1">Thức ăn cho cá</option>
                        <option value="1">Thức ăn cho dê</option>
                        <!-- Add more options as needed -->
                    </select>
                </div>

                <div class="col-md-4">
                    <label for="name" class="form-label style_18"><b>Tên sản phẩm</b></label>
                    <i class="fas fa-gift ms-2"></i>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Nhập tên sản phẩm..."
                           required>
                </div>

                <div class="col-md-4">
                    <label for="price" class="form-label style_18"><b>Giá tiền</b></label>
                    <i class="fas fa-dollar ms-2"></i>
                    <input type="number" class="form-control" id="price" name="price" placeholder="VNĐ..." required>
                </div>
            </div>

            <!-- Khuyến mãi và Trạng thái -->
            <div class="row mb-3">
                <div class="col-md-4">
                    <label for="price" class="form-label style_18"><b>Số lượng</b></label>
                    <i class="fas fa-cubes ms-2"></i>
                    <input type="number" class="form-control" id="price" name="price" placeholder="Bao..." required>
                </div>
                <div class="col-md-4">
                    <label for="discount" class="form-label style_18"><b>Khuyến mãi</b></label>
                    <i class="fas fa-percent ms-2"></i>
                    <input type="text" class="form-control" id="discount" name="discount" placeholder="%" required>
                </div>

                <div class="col-md-4">
                    <div class="mb-3">
                        <div>
                            <label class="form-label style_18"><b>Trạng thái</b></label>
                            <i class="fas fa-toggle-on ms-2"></i>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="active" value="1" checked>
                            <label class="form-check-label" for="active">Đang bán</label>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="inactive" value="0">
                            <label class="form-check-label" for="inactive">Ngừng bán</label>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Mô tả và Hình ảnh -->
            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label style_18"><b>Mô tả</b></label>
                    <textarea class="form-control" id="description" name="description" rows="7"
                              placeholder="Mô tả..."></textarea>
                </div>

                <div class="col-md-4">
                    <label for="image" class="form-label style_18"><b>Hình ảnh</b></label>
                    <i class="fas fa-image ms-2"></i>
                    <div class="file-upload-wrapper">
                        <input id="image" type="file" class="file-upload-input" data-mdb-multiple="true"
                               data-mdb-file-upload="file-upload"/>
                    </div>
                </div>
            </div>

            <!-- Nút Submit -->
            <div class="row mt-3">
                <div class="col-md-8">
                    <button type="submit" class="btn bg_green text-white fw-bold">Thêm mới</button>
                </div>
            </div>
        </form>
    </div>
</main>
<!--Main layout-->


<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer><footer></footer>

</body>

</html>
