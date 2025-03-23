<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tài khoản</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/gioi_thieu.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chi_tiet_ca_nhan/thong_tin_ca_nhan.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger
            intent="WELCOME"
            chat-title="VinaFeed_chat"
            agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264"
            language-code="vi"
    ></df-messenger>

</head>
<body>
<%@ include file="../layout/header.jsp" %>

    <main style="margin-top: 65px;">
        <div class="thong_tin_ca_nhan bg-light">
            <div class="container">
                <div class="row">
                    <div class="col-md-3 thong_tin_left pt-5">
                        <div class="tt_left_top bg-white">
                            <div class="tt_top_user d-flex">
                                <div class="user me-3">
                                    <i class="fa-solid fa-user mt-2 i_user"></i>
                                </div>
                                <div class="p mt-3">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.user}">
                                            ${sessionScope.user.fullName} <br>
                                            ${sessionScope.user.phone}
                                        </c:when>
                                        <c:otherwise>
                                            Đăng nhập để xem thông tin
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <span class="text-primary ms-5 mt-5" onclick="window.location.href='${pageContext.request.contextPath}/profile-user'"  style="cursor: pointer;">xem hồ sơ</span>
                            </div>
                        </div>
                        <div class="tt_left_bottom bg-white mt-4 pt-3 pb-3">
                            <div class="tt_bottom_number d-flex mt-2">
                                <i class="me-3 fa-solid fa-key ms-3"></i>
                                <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/new-password'">Thay đổi mật khẩu của bạn</div>
                            </div>
                            <div class="tt_bottom_number d-flex mt-2">
                                <i class="me-3 fa-solid fa-box ms-3"></i>
                                <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/order-history'">Đơn hàng của tôi</div>
                            </div>
                            <div class="tt_bottom_number d-flex mt-2">
                                <i class="me-3 fa-solid fa-location-dot ms-3"></i>
                                <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/location_user'">Sổ địa chỉ nhận hàng</div>
                            </div>
                            <div class="tt_bottom_number d-flex mt-2" id="logout">
                                <i class="me-3 fa-solid fa-right-from-bracket ms-3"></i>
                                <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/logout'">Đăng xuất</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8 thong_tin_right ms-5 pt-5">
                        <div class="h4 fw-bold">Thay đổi mật khẩu của bạn</div>

                        <!-- Hiển thị thông báo lỗi hoặc thành công -->
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger mt-3">${errorMessage}</div>
                        </c:if>
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success mt-3">${successMessage}</div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/new-password" method="POST">
                            <div class="form-floating mb-3 mt-3">
                                <input type="password" class="form-control" id="current-password" placeholder="Nhập mật khẩu hiện tại" name="currentPassword" required autocomplete="off">
                                <label for="current-password">Nhập mật khẩu hiện tại<span class="req">*</span></label>
                            </div>
                            <p class="login_forgot" style="text-align: end; margin-top: -10px;"><a href="#" style="color: red;">Quên mật khẩu?</a></p>
                            <div class="line_gray mt-3 mb-3"></div>
                            <div class="form-floating mb-3 mt-3">
                                <input type="password" class="form-control" id="new-password" placeholder="Nhập mật khẩu mới" name="newPassword" required autocomplete="off">
                                <label for="new-password">Nhập mật khẩu mới<span class="req">*</span></label>
                            </div>
                            <div class="form-floating mb-3 mt-3">
                                <input type="password" class="form-control" id="confirm-password" placeholder="Xác nhận mật khẩu mới" name="confirmPassword" required autocomplete="off">
                                <label for="confirm-password">Xác nhận mật khẩu mới<span class="req">*</span></label>
                            </div>
                            <button type="submit" class="btn btn-primary mt-4 text-white fw-bold">Lưu</button>
                        </form>

                    </div>
                </div>
            </div>
            <div style="height: 90px;"></div>
        </div>
    </main>
<%@ include file="../layout/near_footer.jsp" %>

<%@ include file="../layout/footer.jsp" %>
</body>
</html>