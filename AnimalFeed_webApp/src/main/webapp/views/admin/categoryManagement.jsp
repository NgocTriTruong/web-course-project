<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý danh mục</title>
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
    <style>
        .btn-disabled {
            background-color: #cccccc !important;
            color: #666666 !important;
            cursor: not-allowed !important;
            pointer-events: none;
        }
        .no-data {
            text-align: center;
            padding: 20px;
            color: #666;
        }
        .badge {
            padding: 5px 10px;
            border-radius: 10px;
        }
    </style>
</head>

<body>
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
                            <h1 class="">Quản lý danh mục</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Hiển thị thông báo lỗi hoặc thành công nếu có -->
    <div class="container px-4">
        <% String error = (String) session.getAttribute("error");
            if (error != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <%= error %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% session.removeAttribute("error"); %>
        <% } %>

        <% String message = (String) session.getAttribute("message");
            if (message != null) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <%= message %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <% session.removeAttribute("message"); %>
        <% } %>
    </div>

    <div class="container px-4">
        <jsp:useBean id="userService" class="vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService" scope="application"/>
        <%
            Integer userId = (Integer) session.getAttribute("userId");
            Integer subRole = (Integer) session.getAttribute("subRole"); // Sửa thành "subRole"
            boolean hasPermission = userId != null && userService.hasPermission(userId, "CATEGORY_MANAGEMENT");
            System.out.println("User ID: " + userId + ", Sub Role: " + subRole + ", Has Permission (via UserService): " + hasPermission);
        %>
        <% if (hasPermission) { %>
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="${pageContext.request.contextPath}/category-addition-admin">
                <i class="far fa-square-plus"></i>
                <span>Thêm loại sản phẩm</span>
            </a>
        </div>
        <% } %>
        <div class="input-group mb-4 px-4">
            <input type="text" class="form-control" id="advanced-search-input" placeholder="Tìm kiếm danh mục..."/>
            <button class="btn bg_green" id="advanced-search-button" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
        <table class="table align-middle mb-0 bg-white">
            <thead class="bg-light">
            <tr class="h6">
                <th>STT</th>
                <th>Tên loại sản phẩm</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty categories}">
                    <tr>
                        <td colspan="4" class="no-data">Không có danh mục nào để hiển thị.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="category" items="${categories}" varStatus="status">
                        <tr>
                            <td><span class="ms-2 h6">${status.count}</span></td>
                            <td>
                                <div class="ms-2">
                                    <p class="h6 fw-bold mb-1">${category.name}</p>
                                </div>
                            </td>
                            <td>
                                <span class="badge rounded-pill d-inline ms-1" style="font-size: 13px; background-color: ${category.status == 1 ? 'green' : 'red'};">
                                    <c:choose>
                                        <c:when test="${category.status == 1}">Hoạt động</c:when>
                                        <c:otherwise>Ngưng hoạt động</c:otherwise>
                                    </c:choose>
                                </span>
                            </td>
                            <td>
                                <% if (hasPermission) { %>
                                <a href="${pageContext.request.contextPath}/edit-category?categoryId=${category.id}" class="btn btn-success btn-floating" style="font-size: 16px;" title="Chỉnh sửa">
                                    <i class="far fa-pen-to-square"></i>
                                </a>
                                <c:if test="${category.status != 0}">
                                    <a href="javascript:void(0)" onclick="showMessConfirm(${category.id})" class="btn btn-warning btn-floating" style="font-size: 16px;" title="Xóa">
                                        <i class="far fa-trash-can"></i>
                                    </a>
                                </c:if>
                                <% } else { %>
                                <a href="#" class="btn btn-success btn-floating btn-disabled" style="font-size: 16px;" title="Không có quyền chỉnh sửa">
                                    <i class="far fa-pen-to-square"></i>
                                </a>
                                <a href="#" class="btn btn-warning btn-floating btn-disabled" style="font-size: 16px;" title="Không có quyền xóa">
                                    <i class="far fa-trash-can"></i>
                                </a>
                                <% } %>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</main>
<!--Main layout-->

<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const searchInput = document.getElementById('advanced-search-input');
        const searchButton = document.getElementById('advanced-search-button');

        function performSearch() {
            const searchTerm = searchInput.value.trim();
            window.location.href = '${pageContext.request.contextPath}/category-management-admin?action=search&searchTerm=' + encodeURIComponent(searchTerm);
        }

        searchButton.addEventListener('click', performSearch);
        searchInput.addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                performSearch();
            }
        });

        // Hàm xác nhận xóa
        window.showMessConfirm = function(categoryId) {
            if (confirm('Bạn có chắc chắn muốn xóa danh mục này?')) {
                window.location.href = '${pageContext.request.contextPath}/delete-category?categoryId=' + categoryId;
            }
        };
    });
</script>
</body>
</html>