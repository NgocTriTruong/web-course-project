
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
              <a href="${pageContext.request.contextPath}/views/web/introduction.jsp" class="text_h1">GIỚI THIỆU</a>
              <ul class="header_line_2">
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#gioi_thieu_chung">
                    <span class="text_line_2">Giới thiệu chung</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#lich_su">
                    <span class="text_line_2">Lịch sử hình thành</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#chien_luoc">
                    <span class="text_line_2">Tầm nhìn chiến lược</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#quan_diem">
                    <span class="text_line_2">Quan điểm kinh doanh</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#cam_ket">
                    <span class="text_line_2">Cam kết chất lượng</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#san_xuat">
                    <span class="text_line_2">Nhà máy sản xuất</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#nhan_luc">
                    <span class="text_line_2">Nguồn nhân lực</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#van_hoa">
                    <span class="text_line_2">Văn hóa công ty</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
                <li class="p-3 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/introduction.jsp#thanh_tich">
                    <span class="text_line_2">Thành tích</span>
                    <i class="ti-angle-right ti_number"></i>
                  </a>
                </li>
              </ul>
            </li>
  
            <li class="p-4 san-pham">
              <a href="${pageContext.request.contextPath}/views/web/product.jsp" class="text_h1">SẢN PHẨM</a>
              <ul class="header_line_2">
                <li class="p-2 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/each_product/product_pig.jsp">
                    <span class="icon"><img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-pig.png" alt="" width="28px" height="25px"></span>
                    <span class="ms-2">Thức ăn cho Heo</span>
                    <i class="ti-angle-right ti_number mt-2"></i>
                  </a>
                </li>
                <li class="p-2 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/each_product/product_chicken.jsp">
                    <span class="icon"><img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-chicken.png" alt="" width="28px" height="20px"></span>
                    <span class="ms-2">Thức ăn cho Gà</span>
                    <i class="ti-angle-right ti_number mt-2"></i>
                  </a>
                </li>
                <li class="p-2 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/each_product/product_duck.jsp">
                    <span class="icon"><img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-duck.png" alt="" width="28px" height="20px"></span>
                    <span class="ms-2">Thức ăn cho Vịt</span>
                    <i class="ti-angle-right ti_number mt-2"></i>
                  </a>
                </li>
                <li class="p-2 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/each_product/product_cow.jsp">
                    <span class="icon"><img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-cow.png" alt="" width="28px" height="20px"></span>
                    <span class="ms-2">Thức ăn cho Bò</span>
                    <i class="ti-angle-right ti_number mt-2"></i>
                  </a>
                </li>
                <li class="p-2 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/each_product/product_shirmp.jsp">
                    <span class="icon"><img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-shrimp.png" alt="" width="28px" height="20px"></span>
                    <span class="ms-2">Thức ăn cho Tôm</span>
                    <i class="ti-angle-right ti_number mt-2"></i>
                  </a>
                </li>
                <li class="p-2 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/each_product/product_fish.jsp">
                    <span class="icon"><img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-fish.png" alt="" width="28px" height="20px"></span>
                    <span class="ms-2">Thức ăn cho Cá</span>
                    <i class="ti-angle-right ti_number mt-2"></i>
                  </a>
                </li>
                <li class="p-2 li_hover">
                  <a href="${pageContext.request.contextPath}/views/web/each_product/product_goat.jsp">
                    <span class="icon"><img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-goat.png" alt="" width="28px" height="20px"></span>
                    <span class="ms-2">Thức ăn cho Dê</span>
                    <i class="ti-angle-right ti_number mt-2"></i>
                  </a>
                </li>
              </ul>
            </li>
  
            <li class="p-4">
              <a href="${pageContext.request.contextPath}/views/web/advise.jsp" class="text_h1">TƯ VẤN KỸ THUẬT</a>
            </li>
            <li class="p-4">
              <a href="${pageContext.request.contextPath}/views/web/recruitment.jsp" class="text_h1">TUYỂN DỤNG</a>
            </li>
            <li class="p-4">
              <a href="${pageContext.request.contextPath}/views/web/tin_tuc/tin_tuc.jsp" class="text_h1">TIN TỨC</a>
            </li>
            <li class="p-4">
              <a href="${pageContext.request.contextPath}/views/web/help.jsp" class="text_h1">HỖ TRỢ KHÁCH HÀNG</a>
            </li>
  
            <li class="p-4 mt-1 pointer">
              <i class="ti-align-justify i_right_number"></i>
              <ul class="header_line_2" style="min-width: 150px;">
                <a href="${pageContext.request.contextPath}/views/web/library.jsp">
                  <li class="d-flex p-2 li_hover">
                    <span class="text_line_2">Thư viện</span>
                    <i class="mt-1 ti-angle-right ti_number" style="margin-left: 50px;"></i>
                  </li>
                </a>
                <a href="${pageContext.request.contextPath}/views/web/partner.jsp">
                  <li class="d-flex p-2 li_hover">
                    <span class="text_line_2">Đối tác</span>
                    <i class="mt-1 ti-angle-right ti_number" style="margin-left: 60px;"></i>
                  </li>
                </a>
              </ul>
            </li>
  
            <li class="p-4 ms-2 pointer">
              <div>
                <a href="#" data-bs-toggle="modal" data-bs-target="#exampleModal3">
                  <i class="fa-solid fa-magnifying-glass i_right_number mt-1"></i>
                </a>
              </div>
            </li>
  
            <!-- chưa đăng nhập -->
            <li id="not-logged-in" class="p-4">
              <div id="user-icon" class="user add_login">
                <i class="fa-solid fa-user i_right_number mt-3 ms-3 text-white"></i>
                <span class="user_hover">Đăng nhập/đăng kí</span>
              </div>
            </li>
            <!-- đã đăng nhập -->
            <li id="logged-in" class="p-4" style="display: none;">
              <a href="${pageContext.request.contextPath}/views/web/chi_tiet_ca_nhan/thong_tin_ca_nhan.jsp">
                <div id="user-icon" class="user add_login">
                  <i class="fa-solid fa-user i_right_number mt-3 ms-3 text-white"></i>
                  <ul class="header_line_2 me-2" style="min-width: 150px; margin-top: 25px;">
                    <a href="${pageContext.request.contextPath}/views/web/chi_tiet_ca_nhan/thong_tin_ca_nhan.jsp">
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
                    <a href="../chi_tiet_ca_nhan/so_dia_chi.jsp">
                      <li class="d-flex p-2 li_hover">
                        <i class="mt-1 fa-solid fa-location-dot ms-2 me-2"></i>
                        <span class="text_line_2">Sổ địa chỉ nhận hàng</span>
                      </li>
                    </a>
                    <a href="">
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
                  <span class="ms-1 text-white" style="margin-top: 14px;" >Giỏ hàng</span>
                </a>
              </div>
            </li>
        </ul>
      </div>
    </div>
  </header>
