<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Chỉnh sửa người dùng</title>
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

<!-- Header -->
<%@ include file="layout/header.jsp" %>

<main style="padding-bottom: 100px;">
    <div class="container px-4">
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="${pageContext.request.contextPath}/userManagement">
                <i class="fas fa-arrow-left"></i>
                <span>Quay lại</span>
            </a>
        </div>

        <h2 class="text-center mb-4" style="padding-top: 50px">Chỉnh sửa thông tin người dùng</h2>

        <!-- Hiển thị thông báo nếu có -->
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-success">
                    ${sessionScope.message}
            </div>
        </c:if>
        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-danger">
                    ${sessionScope.error}
            </div>
        </c:if>

        <!-- Form chỉnh sửa người dùng -->
        <form action="userEdit" method="post">
            <!-- Ẩn ID của người dùng -->
            <input type="hidden" name="id" value="${user.id}" />

            <!-- Tên người dùng -->
            <div class="form-group">
                <label for="fullName">Tên người dùng</label>
                <input type="text" class="form-control" name="fullName" value="${user.fullName}" required />
            </div>

            <!-- Số điện thoại -->
            <div class="form-group">
                <label for="phone">Số điện thoại</label>
                <input type="text" class="form-control" name="phone" value="${user.phone}" required />
            </div>

            <!-- Trạng thái người dùng -->
            <div class="form-group">
                <label for="status">Trạng thái</label>
                <select class="form-control" name="status" required>
                    <option value="2" ${user.status == 2 }>Đang hoạt động</option>
                    <option value="1" ${user.status == 1 }>Ngưng hoạt động</option>
                </select>
            </div>

            <!-- Vai trò người dùng -->
            <div class="form-group">
                <label for="role">Vai trò</label>
                <select class="form-control" name="role" required>
                    <option value="1" ${user.role == 1 ? 'selected' : ''}>Quản trị viên</option>
                    <option value="0" ${user.role == 0 ? 'selected' : ''}>Người dùng</option>
                </select>
            </div>

            <!-- Submit -->
            <button type="submit" class="btn btn-primary">Cập nhật</button>
        </form>

    </div>
</main>

<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>
</html>
