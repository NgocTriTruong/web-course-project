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

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js"></script>

    <style>
        .address-form {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }
        .address-form-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-header { border-bottom: 1px solid #e9ecef; }
        .form-item label { margin-bottom: 0.5rem; }
        .form-details textarea { margin-top: 1rem; }
        .form-footer { border-top: 1px solid #e9ecef; }
    </style>

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
                <!-- Thêm đoạn mã này ngay sau thẻ <div class="row mt-4"> -->
                <c:if test="${not empty error}">
                    <div class="alert alert-danger" role="alert">
                            ${error}
                    </div>
                </c:if>
                <form id="orderForm" action="${pageContext.request.contextPath}/create-order" method="post">
                    <input type="hidden" name="province" id="provinceHidden" value="">
                    <input type="hidden" name="district" id="districtHidden" value="">
                    <input type="hidden" name="ward" id="wardHidden" value="">
                    <input type="hidden" name="addressDetails" id="addressDetailsHidden" value="">
                    <!-- Order Item Details -->
                    <div class="col-md-8 pb-4">
                        <div class="bg-white pt-3 order">
                            <h6 class="mt-2 ms-4">Sản phẩm trong đơn (${confirmedItems.size()})</h6>
                            <c:forEach var="item" items="${confirmedItems}">
                                <div class="cart-item bg-white">
                                    <div class="d-flex align-items-center">
                                        <img src="${item.img}" alt="${item.name}" class="me-3" width="150px" height="150px">
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
                            <h5 class="mt-2 ms-4">Người đặt hàng</h5>
                            <div class="form-group m-3">
                                <input type="text" name="fullName" class="form-control" placeholder="Họ và tên *" required>
                            </div>
                            <div class="form-group m-3">
                                <input type="text" name="phone" class="form-control" placeholder="Số điện thoại *" required>
                            </div>
                            <div class="form-group m-3 pb-3">
                                <input type="email" name="email" class="form-control" placeholder="Email (Không bắt buộc)">
                            </div>
                        </div>

                        <div class="bg-white pt-2 order">
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
                                        <div class="form-item mb-3">
                                            <label for="province" class="form-label">Chọn Tỉnh:</label>
                                            <select id="province" class="form-select" onchange="loadDistricts()">
                                                <option value="">--Chọn Tỉnh--</option>
                                            </select>
                                        </div>
                                        <div class="form-item mb-3">
                                            <label for="district" class="form-label">Chọn Huyện:</label>
                                            <select id="district" class="form-select" onchange="loadWards()">
                                                <option value="">--Chọn Huyện--</option>
                                            </select>
                                        </div>
                                        <div class="form-item mb-3">
                                            <label for="ward" class="form-label">Chọn Xã:</label>
                                            <select id="ward" class="form-select">
                                                <option value="">--Chọn Xã--</option>
                                            </select>
                                        </div>
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

                    <div class="bg-white pt-2 order">
                        <h5 class="mt-2 ms-4">Phương thức thanh toán</h5>
                        <div class="form-check ms-4 mt-4 d-flex">
                            <input type="radio" class="form-check-input me-2" id="cod" name="paymentMethod" value="COD">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/payment/cod.jpg" alt="" width="40px" height="40px" style="margin-top: -7px;">
                            <label class="form-check-label ms-2" for="cod">Thanh toán khi nhận hàng</label>
                        </div>
                        <div class="form-check ms-4 mt-5 d-flex">
                            <input type="radio" class="form-check-input me-2" id="vnpay" name="paymentMethod" value="VNPAY" checked>
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/payment/vnpay.jpg" alt="" width="40px" height="40px" style="margin-top: -7px;">
                            <label class="form-check-label ms-2" for="vnpay">Thanh toán bằng thẻ ATM nội địa (Qua VNPay)</label>
                        </div>
                    </div>

                    <div class="cart-summary bg-white p-3">
                        <button type="submit" class="btn w-100 mt-3 fw-bold" style="background-color: #fcae18; font-size: 17px;">Đặt hàng</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</main>
<%@ include file="../layout/near_footer.jsp" %>
<%@ include file="../layout/footer.jsp" %>

<script src="${pageContext.request.contextPath}/views/template/assets/scripts/call-api-address.js"></script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        loadProvinces();
        document.querySelector('.chose_location').addEventListener('click', openAddressForm);
        document.getElementById('closeAddressForm').addEventListener('click', closeAddressForm);
        document.getElementById('saveAddressButton').addEventListener('click', saveAddress);
    });

    function openAddressForm() {
        document.getElementById('addressFormModal').style.display = 'block';
        resetAddressFields();
    }

    function closeAddressForm() {
        document.getElementById('addressFormModal').style.display = 'none';
    }

    function resetAddressFields() {
        document.getElementById('savedAddresses').selectedIndex = 0;
        document.getElementById('province').selectedIndex = 0;
        document.getElementById('district').innerHTML = '<option value="">--Chọn Huyện--</option>';
        document.getElementById('ward').innerHTML = '<option value="">--Chọn Xã--</option>';
        document.getElementById('addressDetails').value = '';
    }

    function fillAddressFromSaved() {
        const savedAddresses = document.getElementById('savedAddresses');
        const selectedOption = savedAddresses.options[savedAddresses.selectedIndex];

        if (selectedOption.value === "") {
            resetAddressFields();
            return;
        }

        const province = selectedOption.getAttribute('data-province');
        const district = selectedOption.getAttribute('data-district');
        const ward = selectedOption.getAttribute('data-ward');
        const detail = selectedOption.getAttribute('data-detail');

        const provinceSelect = document.getElementById('province');
        const districtSelect = document.getElementById('district');
        const wardSelect = document.getElementById('ward');
        const addressDetails = document.getElementById('addressDetails');

        addressDetails.value = detail || '';

        for (let option of provinceSelect.options) {
            if (option.text === province) {
                option.selected = true;
                break;
            }
        }

        loadDistricts().then(() => {
            for (let option of districtSelect.options) {
                if (option.text === district) {
                    option.selected = true;
                    break;
                }
            }
            loadWards().then(() => {
                for (let option of wardSelect.options) {
                    if (option.text === ward) {
                        option.selected = true;
                        break;
                    }
                }
            });
        });
    }

    function saveAddress() {
        const provinceSelect = document.getElementById('province');
        const districtSelect = document.getElementById('district');
        const wardSelect = document.getElementById('ward');
        const addressDetailsInput = document.getElementById('addressDetails');

        if (!provinceSelect || !districtSelect || !wardSelect || !addressDetailsInput) {
            console.error('Required elements not found');
            return;
        }

        const provinceName = provinceSelect.options[provinceSelect.selectedIndex]?.text || '';
        const districtName = districtSelect.options[districtSelect.selectedIndex]?.text || '';
        const wardName = wardSelect.options[wardSelect.selectedIndex]?.text || '';
        const addressDetails = addressDetailsInput.value.trim();

        if (!provinceName || !districtName || !wardName ||
            provinceName.includes('--Chọn') || districtName.includes('--Chọn') || wardName.includes('--Chọn')) {
            alert('Vui lòng chọn đầy đủ Tỉnh, Huyện và Xã');
            return;
        }

        document.getElementById('provinceHidden').value = provinceName;
        document.getElementById('districtHidden').value = districtName;
        document.getElementById('wardHidden').value = wardName;
        document.getElementById('addressDetailsHidden').value = addressDetails;

        const addressParts = [];
        if (addressDetails) addressParts.push(addressDetails);
        if (wardName) addressParts.push(wardName);
        if (districtName) addressParts.push(districtName);
        if (provinceName) addressParts.push(provinceName);

        const fullAddress = addressParts.length > 0 ? addressParts.join(', ') : '';
        const displayField = document.querySelector('.chose_location');
        if (displayField && fullAddress) {
            displayField.value = fullAddress;
        }

        closeAddressForm();
    }
</script>

<script>
    document.getElementById("orderForm").addEventListener("submit", function (event) {
        event.preventDefault();
        let paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
        if (confirm("Bạn có chắc chắn muốn đặt hàng không?")) {
            if (paymentMethod === "COD") {
                this.submit();
            } else if (paymentMethod === "VNPAY") {
                this.action = "${pageContext.request.contextPath}/payment";
                this.submit();
            }
        }
    });
</script>
</body>
</html>