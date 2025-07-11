<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chi_tiet_ca_nhan/personal_order.css">

<%--    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>--%>
<%--    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js" defer></script>--%>

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
                    <div class="h4 fw-bold">Đơn hàng của tôi</div>
                    <div class="bg-white d-flex od_me_all">
                        <a href="${pageContext.request.contextPath}/order-history?status=all"
                           class="p od_me ${currentStatus == 'all' ? 'active' : ''}">Tất cả</a>
                        <a href="${pageContext.request.contextPath}/order-history?status=1"
                           class="p od_me ${currentStatus == '1' ? 'active' : ''}">Chờ xác nhận</a>
                        <a href="${pageContext.request.contextPath}/order-history?status=2"
                           class="p od_me ${currentStatus == '2' ? 'active' : ''}">Đang chuẩn bị</a>
                        <a href="${pageContext.request.contextPath}/order-history?status=3"
                           class="p od_me ${currentStatus == '3' ? 'active' : ''}">Đang vận chuyển</a>
                        <a href="${pageContext.request.contextPath}/order-history?status=4"
                           class="p od_me ${currentStatus == '4' ? 'active' : ''}">Hoàn tất</a>
                        <a href="${pageContext.request.contextPath}/order-history?status=0"
                           class="p od_me ${currentStatus == '0' ? 'active' : ''}">Đã hủy</a>
                    </div>

                    <c:choose>
                        <c:when test="${not empty orders}">
                            <c:forEach var="order" items="${orders}">
                                <div class="order-item">
                                    <div class="order-header">
                                        <div>
                                            <span class="fw-bold">Mã đơn hàng: #${order.id}</span>
                                            <br>
                                            <span class="text-muted">Mã GHN: ${fn:escapeXml(order.shippingCode)}</span>
                                        </div>
                                        <div class="order-status status-${order.status}">
                                            <c:choose>
                                                <c:when test="${order.status == 0}">Đã hủy</c:when>
                                                <c:when test="${order.status == 1}">Chờ xác nhận</c:when>
                                                <c:when test="${order.status == 2}">Đang chuẩn bị</c:when>
                                                <c:when test="${order.status == 3}">Đang vận chuyển</c:when>
                                                <c:when test="${order.status == 4}">Hoàn tất</c:when>
                                                <c:otherwise>Không xác định</c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-3">
                                            <div class="text-muted">Ngày đặt hàng</div>
                                            <div>${requestScope['formattedOrderDate_' += order.id]}</div>
                                        </div>
                                        <div class="col-md-3">
                                            <div class="text-muted">Tổng tiền</div>
                                            <div class="fw-bold">
                                                <fmt:formatNumber value="${order.totalPrice + order.shippingPrice}"
                                                                  type="currency" currencySymbol="₫"/>
                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="text-muted">Địa chỉ giao hàng</div>
                                            <div>${order.address}</div>
                                        </div>
                                        <div class="col-md-2 text-end">
                                            <a href="order-detail?id=${order.id}" class="detail-btn btn btn-primary btn-sm text-center" style="background-color: #fcae18">
                                                <i class="fas fa-eye"></i> Xem chi tiết
                                            </a>
                                            <c:if test="${order.status == 1 || order.status == 2}">
                                                <button class="btn btn-cancel-order" data-ghn-code="${order.shippingCode}" data-order-id="${order.id}">
                                                    <i class="fas fa-times"></i> Hủy đơn
                                                </button>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="tt_right_all justify-content-center">
                                <div class="tt_right_all_80 pt-4 text-center">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/background/empty_state.png" alt="">
                                    <h6>Bạn chưa có đơn hàng nào</h6>
                                    <p>Cùng khám phá hàng ngàn sản phẩm tại VINAFEED nhé!</p>
                                    <div class="button mt-4 text-white fw-bold" onclick="window.location.href='${pageContext.request.contextPath}/product'">
                                        Khám phá ngay
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div style="height: 90px;"></div>
    </div>
</main>
<%@ include file="../layout/near_footer.jsp" %>
<%@ include file="../layout/footer.jsp" %>

<script src="${pageContext.request.contextPath}/views/template/assets/scripts/api_ghn/order_history.js"></script>
<%--<script>--%>
<%--    function confirmCancelOrder(orderId) {--%>
<%--        if (confirm('Bạn có chắc chắn muốn hủy đơn hàng này không?')) {--%>
<%--            window.location.href = '${pageContext.request.contextPath}/cancel-order?id=' + orderId;--%>
<%--        }--%>
<%--    }--%>
<%--</script>--%>

</body>
</html>