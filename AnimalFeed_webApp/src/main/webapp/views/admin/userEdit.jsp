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

<main class="mb-5" style="margin-top: 100px;">
    <div class="container px-4">
        <!-- Nút quay lại -->
        <a href="userManagement" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
            <i class="fas fa-angle-left"></i> Quay lại
        </a>

        <!-- Tiêu đề -->
        <div class="mb-3 bg_green p-2">
            <span class="text-white h5">Chỉnh sửa thông tin người dùng</span>
        </div>

        <!-- Hiển thị thông báo nếu có -->
        <c:if test="${not empty sessionScope.message}">
            <div class="alert alert-success">
                    ${sessionScope.message}
            </div>
            <c:remove var="message" scope="session"/>
        </c:if>
        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-danger">
                    ${sessionScope.error}
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <!-- Form chỉnh sửa người dùng hoặc xác minh OTP -->
        <c:choose>
            <c:when test="${param.step == 'verify'}">
                <!-- Form xác minh OTP -->
                <form action="userEdit" method="post" class="border p-5">
                    <input type="hidden" name="step" value="verify"/>
                    <div class="mb-3">
                        <label for="otp" class="form-label style_18"><b>Mã OTP</b></label>
                        <i class="fas fa-key ms-2"></i>
                        <input type="text" class="form-control" id="otp" name="otp" placeholder="Nhập mã OTP đã gửi đến email..." required/>
                    </div>
                    <button type="submit" class="btn text-white bg_green fw-bold">Xác minh</button>
                </form>
            </c:when>
            <c:otherwise>
                <!-- Form chỉnh sửa thông tin người dùng -->
                <form action="userEdit" method="post" class="border p-5">
                    <input type="hidden" name="step" value="sendOtp"/>
                    <input type="hidden" name="id" value="${user.id}"/>

                    <!-- Dòng 1 -->
                    <div class="row">
                        <div class="col-md-3">
                            <div class="mb-3">
                                <label for="phone" class="form-label style_18"><b>Số điện thoại</b></label>
                                <i class="fas fa-phone ms-2"></i>
                                <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" placeholder="Nhập số điện thoại..." required/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="mb-3">
                                <label for="fullname" class="form-label style_18"><b>Tên đầy đủ</b></label>
                                <i class="fas fa-user ms-2"></i>
                                <input type="text" class="form-control" id="fullname" name="fullName" value="${user.fullName}" placeholder="Nhập họ và tên..." required/>
                            </div>
                        </div>
                        <div class="col-md-3">
                            <div class="mb-3">
                                <label for="email" class="form-label style_18"><b>Email</b></label>
                                <i class="fas fa-envelope ms-2"></i>
                                <input type="email" class="form-control" id="email" name="email" value="${user.email}" placeholder="Nhập email..." required/>
                            </div>
                        </div>
                    </div>

                    <!-- Dòng 2 -->
                    <div class="row">
                        <!-- Chỉ hiển thị trường "Vai trò" và "Sub Role" nếu người dùng là Super Admin (sub_role = 0) -->
                        <c:if test="${userService.hasPermission(sessionScope.userId, 'USER_MANAGEMENT') && sessionScope.subRole == 0}">
                            <div class="col-md-4">
                                <label for="role" class="form-label style_18"><b>Vai trò</b></label>
                                <i class="fas fa-user-tag ms-2"></i>
                                <select class="form-select" id="role" name="role" required>
                                    <option value="1" ${user.role == 1 ? 'selected' : ''}>Quản trị viên</option>
                                    <option value="0" ${user.role == 0 ? 'selected' : ''}>Người dùng</option>
                                </select>
                            </div>
                            <div class="col-md-4">
                                <label for="sub_role" class="form-label style_18"><b>Loại quản trị viên</b></label>
                                <i class="fas fa-user-shield ms-2"></i>
                                <select class="form-select" id="sub_role" name="sub_role" required>
                                    <option value="0" ${user.sub_role == 0 ? 'selected' : ''}>Super Admin</option>
                                    <option value="1" ${user.sub_role == 1 ? 'selected' : ''}>User Admin</option>
                                    <option value="2" ${user.sub_role == 2 ? 'selected' : ''}>Product Admin</option>
                                    <option value="3" ${user.sub_role == 3 ? 'selected' : ''}>Order Admin</option>
                                    <option value="4" ${user.sub_role == 4 ? 'selected' : ''}>Shipper Admin</option>
                                    <option value="5" ${user.sub_role == 5 ? 'selected' : ''}>News Admin</option>
                                </select>
                            </div>
                        </c:if>
                        <!-- Chỉ hiển thị trường "Trạng thái" nếu người dùng là Super Admin (sub_role = 0) -->
                        <c:if test="${userService.hasPermission(sessionScope.userId, 'USER_MANAGEMENT') && sessionScope.subRole == 0}">
                            <div class="col-md-4 mt-3">
                                <div class="mb-3">
                                    <div>
                                        <label class="form-label style_18"><b>Trạng thái</b></label>
                                        <i class="fas fa-toggle-on ms-2"></i>
                                    </div>
                                    <div class="form-check-inline">
                                        <input class="form-check-input" type="radio" name="status" id="active" value="1" ${user.status == 1 ? 'checked' : ''}>
                                        <label class="form-check-label" for="active">Đang hoạt động</label>
                                    </div>
                                    <div class="form-check-inline">
                                        <input class="form-check-input" type="radio" name="status" id="inactive" value="0" ${user.status == 0 ? 'checked' : ''}>
                                        <label class="form-check-label" for="inactive">Ngưng hoạt động</label>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>

                    <!-- Nút submit -->
                    <button type="submit" class="btn text-white bg_green fw-bold">Cập nhật</button>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<footer class="position-fixed bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>
</html>