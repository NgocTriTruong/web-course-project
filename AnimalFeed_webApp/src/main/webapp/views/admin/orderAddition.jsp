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
                        <!-- Địa chỉ giao hàng -->
                        <div class="mb-3">
                            <label for="address" class="form-label">Địa chỉ giao hàng</label>
                            <input type="text" class="form-control" id="address" name="address" required>
                        </div>
                        <!-- Danh sách sản phẩm -->
                        <div id="productList">
                            <div class="row mb-3 product-row">
                                <div class="col-md-4">
                                    <label class="form-label">Sản phẩm (ID hoặc tên)</label>
                                    <input type="text" class="form-control product-id" name="productIds[]" required>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Tên sản phẩm</label>
                                    <input type="text" class="form-control product-name" disabled>
                                </div>
                                <div class="col-md-2">
                                    <label class="form-label">Số lượng</label>
                                    <input type="number" class="form-control quantity" name="quantities[]" min="1" required>
                                </div>
                                <div class="col-md-2">
                                    <button type="button" class="btn btn-danger remove-product mt-4"><i class="fas fa-trash"></i></button>
                                </div>
                            </div>
                        </div>
                        <button type="button" class="btn btn-primary mb-3" id="addProduct"><i class="fas fa-plus"></i> Thêm sản phẩm</button>
                        <!-- Tổng giá -->
                        <div class="mb-3">
                            <label for="totalPrice" class="form-label">Tổng giá</label>
                            <input type="text" class="form-control" id="totalPrice" name="totalPrice" readonly>
                        </div>
                        <!-- Trạng thái đơn hàng -->
                        <div class="mb-3">
                            <label for="status" class="form-label">Trạng thái</label>
                            <select class="form-select" id="status" name="status" required>
                                <option value="1">Chờ xác nhận</option>
                                <option value="2" selected>Đang chuẩn bị</option>
                                <option value="3">Đang giao</option>
                                <option value="4">Đã giao</option>
                                <option value="5">Đã hủy</option>
                            </select>
                        </div>
                        <!-- Nút hành động -->
                        <button type="submit" class="btn bg_green text-white">Thêm đơn hàng</button>
                        <a href="${pageContext.request.contextPath}/order-manager" class="btn btn-secondary">Hủy</a>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>

<script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
<script>
    // Thêm sản phẩm mới
    document.getElementById('addProduct').addEventListener('click', function() {
        const productRow = document.querySelector('.product-row').cloneNode(true);
        productRow.querySelector('.product-id').value = '';
        productRow.querySelector('.product-name').value = '';
        productRow.querySelector('.quantity').value = '1';
        document.getElementById('productList').appendChild(productRow);
    });

    // Xóa sản phẩm
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('remove-product') && document.querySelectorAll('.product-row').length > 1) {
            e.target.closest('.product-row').remove();
            calculateTotal();
        }
    });

    // Tính tổng giá (giả định giá sản phẩm lấy từ server sau)
    function calculateTotal() {
        let total = 0;
        document.querySelectorAll('.quantity').forEach(input => {
            total += input.value * 10000; // Giả định giá mỗi sản phẩm, cần thay bằng logic thực tế
        });
        document.getElementById('totalPrice').value = total;
    }

    // Gọi tính tổng khi thay đổi số lượng
    document.addEventListener('input', function(e) {
        if (e.target.classList.contains('quantity')) calculateTotal();
    });

    // Xử lý AJAX khi nhập số điện thoại
    document.getElementById('phone').addEventListener('input', function() {
        const phone = this.value.trim();
        const customerNameInput = document.getElementById('customerName');
        const contextPath = "${pageContext.request.contextPath}"; // Lấy context path từ JSP

        if (phone.length >= 10) {
            const baseUrl = window.location.origin + contextPath + "/order-add?phone=";
            const encodedPhone = encodeURIComponent(phone);
            const url = baseUrl + encodedPhone;

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
                        customerNameInput.disabled = false;
                    } else {
                        customerNameInput.value = '';
                        customerNameInput.disabled = false;
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    customerNameInput.value = '';
                    customerNameInput.disabled = false;
                });
        } else {
            customerNameInput.value = '';
            customerNameInput.disabled = false;
        }
    });

    // Xử lý AJAX khi nhập ID hoặc tên sản phẩm
    document.addEventListener('input', function(e) {
        if (e.target.classList.contains('product-id')) {
            const productInput = e.target;
            const productNameInput = productInput.closest('.product-row').querySelector('.product-name');
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
                        } else {
                            productNameInput.value = 'Không có sản phẩm';
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        productNameInput.value = 'Không có sản phẩm';
                    });
            } else {
                productNameInput.value = '';
            }
        }
    });
</script>
</body>
</html>