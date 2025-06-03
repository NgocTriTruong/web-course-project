<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Chỉnh sửa sự cố</title>
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
                            <h1 class="">Chỉnh sửa sự cố</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <div class="container px-4">
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

        <!-- Form chỉnh sửa sự cố -->
        <div class="card shadow-0">
            <div class="card-body py-5 px-5">
                <form action="${pageContext.request.contextPath}/edit-issue" method="post" id="editIssueForm">
                    <input type="hidden" name="id" value="${issue.id}"/>

                    <div class="mb-4">
                        <label for="adminName" class="form-label">Admin</label>
                        <input type="text" class="form-control" id="adminName" value="${issue.adminName}" disabled/>
                        <input type="hidden" name="userId" value="${issue.userId}"/>
                    </div>

                    <div class="mb-4">
                        <label for="productName" class="form-label">Sản phẩm</label>
                        <input type="text" class="form-control product-id" name="productIdOrName" value="${issue.productName}" required/>
                        <input type="hidden" class="product-id-hidden" name="productId" value="${issue.productId}"/>
                    </div>

                    <div class="mb-4">
                        <label for="reason" class="form-label">Lý do</label>
                        <textarea class="form-control" id="reason" name="reason" rows="4" required>${issue.reason}</textarea>
                    </div>

                    <div class="mb-4">
                        <label for="quantity" class="form-label">Số lượng (Tồn kho: ${inventoryQuantity})</label>
                        <input type="number" class="form-control" id="quantity" name="quantity" value="${issue.quantity}" min="1" max="${inventoryQuantity}" required/>
                    </div>

                    <div class="mb-4">
                        <label for="status" class="form-label">Trạng thái</label>
                        <select class="form-select" id="status" name="status" required>
                            <option value="0" ${issue.status == 0 ? 'selected' : ''}>Chưa giải quyết</option>
                            <option value="1" ${issue.status == 1 ? 'selected' : ''}>Đã giải quyết</option>
                        </select>
                    </div>

                    <div class="mb-4">
                        <label for="createDate" class="form-label">Ngày tạo</label>
                        <input type="datetime-local" class="form-control" id="createDate" name="createDate"
                               value="<fmt:formatDate value='${issue.createDate}' pattern='yyyy-MM-dd\'T\'HH:mm'/>" readonly/>
                    </div>

                    <div class="d-flex justify-content-end">
                        <a href="${pageContext.request.contextPath}/issueManagement" class="btn btn-secondary me-2">Hủy</a>
                        <button type="submit" class="btn bg_green text-white">Lưu thay đổi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

<!-- Footer -->
<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5);">©2024 Group-11</p>
</footer>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById('editIssueForm');
        form.addEventListener('submit', function(e) {
            if (!confirm('Bạn có chắc chắn muốn lưu thay đổi?')) {
                e.preventDefault();
            }
        });

        // Autocomplete cho sản phẩm
        document.querySelector('.product-id').addEventListener('input', function(e) {
            const productInput = e.target;
            const productIdHidden = document.querySelector('.product-id-hidden');
            const value = productInput.value.trim();

            if (value.length > 0) {
                fetch("${pageContext.request.contextPath}/api/product?search=" + encodeURIComponent(value))
                    .then(response => response.json())
                    .then(data => {
                        if (data && data.length > 0) {
                            productInput.value = data[0].name;
                            productIdHidden.value = data[0].id;
                        } else {
                            productInput.value = 'Không có sản phẩm';
                            productIdHidden.value = '';
                        }
                    })
                    .catch(error => {
                        console.error('Error fetching product:', error);
                        productInput.value = 'Không có sản phẩm';
                        productIdHidden.value = '';
                    });
            } else {
                productIdHidden.value = '';
            }
        });
    });
</script>
</body>
</html>