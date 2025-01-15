<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <c:forEach var="ca" items="${categoriesData}">
    <c:if test="${ca.id == selectedCategoryId}">
      <title>${ca.name}</title>
    </c:if>
  </c:forEach>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/each_product.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/product/product_sale_new.js"></script>
  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/product/tab_product_all.js"></script>
  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/page.js"></script>

  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/login.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/signup.css">

  <!-- scrollToTopBtn -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/scrollToTopBtn.css">
  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/scrollToTopBtn.js" defer></script>

  <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/search.js" defer></script>
</head>
<body>
<%@ include file="../layout/header.jsp" %>

<main style="margin-top: 95px;" class="product bg-light">

  <div id="pagination" class="position-relative text-center mb-4">
    <img src="${pageContext.request.contextPath}/views/template/assets/images/background/product-main-bg.jpg" class="img-fluid w-100" alt="Thức ăn">
  </div>

  <div class="container">
    <div class="d-flex mb-3">
      <c:forEach var="ca" items="${categoriesData}">
        <a class="d-flex product_button_icon ms-3" href="list-product?categoryId=${ca.id}" style="text-decoration: none;">
          <img src="${ca.img}" alt="${ca.name}" width="30px" height="30px">
          <p class="ms-1 text-white fw-bold">
              ${ca.name}
          </p>
        </a>
      </c:forEach>
    </div>
  </div>

  <!-- Filter -->
  <div class="containers my-4">
    <div class="row justify-content-center mb-3 pt-3">
      Kết quả tìm kiếm
    </div>
  </div>

  <!-- product all -->
  <div class="container">
    <div id="product_all_one">
      <div class="row">
        <c:choose>
          <c:when test="${not empty productsData}">
            <c:forEach var="p" items="${productsData}">
              <div class="col-md-3 bg-white text-center m-3 col product_number">
                <a href="product-detail?pid=${p.id}" class="text-decoration-none text-dark">
                  <div class="product-img">
                    <img src="${p.img}" alt="${p.name}" height="250px" width="200px">
                  </div>
                  <div class="h5 text-h">${p.name}</div>
                  <div class="p mb-2 text-p">${p.description}</div>
                  <div class="h4 text-start ms-3" style="color: red;">
                    <f:formatNumber value="${p.price}"/> <u>đ</u>
                  </div>
                  <div class="p text-start ms-3">Đã bán 1,1k</div>
                  <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                    <i class="fa-solid fa-truck mt-1"></i>
                    <p class="ms-2">2 - 4 ngày</p>
                  </div>
                </a>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <div class="col-12 text-center mt-5">
              <p class="text-muted h5">Không có sản phẩm nào trong danh mục này.</p>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>

  <!-- Pagination -->
  <div class="container mt-4">
    <nav aria-label="Page navigation">
      <ul class="pagination justify-content-center">
        <li class="page-item ${currentPage > 1 ? '' : 'disabled'}">
          <a class="page-link" href="?categoryId=${selectedCategoryId}&page=${currentPage - 1}"><i class="fa-solid fa-chevron-left"></i></a>
        </li>
        <c:forEach var="i" begin="1" end="${endPage}">
          <c:choose>
            <c:when test="${i == 1 || i == endPage || (i >= currentPage - 2 && i <= currentPage + 2)}">
              <li class="page-item ${i == currentPage ? 'active' : ''}" style="color: #94b83d;">
                <a class="page-link" href="?categoryId=${selectedCategoryId}&page=${i}">${i}</a>
              </li>
            </c:when>
            <c:when test="${i == currentPage - 3 || i == currentPage + 3}">
              <li class="page-item disabled">
                <span class="page-link">...</span>
              </li>
            </c:when>
          </c:choose>
        </c:forEach>
        <li class="page-item ${currentPage < endPage ? '' : 'disabled'}">
          <a class="page-link" href="?categoryId=${selectedCategoryId}&page=${currentPage + 1}"><i class="fa-solid fa-chevron-right"></i></a>
        </li>
      </ul>
    </nav>
  </div>

  <!-- Text -->
  <div class="container mt-5">
    <div class="p text-center fw-bold mb-4" style="font-size: 22px;">VINAFEED GROUP LUÔN HIỂU RÕ NHU CẦU DINH DƯỠNG CHO vật nuôi</div>
    <img src="${pageContext.request.contextPath}/views/template/assets/images/gioi_thieu/nha_may_sx.png" alt="" height="500px" width="100%" style="border-radius: 10px;">
    <div class="p mt-4" style="text-indent: 30px;">
      Nâng cao hiệu quả kinh tế và giá trị chăn nuôi từ đàn vật nuôi của mình, luôn là mong muốn của toàn thể người chăn nuôi. Để đàn vật nuôi của mình phát triển toàn diện, và cho năng suất cao thì
      người chăn nuôi cần nắm rõ và vận dụng nhiều yếu tố hỗ trợ trong chăn nuôi vật nuôi. Những yếu tố như: <span class="fw-bold">Thức ăn cho vật nuôi</span>, kỹ thuật và sức khỏe vật nuôi đều góp phần tạo nên kết quả đó.
      <div class="mt-1" style="text-indent: 30px;">
        <span class="fw-bold" style="text-indent: 30px;">Vinafeed Group</span> luôn cung cấp những giải pháp tốt nhất hỗ trợ người chăn nuôi trong từng giai đoạn. Đặc biệt, những giải pháp thông qua thức ăn XANH - SẠCH sẽ đảm bảo trại của người chăn nuôi đạt được kết quả toàn diện nhất.
      </div>

    </div>
    <div class="p mt-3 fw-bold" style="font-size: 22px;">Thức ăn chăn nuôi dành cho vật nuôi qua từng giai đoạn</div>
    <div id="more_text_product" class="more_text_product">
      <div class="p mt-2" style="text-indent: 30px;">
        Hiểu rõ được nhu cầu dinh dưỡng thiết yếu cho từng giai đoạn sinh trưởng của heo, Vinafeed Group đã nghiên cứu, phát triển và sản xuất các dòng thức ăn chăn nuôi vật nuôi riêng biệt cho từng giai đoạn:
        <p style="text-indent: 50px;">
          - Thức ăn cho vật nuôi con.
        </p>
        <p style="text-indent: 50px;">
          - Thức ăn cho vật nuôi thịt.
        </p>
        <p style="text-indent: 50px;">
          - Thức ăn cho vật nuôi hậu bị.
        </p>
        <p style="text-indent: 50px;">
          - Thức ăn cho vật nuôi nái.
        </p>
        <p style="text-indent: 50px;">
          - Thức ăn cho vật nuôi đực.
        </p>
        <p tyle="text-indent: 30px;">
          Mỗi giai đoạn vật nuôi sẽ được xây dựng một khẩu phần thức ăn phù hợp. Tất cả nguồn nguyên liệu đầu vào đều có nguồn gốc truy xuất rõ ràng, xanh sạch tốt cho gia súc. Từ đó mang đến những sản phẩm chất lượng cao cùng sự đảm bảo hiệu suất chăn nuôi bền vững cho trang trại.
        </p>
        <p tyle="text-indent: 30px;">
          Thức ăn chăn nuôi vật nuôi của Vinafeed Group cung cấp cho vật nuôi dinh dưỡng hoàn chỉnh từ nhiều loại nguyên liệu thô như đậu nành, bắp… Cùng những vi chất dinh dưỡng chính như các khoáng chất, vitamin…
        </p>
      </div>
      <div class="p mt-3 fw-bold mb-3" style="font-size: 22px;">Thức ăn chăn nuôi XANH - SẠCH</div>
      <img src="${pageContext.request.contextPath}/views/template/assets/images/banner/Home_Banner_Web.jpg" alt="" width="70%" height="500px" style="margin-left: 200px; border-radius: 10px;">
      <p class="mt-3" style="text-indent: 30px;">XANH - SẠCH là yếu tố chủ chốt và là kim chỉ nam trong suốt gần 30 năm sản xuất thức ăn chăn nuôi heo của Vinafeed Group. </p>
      <div class="p mt-3 fw-bold mb-3" style="font-size: 22px;">Đầu tư công nghệ dinh dưỡng và dây chuyền sản xuất</div>
      <div class="p mt-2" style="text-indent: 30px;">
        Tại 03 nhà máy của chúng tôi, dây chuyền sản xuất và công nghệ đều được đầu tư theo công nghệ Van Aarsen nhập khẩu từ Châu Âu. Đây là công nghệ hoàn toàn tự động từ khâu nhập nguyên liệu cho đến khâu cân, trộn,
        đóng bao thành phẩm, tạo ra những sản phẩm xuất xưởng có chất lượng vượt trội, an toàn cho người chăn nuôi và thân thiện với môi trường.
        <p style="text-indent: 30px;">
          Ngoài ra, việc vận hành của các nhà máy tại Vinafeed Group đều được áp dụng theo hệ thống quản lý chất lượng ISO, giúp phát hiện và loại trừ các rủi ro tiềm ẩn.
        </p>
      </div>
      <div class="p mt-3 fw-bold mb-3" style="font-size: 22px;">Duy trì ổn định chất lượng mỗi ngày</div>
      <div class="p mt-2" style="text-indent: 30px;">
        Vinafeed Group cũng là một trong số ít doanh nghiệp đã đầu tư được phòng thí nghiệm hiện đại và liên kết phòng thí nghiệm, giúp kiểm soát nguồn nguyên liệu mỗi ngày.
        Thông qua phòng thí nghiệm này, nguyên liệu được chọn lọc cẩn thận, nghiêm ngặt cho sản xuất.
        <p style="text-indent: 30px;">
          Vinafeed Group cũng kiểm soát thành phẩm trước khi đóng bao và xuất ra thị trường. Nhằm đảm bảo tuyệt đối cho chất lượng, hiệu quả chăn nuôi cao.
        </p>
      </div>
      <div class="p mt-3 fw-bold mb-3" style="font-size: 22px;">Hỗ trợ trực tiếp kiến thức chăn nuôi thực tiễn</div>
      <div class="p mt-2" style="text-indent: 30px;">
        Thông qua những yêu cầu hỗ trợ mỗi ngày của người chăn nuôi, Vinafeed Group mỗi ngày đều nhận và giải đáp cho hàng nghìn chủ trang trại heo gửi
        về trên khắp cả nước. Bên cạnh đó, đội ngũ kỹ thuật định kỳ đến thăm hàng trăm trang trại để hỗ trợ trực tiếp cho người chăn nuôi.
        <p style="text-indent: 30px;">
          Các chuyên gia của Vinafeed Group đã và đang làm việc liên tục để nắm bắt xu thế chăn nuôi hiện đại. Cũng như sự ảnh hưởng của thay đổi môi trường đến vật nuôi.
          Từ đó cung cấp những kiến thức và kinh nghiệm thực tiễn về mối liên kết của thức ăn chăn nuôi heo và cách chăn nuôi.
        </p>
      </div>
      <img src="${pageContext.request.contextPath}/views/template/assets/images/thu_vien/Cham_soc_heo.jpg" alt="Chăm sóc heo" width="100%" height="500px" style="border-radius: 10px;">
      <div class="p mt-3 fw-bold mb-3" style="font-size: 22px;">Tư vấn dinh dưỡng vật nuôi</div>
      <div class="p mt-2" style="text-indent: 30px;">
        Tất cả sản phẩm thức ăn chăn nuôi heo của Vinafeed Group đều hướng tới mục tiêu duy nhất là ĐẢM BẢO SỨC KHỎE VẬT NUÔI và GIA TĂNG HIỆU QUẢ KINH TẾ BỀN VỮNG cho trang trại. Cùng với đó là những kiến thức về dinh dưỡng vật nuôi từ chuyên gia tập đoàn mà Vinafeed Group
        cung cấp đến người chăn nuôi heo. Hơn thế, mạng lưới vận chuyển và cung cấp sản phẩm rộng khắp toàn quốc hoàn toàn đáp ứng được nhu cầu của từng người chăn nuôi mọi miền đất nước.
        <p style="text-indent: 30px;">
          Liên hệ ngay với chúng tôi, để được hỗ trợ nhanh nhất.
        </p>
      </div>
      <div class="p text-center pt-3 fw-bold pb-4" style="color: #198754; font-size: 25px;">VINAFEED GROUP  - NIỀM TIN CHĂN NUÔI VIỆT</div>
    </div>
    <a href="javascript:void(0)" id="toggle-button-text" class="toggle-button-text text-center">Xem thêm <i class="fa-solid fa-chevron-down ms-2"></i></a>
  </div>
</main>
<%@ include file="../layout/near_footer.jsp" %>

<%@ include file="../layout/footer.jsp" %>

<!-- scrollToTopBtn -->
<button id="scrollToTopBtn"><i class="fa-solid fa-chevron-up"></i></button>

<!-- Script -->
<script src="${pageContext.request.contextPath}/views/template/assets/scripts/product/transferProduct.js" defer></script>

</body>
</html>