<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Lịch sử hoạt động</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

    <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>

    <!-- js add header -->
    <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>

<%@ include file="layout/header.jsp" %>

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
            <div class="card shadow-0" style="
                                            margin-top: -100px;
                                            ">
                <div class="card-body py-5 px-5">
                    <div class="row gx-lg-4 align-items-center">
                        <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                            <h1 class="">Lịch sử hoạt động</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Container for demo purpose -->
    <div class="container px-4 ">
        <div class="input-group mb-4 px-4">
            <form action="${pageContext.request.contextPath}/activity-history" method="get" class="input-group mb-4 px-4">
                <input type="text" class="form-control" id="advanced-search-input" name="keyword" placeholder="Tìm kiếm hoạt động..."/>
                <button class="btn bg_green" id="advanced-search-button" type="submit">
                    <i class="fa fa-search"></i>
                </button>
            </form>
        </div>
        <table class="table align-middle mb-0 bg-white">
            <thead class="bg-light">
            <tr class="h6">
                <th>ID</th>
                <th>Tên admin</th>
                <th>Loại hành động</th>
                <th>Loại thực thể</th>
                <th>Mã thực thể</th>
                <th>Thời gian thực hiện</th>
                <th>Mô tả</th>
                <th>Dữ liệu trước</th>
                <th>Dữ liệu sau</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty actionLogs}">
                    <tr>
                        <td colspan="4" class="no-data">Không có danh mục nào để hiển thị.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="log" items="${actionLogs}" varStatus="status">
                        <tr>
                            <td>${log.id}</td>
                            <td>${log.full_name}</td>
                            <td>${log.action_type}</td>
                            <td>${log.entity_type}</td>
                            <td>${log.entity_id}</td>
                            <td><fmt:formatDate value="${log.created_at}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>${log.description}</td>
                            <td>${log.before_data}</td>
                            <td>${log.after_data}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>

    </div>
    <!-- Container for demo purpose -->
</main>
<!--Main layout-->

<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>
<table>
</table>
</body>
</html>