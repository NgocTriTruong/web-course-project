<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 15-Jan-25
  Time: 8:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Lịch sử đơn hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/font-awesome/all.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">
    <style>
        .order-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .order-item {
            border: 1px solid #ddd;
            margin-bottom: 20px;
            padding: 15px;
            border-radius: 8px;
        }
        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        .order-status {
            padding: 5px 10px;
            border-radius: 4px;
            font-weight: bold;
        }
        .status-0 { background-color: #fff3cd; color: #856404; }
        .status-1 { background-color: #cce5ff; color: #004085; }
        .status-2 { background-color: #d4edda; color: #155724; }
        .status-3 { background-color: #c3e6cb; color: #155724; }
        .status-4 { background-color: #f8d7da; color: #721c24; }

        .order-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 15px;
        }
        .detail-item {
            font-size: 14px;
        }
        .detail-label {
            color: #666;
            margin-bottom: 5px;
        }
        .detail-value {
            font-weight: 500;
        }
        .btn-view-detail {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
        }
        .btn-view-detail:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<%@ include file="../layout/header.jsp" %>
<body>
<!-- Test if JSTL is working -->
<c:set var="test" value="JSTL is working" />
<div style="display:none">${test}</div>

<div class="order-container">
    <h2 class="mb-4">Lịch sử đơn hàng</h2>

    <!-- Debug information -->
    <div style="display:none">
        Orders empty: ${empty orders}
        Orders size: ${orders.size()}
    </div>

    <!-- Check if orders exists and is not empty -->
    <c:choose>
        <c:when test="${not empty orders}">
            <c:forEach var="order" items="${orders}">
                <div class="order-item">
                    <div class="order-header">
                        <div>
                            <span class="fw-bold">Mã đơn hàng: #${order.id}</span>
                        </div>
                        <div class="order-status status-${order.status}">
                            <c:choose>
                                <c:when test="${order.status == 0}">Chờ xác nhận</c:when>
                                <c:when test="${order.status == 1}">Đang xử lý</c:when>
                                <c:when test="${order.status == 2}">Đang giao hàng</c:when>
                                <c:when test="${order.status == 3}">Đã giao hàng</c:when>
                                <c:when test="${order.status == 4}">Đã hủy</c:when>
                                <c:otherwise>Không xác định</c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">
                            <div class="text-muted">Ngày đặt hàng</div>
                            <div>${order.formattedOrderDate}</div>
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
                            <a href="order-detail?id=${order.id}" class="btn btn-primary btn-sm text-center">
                                <i class="fas fa-eye"></i> Xem chi tiết
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="text-center py-5">
                <i class="fas fa-box-open" style="font-size: 48px; color: #ccc;"></i>
                <p class="mt-3">Bạn chưa có đơn hàng nào</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>


</body>
<%@ include file="../layout/near_footer.jsp" %>
<%@ include file="../layout/footer.jsp" %>
</html>
