<%@ page import="vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart" %><%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html lang="en">--%>
<%--<head>--%>
<%--    <meta charset="UTF-8">--%>
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
<%--    <title>Shopping Cart</title>--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/cart.css">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">--%>

<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">--%>
<%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">--%>

<%--    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>--%>

<%--    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js"></script>--%>
<%--</head>--%>
<%--<body>--%>
<%--<%@ include file="layout/header.jsp" %>--%>
<%--    <main style="margin-top: 65px;">--%>
<%--        <div class="container-fuild bg-light">--%>
<%--            <div class="container pt-5 cart">--%>
<%--                <div class="row">--%>
<%--                    <!-- Cart Items List -->--%>
<%--                    <div class="col-md-8 pb-4">--%>
<%--                        <h5 class="mb-3 bg-white cart-select-all">--%>
<%--                            <input type="checkbox" id="select-all" class="me-1"> Chọn tất cả (0)--%>
<%--                            <i class="fa-solid fa-trash text-secondary float-end p-3 me-3 mt-1"></i>--%>
<%--                        </h5>--%>
<%--            --%>
<%--                        <!-- Cart Item 1 -->--%>
<%--                        <div class="cart-item bg-white">--%>
<%--                            <div class="d-flex align-items-center">--%>
<%--                                <input type="checkbox" class="me-3 item-checkbox">--%>
<%--                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_01.png" alt="TOP 1 Image" class="me-3" width="150px" height="150px">--%>
<%--                                <div class="" style="width: 530px;">--%>
<%--                                    <div class="float-start cart-item-text">--%>
<%--                                        <h6 class="mt-3" style="font-size: 18px;">Dùng cho heo con từ 05 ngày tuổi đến 35 ngày tuổi</h6>--%>
<%--                                        <h6 class="text-p text-center">Mã SP: TOP 01</h6>--%>
<%--                                    </div>--%>
<%--                                    <div class="float-end">--%>
<%--                                        <p class="price mt-4 me-3">290.000 đ </p>--%>
<%--                                        <p class="original-price ms-1" style="margin-top: -13px;">490.000 đ</p>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                                <div class="d-flex flex align-items-center ms-2">--%>
<%--                                    <div class="quantity-selector">--%>
<%--                                        <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity(this, -1)">-</button>--%>
<%--                                        <input type="text" class="form-control text-center quantity-input" value="1">--%>
<%--                                        <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity(this, 1)">+</button>--%>
<%--                                    </div>--%>
<%--                                    <i class="fa-solid fa-trash text-secondary float-end mt-1 ms-3"></i>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--            --%>
<%--                        <!-- Cart Item 2 -->--%>
<%--                        <div class="cart-item bg-white">--%>
<%--                            <div class="d-flex align-items-center">--%>
<%--                                <input type="checkbox" class="me-3 item-checkbox">--%>
<%--                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_02.png" alt="TOP 2 Image" class="me-3" width="150px" height="150px">--%>
<%--                                <div class="" style="width: 530px;">--%>
<%--                                    <div class="float-start cart-item-text">--%>
<%--                                        <h6 class="mt-3" style="font-size: 18px;"> Dùng cho heo con tập ăn đến 20kg</h6>--%>
<%--                                        <h6 class="text-p text-center">Mã SP: TOP 02</h6>--%>
<%--                                    </div>--%>
<%--                                    <div class="float-end">--%>
<%--                                        <p class="price mt-4 me-3">490.000 đ </p>--%>
<%--                                        <!-- <p class="original-price ms-1" style="margin-top: -13px;">12.990.000 đ</p> -->--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                                <div class="d-flex flex align-items-center ms-2">--%>
<%--                                    <div class="quantity-selector">--%>
<%--                                        <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity(this, -1)">-</button>--%>
<%--                                        <input type="text" class="form-control text-center quantity-input" value="1">--%>
<%--                                        <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity(this, 1)">+</button>--%>
<%--                                    </div>--%>
<%--                                    <i class="fa-solid fa-trash text-secondary float-end mt-1 ms-3"></i>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--    --%>
<%--                        <!-- Cart Item 3 -->--%>
<%--                        <div class="cart-item bg-white">--%>
<%--                            <div class="d-flex align-items-center">--%>
<%--                                <input type="checkbox" class="me-3 item-checkbox">--%>
<%--                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/pig/TOP_03.png" alt="TOP 3 Image" class="me-3" width="150px" height="150px">--%>
<%--                                <div class="" style="width: 530px;">--%>
<%--                                    <div class="float-start cart-item-text">--%>
<%--                                        <h6 class="mt-3" style="font-size: 18px;">Dùng cho heo thịt giống tốt từ 12 – 30kg</h6>--%>
<%--                                        <h6 class="text-p text-center">Mã SP: TOP 03</h6>--%>
<%--                                    </div>--%>
<%--                                    <div class="float-end">--%>
<%--                                        <p class="price mt-4 me-3">290.000 đ </p>--%>
<%--                                        <p class="original-price ms-1" style="margin-top: -13px;">490.000 đ</p>--%>
<%--                                    </div>--%>
<%--                                </div>--%>
<%--                                <div class="d-flex flex align-items-center ms-2">--%>
<%--                                    <div class="quantity-selector">--%>
<%--                                        <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity(this, -1)">-</button>--%>
<%--                                        <input type="text" class="form-control text-center quantity-input" value="1">--%>
<%--                                        <button class="btn btn-outline-secondary" type="button" onclick="updateQuantity(this, 1)">+</button>--%>
<%--                                    </div>--%>
<%--                                    <i class="fa-solid fa-trash text-secondary float-end mt-1 ms-3"></i>--%>
<%--                                </div>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--            --%>
<%--                        <!-- Add more items as needed -->--%>
<%--                    </div>--%>
<%--            --%>
<%--                    <!-- Order Summary -->--%>
<%--                    <div class="col-md-4">--%>
<%--                        <div class="cart-summary bg-white">--%>
<%--                            <h6>Thông tin đơn hàng</h6>--%>
<%--                            <p>Tổng tiền: <span class="float-end" id="total-price">0 đ</span></p>--%>
<%--                            <div class="line-gray mb-2"></div>--%>
<%--                            <p>Tổng khuyến mãi: <span class="float-end">0 đ</span></p>--%>
<%--                            <p>Phí vận chuyển: <span class="float-end">Miễn phí</span></p>--%>
<%--                            <div class="line-gray mb-2"></div>--%>
<%--                            <p>Cần thanh toán: <span class="float-end text-danger" id="final-price">0 đ</span></p>--%>
<%--                            <button class="btn mt-3 fw-bold" style="background-color: #fcae18; font-size: 17px;" onclick="window.location.href='confirm_order.jsp'">Xác nhận đơn</button>--%>
<%--                        </div>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </main>--%>
<%--    <%@ include file="layout/footer.jsp" %>--%>
<%--    <%@ include file="layout/near_footer.jsp" %>--%>

<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>--%>
<%--<script>--%>
<%--    document.getElementById("select-all").addEventListener("change", function() {--%>
<%--        let checkboxes = document.querySelectorAll(".item-checkbox");--%>
<%--        checkboxes.forEach(cb => cb.checked = this.checked);--%>
<%--        updateTotalPrice();--%>
<%--    });--%>

<%--    document.querySelectorAll(".item-checkbox, .option-checkbox").forEach(item => {--%>
<%--        item.addEventListener("change", updateTotalPrice);--%>
<%--    });--%>

<%--    function updateQuantity(button, change) {--%>
<%--        let quantityInput = button.parentNode.querySelector(".quantity-input");--%>
<%--        let newValue = parseInt(quantityInput.value) + change;--%>
<%--        if (newValue >= 1) {--%>
<%--            quantityInput.value = newValue;--%>
<%--            updateTotalPrice();--%>
<%--        }--%>
<%--    }--%>

<%--    function updateTotalPrice() {--%>
<%--        let total = 0;--%>
<%--        document.querySelectorAll(".cart-item").forEach(item => {--%>
<%--            if (item.querySelector(".item-checkbox").checked) {--%>
<%--                let quantity = parseInt(item.querySelector(".quantity-input").value);--%>
<%--                let basePrice = parseInt(item.querySelector(".price").textContent.replace(/[^0-9]/g, ""));--%>
<%--                total += basePrice * quantity;--%>
<%--                item.querySelectorAll(".option-checkbox:checked").forEach(option => {--%>
<%--                    total += parseInt(option.getAttribute("data-price")) * quantity;--%>
<%--                });--%>
<%--            }--%>
<%--        });--%>
<%--        document.getElementById("total-price").textContent = total.toLocaleString("vi-VN") + " đ";--%>
<%--        document.getElementById("final-price").textContent = total.toLocaleString("vi-VN") + " đ";--%>
<%--    }--%>
<%--</script>--%>
<%--</body>--%>
<%--</html>--%>

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

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js"></script>
</head>
<body>
<%@ include file="layout/header.jsp" %>

<main style="margin-top: 65px;">
    <div class="container-fluid bg-light">
        <div class="container pt-5 cart">
            <div class="row">
                <!-- Cart Items List -->
                <div class="col-md-8 pb-4">
                    <h5 class="mb-3 bg-white cart-select-all p-3">
                        <input type="checkbox" id="select-all" class="me-1"> Select All (<span id="selected-count">0</span>)
                        <button onclick="removeSelectedItems()" class="btn btn-link text-secondary float-end">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </h5>

                    <c:if test="${empty sessionScope.cart.cartDetails}">
                        <div class="text-center p-5 bg-white">
                            <h4>Your cart is empty</h4>
                            <a href="${pageContext.request.contextPath}/views/web/product.jsp" class="btn btn-primary mt-3">Continue Shopping</a>
                        </div>
                    </c:if>

                    <c:forEach var="item" items="${sessionScope.cart.cartDetails}" varStatus="status">
                        <div class="cart-item bg-white p-3 mb-3" data-product-id="${item.productId}">
                            <div class="d-flex align-items-center">
                                <input type="checkbox" class="me-3 item-checkbox">
                                <img src="${item.img}"
                                     alt="${item.name}" class="me-3" width="150px" height="150px">
                                        <h6 class="text-secondary">Product Code: ${item.productId}</h6>
                                    </div>
                                    <div class="price-section mt-2">
                                        <span class="price">${item.total}</span>
                                    </div>
                                    <div class="quantity-controls mt-3">
                                        <div class="quantity-selector d-inline-flex align-items-center">
                                            <button class="btn btn-outline-secondary" type="button"
                                                    onclick="updateCartQuantity(${item.productId}, -1)">-</button>
                                            <input type="text" class="form-control text-center quantity-input mx-2"
                                                   value="${item.quantity}" style="width: 60px"
                                                   onchange="updateCartQuantity(${item.productId}, this.value - ${item.quantity})">
                                            <button class="btn btn-outline-secondary" type="button"
                                                    onclick="updateCartQuantity(${item.productId}, 1)">+</button>
                                        </div>
                                        <button onclick="removeFromCart(${item.productId})"
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
                        <h6 class="mb-4">Order Summary</h6>
                        <div class="d-flex justify-content-between mb-3">
                            <span>Subtotal:</span>
                            <span id="subtotal">${sessionScope.cart.totalPrice}</span>
                        </div>
                        <div class="d-flex justify-content-between mb-3">
                            <span>Shipping:</span>
                            <span>Free</span>
                        </div>
                        <hr>
                        <div class="d-flex justify-content-between mb-4">
                            <strong>Total:</strong>
                            <strong class="text-primary" id="total">${sessionScope.cart.totalPrice}</strong>
                        </div>
                        <form action="${pageContext.request.contextPath}/order-confirm" method="get">
                            <button type="submit"
                                    class="btn btn-primary w-100 ${empty sessionScope.cart.cartDetails ? '' : ''}">
                                Proceed to Checkout
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>
<%@ include file="layout/footer.jsp" %>
<script>
    function updateCartQuantity(productId, change) {
        const currentQuantity = parseInt(document.querySelector(`[data-product-id="${productId}"] .quantity-input`).value);
        const newQuantity = currentQuantity + parseInt(change);

        if (newQuantity < 1) return;

        fetch('${pageContext.request.contextPath}/update-cart?productId=' + productId + '&quantity=' + newQuantity, {
            method: 'GET'
        }).then(response => {
            if (response.ok) {
                location.reload();
            }
        });
    }

    function removeFromCart(productId) {
        if (!confirm('Are you sure you want to remove this item?')) return;

        fetch('${pageContext.request.contextPath}/remove-cart?productId=' + productId, {
            method: 'GET'
        }).then(response => {
            if (response.ok) {
                location.reload();
            }
        });
    }

    function removeSelectedItems() {
        const selectedItems = document.querySelectorAll('.item-checkbox:checked');
        if (!selectedItems.length || !confirm('Remove selected items?')) return;

        const promises = Array.from(selectedItems).map(checkbox => {
            const productId = checkbox.closest('.cart-item').dataset.productId;
            return fetch('${pageContext.request.contextPath}/remove-cart?productId=' + productId, {
                method: 'GET'
            });
        });

        Promise.all(promises).then(() => location.reload());
    }

    function proceedToCheckout() {
        window.location.href = '${pageContext.request.contextPath}/checkout';
    }

    // Update selected count when checkboxes change
    document.querySelectorAll('.item-checkbox').forEach(checkbox => {
        checkbox.addEventListener('change', updateSelectedCount);
    });

    document.getElementById('select-all').addEventListener('change', function() {
        document.querySelectorAll('.item-checkbox').forEach(checkbox => {
            checkbox.checked = this.checked;
        });
        updateSelectedCount();
    });

    function updateSelectedCount() {
        const count = document.querySelectorAll('.item-checkbox:checked').length;
        document.getElementById('selected-count').textContent = count;
    }
</script>
</body>
</html>
