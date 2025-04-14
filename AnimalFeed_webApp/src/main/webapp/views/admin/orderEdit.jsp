<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Chỉnh sửa đơn hàng</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

    <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>

    <style>
        .address-form {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 50px;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .address-form-content {
            background-color: #fefefe;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
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

        .form-footer {
            border-top: 1px solid #e9ecef;
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

        body.modal-open {
            overflow: hidden;
        }
    </style>

    <!-- Tải call-api-address.js -->
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/call-api-address.js"></script>
</head>

<body>
<!-- Main Navigation -->
<%@ include file="layout/header.jsp" %>

<!-- Main layout -->
<main style="padding-bottom: 100px;">
    <section class="mb-5 text-center text-md-start">
        <div class="p-5"
             style="height: 200px; background: linear-gradient(to right, hsl(78, 50%, 48%), hsl(78, 50%, 68%));"></div>
        <div class="container px-4">
            <div class="card shadow-0" style="margin-top: -100px;">
                <div class="card-body py-5 px-5">
                    <div class="row gx-lg-4 align-items-center">
                        <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                            <h1>Chỉnh sửa đơn hàng</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <div class="container px-4">
        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                    ${errorMessage}
            </div>
        </c:if>

        <!-- Form chỉnh sửa đơn hàng -->
        <form id="editOrderForm" action="${pageContext.request.contextPath}/orderEdit" method="post">
            <input type="hidden" name="orderId" value="${order.id}">
            <input type="hidden" id="totalQuantityHidden" name="totalQuantity" value="${order.totalQuantity}">

            <!-- Thông tin khách hàng -->
            <div class="row mb-4">
                <div class="col-md-6">
                    <label class="form-label h5">Tên khách hàng</label>
                    <input type="text" class="form-control" value="${user.fullName}" disabled>
                </div>
                <div class="col-md-6">
                    <label class="form-label h5">Email</label>
                    <input type="text" class="form-control" value="${user.email}" disabled>
                    <!-- Thay phone thành email -->
                </div>
            </div>

            <!-- Địa chỉ giao hàng -->
            <div class="row mb-4">
                <div class="col-md-12">
                    <label class="form-label h5">Địa chỉ giao hàng</label>
                    <div class="input-group position-relative">
                        <input type="text" class="form-control" id="address" name="address" value="${order.address}"
                               readonly onfocus="openAddressForm()" style="padding-right: 30px;">
                        <i class="fas fa-chevron-right chose_location_right" onclick="openAddressForm()"
                           style="position: absolute; right: 10px; top: 50%; transform: translateY(-50%); cursor: pointer;"></i>
                    </div>
                    <input type="hidden" id="addressHidden" name="addressHidden" value="${order.address}">
                </div>
            </div>

            <!-- Form địa chỉ -->
            <div class="address-form" id="addressFormModal">
                <div class="address-form-content bg-white">
                    <div class="form-header d-flex justify-content-between align-items-center p-3">
                        <h5 class="m-0">Chỉnh sửa địa chỉ nhận hàng</h5>
                        <button type="button" id="closeAddressForm" class="btn-close">×</button>
                    </div>
                    <div class="form-container p-3">
                        <div class="form-item mb-3 position-relative">
                            <label for="provinceInput" class="form-label">Chọn Tỉnh:</label>
                            <input type="text" id="provinceInput" class="form-control" placeholder="--Chọn Tỉnh--">
                            <div id="provinceSuggestions" class="suggestions dropdown-menu w-100"
                                 style="max-height: 200px; overflow-y: auto;"></div>
                            <input type="hidden" id="provinceCode" value="">
                        </div>
                        <div class="form-item mb-3 position-relative">
                            <label for="districtInput" class="form-label">Chọn Huyện:</label>
                            <input type="text" id="districtInput" class="form-control" placeholder="--Chọn Huyện--"
                                   disabled>
                            <div id="districtSuggestions" class="suggestions dropdown-menu w-100"
                                 style="max-height: 200px; overflow-y: auto;"></div>
                            <input type="hidden" id="districtCode" value="">
                        </div>
                        <div class="form-item mb-3 position-relative">
                            <label for="wardInput" class="form-label">Chọn Xã:</label>
                            <input type="text" id="wardInput" class="form-control" placeholder="--Chọn Xã--" disabled>
                            <div id="wardSuggestions" class="suggestions dropdown-menu w-100"
                                 style="max-height: 200px; overflow-y: auto;"></div>
                            <input type="hidden" id="wardCode" value="">
                        </div>
                        <div class="form-details mb-3">
                            <label for="addressDetails" class="form-label">Địa chỉ cụ thể:</label>
                            <textarea class="form-control" id="addressDetails"
                                      placeholder="Nhập địa chỉ cụ thể (vd: Số nhà, đường)" rows="3"></textarea>
                        </div>
                    </div>
                    <div class="form-footer p-3">
                        <button type="button" id="saveAddressButton" class="btn w-100"
                                style="background-color: #fcae18;">Lưu địa chỉ
                        </button>
                    </div>
                </div>
            </div>

            <!-- Trạng thái đơn hàng -->
            <div class="row mb-4">
                <div class="col-md-6">
                    <label class="form-label h5">Trạng thái</label>
                    <select class="form-select" name="status" required>
                        <option value="1" ${order.status == 1 ? 'selected' : ''}>Chờ xác nhận</option>
                        <option value="2" ${order.status == 2 ? 'selected' : ''}>Đang chuẩn bị</option>
                        <option value="3" ${order.status == 3 ? 'selected' : ''}>Đang giao</option>
                        <option value="4" ${order.status == 4 ? 'selected' : ''}>Đã giao</option>
                        <option value="5" ${order.status == 5 ? 'selected' : ''}>Đã hủy</option>
                    </select>
                </div>
            </div>

            <!-- Danh sách sản phẩm -->
            <div class="mb-4">
                <h5 class="mb-3">Danh sách sản phẩm</h5>
                <div id="productList">
                    <c:forEach items="${orderDetails}" var="detail">
                        <div class="row mb-3 product-row">
                            <div class="col-md-3">
                                <label class="form-label">Sản phẩm (ID hoặc tên)</label>
                                <input type="text" class="form-control product-id" name="productIds[]"
                                       value="${detail.orderDetail.productId}" required>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label">Tên sản phẩm</label>
                                <input type="text" class="form-control product-name" value="${detail.product.name}"
                                       disabled>
                            </div>
                            <div class="col-md-2">
                                <label class="form-label">Giá (VNĐ)</label>
                                <input type="number" class="form-control product-price" name="displayPrices[]"
                                       value="${detail.orderDetail.totalPrice / detail.orderDetail.quantity * detail.orderDetail.quantity}"
                                       disabled>
                                <input type="hidden" class="product-price-hidden" name="prices[]"
                                       value="${detail.orderDetail.totalPrice / detail.orderDetail.quantity}">
                                <input type="hidden" class="product-base-price"
                                       value="${detail.orderDetail.totalPrice / detail.orderDetail.quantity}">
                            </div>
                            <div class="col-md-2">
                                <label class="form-label">Số lượng</label>
                                <input type="number" class="form-control quantity" name="quantities[]" min="1"
                                       value="${detail.orderDetail.quantity}" required>
                            </div>
                            <div class="col-md-2">
                                <button type="button" class="btn btn-danger remove-product mt-4"><i
                                        class="fas fa-trash"></i></button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <button type="button" id="addProduct" class="btn btn-primary"><i class="fas fa-plus"></i> Thêm sản phẩm
                </button>
            </div>

            <!-- Tổng giá -->
            <div class="row mb-4">
                <div class="col-md-6">
                    <label class="form-label h5">Tổng giá (VNĐ)</label>
                    <input type="number" class="form-control" id="totalPrice" name="totalPrice"
                           value="${order.totalPrice}" readonly>
                </div>
            </div>

            <!-- Nút lưu và hủy -->
            <div class="d-flex justify-content-end">
                <a href="${pageContext.request.contextPath}/order-manager" class="btn btn-secondary me-2">Hủy</a>
                <button type="submit" class="btn bg_green text-white">Lưu</button>
            </div>
        </form>
    </div>
</main>

<!-- Footer -->
<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5);">©2024 Group-11</p>
</footer>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        // Kiểm tra provincesData từ call-api-address.js
        console.log("Waiting for provinces to load...");
        const checkProvinces = setInterval(() => {
            if (provincesData.length > 0) {
                console.log("Provinces loaded:", provincesData);
                clearInterval(checkProvinces);
            }
        }, 100);

        const editOrderForm = document.getElementById('editOrderForm');
        const productList = document.getElementById('productList');
        const addProductButton = document.getElementById('addProduct');

        // Sự kiện nút đóng và lưu form địa chỉ
        document.getElementById('closeAddressForm').addEventListener('click', closeAddressForm);
        document.getElementById('saveAddressButton').addEventListener('click', saveAddress);

        // Tính tổng giá và tổng số lượng
        function calculateTotal() {
            console.log("Calculating total");
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
            console.log("Total Price: " + totalPrice + ", Total Quantity: " + totalQuantity);
        }

        // Thêm sản phẩm mới
        addProductButton.addEventListener('click', function () {
            const productRow = document.createElement('div');
            productRow.classList.add('row', 'mb-3', 'product-row');
            productRow.innerHTML = `
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
      `;
            productList.appendChild(productRow);
            calculateTotal();
        });

        // Xóa sản phẩm
        productList.addEventListener('click', function (e) {
            if (e.target.closest('.remove-product')) {
                e.target.closest('.product-row').remove();
                calculateTotal();
            }
        });

        // Lấy thông tin sản phẩm qua AJAX
        productList.addEventListener('input', function (e) {
            if (e.target.classList.contains('product-id')) {
                console.log("Product ID input changed");
                const productInput = e.target;
                const productRow = productInput.closest('.product-row');
                const productNameInput = productRow.querySelector('.product-name');
                const productPriceInput = productRow.querySelector('.product-price');
                const productPriceHiddenInput = productRow.querySelector('.product-price-hidden');
                const productBasePriceInput = productRow.querySelector('.product-base-price');
                const quantityInput = productRow.querySelector('.quantity');
                const value = productInput.value.trim();
                const contextPath = "${pageContext.request.contextPath}";

                if (value.length > 0) {
                    const url = contextPath + "/api/product?id=" + encodeURIComponent(value);
                    fetch(url, {
                        method: 'GET',
                        headers: {'Accept': 'application/json'}
                    })
                        .then(response => {
                            if (!response.ok) throw new Error('Server error: ' + response.status);
                            return response.json();
                        })
                        .then(data => {
                            if (data.name && !data.error) {
                                productNameInput.value = data.name;
                                const basePrice = data.price || 0;
                                const quantity = parseInt(quantityInput.value) || 1;
                                productBasePriceInput.value = basePrice;
                                productPriceHiddenInput.value = basePrice;
                                productPriceInput.value = basePrice * quantity;
                                console.log("Product found: " + data.name + ", Price: " + basePrice);
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

            if (e.target.classList.contains('quantity')) {
                console.log("Quantity changed");
                const productRow = e.target.closest('.product-row');
                const basePrice = parseFloat(productRow.querySelector('.product-base-price').value) || 0;
                const quantity = parseInt(e.target.value) || 1;
                productRow.querySelector('.product-price').value = basePrice * quantity;
                calculateTotal();
            }
        });

        // Kiểm tra địa chỉ trước khi submit
        editOrderForm.addEventListener('submit', function (e) {
            console.log("Form submit triggered");
            const address = document.getElementById('addressHidden').value;
            console.log("Address value: " + address);
            if (!address) {
                e.preventDefault();
                console.log("Address is empty, preventing form submission");
                alert('Vui lòng nhập địa chỉ nhận hàng!');
                return;
            }

            const formData = new FormData(editOrderForm);
            console.log("Dữ liệu gửi lên:");
            for (let [key, value] of formData.entries()) {
                console.log(`${key}: ${value}`);
            }
        });

        // Tính tổng ban đầu khi tải trang
        calculateTotal();
    });

    // Hàm mở form địa chỉ và điền dữ liệu hiện tại
    function openAddressForm() {
        const modal = document.getElementById('addressFormModal');
        if (modal.style.display === 'block') return; // Không mở nếu form đã hiển thị
        modal.style.display = 'block';
        document.body.classList.add('modal-open');
        document.getElementById('addressFormModal').style.display = 'block';
        document.body.classList.add('modal-open');

        // Lấy địa chỉ hiện tại và chia thành các phần
        const currentAddress = document.getElementById('address').value.split(', ');
        const detail = currentAddress.length > 3 ? currentAddress.slice(0, -3).join(', ') : currentAddress[0] || '';
        const ward = currentAddress.length > 2 ? currentAddress[currentAddress.length - 3] : '';
        const district = currentAddress.length > 1 ? currentAddress[currentAddress.length - 2] : '';
        const province = currentAddress[currentAddress.length - 1] || '';

        // Điền dữ liệu vào form
        document.getElementById('addressDetails').value = detail;
        document.getElementById('wardInput').value = ward;
        document.getElementById('districtInput').value = district;
        document.getElementById('provinceInput').value = province;

        // Đảm bảo dữ liệu tỉnh đã tải trước khi tiếp tục
        if (provincesData.length === 0) {
            console.warn("Provinces data not loaded yet, waiting...");
            setTimeout(openAddressForm, 100); // Thử lại sau 100ms
            return;
        }

        // Tải dữ liệu autocomplete dựa trên địa chỉ hiện tại
        const selectedProvince = provincesData.find(p => p.name === province);
        if (selectedProvince) {
            document.getElementById('provinceCode').value = selectedProvince.code; // Lưu mã tỉnh
            // Tải dữ liệu huyện
            loadDistricts(selectedProvince.code).then(() => {
                const selectedDistrict = districtsData.find(d => d.name === district);
                if (selectedDistrict) {
                    document.getElementById('districtCode').value = selectedDistrict.code; // Lưu mã huyện
                    document.getElementById('districtInput').disabled = false; // Kích hoạt input huyện
                    // Gọi lại setupDistrictAutocomplete để đảm bảo autocomplete hoạt động
                    setupDistrictAutocomplete();
                    // Tải dữ liệu xã
                    loadWards(selectedDistrict.code).then(() => {
                        const selectedWard = wardsData.find(w => w.name === ward);
                        if (selectedWard) {
                            document.getElementById('wardCode').value = selectedWard.name; // Lưu mã xã
                            document.getElementById('wardInput').disabled = false; // Kích hoạt input xã
                            // Gọi lại setupWardAutocomplete để đảm bảo autocomplete hoạt động
                            setupWardAutocomplete();
                        }
                    });
                }
            });
        }
    }

    // Hàm đóng form địa chỉ
    function closeAddressForm() {
        document.getElementById('addressFormModal').style.display = 'none';
        document.body.classList.remove('modal-open');
    }

    // Hàm lưu địa chỉ
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
        document.getElementById('address').value = fullAddress;
        document.getElementById('addressHidden').value = fullAddress;
        closeAddressForm();
    }
</script>
</body>
</html>