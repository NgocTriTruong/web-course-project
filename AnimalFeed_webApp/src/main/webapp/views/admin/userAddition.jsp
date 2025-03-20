<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Thêm người dùng</title>
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

<main class="mb-5" style="margin-top: 100px;">
    <div class="container px-4">
        <!-- Nút quay lại -->
        <a href="userManagement" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
            <i class="fas fa-angle-left"></i> Quay lại
        </a>

        <!-- Tiêu đề -->
        <div class="mb-3 bg_green p-2">
            <span class="text-white h5">Thông tin người dùng</span>
        </div>

        <!-- Hiển thị thông báo -->
        <%
            String success = request.getParameter("success");
            String error = request.getParameter("error");
        %>
        <% if ("true".equals(success)) { %>
        <div class="alert alert-success">Thêm người dùng thành công!</div>
        <% } else if ("true".equals(error)) { %>
        <div class="alert alert-danger">Có lỗi xảy ra. Vui lòng thử lại.</div>
        <% } %>

        <!-- Form thêm người dùng -->
        <form action="addUser" method="POST" class="border p-5">
            <!-- Dòng 1 -->
            <div class="row">
                <div class="col-md-3">
                    <div class="mb-3">
                        <label for="phone" class="form-label style_18"><b>Số điện thoại</b></label>
                        <i class="fas fa-phone ms-2"></i>
                        <input type="text" class="form-control" id="phone" name="phone" placeholder="Nhập số điện thoại..." required>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="mb-3">
                        <label for="password" class="form-label style_18"><b>Mật khẩu</b></label>
                        <i class="fas fa-key ms-2"></i>
                        <input type="password" class="form-control" id="password" name="password" placeholder="Nhập mật khẩu..." required>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="mb-3">
                        <label for="fullname" class="form-label style_18"><b>Tên đầy đủ</b></label>
                        <i class="fas fa-user ms-2"></i>
                        <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Nhập họ và tên..." required>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="mb-3">
                        <label for="email" class="form-label style_18"><b>Email</b></label>
                        <i class="fas fa-envelope ms-2"></i>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Nhập email..." required>
                    </div>
                </div>
            </div>

            <!-- Dòng 2 -->
            <div class="row">
                <div class="col-md-4">
                    <label for="role" class="form-label style_18"><b>Vai trò</b></label>
                    <i class="fas fa-user-tag ms-2"></i>
                    <select class="form-select" id="role" name="role" required>
                        <option value="" disabled selected>Chọn vai trò</option>
                        <option value="1">Quản trị</option>
                        <option value="0">Người dùng</option>
                    </select>
                </div>
                <div class="col-md-4 mt-3">
                    <div class="mb-3">
                        <div>
                            <label class="form-label style_18"><b>Trạng thái</b></label>
                            <i class="fas fa-toggle-on ms-2"></i>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="active" value="2" checked>
                            <label class="form-check-label" for="active">Ngưng hoạt động</label>
                        </div>
                        <div class="form-check-inline">
                            <input class="form-check-input" type="radio" name="status" id="inactive" value="1">
                            <label class="form-check-label" for="inactive">Đang hoạt động</label>
                        </div>
                    </div>
                </div>
            </div>


            <!-- Nút submit -->
            <button type="submit" class="btn text-white bg_green fw-bold">Thêm mới</button>
        </form>
    </div>
</main>

<footer class="position-fixed bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>
</html>
