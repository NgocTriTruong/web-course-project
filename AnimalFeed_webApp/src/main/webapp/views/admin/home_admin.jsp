<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin_home.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div class="container">
  <aside class="sidebar">
    <h2>Admin</h2>
    <ul class="menu">
      <li onclick="showSection('home')"><i class="fas fa-home"></i> Trang chủ</li>
      <c:if test="${userService.hasPermission(sessionScope.userId, 'USER_MANAGEMENT')}">
        <li onclick="showSection('user-management')"><i class="fas fa-users"></i> Người dùng</li>
      </c:if>
      <c:if test="${userService.hasPermission(sessionScope.userId, 'PRODUCT_MANAGEMENT')}">
        <li onclick="showSection('product-management')"><i class="fas fa-cogs"></i> Sản phẩm</li>
      </c:if>
      <c:if test="${userService.hasPermission(sessionScope.userId, 'ORDER_MANAGEMENT')}">
        <li onclick="showSection('order-management')"><i class="fas fa-shopping-cart"></i> Đơn hàng</li>
      </c:if>
      <c:if test="${userService.hasPermission(sessionScope.userId, 'SHIPPER_MANAGEMENT')}">
        <li onclick="showSection('shipper-management')"><i class="fas fa-truck"></i> Shipper</li>
      </c:if>
      <c:if test="${userService.hasPermission(sessionScope.userId, 'NEWS_MANAGEMENT')}">
        <li onclick="showSection('news-management')"><i class="fas fa-newspaper"></i> Tin tức</li>
      </c:if>
      <li onclick="logout()"><i class="fas fa-sign-out-alt"></i> Đăng xuất</li>
    </ul>
  </aside>

  <main class="dashboard">
    <header class="dashboard-header">
      <p>Dashboard</p>
      <div class="user-info">
        <i class="far fa-frown"></i>
        <span>${sessionScope.userFullName}</span>
      </div>
    </header>

    <!-- Trang chủ -->
    <section id="home" class="section active">
      <h3>Chào mừng đến với trang Admin!</h3>
      <p>Chọn một mục từ menu để quản lý hệ thống.</p>
    </section>

    <!-- Quản lý Người dùng -->
    <c:if test="${userService.hasPermission(sessionScope.userId, 'USER_MANAGEMENT')}">
      <section id="user-management" class="section">
        <h3>Quản lý Người dùng</h3>
        <div class="controls">
          <input type="text" id="user-name" placeholder="Tên người dùng">
          <button onclick="addUser()">Thêm Người dùng</button>
        </div>
        <ul id="user-list"></ul>
      </section>
    </c:if>

    <!-- Quản lý Sản phẩm -->
    <c:if test="${userService.hasPermission(sessionScope.userId, 'PRODUCT_MANAGEMENT')}">
      <section id="product-management" class="section">
        <h3>Quản lý Sản phẩm</h3>
        <div class="controls">
          <div class="input-group">
            <input type="text" id="product-id" placeholder="Mã sản phẩm">
            <input type="text" id="category-id" placeholder="Mã danh mục">
            <input type="text" id="product-name" placeholder="Tên sản phẩm">
            <input type="number" id="product-price" placeholder="Giá tiền">
            <input type="number" id="product-quantity" placeholder="Số lượng">
            <select id="product-status">
              <option value="">Chọn trạng thái</option>
              <option value="active">Đang bán</option>
              <option value="inactive">Ngừng bán</option>
            </select>
            <select id="product-type">
              <option value="">Chọn loại sản phẩm</option>
              <option value="Loại A">Loại A</option>
              <option value="Loại B">Loại B</option>
              <option value="Loại C">Loại C</option>
            </select>
            <select id="sale-type">
              <option value="">Chọn loại giảm giá</option>
              <option value="no_sale">Không giảm</option>
              <option value="sale_20">Giảm 20%</option>
            </select>
            <input type="text" id="brand-name" placeholder="Tên thương hiệu">
            <input type="date" id="product-date">
            <input type="file" id="product-image" accept="image/*">
          </div>
          <textarea id="product-desc" placeholder="Mô tả sản phẩm"></textarea>
          <button onclick="addProduct()">Thêm Sản phẩm</button>
        </div>
        <ul id="product-list"></ul>
      </section>
    </c:if>

    <!-- Quản lý Đơn hàng -->
    <c:if test="${userService.hasPermission(sessionScope.userId, 'ORDER_MANAGEMENT')}">
      <section id="order-management" class="section">
        <h3>Quản lý Đơn hàng</h3>
        <div class="controls">
          <input type="text" id="order-id" placeholder="Mã đơn hàng">
          <input type="text" id="customer-name" placeholder="Tên khách hàng">
          <select id="order-status">
            <option value="Đang chuẩn bị hàng">Đang chuẩn bị hàng</option>
            <option value="Đang vận chuyển">Đang vận chuyển</option>
            <option value="Hoàn tất giao hàng">Hoàn tất giao hàng</option>
            <option value="Đã hủy">Đã hủy</option>
          </select>
          <button onclick="addOrder()">Thêm Đơn hàng</button>
          <button onclick="fetchOrders()">Lấy Danh sách Đơn hàng</button>
        </div>
        <table id="order-table">
          <thead>
          <tr>
            <th>Mã Đơn hàng</th>
            <th>Tên Khách hàng</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
          </thead>
          <tbody>
          <!-- Dữ liệu đơn hàng sẽ được thêm vào đây -->
          </tbody>
        </table>
      </section>
    </c:if>

    <!-- Quản lý Shipper -->
    <c:if test="${userService.hasPermission(sessionScope.userId, 'SHIPPER_MANAGEMENT')}">
      <section id="shipper-management" class="section">
        <h3>Quản lý Shipper</h3>
        <div class="controls">
          <input type="text" id="shipper-name" placeholder="Tên shipper">
          <button onclick="addShipper()">Thêm Shipper</button>
        </div>
        <table id="shipper-table">
          <thead>
          <tr>
            <th>Tên Shipper</th>
            <th>Thao tác</th>
          </tr>
          </thead>
          <tbody>
          <!-- Dữ liệu shipper sẽ được thêm vào đây -->
          </tbody>
        </table>
      </section>
    </c:if>

    <!-- Quản lý Tin tức -->
    <c:if test="${userService.hasPermission(sessionScope.userId, 'NEWS_MANAGEMENT')}">
      <section id="news-management" class="section">
        <h3>Quản lý Tin tức</h3>
        <div class="controls">
          <input type="text" id="news-title" placeholder="Tiêu đề tin tức">
          <textarea id="news-content" placeholder="Nội dung tin tức"></textarea>
          <button onclick="addNews()">Thêm Tin tức</button>
        </div>
        <ul id="news-list"></ul>
      </section>
    </c:if>
  </main>
</div>

<script src="${pageContext.request.contextPath}/./views/template/assets/scripts/admin_home.js"></script>
</body>
</html>