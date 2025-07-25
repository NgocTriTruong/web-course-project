<%@ page import="vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/cart.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>

    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger
            intent="WELCOME"
            chat-title="VinaFeed_chat"
            agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264"
            language-code="vi"
    ></df-messenger>
</head>
<body>
<%@ include file="layout/header.jsp" %>

<main style="margin-top: 65px;">
    <div class="container-fluid bg-light">
        <div class="container pt-5 cart">
            <div class="row">
                <!-- Cart Items List -->
                <div class="col-md-8 pb-4">
                    <c:if test="${not empty sessionScope.cartError}">
                        <div class="alert alert-warning alert-dismissible fade show" role="alert">
                                ${sessionScope.cartError}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                        </div>
                    </c:if>
                    <h5 class="mb-3 bg-white cart-select-all p-3 justify-content-center">
                        <input type="checkbox" id="select-all" class="me-1"> Chọn tất cả (<span id="selected-count">0</span>)
                        <button onclick="removeSelectedItems()" class="btn btn-link text-secondary float-end">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </h5>

                    <c:if test="${empty sessionScope.cart.cartDetails}">
                        <div class="text-center p-5 bg-white">
                            <h4>Giỏ hàng của bạn trống</h4>
                            <a href="${pageContext.request.contextPath}/list-product" class="btn btn-primary mt-3">Tiếp tục mua hàng</a>
                        </div>
                    </c:if>

                    <c:forEach var="item" items="${sessionScope.cart.cartDetails}" varStatus="status">
                        <div class="cart-item bg-white p-3 mb-3" data-product-id="${item.productId}" data-status="${item.status}">
                            <div class="d-flex align-items-center">
                                <input type="checkbox" class="me-3 item-checkbox">
                                <img src="${pageContext.request.contextPath}${item.img}" alt="${item.name}" class="me-3" width="150px" height="150px">
                                <div>
                                    <h5>${item.name}</h5>
                                    <h6>${item.desc}</h6>
                                    <div class="price-section mt-2">
                                        <span class="price">
                                            <fmt:formatNumber value="${item.unitPrice}" type="number" pattern="#,###" />đ
                                        </span>
                                    </div>
                                    <div class="quantity-controls mt-3">
                                        <div class="quantity-selector d-inline-flex align-items-center">
                                            <button class="btn btn-outline-secondary" type="button"
                                                    onclick="updateCartQuantity('${item.productId}', -1)">-</button>
                                            <input type="number" class="form-control text-center quantity-input mx-2"
                                                   value="${item.quantity}" min="1" style="width: 60px"
                                                   onchange="updateCartQuantity('${item.productId}', this.value)">
                                            <button class="btn btn-outline-secondary" type="button"
                                                    onclick="updateCartQuantity('${item.productId}', 1)">+</button>
                                        </div>
                                        <button onclick="removeFromCart('${item.productId}')"
                                                class="btn btn-link text-secondary ms-3">
                                            <i class="fa-solid fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <!-- Order Summary -->
                <div class="col-md-4">
                    <div class="cart-summary bg-white p-4">
                        <h6 class="mb-4">Đơn hàng</h6>
                        <hr>
                        <div class="d-flex justify-content-between mb-4">
                            <strong>Số lượng:</strong>
                            <strong class="text-primary" id="quantity">${sessionScope.cart.totalQuantity}</strong>
                        </div>
                        <div class="d-flex justify-content-between mb-4">
                            <strong>Tổng tiền:</strong>
                            <strong class="text-primary" id="total">
                                <fmt:formatNumber value="${sessionScope.cart.totalPrice}" type="currency" currencySymbol="₫"/>
                            </strong>
                        </div>
                        <form action="${pageContext.request.contextPath}/order-confirm" method="get">
                            <button type="submit"
                                    class="btn btn-primary w-100 ${empty sessionScope.cart.cartDetails ? 'disabled' : ''}">
                                Thanh toán
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<%@ include file="layout/near_footer.jsp" %>
<%@ include file="layout/footer.jsp" %>
<script>
    function removeFromCart(productId) {
        const contextPath = '${pageContext.request.contextPath}';
        if (!confirm('Bạn muốn xóa sản phẩm này?')) return;
        fetch(contextPath + '/remove-cart?productId=' + productId)
            .then(response => {
                if (response.ok) window.location.reload();
                else if (response.status === 401) window.location.href = contextPath + '/login';
                else throw new Error('Lỗi khi xóa sản phẩm');
            })
            .catch(error => {
                console.error(error);
                alert('Lỗi khi xóa sản phẩm');
            });
    }

    function removeSelectedItems() {
        const selectedItems = document.querySelectorAll('.item-checkbox:checked');
        if (!selectedItems.length) {
            alert('Vui lòng chọn sản phẩm để xóa');
            return;
        }
        if (!confirm('Bạn muốn xóa các sản phẩm đã chọn?')) return;

        const contextPath = '${pageContext.request.contextPath}';
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = contextPath + '/remove-selected';
        form.style.display = 'none';

        selectedItems.forEach(checkbox => {
            const productId = checkbox.closest('.cart-item').dataset.productId;
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'productIds';
            input.value = productId;
            form.appendChild(input);
        });

        // Append form to the body and submit
        document.body.appendChild(form);
        form.submit();
    }

    function updateCartQuantity(productId, quantityChange) {
        const cartItem = document.querySelector('.cart-item[data-product-id="' + productId + '"]');
        const input = cartItem.querySelector('.quantity-input');
        let newQuantity = typeof quantityChange === 'string' ? parseInt(quantityChange) : parseInt(input.value) + quantityChange;

        if (isNaN(newQuantity) || newQuantity < 1) {
            input.value = 1;
            alert('Số lượng tối thiểu là 1.');
            return;
        }

        const contextPath = '${pageContext.request.contextPath}';
        fetch(contextPath + '/update-cart?productId=' + productId + '&quantity=' + newQuantity, {
            headers: { 'Accept': 'application/json' }
        })
            .then(response => {
                if (!response.ok) {
                    if (response.status === 401) {
                        window.location.href = contextPath + '/login';
                        return;
                    }
                    throw new Error('Lỗi mạng');
                }
                return response.json();
            })
            .then(data => {
                input.value = data.adjustedQuantity;
                if (data.success) {
                    updateTotals();
                } else {
                    alert(data.message || 'Lỗi khi cập nhật giỏ hàng');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Lỗi khi cập nhật giỏ hàng');
            });
    }

    // Update checkbox status in database
    function updateCheckboxStatus(productId) {
        const checked = document.querySelector('.cart-item[data-product-id="' + productId + '"] .item-checkbox').checked;
        const contextPath = '${pageContext.request.contextPath}';
        const status = checked ? 1 : 0;
        fetch(contextPath + '/update-cart?productId=' + productId + '&status=' + status)
            .then(response => {
                if (!response.ok) throw new Error('Lỗi mạng');
                updateTotals();
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Lỗi cập nhật trạng thái');
            });
    }

    // Calculate and update totals based on checked items
    function updateTotals() {
        let total = 0;
        let totalQuantity = 0;
        let selectedCount = 0;

        document.querySelectorAll('.cart-item').forEach(item => {
            const checkbox = item.querySelector('.item-checkbox');
            if (checkbox.checked) {
                const quantity = parseInt(item.querySelector('.quantity-input').value);
                const unitPrice = parseInt(item.querySelector('.price').textContent.replace(/[^\d]/g, ''));
                total += quantity * unitPrice;
                totalQuantity += quantity;
                selectedCount++;
            }
        });

        document.getElementById('total').textContent = formatPrice(total);
        document.getElementById('quantity').textContent = totalQuantity;
        document.getElementById('selected-count').textContent = selectedCount;
    }

    function formatPrice(price) {
        return Math.round(price).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " ₫";
    }

    // Handle "Select All" checkbox
    document.getElementById('select-all').addEventListener('change', function() {
        const isChecked = this.checked;
        document.querySelectorAll('.item-checkbox').forEach(checkbox => {
            checkbox.checked = isChecked;
            const productId = checkbox.closest('.cart-item').dataset.productId;
            updateCheckboxStatus(productId);
        });
        updateTotals();
    });

    // Handle individual item checkboxes
    document.querySelectorAll('.item-checkbox').forEach(checkbox => {
        checkbox.addEventListener('change', function() {
            const productId = this.closest('.cart-item').dataset.productId;
            updateCheckboxStatus(productId);

            // Update "Select All" checkbox
            const allChecked = [...document.querySelectorAll('.item-checkbox')]
                .every(cb => cb.checked);
            document.getElementById('select-all').checked = allChecked;

            updateTotals();
        });
    });

    document.addEventListener('DOMContentLoaded', function() {
        // Find all cart items
        const cartItems = document.querySelectorAll('.cart-item');

        // For each cart item
        cartItems.forEach(item => {
            const checkbox = item.querySelector('.item-checkbox');
            const status = item.dataset.status;

            // If status is 1, check the checkbox
            if (status === '1') {
                checkbox.checked = true;
            }
        });
        updateTotals();
    });
</script>
</body>
</html>