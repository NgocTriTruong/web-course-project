
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
  <meta http-equiv="x-ua-compatible" content="ie=edge"/>
  <title>Chỉnh sửa shipper</title>
  <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
  <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
</head>

<body>

<%@ include file="layout/header.jsp" %>

<main class="mb-5" style="margin-top: 100px;">
  <div class="container px-4">
    <h4 class="mb-3">Chỉnh sửa thông tin shipper</h4>
    <form action="${pageContext.request.contextPath}/shipper-manager" method="post">
      <input type="hidden" name="id" value="${shipper.id}">
      <div class="row">
        <div class="col-md-4">
          <label for="name" class="form-label">Tên shipper</label>
          <input type="text" class="form-control" id="name" name="name" value="${shipper.name}" required>
        </div>
        <div class="col-md-4">
          <label for="phone" class="form-label">Số điện thoại</label>
          <input type="text" class="form-control" id="phone" name="phone" value="${shipper.phone}" required>
        </div>
        <div class="col-md-4">
          <label for="salary" class="form-label">Tiền lương</label>
          <input type="number" step="0.01" class="form-control" id="salary" name="salary" value="${shipper.salary}" required>
        </div>
        <div class="col-md-4 mt-3">
          <label class="form-label">Trạng thái</label>
          <div class="form-check-inline">
            <input class="form-check-input" type="radio" name="status" value="1" ${shipper.status == 1 ? 'checked' : ''}>
            <label class="form-check-label">Đang làm việc</label>
          </div>
          <div class="form-check-inline">
            <input class="form-check-input" type="radio" name="status" value="0" ${shipper.status == 0 ? 'checked' : ''}>
            <label class="form-check-label">Đã nghỉ làm</label>
          </div>
        </div>
      </div>
      <button type="submit" class="btn btn-success mt-3">Cập nhật</button>
    </form>
  </div>
</main>

</body>
</html>
