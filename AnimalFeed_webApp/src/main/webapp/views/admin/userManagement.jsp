<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý người dùng</title>
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
<!--Main Navigation-->
<%@ include file="layout/header.jsp" %>

<!--Main layout-->
<main class="mb-5">

    <section class="mb-5 text-center text-md-start">
        <div class="p-5"
             style="height: 200px; background: linear-gradient(to right, hsl(78, 50%, 48%), hsl(78, 50%, 68%));"></div>
        <div class="container px-4">
            <div class="card shadow-0" style="margin-top: -100px;">
                <div class="card-body py-5 px-5">
                    <div class="row gx-lg-4 align-items-center">
                        <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                            <h1 class="">Quản lý người dùng</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <div class="container px-4">
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="addUser">
                <i class="far fa-square-plus"></i>
                <span>Thêm người dùng</span>
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

        <!-- Bảng dữ liệu người dùng -->
        <div class="datatable">
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr class="h6">
                    <th>STT</th>
                    <th>Tên người dùng</th>
                    <th>Số điện thoại</th>
                    <th>Phân quyền</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user" varStatus="loop">
                    <tr>
                        <td><span class="ms-2 h6">${user.id}</span></td>
                        <td><p class="h6 mb-1 ms-1">${user.fullName}</p></td>
                        <td><span class="h6 ms-1">${user.phone}</span></td>
                        <td>
                            <span class="badge rounded-pill d-inline ms-1"
                                  style="font-size: 14px; background-color: ${user.role == 1 ? '#007bff' : '#6c757d'};">
                                <c:choose>
                                    <c:when test="${user.role == 0}">
                                        Người dùng
                                    </c:when>
                                    <c:when test="${user.role == 1 && user.sub_role == 0}">
                                        Super Admin
                                    </c:when>
                                    <c:when test="${user.role == 1 && user.sub_role == 1}">
                                        Admin Quản lý người dùng
                                    </c:when>
                                    <c:when test="${user.role == 1 && user.sub_role == 2}">
                                        Admin Quản lý sản phẩm
                                    </c:when>
                                    <c:when test="${user.role == 1 && user.sub_role == 3}">
                                        Admin Quản lý đơn hàng
                                    </c:when>
                                    <c:when test="${user.role == 1 && user.sub_role == 4}">
                                        Admin Quản lý shipper
                                    </c:when>
                                    <c:when test="${user.role == 1 && user.sub_role == 5}">
                                        Admin Quản lý tin tức
                                    </c:when>
                                    <c:otherwise>
                                        Không xác định
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <td>
                            <span class="badge rounded-pill d-inline ms-1"
                                  style="font-size: 14px; background-color: ${user.status == 1 ? 'green' : user.status == 2 ? 'red' : 'orange'};">
                                    ${user.status == 1 ? 'Đang hoạt động' : (user.status == 2 ? 'Đã xóa' : 'Ngừng hoạt động')}
                            </span>
                        </td>
                        <td>
                            <a href="userEdit?id=${user.id}" class="btn bg_green btn-floating"
                               style="font-size: 16px;">
                                <i class="far fa-pen-to-square"></i>
                            </a>
                            <c:if test="${user.status != 2}">
                                <a href="#" onclick="deleteUser(${user.id})">
                                    <button type="button" class="btn bg_yellow btn-floating"
                                            style="font-size: 16px;">
                                        <i class="far fa-trash-can"></i>
                                    </button>
                                </a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Phân trang -->
        <div class="container mt-4">
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <li class="page-item ${currentPage > 1 ? '' : 'disabled'}">
                        <a class="page-link" href="?page=${currentPage - 1}&searchTerm=${searchTerm}">
                            <i class="fa-solid fa-chevron-left"></i>
                        </a>
                    </li>
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:choose>
                            <c:when test="${i == 1 || i == totalPages || (i >= currentPage - 2 && i <= currentPage + 2)}">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}&searchTerm=${searchTerm}">${i}</a>
                                </li>
                            </c:when>
                            <c:when test="${i == currentPage - 3 || i == currentPage + 3}">
                                <li class="page-item disabled">
                                    <span class="page-link">...</span>
                                </li>
                            </c:when>
                        </c:choose>
                    </c:forEach>
                    <li class="page-item ${currentPage < totalPages ? '' : 'disabled'}">
                        <a class="page-link" href="?page=${currentPage + 1}&searchTerm=${searchTerm}">
                            <i class="fa-solid fa-chevron-right"></i>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</main>

<!-- Footer -->
<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

<script>
    function deleteUser(userId) {
        if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
            window.location.href = "userManagement?action=delete&id=" + userId;
        }
    }
</script>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const searchInput = document.getElementById('advanced-search-input');
        const searchButton = document.getElementById('advanced-search-button');

        function performSearch() {
            const searchTerm = searchInput.value.trim();
            window.location.href = 'userManagement?action=search&searchTerm=' + encodeURIComponent(searchTerm);
        }

        searchButton.addEventListener('click', performSearch);
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                performSearch();
            }
        });
    });
</script>
</body>
</html>