<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chi_tiet_tin_tuc.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/font-awesome/all.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">

    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/jquery-3.7.1.min.js"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>

</head>
<body>
<div><%@ include file="../layout/header.jsp" %></div>
<main class="main" style="margin-top: 115px;">
    <div class="container my-5 tin_tuc">
        <!-- Header section -->
        <div class="text-center mb-4">
            <h1 class="display-4 text-start">Vinafeed Group Khẳng Định Vị Thế Trong Nghành Nông Nghiệp Công Nghệ Cao Năm 2024</h1>
            <p class="text-danger text-start">Thứ 3, 06/08/2024, 14:53 GMT+7</p>
        </div>

        <!-- Article content section -->
        <div class="row w-100">
            <div class="bai_viet">
                <p>
                    Ngày 02/08 vừa qua, tại Hà Nội, Công ty cổ phần Báo cáo Đánh giá Việt Nam (Vietnam Report) phối hợp cùng Báo VietNamNet, chính thức tổ chức Lễ công bố "TOP 10 Công Ty Uy Tín Nghành Nông Nghiệp Công Nghệ Cao Năm 2024".<br>

                    Đây là năm đầu tiên Vietnam Report mở rộng nghiên cứu sang lĩnh vực này, nhằm ghi nhận và tôn vinh những hạt nhân tạo sự đột phá trong cuộc cách mạng công nghệ cao của ngành nông nghiệp quốc gia. Dựa vào các tiêu chí đánh giá, Vinafeed Group vinh dự có mặt trong bảng xếp hạng này.<br>

                    Tại Lễ công bố và trao giải, ông Trần Trọng Quang - Giám Đốc kinh doanh Vinafeed miền Bắc, là đại diện công ty lên nhận cúp chứng nhận và hoa từ chương trình.
                </p>

                <!-- Image 1 -->
                <div class="text-center my-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/news/new7.jpg" alt="Vinafeed Group Award Ceremony" class="img-fluid">
                    <p class="text-muted img-label">ông Trần Trọng Quang - Giám đốc kinh doanh Vinafeed miền Bắc, đại diện công ty lên nhận cúp chứng nhận và hoa từ chương trình</p>
                </div>

                <p>
                    Top 10 Công ty uy tín ngành Nông nghiệp Công nghệ cao, được xây dựng dựa trên nguyên tắc khoa học và khách quan với ba tiêu chí chính: Năng lực tài chính thể hiện trên báo cáo tài chính năm gần nhất; Uy tín truyền thông được đánh giá bằng phương pháp Media Coding; Khảo sát đối tượng nghiên cứu và các bên liên quan.<br>

                    Trong suốt hơn ba thập kỷ hoạt động, Vinafeed Group đã không ngừng mở rộng quy mô sản xuất và nâng cao chất lượng sản phẩm. Công ty đã thực hiện nhiều chính sách hỗ trợ nông dân, cũng như giúp họ chuyển đổi từ hình thức chăn nuôi nhỏ lẻ sang mô hình chăn nuôi công nghiệp.
                    Điều này không chỉ giúp nâng cao năng suất mà còn tạo ra những sản phẩm an toàn và chất lượng cao cho người tiêu dùng.
                </p>

                <!-- Image 2 -->
                <div class="text-center my-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news2.jpg" alt="APEA 2024 Award" class="img-fluid">
                    <p class="text-muted img-label">Sự kiện vinh danh Top 10 Công ty uy tín ngành Nông nghiệp Công nghệ cao, nhằm ghi nhận và tôn vinh những hạt nhân tạo sự đột phá trong cuộc cách mạng công nghệ cao của ngành nông nghiệp quốc gia</p>
                </div>

                <p>
                    Với việc nằm trong "TOP 10 Công Ty Uy Tín Nghành Nông Nghiệp Công Nghệ Cao Năm 2024". không chỉ là một cột mốc quan trọng trong hành trình phát triển của Vinafeed Group, mà còn là minh chứng cho những nỗ lực không ngừng nghỉ của công ty, trong việc nâng cao chất lượng nông nghiệp tại Việt Nam.
                    Cùng với các doanh nghiệp được vinh danh trong bảng xếp hạng, Vinafeed Group sẽ tiếp tục duy trì và phát huy thế mạnh của mình, để cùng chung tay, góp sức phát triển ngành nông nghiệp tại Việt Nam lớn mạnh hơn nữa.
                </p>
            </div>
        </div>

        <div class="text-center my-4">
            <p>Chia sẻ:</p>
            <a href="#" id="facebook">
                <i class="fa-brands fa-facebook-f"></i>
            </a>
            <a href="#" id="twiter">
                <i class="fa-brands fa-twitter"></i>
            </a>
        </div>
    </div>

    <!-- Related Articles section -->
    <div id="newsCarousel" class="carousel slide related-news mb-5" data-bs-ride="carousel">
        <div class="carousel-inner w-75 ms-auto me-auto">
            <div class="carousel-item active">
                <div class="row">
                    <div class="col-md-4">
                        <div class="news-card">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news2.jpg" alt="News 1">
                            <div class="card-body">
                                <h5 class="card-title">Vinafeed Group Được Vinh Danh Thương Hiệu Vàng Nông Nghiệp...</h5>
                                <p class="card-text">Vinafeed Group vinh dự đạt giải Thương Hiệu Vàng Nông Nghiệp Việt Nam năm nay...</p>
                                <a href="#" class="text-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="news-card">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news3.jpg" alt="News 2">
                            <div class="card-body">
                                <h5 class="card-title">Vinafeed Group Khẳng Định Vị Thế Trong Ngành Nông Nghiệp Công...</h5>
                                <p class="card-text">Ngày 02/08 vừa qua, tại Hà Nội...</p>
                                <a href="#" class="text-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="news-card">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news4.jpg" alt="News 3">
                            <div class="card-body">
                                <h5 class="card-title">Vinafeed Group Tiếp Tục Giữ Vững Danh Hiệu "Hàng Việt Nam Chất...</h5>
                                <p class="card-text">Tối ngày 14/03 vừa qua lễ công bố "Hàng Việt Nam chất lượng cao năm 2024"...</p>
                                <a href="#" class="text-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Repeat carousel-item for more slides -->
            <div class="carousel-item">
                <div class="row">
                    <div class="col-md-4">
                        <div class="news-card">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news2.jpg" alt="News 1">
                            <div class="card-body">
                                <h5 class="card-title">Vinafeed Group Được Vinh Danh Thương Hiệu Vàng Nông Nghiệp...</h5>
                                <p class="card-text">Vinafeed Group vinh dự đạt giải Thương Hiệu Vàng Nông Nghiệp Việt Nam năm nay...</p>
                                <a href="#" class="text-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="news-card">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news3.jpg" alt="News 2">
                            <div class="card-body">
                                <h5 class="card-title">Vinafeed Group Khẳng Định Vị Thế Trong Ngành Nông Nghiệp Công...</h5>
                                <p class="card-text">Ngày 02/08 vừa qua, tại Hà Nội...</p>
                                <a href="#" class="text-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="news-card">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/news/news4.jpg" alt="News 3">
                            <div class="card-body">
                                <h5 class="card-title">Vinafeed Group Tiếp Tục Giữ Vững Danh Hiệu "Hàng Việt Nam Chất...</h5>
                                <p class="card-text">Tối ngày 14/03 vừa qua lễ công bố "Hàng Việt Nam chất lượng cao năm 2024"...</p>
                                <a href="#" class="text-danger">Xem chi tiết</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Controls -->
        <button class="carousel-control-prev" type="button" data-bs-target="#newsCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#newsCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>
</main>
<div><%@ include file="../layout/footer.jsp" %></div> <!-- Chỉ giữ ở đầu -->

<script>
    $(document).ready(function() {
        // Initialize the carousel
        $('#newsCarousel').carousel({
            interval: 3000 // Set carousel interval (3 seconds per slide)
        });

        // Optional: Add click events for custom navigation or other jQuery-based interactions
        $('.carousel-control-prev').click(function() {
            $('#newsCarousel').carousel('prev');
        });

        $('.carousel-control-next').click(function() {
            $('#newsCarousel').carousel('next');
        });
    });
</script>
</body>
</html>
