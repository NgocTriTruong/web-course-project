<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header>
  <!-- Sidenav -->
  <nav id="main-sidenav" class="sidenav sidenav-md shadow-1" data-mdb-mode="side"
       data-mdb-hidden="false" style="width: 280px; background-color: #1c1301">
    <a class="ripple d-flex justify-content-center pt-4 pb-2" href="#!" data-mdb-ripple-color="primary">
      <img id="MDB-logo" src="${pageContext.request.contextPath}/views/template/assets/images/header/logo_vina.png" style="width: 100px" height="70px"
           alt="MDB Logo" draggable="false"/>
    </a>

    <hr class="hr">

    <ul class="sidenav-menu text-white mb-5">
      <li class="sidenav-item">
        <a class="sidenav-link text-white" href="${pageContext.request.contextPath}/dashboard">
          <i class="fas fa-tachometer-alt fa-fw me-3 "></i><span class="h5 text">Trang chủ</span></a>
      </li>
      <li class="sidenav-item pt-3">
        <span class="sidenav-subheading text-muted text-uppercase fw-bold" style="font-size: 15px; padding-bottom: 10px;">Quản lý</span>
      </li>
      <li class="sidenav-item mt-2">
        <a class="sidenav-link " href="userManagement">
          <i class="fas fa-users fa-fw me-2"></i><span class="h5 text">Quản lý người dùng</span><i class="fas fa-chevron-down" style="margin-left: 11px;margin-bottom: 1px;"></i></a>
        <ul class="sidenav-collapse">
          <li class="sidenav-item">
            <a class="sidenav-link h6" href="addUser" style="font-size: 17px;">Thêm người dùng</a>
          </li>
          <li class="sidenav-item">
            <a class="sidenav-link" href="userManagement" style="font-size: 17px;">Danh sách người dùng</a>
          </li>
        </ul>
      </li>
      <li class="sidenav-item">
        <a class="sidenav-link " href="${pageContext.request.contextPath}/product-manager">
          <i class="fas fa-gift fa-fw me-2"></i><span class="h5 text">Quản lý sản phẩm</span> <i class="fas fa-chevron-down" style="margin-left: 31px;margin-bottom: 2px;"></i></a>
        <ul class="sidenav-collapse ">
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/views/admin/productAddition.jsp" style="font-size: 17px;">Thêm sản phẩm</a>
          </li>
          <li class="sidenav-item"> 
            <a class="sidenav-link" href="${pageContext.request.contextPath}/product-manager" style="font-size: 17px;">Danh sách sản phẩm</a>
          </li>
        </ul>
      </li>
      <li class="sidenav-item">
        <a class="sidenav-link " href="${pageContext.request.contextPath}/category-management-admin">
          <i class="fas fa-bars fa-fw me-2 "></i><span  class="h5 text">Quản lý danh mục</span><i class="fas fa-chevron-down" style="margin-left: 33px;"></i></a>
        <ul class="sidenav-collapse ">
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/category-addition-admin" style="font-size: 17px;">Thêm danh mục</a>
          </li>
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/category-management-admin" style="font-size: 17px;">Danh sách danh mục</a>
          </li>
        </ul>
      </li>
      <li class="sidenav-item">
        <a class="sidenav-link" href="order-manager">
          <i class="fas fa-cart-shopping fa-fw me-2 "></i><span class="h5 text">Quản lý đơn đặt hàng</span><i class="fas fa-chevron-down" style="margin-left: -1px; margin-bottom: 2px;"></i></a>
        <ul class="sidenav-collapse ">
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/views/admin/orderAddition.jsp" style="font-size: 17px;">Thêm đơn đặt hàng</a>
          </li>
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/views/admin/orderManagement.jsp" style="font-size: 17px;">Danh sách đơn đặt hàng</a>
          </li>
        </ul>
      </li>
      <li class="sidenav-item">
        <a class="sidenav-link" href="${pageContext.request.contextPath}/contact-admin">
          <i class="fas fa-message fa-fw me-2 "></i><span class="h5 text">Quản lý trang liên hệ</span><i class="fas fa-chevron-down" style="margin-left: 10px;"></i></a>
        <ul class="sidenav-collapse ">
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/contact-admin" style="font-size: 17px;">Danh sách liên hệ</a>
          </li>
        </ul>
      </li>
      <li class="sidenav-item">
        <a class="sidenav-link" href="${pageContext.request.contextPath}/post-admin">
          <i class="fas fa-blog fa-fw me-2 "></i><span class="h5 text">Quản lý trang tin tức</span><i class="fas fa-chevron-down" style="margin-left: 10px;"></i></a>
        <ul class="sidenav-collapse ">
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/post-add" style="font-size: 17px;">Thêm bài viết</a>
          </li>
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/post-admin" style="font-size: 17px;">Danh sách bài viết</a>
          </li>
        </ul>
      </li>
      <li class="sidenav-item">
        <a class="sidenav-link" href="${pageContext.request.contextPath}/shipper-manager">
          <i class="fas fa-shipping-fast fa-fw me-2 "></i><span class="h5 text">Quản lý shipper</span><i class="fas fa-chevron-down" style="margin-left: 48px;"></i></a>
        <ul class="sidenav-collapse ">
          <li class="sidenav-item">

            <a class="sidenav-link" href="${pageContext.request.contextPath}/shipper-addtion-admin" style="font-size: 17px;">Thêm shipper</a>
          </li>
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/shipper-manager" style="font-size: 17px;">Danh sách shipper</a>
          </li>
        </ul>
      </li>
      <li class="sidenav-item">
        <a class="sidenav-link" href="${pageContext.request.contextPath}/job-managemet-admin">
          <i class="fas fa-handshake fa-fw me-2 "></i><span class="h5 text">Trang tuyển dụng</span><i class="fas fa-chevron-down" style="margin-left: 30px;"></i></a>
        <ul class="sidenav-collapse ">
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/job-addtion-admin" style="font-size: 17px;">Thêm công việc</a>
          </li>
          <li class="sidenav-item">
            <a class="sidenav-link" href="${pageContext.request.contextPath}/job-addtion-admin" style="font-size: 17px;">Danh sách tuyển dụng</a>
          </li>
        </ul>
      </li>
    </ul>
  </nav>
  <!-- Sidenav -->

  <!-- Navbar -->
  <nav id="main-navbar" class="navbar navbar-expand-lg navbar-light bg-white fixed-top shadow-1 ms-4">
    <!-- Container wrapper -->
    <div class="container-fluid">
      <!-- Toggler -->
      <button data-mdb-toggle="sidenav" data-mdb-target="#main-sidenav"
              class="btn shadow-1 p-0 me-3 d-block " aria-controls="#main-sidenav" aria-haspopup="true" style="margin-left: -27px">
        <i class="fas fa-bars" style="font-size: 26px"></i>
      </button>

      <!-- Right links -->
      <ul class="navbar-nav ms-auto d-flex flex-row">
        <!-- Avatar -->
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle hidden-arrow d-flex align-items-center" href="#"
             id="navbarDropdownMenuLink" role="button" data-mdb-toggle="dropdown" aria-expanded="false">
             Admin
            <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news1.jpg" class="rounded-circle ms-2 me-4" height="40px" width="40px"
                 alt="Avatar"
                 loading="lazy"/>
          </a>
          <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownMenuLink">
            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/profile-admin">Hồ sơ của tôi</a></li>
            <li><a class="dropdown-item" href="logout-admin">Đăng xuất</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>
</header>