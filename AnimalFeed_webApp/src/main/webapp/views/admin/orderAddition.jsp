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
                    <form id="addOrderForm" action="${pageContext.request.contextPath}/order-add" method="post">

                        <!-- Hidden fields for address -->
                        <input type="hidden" name="province" id="provinceHidden" value="">
                        <input type="hidden" name="district" id="districtHidden" value="">
                        <input type="hidden" name="ward" id="wardHidden" value="">
                        <input type="hidden" name="addressDetails" id="addressDetailsHidden" value="">

                        <!-- Số điện thoại -->
                        <div class="mb-3">
                            <label for="phone" class="form-label">Số điện thoại</label>
                            <input type="text" class="form-control" id="phone" name="phone" required>
                        </div>
                        <!-- Tên khách hàng -->
                        <div class="mb-3">
                            <label for="customerName" class="form-label">Tên khách hàng</label>
                            <input type="text" class="form-control" id="customerName" name="customerName" required>
                        </div>
                        <!-- Địa chỉ -->
                        <div class="form-group mb-3 position-relative">
                            <label for="chose_location" class="form-label">Địa chỉ nhận hàng</label>
                            <input type="text" class="form-control chose_location" id="chose_location"
                                   placeholder="Tỉnh/Thành Phố, Quận/Huyện, Phường/Xã" readonly onclick="openAddressForm()">
                            <i class="fas fa-chevron-right chose_location_right float-end" onclick="openAddressForm()"></i>

                            <div class="address-form" id="addressFormModal">
                                <div class="address-form-content bg-white">
                                    <div class="form-header d-flex justify-content-between align-items-center p-3">
                                        <h5 class="m-0">Thêm địa chỉ nhận hàng</h5>
                                        <button type="button" id="closeAddressForm" class="btn-close">×</button>
                                    </div>
                                    <div class="form-container p-3">
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
                                <textarea class="form-control" id="addressDetails"
                                          placeholder="Nhập địa chỉ cụ thể (vd: Số nhà, đường)" rows="3"></textarea>
                                        </div>
                                    </div>
                                    <div class="form-footer p-3">
                                        <button type="button" id="saveAddressButton" class="btn w-100" style="background-color: #fcae18;">
                                            Lưu địa chỉ
                                        </button>
                                    </div>
                                </div>
                            </div>
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
                                    <input type="number" class="form-control product-price" disabled>
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
<!-- Load API address script -->
<script src="${pageContext.request.contextPath}/views/template/assets/scripts/call-api-address.js"></script>
<!-- Custom script -->
<script>
    // Thêm sản phẩm mới
    document.getElementById('addProduct').addEventListener('click', function() {
        const productRow = document.querySelector('.product-row').cloneNode(true);
        productRow.querySelector('.product-id').value = '';
        productRow.querySelector('.product-name').value = '';
        productRow.querySelector('.product-price').value = '';
        productRow.querySelector('.quantity').value = '1';
        document.getElementById('productList').appendChild(productRow);
        calculateTotal();
    });

    // Xóa sản phẩm
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('remove-product') && document.querySelectorAll('.product-row').length > 1) {
            e.target.closest('.product-row').remove();
            calculateTotal();
        }
    });

    // Tính tổng giá dựa trên giá thực tế
    function calculateTotal() {
        let total = 0;
        document.querySelectorAll('.product-row').forEach(row => {
            const price = parseFloat(row.querySelector('.product-price').value) || 0;
            const quantity = parseInt(row.querySelector('.quantity').value) || 0;
            total += price * quantity;
        });
        document.getElementById('totalPrice').value = total;
    }

    // Gọi tính tổng khi thay đổi số lượng
    document.addEventListener('input', function(e) {
        if (e.target.classList.contains('quantity')) {
            calculateTotal();
        }
    });

    // Xử lý AJAX khi nhập số điện thoại
    document.getElementById('phone').addEventListener('input', function() {
        const phone = this.value.trim();
        const customerNameInput = document.getElementById('customerName');
        const contextPath = "${pageContext.request.contextPath}";

        if (phone.length >= 10) {
            const url = window.location.origin + contextPath + "/order-add?phone=" + encodeURIComponent(phone);
            fetch(url, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json'
                }
            })
                .then(response => {
                    if (!response.ok) throw new Error('Server error: ' + response.status);
                    return response.json();
                })
                .then(data => {
                    if (data.fullName) {
                        customerNameInput.value = data.fullName;
                    } else {
                        customerNameInput.value = '';
                    }
                })
                .catch(error => {
                    console.error('Error fetching customer name:', error);
                    customerNameInput.value = '';
                });
        } else {
            customerNameInput.value = '';
        }
    });

    // Xử lý AJAX khi nhập ID hoặc tên sản phẩm
    document.addEventListener('input', function(e) {
        if (e.target.classList.contains('product-id')) {
            const productInput = e.target;
            const productRow = productInput.closest('.product-row');
            const productNameInput = productRow.querySelector('.product-name');
            const productPriceInput = productRow.querySelector('.product-price');
            const value = productInput.value.trim();
            const contextPath = "${pageContext.request.contextPath}";

            if (value.length > 0) {
                const url = contextPath + "/api/product?id=" + encodeURIComponent(value);
                fetch(url, {
                    method: 'GET',
                    headers: {
                        'Accept': 'application/json'
                    }
                })
                    .then(response => {
                        if (!response.ok) throw new Error('Server error: ' + response.status);
                        return response.json();
                    })
                    .then(data => {
                        if (data.name && !data.error) {
                            productNameInput.value = data.name;
                            productPriceInput.value = data.price || '';
                        } else {
                            productNameInput.value = 'Không có sản phẩm';
                            productPriceInput.value = '';
                        }
                        calculateTotal();
                    })
                    .catch(error => {
                        console.error('Error fetching product:', error);
                        productNameInput.value = 'Không có sản phẩm';
                        productPriceInput.value = '';
                        calculateTotal();
                    });
            } else {
                productNameInput.value = '';
                productPriceInput.value = '';
                calculateTotal();
            }
        }
    });

    // Logic cho địa chỉ
    document.addEventListener("DOMContentLoaded", function() {
        console.log("DOMContentLoaded triggered - Loading provinces...");
        loadProvinces(); // Gọi hàm từ call-api-address.js

        document.getElementById('closeAddressForm').addEventListener('click', function() {
            console.log("Closing address form");
            closeAddressForm();
        });

        document.getElementById('saveAddressButton').addEventListener('click', function() {
            console.log("Saving address");
            saveAddress();
        });
    });

    function saveAddress() {
        console.log("Saving address...");
        const provinceSelect = document.getElementById('province');
        const districtSelect = document.getElementById('district');
        const wardSelect = document.getElementById('ward');
        const addressDetailsInput = document.getElementById('addressDetails');

        const provinceCode = provinceSelect.value;
        const districtCode = districtSelect.value;
        const wardCode = wardSelect.value;
        const addressDetails = addressDetailsInput.value.trim();

        const provinceName = provinceSelect.options[provinceSelect.selectedIndex]?.text || '';
        const districtName = districtSelect.options[districtSelect.selectedIndex]?.text || '';
        const wardName = wardSelect.options[wardSelect.selectedIndex]?.text || '';

        if (!provinceCode || !districtCode || !wardCode ||
            provinceName.includes('--Chọn') ||
            districtName.includes('--Chọn') ||
            wardName.includes('--Chọn')) {
            alert('Vui lòng chọn đầy đủ Tỉnh, Huyện và Xã');
            return;
        }

        document.getElementById('provinceHidden').value = provinceName;
        document.getElementById('districtHidden').value = districtName;
        document.getElementById('wardHidden').value = wardName;
        document.getElementById('addressDetailsHidden').value = addressDetails;

        const addressParts = [];
        if (addressDetails) addressParts.push(addressDetails.trim());
        if (wardName && !wardName.includes('--Chọn')) addressParts.push(wardName.trim());
        if (districtName && !districtName.includes('--Chọn')) addressParts.push(districtName.trim());
        if (provinceName && !provinceName.includes('--Chọn')) addressParts.push(provinceName.trim());

        const fullAddress = addressParts.length > 0 ? addressParts.join(', ') : '';
        const displayField = document.getElementById('chose_location');
        if (displayField && fullAddress) {
            displayField.value = fullAddress;
            console.log("Address saved:", fullAddress);
        }

        closeAddressForm();
    }
</script>
</body>
</html>