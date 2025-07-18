<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý Shipper</title>
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
                            <h1 class="">Quản lý Shipper</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <div class="container px-4">
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="add-shipper">
                <i class="far fa-square-plus"></i>
                <span>Thêm Shipper</span>
            </a>
        </div>

        <!-- Hiển thị thông báo thành công hoặc lỗi -->
        <div class="container">
            <% String message = (String) session.getAttribute("message");
                String error = (String) session.getAttribute("error");
                if (message != null) { %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= message %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% session.removeAttribute("message"); %>
            <% } else if (error != null) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <%= error %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <% session.removeAttribute("error"); %>
            <% } %>
        </div>

        <!-- Tìm kiếm -->
        <div class="input-group mb-4 px-4">
            <input type="text" class="form-control" id="advanced-search-input"
                   placeholder="Nhập tên hoặc số điện thoại để tìm kiếm" value="${searchTerm}"/>
            <button class="btn bg_green" id="advanced-search-button" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>

        <!-- Bảng dữ liệu shipper -->
        <div class="datatable">
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr class="h6">
                    <th>STT</th>
                    <th>Tên Shipper</th>
                    <th>Số điện thoại</th>
                    <th>Tiền lương</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${shipperList}" var="shipper" varStatus="loop">
                    <tr>
                        <td><span class="ms-2 h6">${loop.index + 1}</span></td>
                        <td><p class="h6 mb-1 ms-1">${shipper.name}</p></td>
                        <td><span class="h6 ms-1">${shipper.phone}</span></td>
                        <td><span class="h6 ms-1">${shipper.salary}</span></td>
                        <td>
                            <span class="badge ${shipper.status == 1 ? 'badge-success' : 'badge-danger'}"
                                  style="font-size: 13px;">
                                    ${shipper.status == 1 ? 'Đang làm việc' : 'Đã nghỉ làm'}
                            </span>
                        </td>
                        <td>
                            <c:if test="${shipper.status != 2}">
                                <a href="edit-shipper?id=${shipper.id}" class="btn bg_green btn-floating"
                                   style="font-size: 16px;">
                                    <i class="far fa-pen-to-square"></i>
                                </a>
                                <button type="button" class="btn bg_yellow btn-floating"
                                        onclick="deleteShipper(${shipper.id})" style="font-size: 16px;">
                                    <i class="far fa-trash-can"></i>
                                </button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Phân trang -->
        <nav aria-label="Pagination">
            <ul class="pagination justify-content-end">
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}&searchTerm=${searchTerm}">${i}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </div>
</main>

<!-- Footer -->
<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5);">©2024 Group-11</p>
</footer>

<script>
    // JavaScript cho thao tác xóa shipper
    function deleteShipper(shipperId) {
        if (confirm("Bạn có chắc chắn muốn xóa shipper này?")) {
            window.location.href = "shipper-management?action=delete&id=" + shipperId;
        }
    }
</script>
</body>
</html>
