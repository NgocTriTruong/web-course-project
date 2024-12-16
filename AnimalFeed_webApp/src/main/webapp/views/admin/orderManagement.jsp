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

<div id="header-placeholder"></div>

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
                  <h1 class="">Quản lý đơn hàng</h1>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

    <!-- Container for demo purpose -->
    <div class="container px-4">
        <div class="mb-3 d-flex justify-content-end px-4">
            <a class="btn bg_green text-white fw-bold" href="orderAddition.jsp">
                <i class="far fa-square-plus"></i>
                <span>Thêm đơn đặt hàng</span>
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
                    <th class="text-center">STT</th>
                    <th class="text-center">Khách hàng</th>
                    <th class="text-center">Đơn hàng</th>
                    <th class="text-center">Thành tiền</th>
                    <th class="text-center">Trạng thái</th>
                    <th class="text-center">Ngày đặt</th>
                    <th class="text-center">Ngày giao</th>
                    <th class="text-center">Hành động</th>
                    <th class="text-center">Chi tiết</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <span class="ms-2 h6">1</span>
                    </td>
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="">
                                <p class="fw-bold mb-1">Trương Ngọc Trí</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 text-center h6">1</p>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">10.000.000</p>
                    </td>
                    <td>
                        <span class="badge badge-success rounded-pill d-inline" style="font-size: 13px;">Hoàn thành</span>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">10/11/2024</p>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">11/11/2024</p>
                    </td>
                    <td>
                        <button type="button" class="btn bg_green btn-floating" style="font-size: 16px;">
                            <i class="far fa-pen-to-square"></i>
                        </button>
                        <button type="button" class="btn bg_yellow btn-floating" style="font-size: 16px;">
                            <i class="far fa-trash-can"></i>
                        </button>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/views/admin/orderDetailManagement.jsp">
                            <button type="button" class="btn bg_green" style="font-size: 12px;">
                                Xem chi tiết
                            </button>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="ms-2 h6">2</span>
                    </td>
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="">
                                <p class="fw-bold mb-1">Trương Ngọc Trí</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 text-center h6">1</p>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">10.000.000</p>
                    </td>
                    <td>
                        <span class="badge badge-success rounded-pill d-inline" style="font-size: 13px;">Hoàn thành</span>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">10/11/2024</p>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">11/11/2024</p>
                    </td>
                    <td>
                        <button type="button" class="btn bg_green btn-floating" style="font-size: 16px;">
                            <i class="far fa-pen-to-square"></i>
                        </button>
                        <button type="button" class="btn bg_yellow btn-floating" style="font-size: 16px;">
                            <i class="far fa-trash-can"></i>
                        </button>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/views/admin/orderDetailManagement.jsp">
                            <button type="button" class="btn bg_green" style="font-size: 12px;">
                                Xem chi tiết
                            </button>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="ms-2 h6">3</span>
                    </td>
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="">
                                <p class="fw-bold mb-1">Trương Ngọc Trí</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 text-center h6">1</p>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">10.000.000</p>
                    </td>
                    <td>
                        <span class="badge badge-success rounded-pill d-inline" style="font-size: 13px;">Hoàn thành</span>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">10/11/2024</p>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">11/11/2024</p>
                    </td>
                    <td>
                        <button type="button" class="btn bg_green btn-floating" style="font-size: 16px;">
                            <i class="far fa-pen-to-square"></i>
                        </button>
                        <button type="button" class="btn bg_yellow btn-floating" style="font-size: 16px;">
                            <i class="far fa-trash-can"></i>
                        </button>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/views/admin/orderDetailManagement.jsp">
                            <button type="button" class="btn bg_green" style="font-size: 12px;">
                                Xem chi tiết
                            </button>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span class="ms-2 h6">4</span>
                    </td>
                    <td>
                        <div class="d-flex align-items-center">
                            <div class="">
                                <p class="fw-bold mb-1">Trương Ngọc Trí</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 text-center h6">1</p>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">10.000.000</p>
                    </td>
                    <td>
                        <span class="badge badge-success rounded-pill d-inline" style="font-size: 13px;">Hoàn thành</span>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">10/11/2024</p>
                    </td>
                    <td>
                        <p class="fw-normal mb-1 h6">11/11/2024</p>
                    </td>
                    <td>
                        <button type="button" class="btn bg_green btn-floating" style="font-size: 16px;">
                            <i class="far fa-pen-to-square"></i>
                        </button>
                        <button type="button" class="btn bg_yellow btn-floating" style="font-size: 16px;">
                            <i class="far fa-trash-can"></i>
                        </button>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/views/admin/orderDetailManagement.jsp">
                            <button type="button" class="btn bg_green" style="font-size: 12px;">
                                Xem chi tiết
                            </button>
                        </a>
                    </td>
                </tr>
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
