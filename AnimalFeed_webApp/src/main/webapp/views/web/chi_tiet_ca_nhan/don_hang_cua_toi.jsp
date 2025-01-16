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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js" defer></script>

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
                                            Khách hàng <br>
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
                                <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/views/web/chi_tiet_ca_nhan/don_hang_cua_toi.jsp'">Đơn hàng của tôi</div>
                            </div>
                            <div class="tt_bottom_number d-flex mt-2">
                                <i class="me-3 fa-solid fa-location-dot ms-3"></i>
                                <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/vlocation_user'">Sổ địa chỉ nhận hàng</div>
                            </div>
                            <div class="tt_bottom_number d-flex mt-2" id="logout">
                                <i class="me-3 fa-solid fa-right-from-bracket ms-3"></i>
                                <div class="p">Đăng xuất</div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8 thong_tin_right ms-5 pt-5">
                        <div class="h4 fw-bold">Đơn hàng của tôi</div>
                        <div class="bg-white d-flex mt-3 od_me_all">
                            <div class="p od_me">Tất cả</div>
                            <div class="p od_me">Đang xử lý</div>
                            <div class="p od_me">Đang giao</div>
                            <div class="p od_me">Hoàn tất</div>
                            <div class="p od_me">Đã hủy</div>
                        </div>
                        <div class="tt_right_all justify-content-center">
                            <div class="tt_right_all_80 pt-4 text-center">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/background/empty_state.png" alt="">
                                <h6>Bạn chưa có đơn hàng nào</h6>
                                <p>Cùng khám phá hàng ngàn sản phẩm tại VINAFEED nhé!</p>
                                <div class="button mt-4 text-white fw-bold" onclick="window.location.href='../product.jsp'">
                                    Khám phá ngay
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="height: 90px;"></div>
        </div>
    </main>
    <div id="near-footer-placeholder"></div>
<%@ include file="../layout/near_footer.jsp" %>
<%@ include file="../layout/footer.jsp" %>

</body>
</html>