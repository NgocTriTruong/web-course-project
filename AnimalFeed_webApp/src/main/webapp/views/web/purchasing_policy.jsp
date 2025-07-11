<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>chinh-sach-bao-mat</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chinh_sach_bao_mat.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

    <script src="${pageContext.request.contextPath}/views/template/scripts/confirm_login.js"></script>

    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger
            intent="WELCOME"
            chat-title="VinaFeed_chat"
            agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264"
            language-code="vi"
    ></df-messenger>

</head>
<body>
<%@ include file="layout/header.jsp" %>

    <main style="margin-top: 98px;">
        <div class="container">
            <div class="content">
                <div class="h2 mb-3">Hướng dẫn mua hàng và thanh toán online</div>
                <div class="p mb-3 text-justify color-dark">
                    <span class="fw-bold">Bước 1:</span> Tại giao diện trang chủ của VINAFEED</br>
                    Quý khách gõ vào thanh tìm kiếm tên sản phẩm mà mình quan tâm. Ví dụ Quý khách đang muốn mua sản phẩm thức ăn cho Heo (TOP), sau khi gõ hệ thống sẽ truy xuất ra các sản phẩm liên quan tới từ khóa mà Quý khách muốn tìm.
                </div>
                <div class="p mb-3 text-justify color-dark">
                    <span class="fw-bold">Bước 2:</span> Bây giờ Quý khách hãy click chọn sản phẩm mà mình muốn tham khảo. Ví dụ Quý khách chọn sản phẩm TOP 01, sau khi click chọn sẽ hiển thị trang chi tiết sản phẩm như dưới. 
                    Quý khách có thể xem các thông số thuộc tính cũng như bài viết đánh giá về sản phẩm phía bên dưới trang web.
                </div>
                <div class="p mb-3 text-justify color-dark">
                    <span class="fw-bold">Bước 3:</span> Sau khi đã chọn được sản phẩm phù hợp, Quý khách vui lòng click vào nút “MUA NGAY”. Sau đó Quý khách có thể thấy được giỏ hàng của mình đang gồm những sản phẩm đã chọn mua.
                </div>
                <div class="p mb-3 text-justify color-dark">
                    <span class="fw-bold">Bước 4:</span> Quý khách kiểm tra thông tin sản phẩm đã chọn, sau đó điền thông tin đặt hàng.</br>
                    Quý khách lựa chọn hình thức giao hàng mong muốn: Giao hàng tận nơi/Nhận tại cửa hàng và điền form thông tin khách hàng bao gồm: “Anh/Chị”, “Họ và tên”, “Số điện thoại”, “Email”. Trường “Email” Quý khách có thể bỏ qua nếu không muốn.</br>
                    <span class="fw-bold">Đối với phương án giao hàng tận nơi:</span> Quý khách điền thông tin địa chỉ nhận hàng và thời gian giao hàng.</br>
                    <span class="fw-bold">Đối với phương án nhận tại cửa hàng:</span>  Quý khách chọn tỉnh, huyện mong muốn và chọn cửa hàng phù hợp trong danh sách kết quả.
                </div>
                <div class="p mb-5 text-justify color-dark">
                    <span class="fw-bold">Bước 5:</span> Thanh Toán.</br>
                    Quý khách có thể lựa chọn hình thức thanh toán bằng tiền mặt khi nhận hàng, hoặc chuyển khoản qua ngân hàng.</br>
                    Nếu Quý khách chọn hình thức thanh toán tiền mặt khi nhận hàng. Vui lòng bấm nút "Đặt hàng" để hoàn tất.</br>
                    Vậy là Quý khách đã hoàn tất việc đặt hàng và thanh toán, tư vấn viên của VINAFEED sẽ gọi điện để xác nhận đơn hàng trong thời gian sớm nhất, đồng thời giải đáp các thắc mắc liên quan nếu khách hàng có nhu cầu.
                </div>
            </div>
        </div>
    </main>
<%@ include file="layout/near_footer.jsp" %>
<%@ include file="layout/footer.jsp" %>

</body>
</html>