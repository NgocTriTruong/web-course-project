<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý sản phẩm</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

    <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>

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
                  <h1 class="">Quản lý sản phẩm</h1>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>


    <!-- Container for demo purpose -->
    <div class="container px-4 ">
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="productAddition.jsp">
                <i class="far fa-square-plus"></i>
                <span>Thêm sản phẩm</span>
            </a>
        </div>
        <div class="input-group mb-4 px-4">
            <input type="text" class="form-control" id="advanced-search-input"
                   placeholder=""/>
            <button class="btn bg_green" id="advanced-search-button" type="button">
                <i class="fa fa-search"></i>
            </button>
        </div>
        <div class="datatable">
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr class="h6">
                    <th>STT</th>
                    <th>Danh mục</th>
                    <th>Tên sản phẩm</th>
                    <th>Mô tả</th>
                    <th>Giá</th>
                    <th>Khuyến mãi</th>
                    <th>Trạng thái</th>
                    <th>Thao tác</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${products}" varStatus="status">
                    <tr>
                        <td>
                            <span class="ms-2 h6">${p.id}</span>
                        </td>
                        <td>
                            <div>
                                <c:forEach var="ca" items="${categories}">
                                    <p class="h6 mb-1">
                                        <c:if test="${p.cat_id == ca.id}">
                                            ${ca.name}
                                        </c:if>
                                    </p>
                                </c:forEach>
                            </div>
                        </td>
                        <td>
                            <div class="d-flex align-items-center">
                                <div class="">
                                    <p class="h6 mb-1 ms-1">${p.name}</p>
                                </div>
                            </div>
                        </td>
                        <td>
                            <span class="h6">${p.description}</span>
                        </td>
                        <td>
                            <p class="fw-normal mb-1 h6">${p.price}</p>
                        </td>
                        <td>
                            <div>
                                <c:forEach var="dis" items="${discountsData}">
                                    <c:if test="${p.discountId == dis.id}">
                                        <span class="badge badge-danger rounded-pill d-inline ms-3" style="font-size: 14px;">
                                                ${dis.percentage}%
                                        </span>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </td>

                        <td>
                            <span class="badge rounded-pill d-inline ms-1" style="font-size: 14px; background-color: ${p.status == 1 ? 'green' : p.status == 2 ? 'orange' : 'red'};">
                                <c:choose>
                                    <c:when test="${p.status == 1}">Đang bán</c:when>
                                    <c:when test="${p.status == 2}">Ngưng bán</c:when>
                                    <c:otherwise>Đã xóa</c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <td>
                            <a href="productAddition.jsp?id=${p.id}" class="btn bg_green btn-floating" style="font-size: 16px;">
                                <i class="far fa-pen-to-square"></i>
                            </a>
                            <a href="delete-product?productId= ${p.id}">
                                <button type="button" class="btn bg_yellow btn-floating" style="font-size: 16px;">
                                    <i class="far fa-trash-can"></i>
                                </button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</main>
<!--Main layout-->

<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>
</html>