<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý tuyển dụng</title>
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
</head>

<body>

<%@ include file="layout/header.jsp" %>

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
          <div class="card shadow-0" style="
                                            margin-top: -100px;
                                            ">
            <div class="card-body py-5 px-5">
              <div class="row gx-lg-4 align-items-center">
                <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                  <h1 class="">Quản lý tuyển dụng</h1>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

    <!-- Container for demo purpose -->
    <div class="container px-4 ">
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="${pageContext.request.contextPath}/job-addtion-admin">
                <i class="far fa-square-plus"></i>
                <span>Thêm công việc</span>
            </a>
        </div>
        <div class="input-group mb-4 px-4">
            <form action="${pageContext.request.contextPath}/job-managemet-admin" method="get" class="input-group mb-4 px-4">
                <input type="text" class="form-control" id="advanced-search-input" name="keyword" placeholder="Tìm kiếm công việc..."/>
                <button class="btn bg_green" id="advanced-search-button" type="submit">
                    <i class="fa fa-search"></i>
                </button>
            </form>
        </div>
        <table class="table align-middle mb-0 bg-white">
            <thead class="bg-light">
            <tr class="h6">
                <th>STT</th>
                <th>Vị trí công việc</th>
                <th>Nơi làm việc</th>
                <th>Số điện thoại liên hệ</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty jobs}">
                    <tr>
                        <td colspan="4" class="no-data">Không có danh mục nào để hiển thị.</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="jobs" items="${jobs}" varStatus="status">
                        <tr>
                            <td><span class="ms-2 h6">${status.count}</span></td>
                            <td>
                                <div class="">
                                    <p class="h6 mb-1">${jobs.job_position}</p>
                                </div>
                            </td>
                            <td>
                                <div class="">
                                    <p class="h6 mb-1">${jobs.location}</p>
                                </div>
                            </td>
                            <td>
                                <div class="">
                                    <p class="h6 mb-1">${jobs.phone}</p>
                                </div>
                            </td>
                            <td>
                            <span class="badge rounded-pill d-inline ms-1" style="font-size: 14px; background-color: ${jobs.status == 1 ? 'green' : jobs.status == 2 ? 'orange' : 'red'};">
                                <c:choose>
                                    <c:when test="${jobs.status == 1}">Hoạt động</c:when>
                                    <c:when test="${jobs.status == 2}">Ngưng hoạt động</c:when>
                                    <c:otherwise>Đã xóa</c:otherwise>
                                </c:choose>
                            </span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/edit-job-admin?jobId=${jobs.id}" class="btn btn-success btn-floating" style="font-size: 16px;" title="Chỉnh sửa">
                                    <i class="far fa-pen-to-square"></i>
                                </a>
                                <c:if test="${jobs.status != 0}">
                                    <a href="#" onclick="showMessConfirmJob(${jobs.id})">
                                        <button type="button" class="btn bg_yellow btn-floating" style="font-size: 16px;" >
                                            <i class="far fa-trash-can"></i>
                                        </button>
                                    </a>
                                </c:if>
                            </td>
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

<script>
    function showMessConfirmJob(id) {
        var option = confirm("Bạn có chắc chắn muốn xóa?");
        if(option === true) {
            window.location.href = "delete-job-admin?jobId=" + id;
        }
    }
</script>

</body>
</html>
