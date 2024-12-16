<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
    <title>Trang chủ</title>
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
<!-- Start your project here-->

<header id="header-placeholder"></header>

    
    <!-- Main layout -->
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
                      <h1 class="">Trang chủ</h1>
                    </div>
        
                    <div class="col-lg-6 text-center text-lg-end">
                      <button type="button" class="btn mb-4 px-2 px-md-3 bg_green text-white" data-ripple-color="primary" style="font-size: 16px;">
                        Export<i class="fas fa-download ms-2"></i>
                      </button>
        
                    </div>
        
                  </div>
                </div>
              </div>
            </div>
          </section>
        <!-- Container for demo purpose -->
        <div class="container px-4">
            <div>
                <div class="row">
                    <!-- Earnings (Monthly) Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="hover_container"></div>
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="h6 text-xs fw-bold text-danger text-uppercase mb-1" style="width: 175px;">
                                            Tổng đơn đặt hàng
                                        </div>
                                        <div class="h4 mb-0 fw-bold text-gray-800 mt-3 ms-2">648</div>
                                    </div>
                                    <div class="col-auto mt-4">
                                        <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
    
                        </div>
                    </div>
    
                    <!-- Earnings (Monthly) Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-success shadow h-100 py-2">
                            <div class="hover_container"></div>
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="h6 text-xs fw-bold text-success text-uppercase mb-1" style="width: 175px;">
                                            Doanh thu
                                        </div>
                                        <div class="h4 mb-0 fw-bold text-gray-800 mt-3 ms-2">542.254.000đ</div>
                                    </div>
                                    <div class="col-auto mt-4">
                                        <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
    
                    <!-- Earnings (Monthly) Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-info shadow h-100 py-2">
                            <div class="hover_container"></div>
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="h6 text-xs fw-bold text-info text-uppercase mb-1" style="width: 178px;"> Tổng số
                                            khách hàng
                                        </div>
                                        <div class="h4 mb-0 fw-bold text-gray-800 float-start mt-3 ms-2" style="width: 120px;">3.200</div>
                                        <div class="col-auto float-end ps-4 pt-2" style="width: 70px;">
                                            <i class="fas fa-user-group fa-2x text-gray-300"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
    
                    <!-- Pending Requests Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-warning shadow h-100 py-2">
                            <div class="hover_container"></div>
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="h6 text-xs fw-bold text-warning text-uppercase mb-1" style="width: 155px;">
                                            Tổng sản phẩm
                                        </div>
                                        <div class="h4 mt-3 ms-2 mb-0 fw-bold text-gray-800">200</div>
                                    </div>
                                    <div class="col-auto mt-4">
                                        <i class="fas fa-gift fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
    
            <!-- Section: Sales Performance KPIs -->
            <section class="mb-8">
                <div class="card shadow-0">
                    <div class="card-header border-0 p-3 mt-2">
                        <strong class="h3">Thống kê chi tiết</strong>
                    </div>
                    <div class="card-body">
                            <div class="input-group mb-4 ">
                                <input type="text" class="form-control" id="advanced-search-input"
                                       placeholder=""/>
                                <button class="btn bg_green" id="advanced-search-button" type="button">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        <div class="table-responsive datatable">
                            <table class="table table-hover table-borderless table-striped text-nowrap">
                                <thead>
                                <tr class="h6">
                                    <th scope="col">STT</th>
                                    <th scope="col">Tên khách hàng</th>
                                    <th scope="col">Số điện thoại</th>
                                    <th scope="col">Đơn đã đặt</th>
                                    <th scope="col">Số sản phẩm</th>
                                    <th scope="col">Tổng tiền phải trả</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <th scope="row"> <span class="h6 ms-2">1</span></th>
                                    <td><span class="h6">Trương Ngọc trí</span></td>
                                    <td><span class="h6">0123456789</span></td>
                                    <td><span class="h6 ms-4">20</span></td>
                                    <td><span class="h6 ms-4">40</span></td>
                                    <td><span class="h6 ms-3">16.200.000</span></td>
                                </tr>
                                <tr>
                                    <th scope="row"><span class="h6 ms-2">2</span></th>
                                    <td><span class="h6">Trương Ngọc trí</span></td>
                                    <td><span class="h6">0123456789</span></td>
                                    <td><span class="h6 ms-4">20</span></td>
                                    <td><span class="h6 ms-4">40</span></td>
                                    <td><span class="h6 ms-3">16.200.000</span></td>
                                </tr>
                                <tr>
                                    <th scope="row"><span class="h6 ms-2">3</span></th>
                                    <td><span class="h6">Trương Ngọc trí</span></td>
                                    <td><span class="h6">0123456789</span></td>
                                    <td><span class="h6 ms-4">20</span></td>
                                    <td><span class="h6 ms-4">40</span></td>
                                    <td><span class="h6 ms-3">16.200.000</span></td>
                                </tr>
                                <tr>
                                    <th scope="row"><span class="h6 ms-2">4</span></th>
                                    <td><span class="h6">Trương Ngọc trí</span></td>
                                    <td><span class="h6">0123456789</span></td>
                                    <td><span class="h6 ms-4">20</span></td>
                                    <td><span class="h6 ms-4">40</span></td>
                                    <td><span class="h6 ms-3">16.200.000</span></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
    
            </section>
            <!-- Section: Sales Performance KPIs -->
    
    
        </div>
        <!-- Container for demo purpose -->
    </main>
<footer class="text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

</body>

</html>
