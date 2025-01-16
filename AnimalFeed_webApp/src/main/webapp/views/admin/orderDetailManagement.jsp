<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý đơn hàng</title>
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
                  <h1 class="">Đơn hàng #${order.id}</h1>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

    <!-- Container for demo purpose -->
    <div class="container px-4">
        <div class="datatable">
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr class="h6">
                    <th class="text-center">STT</th>
                    <th class="text-center">Sản phẩm</th>
                    <th class="text-center">Số lượng</th>
                    <th class="text-center">Tổng tiền</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orderDetails}" var="od" varStatus="loop">
                    <tr>
                        <td class="text-center">${loop.index + 1}</td>
                        <td class="text-center">
                            <div class="d-flex align-items-center justify-content-center">
                                <div>
                                    <p class="fw-bold mb-1">Product ID: ${od.productId}</p>
                                </div>
                            </div>
                        </td>
                        <td class="text-center">${od.quantity}</td>
                        <td class="text-center">
                            <fmt:formatNumber value="${od.totalPrice}" type="currency" currencySymbol="₫"/>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
    <!-- Container for demo purpose -->
</main>
<!--Main layout-->

<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>
</html>
