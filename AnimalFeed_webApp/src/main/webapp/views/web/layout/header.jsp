
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  <header>

    <div id="header" class="bg-light">
      <div class="content d-flex justify-content-center">
        <div class="logo_shop">

          <a href="${pageContext.request.contextPath}/index.jsp">
            <img src="${pageContext.request.contextPath}/views/template/assets/images/header/logo_vina.png" alt="Logo Vina" height="75px" width="90px" style=" margin: 9px 0 0 22px;">
          </a>
        </div>
        <ul class="d-flex justify-content-center">
            <li class="p-4">
              <a href="${pageContext.request.contextPath}/introduction" class="text_h1">GIỚI THIỆU</a>
              <ul class="header_line_2">
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#gioi_thieu_chung">
                    <span class="text_line_2">Giới thiệu chung</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#lich_su">
                    <span class="text_line_2">Lịch sử hình thành</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#chien_luoc">
                    <span class="text_line_2">Tầm nhìn chiến lược</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#quan_diem">
                    <span class="text_line_2">Quan điểm kinh doanh</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#cam_ket">
                    <span class="text_line_2">Cam kết chất lượng</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#san_xuat">
                    <span class="text_line_2">Nhà máy sản xuất</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#nhan_luc">
                    <span class="text_line_2">Nguồn nhân lực</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#van_hoa">
                    <span class="text_line_2">Văn hóa công ty</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/introduction#thanh_tich">
                    <span class="text_line_2">Thành tích</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
              </ul>
            </li>
  
            <li class="p-4 san-pham">
              <a href="${pageContext.request.contextPath}/product" class="text_h1">SẢN PHẨM</a>
              <ul class="header_line_2">
              <c:forEach var="ca" items="${categoriesData}">
                <li class="p-2 li_hover">
                  <a href="list-product?categoryId=${ca.id}">
                    <span class="icon"><img src="${ca.img}" alt="${ca.name}" width="28px" height="25px"></span>
                    <span class="ms-2">${ca.name}</span>
                    <i class="ti-angle-right ti_number mt-2"></i>
                  </a>
                </li>
              </c:forEach>
              </ul>
            </li>
  
            <li class="p-4">
              <a href="advise" class="text_h1">TƯ VẤN KỸ THUẬT</a>
            </li>
            <li class="p-4">
              <a href="recruitment" class="text_h1">TUYỂN DỤNG</a>
            </li>
            <li class="p-4">
              <a href="news" class="text_h1">TIN TỨC</a>
            </li>
            <li class="p-4">
              <a href="${pageContext.request.contextPath}/views/web/help.jsp" class="text_h1">HỖ TRỢ KHÁCH HÀNG</a>
            </li>
  
            <li class="p-4 mt-1 pointer">
              <i class="ti-align-justify i_right_number"></i>
              <ul class="header_line_2" style="min-width: 150px;">
                <a href="library">
                  <li class="d-flex p-2 li_hover">
                    <span class="text_line_2">Thư viện</span>
                    <i class="mt-1 ti-angle-right ti_number" style="margin-left: 50px;"></i>
                  </li>
                </a>
                <a href="partner">
                  <li class="d-flex p-2 li_hover">
                    <span class="text_line_2">Đối tác</span>
                    <i class="mt-1 ti-angle-right ti_number" style="margin-left: 60px;"></i>
                  </li>
                </a>
              </ul>
            </li>
  
            <li class="p-4 ms-2 pointer" >
              <div>
                  <i class="fa-solid fa-magnifying-glass i_right_number mt-1" id="search-toggle"></i>
              </div>
            </li>

            <!-- Form tìm kiếm ẩn -->
            <li id="search-form-container" style="display: none; position: absolute; top: 50px; right: 20px; background: white; border: 1px solid #ddd; padding: 10px; border-radius: 10px; margin-right: 133px; margin-top: 19px; width: 324px; z-index: 9999;">
              <form method="get" action="product-list-search">
                <input type="text" name="description" placeholder="Tìm kiếm theo mô tả" style="display: block; margin-bottom: 10px; width: 100%; height: 42px;    padding-left: 5px;" />
                <input type="number" name="minPrice" placeholder="Giá tối thiểu" style="display: block; margin-bottom: 10px; width: 100%; height: 42px;    padding-left: 5px;" />
                <input type="number" name="maxPrice" placeholder="Giá tối đa" style="display: block; margin-bottom: 10px; width: 100%; height: 42px;    padding-left: 5px;" />
                <button type="submit" style="width: 100%;">Tìm kiếm</button>
              </form>
            </li>
  
            <!-- chưa đăng nhập -->
            <li id="not-logged-in" class="p-4" style="${sessionScope.user != null ? 'display:none;' : ''}">
              <a href="login">
                <div id="user-icon" class="user add_login">
                  <i class="fa-solid fa-user i_right_number mt-3 ms-3 text-white"></i>
                  <span class="user_hover">Đăng nhập/đăng kí</span>
                </div>
              </a>
            </li>
            <!-- đã đăng nhập -->
            <li id="logged-in" class="p-4" style="${sessionScope.user == null ? 'display:none;' : ''}">
              <a href="${pageContext.request.contextPath}/profile-user" style="width: 0;">
                <div id="user-icon" class="user add_login">
                  <i class="fa-solid fa-user i_right_number mt-3 ms-3 text-white"></i>
                  <ul class="header_line_2 me-2" style="min-width: 150px; margin-top: 25px;">
                    <a href="${pageContext.request.contextPath}/profile-user">
                      <li class="d-flex p-2 li_hover">
                        <i class="mt-1 fa-solid fa-user ms-2 me-2"></i>
                        <span class="text_line_2">Thông tin cá nhân</span>
                      </li>
                    </a>
                    <a href="${pageContext.request.contextPath}/views/web/chi_tiet_ca_nhan/don_hang_cua_toi.jsp">
                      <li class="d-flex p-2 li_hover">
                        <i class="mt-1 fa-solid fa-box ms-2 me-2"></i>
                        <span class="text_line_2">Đơn hàng của tôi</span>
                      </li>
                    </a>
                    <a href="location_user">
                      <li class="d-flex p-2 li_hover">
                        <i class="mt-1 fa-solid fa-location-dot ms-2 me-2"></i>
                        <span class="text_line_2">Sổ địa chỉ nhận hàng</span>
                      </li>
                    </a>
                    <a href="${pageContext.request.contextPath}/logout">
                      <li class="d-flex p-2 li_hover" id="logout">
                        <i class="mt-1 fa-solid fa-right-from-bracket ms-2 me-2"></i>
                        <span class="text_line_2">Đăng xuất</span>
                      </li>
                    </a>
                  </ul>
                </div>
              </a>
            </li>

          <li class="p-4">
            <div class="cart d-flex">
              <a href="${pageContext.request.contextPath}/views/web/cart.jsp">
                <i class="fa-solid fa-shopping-cart i_right_number mt-3 text-white"></i>
                  <span class="ms-1 text-white" style="margin-top: 14px;">Giỏ hàng</span>
                  <c:if test="${not empty sessionScope.cart}">
                    <span class="cart-count bg-danger text-white rounded-circle"
                        style="position: absolute;
                               padding: 2px 6px;
                               font-size: 12px;
                               margin-left: 5px;
                               margin-top: -5px;">
                      ${sessionScope.cart.getTotalItems()}
                    </span>
                  </c:if>
              </a>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </header>
