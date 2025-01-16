<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý bài viết</title>
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

    <!-- js add header -->
    <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
</head>

<body>
<!--Main Navigation-->
<%@ include file="layout/header.jsp" %>
<!--Main Navigation-->

<!--Main layout-->
<main class="mb-5">
    <section class="mb-5 text-center text-md-start">
        <!-- Background gradient -->
        <div class="p-5" style="height: 200px;
                                    background: linear-gradient(
                                    to right,
                                    hsl(78, 50%, 48%),
                                    hsl(78, 50%, 68%)
                                    );">
        </div>
        <!-- Background gradient -->

        <div class="container px-4">
            <div class="card shadow-0" style="margin-top: -100px;">
                <div class="card-body py-5 px-5">
                    <div class="row gx-lg-4 align-items-center">
                        <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                            <h1>Quản lý tin tức</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Container for demo purpose -->
    <div class="container px-4">
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="${pageContext.request.contextPath}/admin/posts/add">
                <i class="far fa-square-plus"></i>
                <span>Thêm bài viết</span>
            </a>
        </div>

        <div class="input-group mb-4 px-4">
            <form action="${pageContext.request.contextPath}/post-admin" method="post" class="input-group w-100">
                <input type="hidden" name="action" value="search"/>
                <input type="text" class="form-control" name="keyword" value="${keyword}"
                       placeholder="Tìm kiếm bài viết..." id="advanced-search-input"/>
                <button class="btn bg_green" id="advanced-search-button" type="submit">
                    <i class="fa fa-search"></i>
                </button>
            </form>
        </div>

        <div class="datatable px-4">
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr class="h6">
                    <th>STT</th>
                    <th>Tiêu đề</th>
                    <th>Mô tả</th>
                    <th>Thời gian tạo</th>  <!-- Không có cột Người tạo -->
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${not empty posts}">
                        <c:forEach items="${posts}" var="post" varStatus="loop">
                            <tr>
                                <td>
                                    <span class="ms-2 h6">${loop.index + 1}</span>
                                </td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="">
                                            <p class="fw-bold mb-1">${post.title}</p>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <span>${post.content}</span>
                                </td>
                                <td>  <!-- Chuyển thời gian tạo lên đây -->
                                    <span class="h6 ms-2">
                        <fmt:formatDate value="${post.createDate}" pattern="dd/MM/yyyy"/>
                    </span>
                                </td>
                                <td>
                    <span class="badge badge-success rounded-pill d-inline ms-2"
                          style="font-size: 13px;">
                            ${post.status == 1 ? 'Hiển thị' : 'Ẩn'}
                    </span>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/post-edit?id=${post.id}"
                                       class="btn bg_green btn-floating">
                                        <i class="far fa-pen-to-square"></i>
                                    </a>
                                    <button type="button" class="btn bg_yellow btn-floating"
                                            onclick="deletePost(${post.id})">
                                        <i class="far fa-trash-can"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="text-center">Không có bài viết nào</td> <!-- Sửa colspan từ 7 thành 6 -->
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</main>
<!--Main layout-->

<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

<script>
    function deletePost(postId) {
        if (confirm('Bạn có chắc chắn muốn xóa bài viết này?')) {
            window.location.href = '${pageContext.request.contextPath}/admin/posts?action=delete&id=' + postId;
        }
    }
</script>
</body>
</html>