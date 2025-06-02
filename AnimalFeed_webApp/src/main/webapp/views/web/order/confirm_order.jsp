<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirm Order</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/cart.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

    <style>
        .address-form {
            display: none;
            position: fixed; /* Giữ form cố định trên màn hình */
            z-index: 1000; /* Đảm bảo form nằm trên các phần tử khác */
            left: 0;
            top: 0;
            width: 100%; /* Chiếm toàn bộ chiều rộng màn hình */
            height: 100%; /* Chiếm toàn bộ chiều cao màn hình */
            overflow: auto; /* Cho phép cuộn nếu nội dung vượt quá */
            background-color: rgba(0, 0, 0, 0.4); /* Nền mờ */
        }
        .address-form-content {
            background-color: #fefefe;
            position: absolute; /* Đặt vị trí tuyệt đối trong container fixed */
            top: 50%; /* Đặt đỉnh của form ở giữa màn hình */
            left: 50%; /* Đặt trái của form ở giữa màn hình */
            transform: translate(-50%, -50%); /* Dịch chuyển để căn giữa hoàn toàn */
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-header {
            border-bottom: 1px solid #e9ecef;
        }
        .form-item label {
            margin-bottom: 0.5rem;
        }
        .form-details textarea {
            margin-top: 1rem;
        }
        .form-footer {
            border-top: 1px solid #e9ecef;
        }
        /* Đảm bảo nội dung bên dưới vẫn cuộn được */
        body.modal-open {
            overflow: hidden; /* Ngăn cuộn body khi form mở */
        }

        .suggestions {
            position: absolute;
            z-index: 1000;
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 4px;
            display: none;
        }

        .suggestions.show {
            display: block;
        }

        .suggestions .dropdown-item {
            padding: 8px 12px;
            cursor: pointer;
        }

        .suggestions .dropdown-item:hover {
            background-color: #f8f9fa;
        }

        .sticky-sidebar {
            position: sticky;
            top: 100px;
            align-self: flex-start;
        }
    </style>
    <script>
        // Định nghĩa contextPath cho JS
        const contextPath = "${pageContext.request.contextPath}";
    </script>

    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger intent="WELCOME" chat-title="VinaFeed_chat" agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264" language-code="vi"></df-messenger>
</head>
<body>
<%@ include file="../layout/header.jsp" %>
<main style="margin-top: 85px;">
    <div class="container-fluid bg-light">
        <div class="container pt-4 checkout">
            <a href="${pageContext.request.contextPath}/cart" style="color: rgb(7, 93, 54);">
                <i class="fas fa-chevron-left me-1"></i> Quay lại giỏ hàng
            </a>
            <div class="row mt-4">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                            ${error}
                    </div>
                </c:if>
                <form id="orderForm" action="${pageContext.request.contextPath}/create-order" method="post">
                    <input type="hidden" name="province" id="provinceHidden" value="">
                    <input type="hidden" name="district" id="districtHidden" value="">
                    <input type="hidden" name="ward" id="wardHidden" value="">
                    <input type="hidden" name="wardCode" id="wardCodeHidden" value="">
                    <input type="hidden" name="addressDetails" id="addressDetailsHidden" value="">
                    <input type="hidden" name="shippingFee" id="shippingFeeHidden" value="0">

                    <div class="row">
                        <!-- Order Item Details -->
                        <div class="col-md-8 pb-4">
                            <div class="bg-white pt-3 order">
                                <h6 class="mt-2 ms-4">Sản phẩm trong đơn (${confirmedItems.size()})</h6>
                                <c:forEach var="item" items="${confirmedItems}">
                                    <div class="cart-item bg-white">
                                        <div class="d-flex align-items-center">
                                            <img src="${pageContext.request.contextPath}${item.img}" alt="${item.name}" class="me-3" width="150px" height="150px">
                                            <div class="" style="width: 100%;">
                                                <div class="float-start cart-item-text">
                                                    <h6 class="mt-3" style="font-size: 18px;">${item.name}</h6>
                                                    <h6 class="text-p text-center">Mã SP: ${item.productId}</h6>
                                                    <p>Số lượng: ${item.quantity}</p>
                                                </div>
                                                <div class="float-end">
                                                    <p class="price mt-4 me-3">
                                                        <fmt:formatNumber value="${item.total}" pattern="#,###.###"/> đ
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="line-gray"></div>
                                </c:forEach>
                            </div>

                            <div class="bg-white pt-2 order">
                                <h5 class="mt-2 ms-4 mb-3">Thông tin người đặt hàng</h5>
                                <div class="form-group mx-3 mb-3">
                                    <label for="fullName" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                                    <input type="text" id="fullName" name="fullName" class="form-control"
                                           placeholder="Nhập họ và tên người nhận hàng"
                                           value="${userFullName}" required>
                                    <div id="fullNameError" class="error-message"></div>
                                </div>
                                <div class="form-group mx-3 mb-3">
                                    <label for="phone" class="form-label">Số điện thoại <span class="text-danger">*</span></label>
                                    <input type="text" id="phone" name="phone" class="form-control"
                                           placeholder="Nhập số điện thoại liên hệ"
                                           value="${userPhone}" required>
                                    <div id="phoneError" class="error-message"></div>
                                </div>
                                <div class="form-group mx-3 pb-3">
                                    <label for="email" class="form-label">Email</label>
                                    <input type="email" id="email" name="email" class="form-control"
                                           placeholder="Nhập email (không bắt buộc)"
                                           value="${userEmail}">
                                </div>
                            </div>

                            <div class="bg-white pt-2 order pb-3 mt-2">
                                <h5 class="mt-2 ms-4">Hình thức nhận hàng</h5>
                                <div class="d-flex">
                                    <div class="form-check ms-2">
                                        <input type="radio" class="form-check-input" id="delivery" name="deliveryMethod" value="delivery" checked>
                                        <label class="form-check-label" for="delivery">Giao hàng tận nơi</label>
                                    </div>
                                    <div class="form-check ms-3">
                                        <input type="radio" class="form-check-input" id="storePickup" name="deliveryMethod" value="pickup">
                                        <label class="form-check-label" for="storePickup">Nhận tại cửa hàng</label>
                                    </div>
                                </div>
                                <div class="form-group m-3 position-relative">
                                    <input type="text" class="form-control chose_location" placeholder="Tỉnh/Thành Phố, Quận/Huyện, Phường/Xã" readonly onclick="openAddressForm()">
                                    <i class="fas fa-chevron-right chose_location_right float-end" onclick="openAddressForm()" style="margin-top: -24px; margin-right: 10px;"></i>

                                    <div class="address-form" id="addressFormModal">
                                        <div class="address-form-content bg-white">
                                            <div class="form-header d-flex justify-content-between align-items-center p-3">
                                                <h5 class="m-0">Chọn địa chỉ giao hàng</h5>
                                                <button type="button" id="closeAddressForm" class="btn-close">×</button>
                                            </div>
                                            <div class="form-container p-3">
                                                <!-- Dropdown chọn địa chỉ đã lưu -->
                                                <div class="form-item mb-3">
                                                    <label for="savedAddresses" class="form-label">Chọn địa chỉ đã lưu:</label>
                                                    <select id="savedAddresses" class="form-select" onchange="fillAddressFromSaved()">
                                                        <option value="">--Chọn địa chỉ đã lưu hoặc nhập mới--</option>
                                                        <c:if test="${empty addressList}">
                                                            <option value="" disabled>Không có địa chỉ đã lưu</option>
                                                        </c:if>
                                                        <c:forEach var="address" items="${addressList}">
                                                            <option value="${address.id}"
                                                                    data-province="${address.province}"
                                                                    data-district="${address.district}"
                                                                    data-ward="${address.ward}"
                                                                    data-detail="${address.detail}">
                                                                    ${address.detail}, ${address.ward}, ${address.district}, ${address.province}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>

                                                <!-- Autocomplete cho Tỉnh -->
                                                <div class="form-item mb-3 position-relative">
                                                    <label for="provinceInput" class="form-label">Chọn Tỉnh:</label>
                                                    <input type="text" id="provinceInput" value="" class="form-control" placeholder="--Chọn Tỉnh--">
                                                    <div id="provinceSuggestions" class="suggestions dropdown-menu w-100" style="max-height: 200px; overflow-y: auto;"></div>
                                                    <input type="hidden" id="provinceCode" value="">
                                                </div>

                                                <!-- Autocomplete cho Huyện -->
                                                <div class="form-item mb-3 position-relative">
                                                    <label for="districtInput" class="form-label">Chọn Huyện:</label>
                                                    <input type="text" id="districtInput" class="form-control" placeholder="--Chọn Huyện--" disabled>
                                                    <div id="districtSuggestions" class="suggestions dropdown-menu w-100" style="max-height: 200px; overflow-y: auto;"></div>
                                                    <input type="hidden" id="districtCode" value="">
                                                </div>

                                                <!-- Autocomplete cho Xã -->
                                                <div class="form-item mb-3 position-relative">
                                                    <label for="wardInput" class="form-label">Chọn Xã:</label>
                                                    <input type="text" id="wardInput" class="form-control" placeholder="--Chọn Xã--" disabled>
                                                    <div id="wardSuggestions" class="suggestions dropdown-menu w-100" style="max-height: 200px; overflow-y: auto;"></div>
                                                    <input type="hidden" id="wardCode" value="">
                                                </div>

                                                <!-- Chi tiết địa chỉ -->
                                                <div class="form-details">
                                                    <textarea class="form-control" id="addressDetails" placeholder="Nhập địa chỉ cụ thể (vd: Số nhà, đường)" rows="3"></textarea>
                                                </div>
                                            </div>
                                            <div class="form-footer p-3">
                                                <button type="button" id="saveAddressButton" class="btn w-100" style="background-color: #fcae18;">Lưu địa chỉ</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group m-3 pb-3">
                                    <textarea name="note" class="form-control" placeholder="Ghi chú (VD: Hãy gọi tôi khi chuẩn bị hàng xong)" style="height: 100px;"></textarea>
                                </div>
                            </div>
                        </div>

                        <!-- Cột bên phải - Thanh toán và tổng tiền -->
                        <div class="col-md-4 pb-4 sticky-sidebar">
                            <!-- Phương thức thanh toán -->
                            <div class="bg-white pt-3 order rounded shadow-sm mb-3">
                                <h5 class="mt-2 ms-4 mb-3">Phương thức thanh toán</h5>
                                <div class="mx-3 mb-3 pb-3">
                                    <div class="payment-option mb-4">
                                        <div class="form-check d-flex align-items-center">
                                            <input type="radio" class="form-check-input me-2" id="vnpay" name="paymentMethod" value="VNPAY" checked>
                                            <img src="${pageContext.request.contextPath}/views/template/assets/images/payment/vnpay.jpg" alt="VNPAY" width="40" height="40" class="me-2">
                                            <label class="form-check-label" for="vnpay">
                                                <div>Thanh toán qua VNPAY</div>
                                                <small class="text-muted">Thẻ ATM nội địa/Internet Banking</small>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="payment-option mb-2">
                                        <div class="form-check d-flex align-items-center">
                                            <input type="radio" class="form-check-input me-2" id="cod" name="paymentMethod" value="COD">
                                            <img src="${pageContext.request.contextPath}/views/template/assets/images/payment/cod.jpg" alt="COD" width="40" height="40" class="me-2">
                                            <label class="form-check-label" for="cod">
                                                <div>Thanh toán khi nhận hàng (COD)</div>
                                                <small class="text-muted">Trả tiền mặt khi nhận hàng</small>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Tổng thanh toán -->
                            <div class="bg-white p-3 order rounded shadow-sm">
                                <h5 class="mb-3">Tổng thanh toán</h5>
                                <div class="d-flex justify-content-between mb-2">
                                    <span>Tổng tiền hàng:</span>
                                    <span class="fw-bold"><fmt:formatNumber value="${totalPrice}" pattern="#,###.###"/> đ</span>
                                </div>
                                <input type="hidden" id="cartTotalPrice" value="${totalPrice}">
                                <div class="d-flex justify-content-between mb-2">
                                    <span>Phí vận chuyển:</span>
                                    <span id="summaryShippingFee">0 đ</span>
                                </div>
                                <hr>
                                <c:set var="totalAmount" value="${totalPrice}" />
                                <div class="d-flex justify-content-between mb-3">
                                    <span class="fw-bold">Thành tiền:</span>
                                    <span class="fw-bold price" id="totalPayment"><fmt:formatNumber value="${totalAmount}" pattern="#,###.###"/> đ</span>
                                </div>
                                <div class="cart-summary bg-white p-3">
                                    <button type="submit" id="submitOrder" class="btn w-100 mt-3 fw-bold" style="background-color: #fcae18; font-size: 17px;">Đặt hàng</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>
<%@ include file="../layout/near_footer.jsp" %>
<%@ include file="../layout/footer.jsp" %>

<script src="${pageContext.request.contextPath}/views/template/assets/scripts/api_ghn/api_ghn_confirm_order.js"></script>
</body>
</html>