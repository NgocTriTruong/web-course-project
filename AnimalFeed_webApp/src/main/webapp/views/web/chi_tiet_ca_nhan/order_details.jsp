<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết đơn hàng #${order.id}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/gioi_thieu.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chi_tiet_ca_nhan/thong_tin_ca_nhan.css">
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

    <style>
        .order-detail-container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .status-badge {
            padding: 5px 10px;
            border-radius: 4px;
            font-weight: bold;
        }
        .product-item {
            border-bottom: 1px solid #eee;
            padding: 15px 0;
        }
        .product-item:last-child {
            border-bottom: none;
        }
    </style>
</head>
<body>
<%@ include file="../layout/header.jsp" %>

<main style="margin-top: 100px;">
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-12">
                <h2 class="mt-2">Chi tiết đơn hàng #${order.id}</h2>
                <h5 class="text-muted">Mã GHN: ${fn:escapeXml(order.shippingCode)}</h5>

                <div class="order-detail-container">
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <h5><u>Thông tin đơn hàng:</u></h5>
                            <p><strong>Ngày đặt:</strong> ${formattedOrderDate}</p>
                            <p><strong>Trạng thái:</strong>
                                <span class="status-badge status-${order.status}">
                                    <c:choose>
                                        <c:when test="${order.status == 1}">Chờ xác nhận</c:when>
                                        <c:when test="${order.status == 2}">Đang xử lý</c:when>
                                        <c:when test="${order.status == 3}">Đang giao hàng</c:when>
                                        <c:when test="${order.status == 4}">Đã giao hàng</c:when>
                                        <c:when test="${order.status == 0}">Đã hủy</c:when>
                                        <c:otherwise>Không xác định</c:otherwise>
                                    </c:choose>
                                </span>
                            </p>

                            <p><strong>Hình thức thanh toán:</strong>
                                <c:choose>
                                    <c:when test="${not empty payments}">
                                        <c:choose>
                                            <c:when test="${payments.method eq 'COD'}">Thanh toán khi nhận hàng (COD)</c:when>
                                            <c:when test="${payments.method eq 'VNPay'}">Thanh toán qua VNPay</c:when>
                                            <c:otherwise>${payments.method}</c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>Đang cập nhật</c:otherwise>
                                </c:choose>
                            </p>


                        </div>
                        <div class="col-md-6">
                            <h5><u>Thông tin giao hàng:</u></h5>
                            <p><strong>Địa chỉ:</strong> ${order.address}</p>
                            <p><strong>Phí vận chuyển:</strong>
                                <fmt:formatNumber value="${order.shippingPrice}" type="currency" currencySymbol="₫"/>
                            </p>
                        </div>
                    </div>

                    <h5><u>Chi tiết sản phẩm:</u></h5>
                    <div class="product-list">
                        <c:forEach var="detail" items="${orderDetailsWithProducts}">
                            <div class="product-item">
                                <div class="row">
                                    <div class="col-md-6 d-flex">
                                        <strong>${detail.product.name}</strong>
                                        <p class="ms-3">${detail.product.description}</p>
                                    </div>
                                    <div class="col-md-2">
                                        <p>Số lượng: ${detail.orderDetail.quantity}</p>
                                    </div>
                                    <div class="col-md-4 text-end">
                                        <p>Tổng: <fmt:formatNumber value="${detail.orderDetail.totalPrice}" type="currency" currencySymbol="₫"/></p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <div class="row mt-4">
                        <div class="col-md-12 text-end">
                            <h5>Tổng cộng: <fmt:formatNumber value="${order.totalPrice + order.shippingPrice}" type="currency" currencySymbol="₫"/></h5>
                        </div>
                    </div>
                </div>

                <div class="text-center mt-4">
                    <a href="${pageContext.request.contextPath}/order-history" class="btn btn-secondary" style="background-color: #94b83d">
                        <i class="fas fa-arrow-left"></i> Quay lại danh sách đơn hàng
                    </a>
                </div>
            </div>
        </div>
    </div>
</main>

<%@ include file="../layout/near_footer.jsp" %>
<%@ include file="../layout/footer.jsp" %>
</body>
</html>