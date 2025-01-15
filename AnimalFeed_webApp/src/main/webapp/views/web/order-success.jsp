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
        <i class="fas fa-check-circle success-icon"></i>
        <h1>Order Placed Successfully!</h1>
        <p>Thank you for your order. We'll process it right away!</p>
    </div>

    <div class="order-details">
        <h2>Order Details</h2>
        <div class="order-items">
            <div class="item-row item-header">
                <div>Product</div>
                <div>Quantity</div>
                <div>Price</div>
                <div>Total</div>
            </div>
            <c:forEach items="${sessionScope.orderItems}" var="item">
                <div class="item-row">
                    <div>${item.name}</div>
                    <div>${item.quantity}</div>
                    <div>
                        <fmt:formatNumber value="${item.price}" type="currency" currencySymbol="₫"/>
                    </div>
                    <div>
                        <fmt:formatNumber value="${item.total}" type="currency" currencySymbol="₫"/>
                    </div>
                </div>
            </c:forEach>
        </div>

        <div class="total-section">
            <p>
                <strong>Total Amount: </strong>
                <fmt:formatNumber value="${sessionScope.successOrder.totalPrice}" type="currency" currencySymbol="₫"/>
            </p>
        </div>
    </div>

    <div class="customer-info">
        <div class="info-group">
            <p class="info-label">Full Name:</p>
            <p>${sessionScope.customerInfo.fullName}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Phone:</p>
            <p>${sessionScope.customerInfo.phone}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Email:</p>
            <p>${sessionScope.customerInfo.email}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Delivery Address:</p>
            <p>${sessionScope.customerInfo.address}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Delivery Method:</p>
            <p>${sessionScope.customerInfo.deliveryMethod}</p>
        </div>
        <div class="info-group">
            <p class="info-label">Payment Method:</p>
            <p>${sessionScope.customerInfo.paymentMethod}</p>
        </div>
        <c:if test="${not empty sessionScope.customerInfo.note}">
            <div class="info-group">
                <p class="info-label">Note:</p>
                <p>${sessionScope.customerInfo.note}</p>
            </div>
        </c:if>
    </div>

    <a href="${pageContext.request.contextPath}/" class="back-button">
        Continue Shopping
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