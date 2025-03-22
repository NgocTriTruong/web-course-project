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
                    <form id="addOrderForm" action="${pageContext.request.contextPath}/add-order" method="post">
                        <!-- Hidden fields -->
                        <input type="hidden" name="address" id="addressHidden" value="">
                        <input type="hidden" name="totalQuantity" id="totalQuantityHidden" value="">

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
                                    <input type="number" class="form-control product-price" name="displayPrices[]" disabled>
                                    <input type="hidden" class="product-price-hidden" name="prices[]">
                                    <input type="hidden" class="product-base-price" value="0"> <!-- Lưu giá gốc (đã giảm) -->
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
<script src="${pageContext.request.contextPath}/views/template/assets/scripts/call-api-address.js"></script>
<script>
    console.log("JavaScript loaded"); // Log để kiểm tra script chạy

    // Hàm mở form địa chỉ
    function openAddressForm() {
        console.log("Opening address form");
        document.getElementById('addressFormModal').style.display = 'block';
    }

    // Hàm đóng form địa chỉ
    function closeAddressForm() {
        console.log("Closing address form");
        document.getElementById('addressFormModal').style.display = 'none';
    }

    // Hàm lưu địa chỉ
    function saveAddress() {
        console.log("Saving address");
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

        console.log("Province: " + provinceName + ", District: " + districtName + ", Ward: " + wardName + ", Details: " + addressDetails);

        if (!provinceCode || !districtCode || !wardCode ||
            provinceName.includes('--Chọn') || districtName.includes('--Chọn') || wardName.includes('--Chọn')) {
            console.log("Validation failed: Missing province, district, or ward");
            alert('Vui lòng chọn đầy đủ Tỉnh, Huyện và Xã');
            return;
        }

        const addressParts = [];
        if (addressDetails) addressParts.push(addressDetails.trim());
        if (wardName && !wardName.includes('--Chọn')) addressParts.push(wardName.trim());
        if (districtName && !districtName.includes('--Chọn')) addressParts.push(districtName.trim());
        if (provinceName && !provinceName.includes('--Chọn')) addressParts.push(provinceName.trim());

        const fullAddress = addressParts.length > 0 ? addressParts.join(', ') : '';
        console.log("Full Address: " + fullAddress);
        document.getElementById('chose_location').value = fullAddress;
        document.getElementById('addressHidden').value = fullAddress;

        closeAddressForm();
    }

    // Tính tổng giá và tổng số lượng
    function calculateTotal() {
        console.log("Calculating total");
        let totalPrice = 0;
        let totalQuantity = 0;
        document.querySelectorAll('.product-row').forEach(row => {
            const basePrice = parseFloat(row.querySelector('.product-base-price').value) || 0;
            const quantity = parseInt(row.querySelector('.quantity').value) || 0;
            const displayPrice = basePrice * quantity; // Giá hiển thị = giá đã giảm × số lượng
            row.querySelector('.product-price').value = displayPrice; // Cập nhật ô giá
            totalPrice += displayPrice;
            totalQuantity += quantity;
        });
        document.getElementById('totalPrice').value = totalPrice;
        document.getElementById('totalQuantityHidden').value = totalQuantity;
        console.log("Total Price: " + totalPrice + ", Total Quantity: " + totalQuantity);
    }

    document.addEventListener("DOMContentLoaded", function() {
        console.log("DOM fully loaded");

        // Load tỉnh
        loadProvinces();

        // Gắn sự kiện cho nút đóng form địa chỉ
        const closeAddressFormBtn = document.getElementById('closeAddressForm');
        if (closeAddressFormBtn) {
            closeAddressFormBtn.addEventListener('click', closeAddressForm);
        } else {
            console.error("Không tìm thấy nút closeAddressForm");
        }

        // Gắn sự kiện cho nút lưu địa chỉ
        const saveAddressBtn = document.getElementById('saveAddressButton');
        if (saveAddressBtn) {
            saveAddressBtn.addEventListener('click', saveAddress);
        } else {
            console.error("Không tìm thấy nút saveAddressButton");
        }

        // Thêm sản phẩm mới
        const addProductBtn = document.getElementById('addProduct');
        if (addProductBtn) {
            addProductBtn.addEventListener('click', function() {
                console.log("Adding new product row");
                const productRow = document.querySelector('.product-row').cloneNode(true);
                productRow.querySelector('.product-id').value = '';
                productRow.querySelector('.product-name').value = '';
                productRow.querySelector('.product-price').value = '';
                productRow.querySelector('.quantity').value = '1';
                document.getElementById('productList').appendChild(productRow);
                calculateTotal();
            });
        } else {
            console.error("Không tìm thấy nút addProduct");
        }

        // Xóa sản phẩm
        document.addEventListener('click', function(e) {
            if (e.target.classList.contains('remove-product') && document.querySelectorAll('.product-row').length > 1) {
                console.log("Removing product row");
                e.target.closest('.product-row').remove();
                calculateTotal();
            }
        });

        // Gọi tính tổng khi thay đổi số lượng
        document.addEventListener('input', function(e) {
            if (e.target.classList.contains('quantity')) {
                console.log("Quantity changed");
                calculateTotal();
            }
        });

        // Xử lý AJAX khi nhập số điện thoại
        const phoneInput = document.getElementById('phone');
        if (phoneInput) {
            phoneInput.addEventListener('input', function() {
                console.log("Phone input changed");
                const phone = this.value.trim();
                const customerNameInput = document.getElementById('customerName');
                const contextPath = "${pageContext.request.contextPath}";

                if (phone.length >= 10) {
                    const url = contextPath + "/add-order-management?phone=" + encodeURIComponent(phone);
                    fetch(url, {
                        method: 'GET',
                        headers: { 'Accept': 'application/json' }
                    })
                        .then(response => {
                            if (!response.ok) throw new Error('Server error: ' + response.status);
                            return response.json();
                        })
                        .then(data => {
                            console.log("Customer name fetched: " + data.fullName);
                            customerNameInput.value = data.fullName || '';
                        })
                        .catch(error => {
                            console.error('Error fetching customer name:', error);
                            customerNameInput.value = '';
                        });
                } else {
                    customerNameInput.value = '';
                }
            });
        } else {
            console.error("Không tìm thấy input phone");
        }

        // Xử lý AJAX khi nhập ID hoặc tên sản phẩm
        document.addEventListener('input', function(e) {
            if (e.target.classList.contains('product-id')) {
                console.log("Product ID input changed");
                const productInput = e.target;
                const productRow = productInput.closest('.product-row');
                const productNameInput = productRow.querySelector('.product-name');
                const productPriceInput = productRow.querySelector('.product-price');
                const productPriceHiddenInput = productRow.querySelector('.product-price-hidden');
                const productBasePriceInput = productRow.querySelector('.product-base-price'); // Lưu giá gốc (đã giảm)
                const quantityInput = productRow.querySelector('.quantity');
                const value = productInput.value.trim();
                const contextPath = "${pageContext.request.contextPath}";

                if (value.length > 0) {
                    const url = contextPath + "/api/product?id=" + encodeURIComponent(value);
                    fetch(url, {
                        method: 'GET',
                        headers: { 'Accept': 'application/json' }
                    })
                        .then(response => {
                            if (!response.ok) throw new Error('Server error: ' + response.status);
                            return response.json();
                        })
                        .then(data => {
                            if (data.name && !data.error) {
                                productNameInput.value = data.name;
                                const basePrice = data.price || 0; // Giá đã giảm từ API
                                const quantity = parseInt(quantityInput.value) || 1;
                                productBasePriceInput.value = basePrice; // Lưu giá gốc (đã giảm)
                                productPriceHiddenInput.value = basePrice; // Giá gửi lên server
                                productPriceInput.value = basePrice * quantity; // Giá hiển thị = giá đã giảm × số lượng
                                console.log("Product found: " + data.name + ", Price (discounted): " + basePrice);
                            } else {
                                productNameInput.value = 'Không có sản phẩm';
                                productPriceInput.value = '';
                                productPriceHiddenInput.value = '';
                                productBasePriceInput.value = '0';
                                console.log("Product not found");
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

            // Cập nhật giá khi thay đổi số lượng
            if (e.target.classList.contains('quantity')) {
                console.log("Quantity changed");
                const productRow = e.target.closest('.product-row');
                const basePrice = parseFloat(productRow.querySelector('.product-base-price').value) || 0;
                const quantity = parseInt(e.target.value) || 1;
                productRow.querySelector('.product-price').value = basePrice * quantity; // Cập nhật giá hiển thị
                calculateTotal();
            }
        });

        // Xử lý submit form
        const addOrderForm = document.getElementById('addOrderForm');
        if (addOrderForm) {
            console.log("Attaching submit event to form");
            addOrderForm.addEventListener('submit', function(e) {
                console.log("Form submit triggered");
                const address = document.getElementById('addressHidden').value;
                console.log("Address value: " + address);
                if (!address) {
                    e.preventDefault();
                    console.log("Address is empty, preventing form submission");
                    alert('Vui lòng nhập địa chỉ nhận hàng!');
                    return;
                }

                // Kiểm tra các trường bắt buộc
                const phone = document.getElementById('phone').value;
                const customerName = document.getElementById('customerName').value;
                const productIds = document.querySelectorAll('.product-id');
                const quantities = document.querySelectorAll('.quantity');
                console.log("Phone: " + phone);
                console.log("CustomerName: " + customerName);
                productIds.forEach((input, index) => {
                    console.log(`ProductId[${index}]: ${input.value}`);
                });
                quantities.forEach((input, index) => {
                    console.log(`Quantity[${index}]: ${input.value}`);
                });

                // Kiểm tra tính hợp lệ của form
                if (!addOrderForm.checkValidity()) {
                    e.preventDefault();
                    console.log("Form validation failed");
                    addOrderForm.reportValidity();
                    return;
                }

                const formData = new FormData(addOrderForm);
                console.log("Dữ liệu gửi lên:");
                for (let [key, value] of formData.entries()) {
                    console.log(`${key}: ${value}`);
                }
            });
        } else {
            console.error("Không tìm thấy form với id='addOrderForm'");
        }
    });
</script>
</body>
</html>