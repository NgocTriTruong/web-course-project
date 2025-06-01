<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Success - Animal Feed</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/font-awesome/all.css" />
    <style>
        .success-container {
            font-family: sans-serif, Tahoma;
            max-width: 800px;
            margin: 2rem auto;
            padding: 2rem;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .success-header {
            text-align: center;
            margin-bottom: 2rem;
            color: #28a745;
        }

        .success-icon {
            font-size: 4rem;
            margin-bottom: 1rem;
        }

        .order-details {
            margin-top: 2rem;
            border-top: 1px solid #dee2e6;
            padding-top: 1rem;
        }

        .order-items {
            margin: 1rem 0;
            border: 1px solid #dee2e6;
            border-radius: 4px;
        }

        .item-row {
            display: grid;
            grid-template-columns: 2fr 1fr 1fr 1fr;
            padding: 0.75rem;
            border-bottom: 1px solid #dee2e6;
        }

        .item-header {
            font-weight: bold;
            background-color: #f8f9fa;
        }

        .customer-info {
            margin-top: 2rem;
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }

        .info-group {
            margin-bottom: 1rem;
        }

        .info-label {
            font-weight: bold;
            color: #6c757d;
        }

        .total-section {
            margin-top: 2rem;
            text-align: right;
            font-size: 1.1rem;
        }

        .back-button {
            display: block;
            width: 200px;
            margin: 2rem auto;
            padding: 0.75rem 1.5rem;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            text-align: center;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .back-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="success-container">
    <div class="success-header">
        <c:if test="${transResult}">
            <i class="fas fa-check-circle success-icon"></i>
            <h1>Đặt hàng thành công!</h1>
            <p>Cảm ơn quý khách đã đặt hàng và thanh toán qua VNPAY, đơn hàng của quý khách sẽ được xử lý sớm nhất!</p>
        </c:if>
        <c:if test="${transResult == false}">
            <i class="fas fa-times-circle error-icon" style="color: red; font-size: 4rem; margin-bottom: 1rem;"></i>
            <h1 style="color: red;">Đặt hàng thất bại!</h1>
            <p>Có lỗi xảy ra trong quá trình xử lý đơn hàng, vui lòng thử lại sau!</p>
            <p>Liên hệ tổng đài để được tư vấn: <span style="color: red;">0905234543</span></p>
        </c:if>
    </div>

    <div class="order-details">
        <h2>Đơn hàng</h2>
        <div class="order-items">
            <div class="item-row item-header">
                <div>Sản phẩm</div>
                <div>Số lượng</div>
                <div>Đơn giá</div>
                <div>Tổng</div>
            </div>
            <c:forEach items="${sessionScope.orderItems}" var="item">
                <div class="item-row">
                    <div>${item.name}</div>
                    <div>${item.quantity}</div>
                    <div>
                        <fmt:formatNumber value="${item.unitPrice}" type="currency" currencySymbol="₫"/>
                    </div>
                    <div>
                        <fmt:formatNumber value="${item.total}" type="currency" currencySymbol="₫"/>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="total-section">
            <p style="display: flex">
                <h5>Phí vận chuyển: </h5>
                <fmt:formatNumber value="${sessionScope.successOrder.shippingPrice}" type="currency" currencySymbol="₫"/>
            </p>
        </div>

        <div class="total-section">
            <p>
                <strong>Tổng thanh toán: </strong>
                <fmt:formatNumber value="${sessionScope.successOrder.totalPrice}" type="currency" currencySymbol="₫"/>
            </p>
        </div>
    </div>

    <div class="customer-info">
        <div class="info-group">
            <p class="info-label">Họ tên:</p>
            <p>${sessionScope.customerInfo.fullName}</p>
        </div>
        <div class="info-group">
            <p class="info-label">SĐT:</p>
            <p>${sessionScope.customerInfo.phone}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Email:</p>
            <p>${sessionScope.customerInfo.email}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Địa chỉ nhận hàng:</p>
            <p>${sessionScope.customerInfo.address}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Hình thức nhận hàng:</p>
            <p>${sessionScope.customerInfo.deliveryMethod}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Hình thức thanh toán:</p>
            <p>${sessionScope.customerInfo.paymentMethod}</p>
        </div>
        <c:if test="${not empty sessionScope.customerInfo.note}">
            <div class="info-group">
                <p class="info-label">Ghi chú:</p>
                <p>${sessionScope.customerInfo.note}</p>
            </div>
        </c:if>
    </div>

    <a href="${pageContext.request.contextPath}/product" class="back-button">
        Tiếp tục mua hàng
    </a>
</div>
<%
    // Clear the session attributes after displaying
    session.removeAttribute("successOrder");
    session.removeAttribute("orderItems");
    session.removeAttribute("customerInfo");
%>
</body>
</html>