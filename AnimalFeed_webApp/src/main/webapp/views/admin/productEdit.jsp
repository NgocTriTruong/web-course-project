<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Chỉnh sửa sản phẩm</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

    <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>

</head>

<body>
<!-- Start your project here-->
<!--Main Navigation-->
<%@ include file="layout/header.jsp" %>

<!--Main layout-->
<main class="mb-5" style="margin-top: 100px;">
    <div class="container px-4">
        <a href="product-manager" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
            <i class="fas fa-angle-left"></i> Quay lại
        </a>

        <div class="mb-3 bg_green p-2 bg_green">
            <span class="text-white h5">Thông tin sản phẩm</span>
        </div>

        <form action="edit-product" method="post" class="border p-5" enctype="multipart/form-data">

            <div class="col-md-4">
                <label for="productId" class="form-label style_18"><b>ID sản phẩm</b></label>
                <input type="text" class="form-control" id="productId" name="productId" value="${product.id}" readonly>
            </div>

            <!-- Thông tin cơ bản -->
            <div class="row mb-3">
                <div class="col-md-4">
                    <label for="category" class="form-label style_18"><b>Loại sản phẩm</b></label>
                    <i class="fas fa-bars ms-2"></i>
                    <select class="form-select" id="category" name="category" required>
                        <c:forEach var="ca" items="${categoriesData}" >
                            <option value="${ca.id}" ${ca.id == product.cat_id ? 'selected' : ''}>${ca.name}</option>
                        </c:forEach>    
                    </select>
                </div>

                <div class="col-md-4">
                    <label for="name" class="form-label style_18"><b>Tên sản phẩm</b></label>
                    <i class="fas fa-gift ms-2"></i>
                    <input type="text" class="form-control" id="name" name="name" value="${product.name}"
                           required>
                </div>

                <div class="col-md-4">
                    <label for="price" class="form-label style_18"><b>Giá tiền</b></label>
                    <i class="fas fa-dollar ms-2"></i>
                    <input type="number" class="form-control" id="price" name="price" value="${product.price}" required>
                </div>
            </div>

            <!-- Khuyến mãi và Trạng thái -->
            <div class="row mb-3">
                <div class="col-md-4">
                    <label for="quantity" class="form-label style_18"><b>Số lượng</b></label>
                    <i class="fas fa-cubes ms-2"></i>
                    <input type="number" class="form-control" id="quantity" name="quantity" value="${product.quantity}" required>
                </div>
                <div class="col-md-4">
                    <label for="discount" class="form-label style_18"><b>Khuyến mãi</b></label>
                    <i class="fas fa-percent ms-2"></i>
                    <select class="form-select" id="discount" name="discount" required>
                        <c:forEach var="dis" items="${discountsData}" >
                            <option value="${dis.id}" ${dis.id == product.discountId ? "selected" : ""}>${dis.percentage}%</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label style_18"><b>Trạng thái</b></label>
                    <div style="font-size: 20px;">
                        <input type="radio" id="status1" name="status" value="1" ${product.status == "1" ? "checked" : ""}>
                        <label for="status1">Đang bán</label>
                    </div>
                    <div style="font-size: 20px;">
                        <input type="radio" id="status2" name="status" value="2" ${product.status == "2" ? "checked" : ""}>
                        <label for="status2">Ngừng bán</label>
                    </div>
                </div>

            </div>

            <!-- Mô tả và Hình ảnh -->
            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label style_18"><b>Mô tả</b></label>
                    <textarea class="form-control" id="description" name="description" rows="7"
                              placeholder="Mô tả...">${product.description}</textarea>
                </div>

                <div class="col-md-4">
                    <label for="image" class="form-label style_18"><b>Hình ảnh</b></label>
                    <i class="fas fa-image ms-2"></i>
                    <div class="file-upload-wrapper">
                        <input id="image" type="file" class="form-control" name="image">
                    </div>
                </div>
            </div>

            <!-- Nút Submit -->
            <div class="row mt-3">
                <div class="col-md-8">
                    <button type="submit" class="btn bg_green text-white fw-bold">Cập nhật</button>
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
