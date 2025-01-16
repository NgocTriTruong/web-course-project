<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 16-Jan-25
  Time: 1:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <!-- Include your CSS files here -->
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
                <h2>Chi tiết đơn hàng #${order.id}</h2>

                <div class="order-detail-container">
                    <div class="row mb-4">
                        <div class="col-md-6">
                            <h5>Thông tin đơn hàng</h5>
                            <p><strong>Ngày đặt:</strong> ${order.formattedOrderDate}</p>
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
                        </div>
                        <div class="col-md-6">
                            <h5>Thông tin giao hàng</h5>
                            <p><strong>Địa chỉ:</strong> ${order.address}</p>
                            <p><strong>Phí vận chuyển:</strong>
                                <fmt:formatNumber value="${order.shippingPrice}" type="currency" currencySymbol="₫"/>
                            </p>
                        </div>
                    </div>

                    <h5>Chi tiết sản phẩm</h5>
                    <div class="product-list">
                        <c:forEach var="detail" items="${orderDetailsWithProducts}">
                            <div class="product-item">
                                <div class="row">
                                    <div class="col-md-6">
                                        <h6>${detail.product.name}</h6>
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
                    <a href="${pageContext.request.contextPath}/order-history" class="btn btn-secondary">
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
