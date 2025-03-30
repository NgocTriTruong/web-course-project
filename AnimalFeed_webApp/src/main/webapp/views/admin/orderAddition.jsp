<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Thêm đơn hàng</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

    <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/call-api-address.js"></script>

    <style>
        .address-form {
            display: none;
            position: fixed; /* Giữ form cố định trên màn hình */
            z-index: 1000; /* Đảm bảo form nằm trên các phần tử khác */
            left: 0;
            top: 50px;
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
    </style>
</head>

<body>

<%@ include file="layout/header.jsp" %>

<main style="padding-bottom: 100px;">
    <section class="mb-5 text-center text-md-start">
        <div class="p-5" style="height: 200px; background: linear-gradient(to right, hsl(78, 50%, 48%), hsl(78, 50%, 68%));"></div>
        <div class="container px-4">
            <div class="card shadow-0" style="margin-top: -100px;">
                <div class="card-body py-5 px-5">
                    <h1 class="mb-4">Thêm đơn hàng mới</h1>
                    <form id="addOrderForm" action="${pageContext.request.contextPath}/add-order" method="post">
                        <!-- Hidden fields -->
                        <input type="hidden" name="address" id="addressHidden" value="">
                        <input type="hidden" name="totalQuantity" id="totalQuantityHidden" value="">

                        <!-- Email -->
                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" required>
                        </div>
                        <!-- Số điện thoại (tùy chọn) -->
                        <div class="mb-3">
                            <label for="phone" class="form-label">Số điện thoại (tùy chọn)</label>
                            <input type="text" class="form-control" id="phone" name="phone">
                        </div>
                        <!-- Tên khách hàng -->
                        <div class="mb-3">
                            <label for="customerName" class="form-label">Tên khách hàng</label>
                            <input type="text" class="form-control" id="customerName" name="customerName" required>
                        </div>
                        <!-- Địa chỉ -->
                        <div class="mb-3">
                            <label for="address" class="form-label">Địa chỉ giao hàng</label>
                                <input id="address" type="text" class="form-control chose_location" placeholder="Tỉnh/Thành Phố, Quận/Huyện, Phường/Xã" readonly onclick="openAddressForm()">
                                <i class="fas fa-chevron-right chose_location_right float-end" onclick="openAddressForm()" style="margin-top: -24px; margin-right: 10px;"></i>

                                <div class="address-form" id="addressFormModal">
                                    <div class="address-form-content bg-white">
                                        <div class="form-header d-flex justify-content-between align-items-center p-3">
                                            <h5 class="m-0">Chọn địa chỉ giao hàng</h5>
                                            <button type="button" id="closeAddressForm" class="btn-close">×</button>
                                        </div>
                                        <div class="form-container p-3">

                                            <!-- Autocomplete cho Tỉnh -->
                                            <div class="form-item mb-3 position-relative">
                                                <label for="provinceInput" class="form-label">Chọn Tỉnh:</label>
                                                <input type="text" id="provinceInput" class="form-control" placeholder="--Chọn Tỉnh--">
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
                            </input>
                        </div>
                        <!-- Danh sách sản phẩm -->
                        <div id="productList">
                            <div class="row mb-3 product-row">
                                <div class="col-md-3">
                                    <label class="form-label">Sản phẩm (ID hoặc tên)</label>
                                    <input type="text" class="form-control product-id" name="productIds[]" required>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Tên sản phẩm</label>
                                    <input type="text" class="form-control product-name" disabled>
                                </div>
                                <div class="col-md-2">
                                    <label class="form-label">Giá (VNĐ)</label>
                                    <input type="number" class="form-control product-price" name="displayPrices[]" disabled>
                                    <input type="hidden" class="product-price-hidden" name="prices[]">
                                    <input type="hidden" class="product-base-price" value="0">
                                </div>
                                <div class="col-md-2">
                                    <label class="form-label">Số lượng</label>
                                    <input type="number" class="form-control quantity" name="quantities[]" min="1" value="1" required>
                                </div>
                                <div class="col-md-2">
                                    <button type="button" class="btn btn-danger remove-product mt-4"><i class="fas fa-trash"></i></button>
                                </div>
                            </div>
                        </div>
                        <!-- Nút thêm sản phẩm -->
                        <button type="button" class="btn btn-primary mb-3" id="addProduct">Thêm sản phẩm</button>
                        <!-- Tổng giá -->
                        <div class="mb-3">
                            <label for="totalPrice" class="form-label">Tổng giá (VNĐ)</label>
                            <input type="number" class="form-control" id="totalPrice" name="totalPrice" readonly>
                        </div>
                        <!-- Nút submit -->
                        <button type="submit" class="btn btn-success">Thêm đơn hàng</button>
                        <a href="${pageContext.request.contextPath}/order-manager" class="btn btn-secondary">Hủy</a>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>

<script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // loadProvinces đã được gọi trong call-api-address.js
        console.log("Waiting for provinces to load...");
        const checkProvinces = setInterval(() => {
            if (provincesData.length > 0) {
                console.log("Provinces loaded:", provincesData);
                clearInterval(checkProvinces);
            }
        }, 100);

        document.getElementById('closeAddressForm').addEventListener('click', closeAddressForm);
        document.getElementById('saveAddressButton').addEventListener('click', saveAddress);

        document.getElementById('addProduct').addEventListener('click', function() {
            const productRow = document.querySelector('.product-row').cloneNode(true);
            productRow.querySelector('.product-id').value = '';
            productRow.querySelector('.product-name').value = '';
            productRow.querySelector('.product-price').value = '';
            productRow.querySelector('.quantity').value = '1';
            document.getElementById('productList').appendChild(productRow);
            calculateTotal();
        });

        document.addEventListener('click', function(e) {
            if (e.target.classList.contains('remove-product') && document.querySelectorAll('.product-row').length > 1) {
                e.target.closest('.product-row').remove();
                calculateTotal();
            }
        });

        document.addEventListener('input', function(e) {
            if (e.target.classList.contains('quantity')) calculateTotal();
            if (e.target.classList.contains('product-id')) {
                const productInput = e.target;
                const productRow = productInput.closest('.product-row');
                const productNameInput = productRow.querySelector('.product-name');
                const productPriceInput = productRow.querySelector('.product-price');
                const productPriceHiddenInput = productRow.querySelector('.product-price-hidden');
                const productBasePriceInput = productRow.querySelector('.product-base-price');
                const quantityInput = productRow.querySelector('.quantity');
                const value = productInput.value.trim();

                if (value.length > 0) {
                    fetch("${pageContext.request.contextPath}/api/product?id=" + encodeURIComponent(value))
                        .then(response => response.json())
                        .then(data => {
                            if (data.name && !data.error) {
                                productNameInput.value = data.name;
                                const basePrice = data.price || 0;
                                const quantity = parseInt(quantityInput.value) || 1;
                                productBasePriceInput.value = basePrice;
                                productPriceHiddenInput.value = basePrice;
                                productPriceInput.value = basePrice * quantity;
                            } else {
                                productNameInput.value = 'Không có sản phẩm';
                                productPriceInput.value = '';
                                productPriceHiddenInput.value = '';
                                productBasePriceInput.value = '0';
                            }
                            calculateTotal();
                        })
                        .catch(error => {
                            console.error('Error fetching product:', error);
                            productNameInput.value = 'Không có sản phẩm';
                            productPriceInput.value = '';
                            productPriceHiddenInput.value = '';
                            productBasePriceInput.value = '0';
                            calculateTotal();
                        });
                } else {
                    productNameInput.value = '';
                    productPriceInput.value = '';
                    productPriceHiddenInput.value = '';
                    productBasePriceInput.value = '0';
                    calculateTotal();
                }
            }
        });

        document.getElementById('email').addEventListener('input', function() {
            const email = this.value.trim();
            const customerNameInput = document.getElementById('customerName');
            if (email.length > 0) {
                fetch("${pageContext.request.contextPath}/add-order-management?email=" + encodeURIComponent(email))
                    .then(response => response.json())
                    .then(data => {
                        customerNameInput.value = data.fullName || '';
                        document.getElementById('phone').value = data.phone || '';
                    })
                    .catch(error => {
                        console.error('Error fetching customer name:', error);
                        customerNameInput.value = '';
                        document.getElementById('phone').value = '';
                    });
            } else {
                customerNameInput.value = '';
                document.getElementById('phone').value = '';
            }
        });

        document.getElementById('addOrderForm').addEventListener('submit', function(e) {
            if (!document.getElementById('addressHidden').value) {
                e.preventDefault();
                alert('Vui lòng nhập địa chỉ nhận hàng!');
            }
        });
    });

    function openAddressForm() {
        document.getElementById('addressFormModal').style.display = 'block';
        document.body.classList.add('modal-open');
        // Reset form khi mở
        document.getElementById('provinceInput').value = '';
        document.getElementById('districtInput').value = '';
        document.getElementById('districtInput').disabled = true;
        document.getElementById('wardInput').value = '';
        document.getElementById('wardInput').disabled = true;
        document.getElementById('addressDetails').value = '';
    }

    function closeAddressForm() {
        document.getElementById('addressFormModal').style.display = 'none';
        document.body.classList.remove('modal-open');
    }

    function saveAddress() {
        const province = document.getElementById('provinceInput').value;
        const district = document.getElementById('districtInput').value;
        const ward = document.getElementById('wardInput').value;
        const addressDetails = document.getElementById('addressDetails').value.trim();

        if (!province || !district || !ward) {
            alert('Vui lòng chọn đầy đủ Tỉnh, Huyện và Xã');
            return;
        }

        const addressParts = [addressDetails, ward, district, province].filter(Boolean);
        const fullAddress = addressParts.join(', ');
        document.getElementById('chose_location').value = fullAddress;
        document.getElementById('addressHidden').value = fullAddress;
        closeAddressForm();
    }

    function calculateTotal() {
        let totalPrice = 0;
        let totalQuantity = 0;
        document.querySelectorAll('.product-row').forEach(row => {
            const basePrice = parseFloat(row.querySelector('.product-base-price').value) || 0;
            const quantity = parseInt(row.querySelector('.quantity').value) || 0;
            const displayPrice = basePrice * quantity;
            row.querySelector('.product-price').value = displayPrice;
            totalPrice += displayPrice;
            totalQuantity += quantity;
        });
        document.getElementById('totalPrice').value = totalPrice;
        document.getElementById('totalQuantityHidden').value = totalQuantity;
    }
</script>
</body>
</html>