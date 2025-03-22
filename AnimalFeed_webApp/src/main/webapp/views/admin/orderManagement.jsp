<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý đơn hàng</title>
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
<!--Main Navigation-->
<%@ include file="layout/header.jsp" %>

<!--Main layout-->
<main style="padding-bottom: 100px;">
    <section class="mb-5 text-center text-md-start">
        <div class="p-5" style="height: 200px; background: linear-gradient(to right, hsl(78, 50%, 48%), hsl(78, 50%, 68%));"></div>
        <div class="container px-4">
            <div class="card shadow-0" style="margin-top: -100px;">
                <div class="card-body py-5 px-5">
                    <div class="row gx-lg-4 align-items-center">
                        <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                            <h1 class="">Quản lý đơn hàng</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <div class="mb-3 d-flex justify-content-end px-4">
        <a class="btn bg_green text-white fw-bold" href="${pageContext.request.contextPath}/add-order-management">
            <i class="far fa-square-plus"></i>
            <span>Thêm đơn hàng</span>
        </a>
    </div>

    <div class="container px-4">
        <!-- Tìm kiếm -->
        <div class="input-group mb-4 px-4">
            <input type="text" class="form-control" id="advanced-search-input"
                   placeholder="Nhập tên khách hàng hoặc mã đơn hàng để tìm kiếm" value="${searchTerm}"/>
            <button class="btn bg_green" id="advanced-search-button" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>

        <!-- Bảng dữ liệu đơn hàng -->
        <div class="datatable">
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr class="h6">
                    <th>STT</th>
                    <th>Khách hàng</th>
                    <th>Đơn hàng</th>
                    <th>Ngày đặt</th>
                    <th>Ngày giao</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="order" varStatus="loop">
                    <tr>
                        <td><span class="ms-2 h6">${loop.index + 1}</span></td>
                        <td><p class="h6 mb-1 ms-1">${order.userId}</p></td>
                        <td><span class="h6 ms-1">${order.id}</span></td>
                        <td>
                            <fmt:parseDate value="${order.orderDate.toString()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedOrderDate"/>
                            <fmt:formatDate value="${parsedOrderDate}" pattern="dd/MM/yyyy"/>
                        </td>
                        <td>
                            <fmt:parseDate value="${order.shippingDate.toString()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDeliveryDate"/>
                            <fmt:formatDate value="${parsedDeliveryDate}" pattern="dd/MM/yyyy"/>
                        </td>
                        <td>
                            <span class="badge ${order.status == 1 ? 'badge-warning' :
                                               (order.status == 2 ? 'badge-primary' :
                                               (order.status == 3 || order.status == 4 ? 'badge-success' : 'badge-danger'))}"
                                  style="font-size: 13px;">
                                    ${order.status == 1 ? 'Chờ xác nhận' :
                                            (order.status == 2 ? 'Đang chuẩn bị' :
                                                    (order.status == 3 ? 'Đang giao' :
                                                            (order.status == 4 ? 'Đã giao' : 'Đã hủy')))}
                            </span>
                        </td>
                        <td>
                            <a href="orderEdit?id=${order.id}" class="btn bg_green btn-floating" style="font-size: 16px;"
                               title="Chỉnh sửa">
                                <i class="far fa-pen-to-square"></i>
                            </a>
                            <a href="order-manager?action=view&id=${order.id}" class="btn bg_blue btn-floating"
                               style="font-size: 16px;" title="Xem chi tiết">
                                <i class="far fa-eye"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Phân trang -->
        <nav aria-label="Pagination">
            <ul class="pagination justify-content-end">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="${pageContext.request.contextPath}/admin/orders?page=${currentPage-1}${not empty searchTerm ? '&searchTerm='.concat(searchTerm) : ''}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${endPage}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}${not empty searchTerm ? '&searchTerm='.concat(searchTerm) : ''}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${currentPage < endPage}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage+1}${not empty searchTerm ? '&searchTerm='.concat(searchTerm) : ''}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</main>

<!-- Footer -->
<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5);">©2024 Group-11</p>
</footer>

<script>
    // JavaScript cho tìm kiếm
    document.addEventListener('DOMContentLoaded', function() {
        const searchInput = document.getElementById('advanced-search-input');
        const searchButton = document.getElementById('advanced-search-button');

        function performSearch() {
            const searchTerm = searchInput.value.trim();
            window.location.href = '${pageContext.request.contextPath}/admin/orders?action=search&searchTerm=' + encodeURIComponent(searchTerm);
        }
    });
</script>
</body>
</html>