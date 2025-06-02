<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý sự cố</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
</head>

<body>
<!--Main Navigation-->
<%@ include file="layout/header.jsp" %>

<!--Main layout-->
<main class="mb-5">
    <section class="mb-5 text-center text-md-start">
        <div class="p-5" style="height: 200px; background: linear-gradient(to right, hsl(78, 50%, 48%), hsl(78, 50%, 68%));"></div>
        <div class="container px-4">
            <div class="card shadow-0" style="margin-top: -100px;">
                <div class="card-body py-5 px-5">
                    <div class="row gx-lg-4 align-items-center">
                        <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                            <h1 class="">Quản lý sự cố</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <div class="container px-4">
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="${pageContext.request.contextPath}/add-issue">
                <i class="far fa-square-plus"></i>
                <span>Thêm báo cáo sự cố</span>
            </a>
        </div>

        <!-- Tìm kiếm -->
        <div class="input-group mb-4 px-4">
            <input type="text" class="form-control" id="advanced-search-input"
                   placeholder="Nhập mã sự cố hoặc lý do để tìm kiếm" value="${searchTerm}"/>
            <button class="btn bg_green" id="advanced-search-button" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>

        <!-- Hiển thị thông báo -->
        <c:if test="${not empty message}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Bảng dữ liệu sự cố -->
    <div class="datatable">
        <table class="table align-middle mb-0 bg-white">
            <thead class="bg-light">
            <tr class="h6">
                <th>STT</th>
                <th>Mã sự cố</th>
                <th>Admin</th>
                <th>ID sản phẩm</th>
                <th>Lý do</th>
                <th>Số lượng</th>
                <th>Trạng thái</th>
                <th>Ngày tạo</th>
                <th>Thao tác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${issues}" var="issue" varStatus="loop">
                <tr>
                    <td><span class="ms-2 h6">${loop.index + 1}</span></td>
                    <td><span class="h6 ms-1">${issue.id}</span></td>
                    <td><p class="h6 mb-1 ms-1">${issue.userId}</p></td>
                    <td><span class="h6 ms-1">${issue.productId}</span></td>
                    <td><span class="h6 ms-1">${issue.reason}</span></td>
                    <td><span class="h6 ms-1">${issue.quantity}</span></td>
                    <td>
                            <span class="badge ${issue.status == 0 ? 'badge-warning' : 'badge-success'}"
                                  style="font-size: 13px;">
                                    ${issue.status == 0 ? 'Chưa giải quyết' : 'Đã giải quyết'}
                            </span>
                    </td>
                    <td>
                        <fmt:parseDate value="${issue.createDate.toString()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedCreateDate"/>
                        <fmt:formatDate value="${parsedCreateDate}" pattern="dd/MM/yyyy"/>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/edit-issue?id=${issue.id}" class="btn bg_green btn-floating" style="font-size: 16px;" title="Chỉnh sửa">
                            <i class="far fa-pen-to-square"></i>
                        </a>
                        <a href="#" onclick="solveIssue(${issue.id})" class="btn bg_yellow btn-floating" style="font-size: 16px;" title="Đã giải quyết">
                            <i class="far fa-check-circle"></i>
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
                    <a class="page-link" href="?page=${currentPage-1}${not empty searchTerm ? '&searchTerm='.concat(searchTerm) : ''}" aria-label="Previous">
                        <span aria-hidden="true">«</span>
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
                        <span aria-hidden="true">»</span>
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
    function solveIssue(issueId) {
        if (confirm("Đánh dấu báo cáo này đã được giải quyết?")) {
            window.location.href = "${pageContext.request.contextPath}/solve-issue?id=" + issueId;
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        const searchInput = document.getElementById('advanced-search-input');
        const searchButton = document.getElementById('advanced-search-button');

        function performSearch() {
            const searchTerm = searchInput.value.trim();
            window.location.href = '${pageContext.request.contextPath}/issueManagement?action=search&searchTerm=' + encodeURIComponent(searchTerm);
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