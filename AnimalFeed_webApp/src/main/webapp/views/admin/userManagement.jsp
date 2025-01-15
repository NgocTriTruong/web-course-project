<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
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
        <div class="input-group mb-4 px-4">
            <input type="text" class="form-control" id="advanced-search-input" placeholder="Nhập tên hoặc số điện thoại để tìm kiếm"/>
            <button class="btn bg_green" id="advanced-search-button" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
        <div class="datatable">
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr class="h6">
                    <th>STT</th>
                    <th>Tên người dùng</th>
                    <th>Số điện thoại</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="user" varStatus="loop">
                    <tr>
                        <td><span class="ms-2 h6">${loop.index + 1}</span></td>
                        <td><p class="h6 mb-1 ms-1">${user.fullName}</p></td>
                        <td><span class="h6 ms-1">${user.phone}</span></td>
                        <td>
                            <span class="badge ${user.status == 1 ? 'badge-success' : 'badge-danger'} rounded-pill d-inline" style="font-size: 13px;">
                                    ${user.status == 1 ? 'Đang hoạt động' : 'Ngừng hoạt động'}
                            </span>
                        </td>
                        <td>
                            <a href="userEdit?id=${user.id}" class="btn bg_green btn-floating" style="font-size: 16px;">
                                <i class="far fa-pen-to-square"></i>
                            </a>
                            <button type="button" class="btn bg_yellow btn-floating" style="font-size: 16px;" onclick="deleteUser(${user.id})">
                                <i class="far fa-trash-can"></i>
                            </button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
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

<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5);">©2024 Group-11</p>
</footer>

<script>
    function deleteUser(userId) {
        const deleteButton = document.getElementById('confirmDeleteButton');
        deleteButton.href = 'userManagement?action=delete&id=' + userId;
        const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
        deleteModal.show();
    }

    document.getElementById('advanced-search-button').addEventListener('click', function () {
        const searchTerm = document.getElementById('advanced-search-input').value;
        fetch(`userManagement?searchTerm=${searchTerm}`)
            .then(response => response.text())
            .then(data => {
                document.querySelector('.datatable tbody').innerHTML = data;
            })
            .catch(error => console.error('Error:', error));
    });
</script>

<!-- Modal for delete confirmation -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">Bạn có chắc chắn muốn xóa người dùng này không?</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <a href="#" id="confirmDeleteButton" class="btn btn-danger">Xóa</a>
            </div>
        </div>
    </div>
</div>
<!-- Add JavaScript -->
<script>
    // Xử lý tìm kiếm nâng cao
    document.getElementById('advanced-search-button').addEventListener('click', function () {
        const searchQuery = document.getElementById('advanced-search-input').value.trim();
        if (searchQuery) {
            window.location.href = `userManagement?action=search&query=${encodeURIComponent(searchQuery)}`;
        } else {
            alert('Vui lòng nhập từ khóa tìm kiếm.');
        }
    });

    // Xử lý phân trang
    function goToPage(pageNumber) {
        const currentUrl = new URL(window.location.href);
        currentUrl.searchParams.set('page', pageNumber);
        window.location.href = currentUrl.toString();
    }

    // Xác nhận trước khi xóa
    function deleteUser(userId) {
        if (confirm('Bạn có chắc chắn muốn xóa người dùng này?')) {
            window.location.href = `userManagement?action=delete&id=${userId}`;
        }
    }

    // Tự động chuyển focus về ô tìm kiếm khi trang tải lại
    window.addEventListener('load', function () {
        const searchInput = document.getElementById('advanced-search-input');
        if (searchInput) searchInput.focus();
    });
</script>
</body>
</html>
