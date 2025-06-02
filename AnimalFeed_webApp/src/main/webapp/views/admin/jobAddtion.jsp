<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>${job != null ? 'Chỉnh sửa công việc' : 'Thêm công việc'}</title>
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
<main class="mb-5" style="margin-top: 100px;">
    <!-- Container for demo purpose -->
    <div class="container px-4 ">
        <a href="${pageContext.request.contextPath}/job-managemet-admin" class="btn btn-link mb-2 text_green" style="font-size: 16px;">
            <i class="fas fa-angle-left"></i> Quay lại
        </a>
        <div class="mb-3 bg_green p-2">
            <span class="text-white h5">${job != null ? 'Chỉnh sửa thông tin tuyển dụng' : 'Thông tin tuyển dụng'}</span>
        </div>
        <form class="border p-5" action="${pageContext.request.contextPath}/${job != null ? 'edit-job-admin' : 'job-addtion-admin'}" method="post">
            <c:if test="${job != null}">
                <input type="hidden" name="jobId" value="${job.id}"/>
            </c:if>
            <div class="row">
                <div class="col-md-4">
                    <label for="position" class="form-label style_18"><b>Vị trí</b></label>
                    <i class="fas fa-location ms-2"></i>
                    <input type="text" class="form-control" id="position" name="position" value="${job != null ? job.job_position : ''}"
                           placeholder="Nhập vị trí công việc..." required>
                </div>
                <div class="col-md-4">
                    <label for="location" class="form-label style_18"><b>Nơi làm việc</b></label>
                    <i class="fas fa-location-dot ms-2"></i>
                    <input type="text" class="form-control" id="location" name="location" value="${job != null ? job.location : ''}"
                           placeholder="Nhập nơi làm việc..." required>
                </div>
                <div class="col-md-4">
                    <label for="phone" class="form-label style_18"><b>Số điện thoại</b></label>
                    <i class="fas fa-phone ms-2"></i>
                    <input type="text" class="form-control" id="phone" name="phone" value="${job != null ? job.phone : ''}"
                           placeholder="Nhập số điện thoại liên hệ..." required>
                </div>

            </div>
            <!-- Repeat the pattern for other form elements -->
            <button type="submit" class="btn bg_green text-white fw-bold mt-4">${job != null ? 'Cập nhật' : 'Thêm mới'}</button>
        </form>

    </div>
    <!-- Container for demo purpose -->
</main>
<!--Main layout-->

<footer class="position-fixed bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>
</html>
