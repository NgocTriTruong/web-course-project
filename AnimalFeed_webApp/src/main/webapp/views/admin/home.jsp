<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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

    <!-- Chart.js -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>

<body>
<!-- Start your project here-->

<%@ include file="layout/header.jsp" %>
    
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
                      <button type="button" class="btn mb-4 px-2 px-md-3 bg_green text-white" data-ripple-color="primary" style="font-size: 16px;"
                              onclick="exportToExcel()">
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
                                        <div class="h4 mb-0 fw-bold text-gray-800 mt-3 ms-2">${totalOrder}</div>
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
                                        <div class="h4 mb-0 fw-bold text-gray-800 mt-3 ms-2">${totalRevenue}đ</div>
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
                                        <div class="h4 mb-0 fw-bold text-gray-800 float-start mt-3 ms-2" style="width: 120px;">${totalUser}</div>
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
                                        <div class="h4 mt-3 ms-2 mb-0 fw-bold text-gray-800">${totalProduct}</div>
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
                    <!-- Form chọn năm và tháng -->
                    <div class="card-body">
                        <form id="dateFilterForm">
                            <div class="row">
                                <div class="col-md-3">
                                    <label for="yearSelect" class="form-label">Chọn năm:</label>
                                    <select name="year" id="yearSelect" class="form-select filter-input">
                                        <c:forEach var="y" begin="2020" end="2030">
                                            <option value="${y}" ${y == selectedYear ? 'selected' : ''}>${y}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label for="monthSelect" class="form-label">Chọn tháng</label>
                                    <select name="month" id="monthSelect" class="form-select filter-input">
                                        <option value="0" ${selectedMonth == 0 ? 'selected' : ''}>Cả năm</option>
                                        <c:forEach var="m" begin="1" end="12">
                                            <option value="${m}" ${m == selectedMonth ? 'selected' : ''}>Tháng ${m}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-1">
                                    <button type="button" id="filterBtn" class="btn btn-primary" style="margin-top: 36px">Lọc</button>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- biểudđồ thống kê -->
                    <div class="row">
                        <div class="col-lg-7">
                            <div class="card shadow-0 h-100">
                                <div class="card-header border-0 p-3 doanhthu" style="margin-bottom: -10px;">
                                    <strong class="h4">
                                        <c:choose>
                                            <c:when test="${selectedMonth == 0}">
                                                Doanh thu theo tháng (Năm ${selectedYear})
                                            </c:when>
                                            <c:otherwise>
                                                Doanh thu theo ngày (Tháng ${selectedMonth}/${selectedYear})
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                </div>
                                <div class="card-body" style="height: 774px;">
                                    <canvas id="revenueChart" height="300"></canvas>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-5">
                            <div class="card shadow-0 h-100" style="margin-top: 240px;">
                                <div class="card-header border-0 p-3 phanLoai" style="margin-left: 26px;">
                                    <strong class="h4">
                                        <c:choose>
                                            <c:when test="${selectedMonth == 0}">
                                                Phân loại đơn hàng (Năm ${selectedYear})
                                            </c:when>
                                            <c:otherwise>
                                                Phân loại đơn hàng (Tháng ${selectedMonth}/${selectedYear})
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                </div>
                                <div class="card-body" style="margin-top: -109px; height: 428px; width: 437px; margin-left: 51px;">
                                    <canvas id="orderStatusChart" height="300"></canvas>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row" style="margin-top: -69px;">
                        <div class="col-1"></div>
                        <div class="col-10">
                            <div class="card shadow-0">
                                <div class="card-header border-0 p-3 topBan" style="margin-left: 224px;">
                                    <strong class="h3">
                                        <c:choose>
                                            <c:when test="${selectedMonth == 0}">
                                                Top 10 sản phẩm bán chạy (Năm ${selectedYear})
                                            </c:when>
                                            <c:otherwise>
                                                Top 10 sản phẩm bán chạy (Tháng ${selectedMonth}/${selectedYear})
                                            </c:otherwise>
                                        </c:choose>
                                    </strong>
                                </div>
                                <div class="card-body" style=" margin-top: -16px;">
                                    <canvas id="topProductsChart" height="300" style="height: 750px;width: 750px;"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-1"></div>
                    </div>

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
                                <c:forEach var="user" items="${getUserStats}" varStatus="status">
                                    <tr>
                                        <th scope="row"> <span class="h6 ms-2">${status.index + 1}</span></th>
                                        <td><span class="h6">${user.fullName}</span></td>
                                        <td><span class="h6">${user.phone}</span></td>
                                        <td><span class="h6 ms-4">${user.totalOrders}</span></td>
                                        <td><span class="h6 ms-4">${user.totalProductsOrdered}</span></td>
                                        <td><span class="h6 ms-3">${user.totalAmountToPay}</span></td>
                                    </tr>
                                </c:forEach>
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

<script>
    function exportToExcel() {
        window.location.href = "${pageContext.request.contextPath}/exportExcel";
    }
</script>

<script>
    // Hàm khởi tạo biểu đồ
    // Biểu đồ doanh thu theo tháng
    $(document).ready(function() {
        // Khởi tạo biểu đồ ban đầu
        initRevenueChart();
        initOrderStatusChart();
        initTopProductsChart();

        // Bắt sự kiện click nút lọc
        $('#filterBtn').click(function() {
            const year = $('#yearSelect').val();
            const month = $('#monthSelect').val();

            // Gọi AJAX để cập nhật dữ liệu
            updateChartAndTitle(year, month);
        });
    });

    function initRevenueChart() {
        const revenueCtx = document.getElementById('revenueChart').getContext('2d');
        window.revenueChart = new Chart(revenueCtx, {
            type: 'line',
            data: {
                labels: [
                    <c:choose>
                        <c:when test="${not empty monthlyRevenueLabels}">
                            <c:forEach var="label" items="${monthlyRevenueLabels}" varStatus="loop">
                                "${label}"${loop.last ? '' : ','}
                            </c:forEach>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                ], // Dữ liệu từ controller
                datasets: [{
                    label: 'Doanh thu (VND)',
                    data: [
                        <c:choose>
                            <c:when test="${not empty monthlyRevenueData}">
                                <c:forEach var="data" items="${monthlyRevenueData}" varStatus="loop">
                                    ${data}${loop.last ? '' : ','}
                                </c:forEach>
                            </c:when>
                            <c:otherwise>[]</c:otherwise>
                        </c:choose>
                    ],
                    backgroundColor: 'rgba(78, 115, 223, 0.05)',
                    borderColor: 'rgba(78, 115, 223, 1)',
                    pointBackgroundColor: 'rgba(78, 115, 223, 1)',
                    pointBorderColor: '#fff',
                    pointHoverBackgroundColor: '#fff',
                    pointHoverBorderColor: 'rgba(78, 115, 223, 1)',
                    tension: 0.3
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    },
                    tooltip: {
                        callbacks: {
                            label: function(context) {
                                return ' ' + context.parsed.y.toLocaleString('vi-VN') + 'đ';
                            }
                        }
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function(value) {
                                return value.toLocaleString('vi-VN') + 'đ';
                            }
                        }
                    }
                }
            }
        });
    }

    // Hàm khởi tạo biểu đồ trạng thái đơn hàng
    function initOrderStatusChart() {
        // Biểu đồ trạng thái đơn hàng
        const orderStatusCtx = document.getElementById('orderStatusChart').getContext('2d');
        window.orderStatusChart = new Chart(orderStatusCtx, {
            type: 'doughnut',
            data: {
                labels: [
                    <c:choose>
                    <c:when test="${not empty orderStatusLabels}">
                    <c:forEach var="label" items="${orderStatusLabels}" varStatus="loop">
                    <c:choose>
                    <c:when test="${label == '1'}">"Đang xử lý"</c:when>
                    <c:when test="${label == '2'}">"Đang chuẩn bị"</c:when>
                    <c:when test="${label == '3'}">"Đang giao"</c:when>
                    <c:when test="${label == '4'}">"Hoàn thành"</c:when>
                    <c:when test="${label == '0'}">"Hủy"</c:when>
                    <c:otherwise>"Trạng thái: ${label}"</c:otherwise>
                    </c:choose>${loop.last ? '' : ','}
                    </c:forEach>
                    </c:when>
                    <c:otherwise></c:otherwise>
                    </c:choose>
                ],
                datasets: [{
                    data: [
                        <c:choose>
                        <c:when test="${not empty orderStatusData}">
                        <c:forEach var="data" items="${orderStatusData}" varStatus="loop">
                        ${data}${loop.last ? '' : ','}
                        </c:forEach>
                        </c:when>
                            <c:otherwise>[]</c:otherwise>
                        </c:choose>
                    ],
                    backgroundColor: [
                        'rgba(54, 162, 235, 0.7)',
                        'rgba(75, 192, 192, 0.7)',
                        'rgba(255, 206, 86, 0.7)',
                        'rgba(255, 99, 132, 0.7)',
                        'rgba(153, 102, 255, 0.7)',
                    ],
                    borderColor: [
                        'rgba(54, 162, 235, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(255, 99, 132, 1)',
                        'rgba(153, 102, 255, 1)',
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'right'
                    }
                }
            }
        });
    }

    // Hàm khởi tạo biểu đồ top sản phẩm
    function initTopProductsChart() {
        // Biểu đồ top sản phẩm bán chạy
        const topProductsCtx = document.getElementById('topProductsChart').getContext('2d');
        window.topProductsChart = new Chart(topProductsCtx, {
            type: 'bar',
            data: {
                labels: [
                    <c:choose>
                    <c:when test="${not empty topProductsLabels}">
                    <c:forEach var="label" items="${topProductsLabels}" varStatus="loop">
                    "${label}"${loop.last ? '' : ','}
                    </c:forEach>
                    </c:when>
                    <c:otherwise></c:otherwise>
                    </c:choose>
                ],
                datasets: [{
                    label: 'Số lượng bán',
                    data: [
                        <c:choose>
                        <c:when test="${not empty topProductsData}">
                        <c:forEach var="data" items="${topProductsData}" varStatus="loop">
                        ${data}${loop.last ? '' : ','}
                        </c:forEach>
                        </c:when>
                            <c:otherwise>[]</c:otherwise>
                        </c:choose>
                    ],
                    backgroundColor: [ // Mỗi cột một màu
                        '#f39c12'
                    ],
                    borderColor: [ // Border tương ứng
                        '#f39c12'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        display: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            stepSize: 5
                        }
                    }
                }
            }
        });
    }

    function updateChartAndTitle(year, month) {
        console.log("Before AJAX - Year:", year, "Month:", month);
        $.ajax({
            url: '${pageContext.request.contextPath}/dashboard',
            type: 'GET',
            data: {
                year: year,
                month: month,
            },
            // headers: {
            //     'X-Requested-With': 'XMLHttpRequest'
            // },
            success: function(data) {
                // Cập nhật tiêu đề
                // updateTitle(year, month);
                updateTitleDoanhThu(year, month);
                updateTitlePhanLoai(year, month);
                updateTitleTopBan(year, month);

                // Cập nhật biểu đồ
                // updateChart(data.labels, data.data);
                // Cập nhật các biểu đồ
                window.revenueChart.data.labels = data.monthlyRevenueLabels;
                window.revenueChart.data.datasets[0].data = data.monthlyRevenueData;
                window.revenueChart.update();

                window.orderStatusChart.data.labels = data.orderStatusLabels.map(label => {
                    switch(label) {
                        case '1': return 'Đang xử lý';
                        case '2': return 'Đang chuẩn bị';
                        case '3': return 'Đang giao';
                        case '4': return 'Hoàn thành';
                        case '0': return 'Hủy';
                        default: return 'Trạng thái: ' + label;
                    }
                });
                window.orderStatusChart.data.datasets[0].data = data.orderStatusData;
                window.orderStatusChart.update();

                window.topProductsChart.data.labels = data.topProductsLabels;
                window.topProductsChart.data.datasets[0].data = data.topProductsData;
                window.topProductsChart.update();
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }

    function updateTitleDoanhThu(year, month) {
        let title;
        if (month == 0) {
            title = "Doanh thu theo tháng (Năm " + year + ")";
        } else {
            title = "Doanh thu theo ngày (Tháng " + month + "/" + year + ")";
        }
        $('.doanhthu strong').text(title);
    }
    function updateTitlePhanLoai(year, month) {
        let title;
        if (month == 0) {
            title = "Phân loại đơn hàng (Năm " + year + ")";
        } else {
            title = "Phân loại đơn hàng (Tháng " + month + "/" + year + ")";
        }
        $('.phanLoai strong').text(title);
    }
    function updateTitleTopBan(year, month) {
        let title;
        if (month == 0) {
            title = "Top 10 sản phẩm bán chạy (Năm " + year + ")";
        } else {
            title = "Top 10 sản phẩm bán chạy (Tháng " + month + "/" + year + ")";
        }
        $('.topBan strong').text(title);
    }

</script>

</html>
