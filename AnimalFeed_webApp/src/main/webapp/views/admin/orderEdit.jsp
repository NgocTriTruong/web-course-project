<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css"/>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

  <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>
  <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
</head>

<body>
<!-- Main Navigation -->
<%@ include file="layout/header.jsp" %>

<!-- Main layout -->
<main style="padding-bottom: 100px;">
  <section class="mb-5 text-center text-md-start">
    <div class="p-5" style="height: 200px; background: linear-gradient(to right, hsl(78, 50%, 48%), hsl(78, 50%, 68%));"></div>
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
          <label class="form-label h5">Số điện thoại</label>
          <input type="text" class="form-control" value="${user.phone}" disabled>
        </div>
      </div>

      <!-- Địa chỉ giao hàng -->
      <div class="row mb-4">
        <div class="col-md-12">
          <label class="form-label h5">Địa chỉ giao hàng</label>
          <input type="text" class="form-control" id="address" name="address" value="${order.address}" required>
          <input type="hidden" id="addressHidden" name="addressHidden" value="${order.address}">
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
                <input type="text" class="form-control product-id" name="productIds[]" value="${detail.orderDetail.productId}" required>
              </div>
              <div class="col-md-3">
                <label class="form-label">Tên sản phẩm</label>
                <input type="text" class="form-control product-name" value="${detail.product.name}" disabled>
              </div>
              <div class="col-md-2">
                <label class="form-label">Giá (VNĐ)</label>
                <input type="number" class="form-control product-price" name="displayPrices[]" value="${detail.orderDetail.totalPrice / detail.orderDetail.quantity * detail.orderDetail.quantity}" disabled>
                <input type="hidden" class="product-price-hidden" name="prices[]" value="${detail.orderDetail.totalPrice / detail.orderDetail.quantity}">
                <input type="hidden" class="product-base-price" value="${detail.orderDetail.totalPrice / detail.orderDetail.quantity}">
              </div>
              <div class="col-md-2">
                <label class="form-label">Số lượng</label>
                <input type="number" class="form-control quantity" name="quantities[]" min="1" value="${detail.orderDetail.quantity}" required>
              </div>
              <div class="col-md-2">
                <button type="button" class="btn btn-danger remove-product mt-4"><i class="fas fa-trash"></i></button>
              </div>
            </div>
          </c:forEach>
        </div>
        <button type="button" id="addProduct" class="btn btn-primary"><i class="fas fa-plus"></i> Thêm sản phẩm</button>
      </div>

      <!-- Tổng giá -->
      <div class="row mb-4">
        <div class="col-md-6">
          <label class="form-label h5">Tổng giá (VNĐ)</label>
          <input type="number" class="form-control" id="totalPrice" name="totalPrice" value="${order.totalPrice}" readonly>
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
  // JavaScript cho chỉnh sửa đơn hàng
  document.addEventListener('DOMContentLoaded', function() {
    const editOrderForm = document.getElementById('editOrderForm');
    const productList = document.getElementById('productList');
    const addProductButton = document.getElementById('addProduct');

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
    addProductButton.addEventListener('click', function() {
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
    productList.addEventListener('click', function(e) {
      if (e.target.closest('.remove-product')) {
        e.target.closest('.product-row').remove();
        calculateTotal();
      }
    });

    // Lấy thông tin sản phẩm qua AJAX
    productList.addEventListener('input', function(e) {
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
            headers: { 'Accept': 'application/json' }
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
        productRow.querySelector('.product-price').value = basePrice * quantity;
        calculateTotal();
      }
    });

    // Kiểm tra địa chỉ trước khi submit
    editOrderForm.addEventListener('submit', function(e) {
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
</script>
</body>
</html>