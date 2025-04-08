    // Hàm khởi tạo biểu đồ
    // Biểu đồ doanh thu theo tháng
    $(document).ready(function() {
    // Khởi tạo biểu đồ ban đầu
    initChart();

    // Bắt sự kiện click nút lọc
    $('#filterBtn').click(function() {
    const year = $('#yearSelect').val();
    const month = $('#monthSelect').val();

    // Gọi AJAX để cập nhật dữ liệu
    updateChartAndTitle(year, month);
});
});

    function initChart() {
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
        updateTitle(year, month);

        // Cập nhật biểu đồ
        updateChart(data.labels, data.data);
    },
    error: function(xhr, status, error) {
        console.error('Error:', error);
    }
});
}

function updateTitle(year, month) {
let title;
if (month == 0) {
    title = "Doanh thu theo tháng (Năm " + year + ")";
} else {
    title = "Doanh thu theo ngày (Tháng " + month + "/" + year + ")";
}
$('.doanhthu strong').text(title);
}

function updateChart(labels, data) {
window.revenueChart.data.labels = labels;
window.revenueChart.data.datasets[0].data = data;
window.revenueChart.update();
}

// Biểu đồ trạng thái đơn hàng
const orderStatusCtx = document.getElementById('orderStatusChart').getContext('2d');
const orderStatusChart = new Chart(orderStatusCtx, {
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
            'rgba(255, 99, 132, 0.7)'
        ],
        borderColor: [
            'rgba(54, 162, 235, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(255, 99, 132, 1)'
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

// Biểu đồ top sản phẩm bán chạy
const topProductsCtx = document.getElementById('topProductsChart').getContext('2d');
const topProductsChart = new Chart(topProductsCtx, {
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
            'rgba(54, 162, 235, 0.7)',   // Màu xanh dương
            'rgba(255, 99, 132, 0.7)',   // Màu đỏ
            'rgba(75, 192, 192, 0.7)',   // Màu xanh ngọc
            'rgba(255, 206, 86, 0.7)',   // Màu vàng
            'rgba(153, 102, 255, 0.7)',  // Màu tím
            'rgba(255, 159, 64, 0.7)',   // Màu cam
            'rgba(199, 199, 199, 0.7)',  // Màu xám
            'rgba(83, 102, 255, 0.7)',   // Màu xanh tím
            'rgba(255, 99, 71, 0.7)',    // Màu đỏ cam
            'rgba(144, 238, 144, 0.7)'   // Màu xanh lá nhạt
        ],
        borderColor: [ // Border tương ứng
            'rgba(54, 162, 235, 1)',
            'rgba(255, 99, 132, 1)',
            'rgba(75, 192, 192, 1)',
            'rgba(255, 206, 86, 1)',
            'rgba(153, 102, 255, 1)',
            'rgba(255, 159, 64, 1)',
            'rgba(199, 199, 199, 1)',
            'rgba(83, 102, 255, 1)',
            'rgba(255, 99, 71, 1)',
            'rgba(144, 238, 144, 1)'
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
