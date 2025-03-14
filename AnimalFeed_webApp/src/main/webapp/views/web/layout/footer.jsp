
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<footer>
        <div id="footer" class="container-fluid bg-dark">
            <div class="container d-flex content_top">
                <div class="text_left mt-4">
                    <div class="h4 text-white">Hệ thông VINA trên toàn quốc</div>
                    <div class="p text-white">Bao gồm các chi nhánh, các nhà phân phối và các các cửa hàng bán lẻ có cung cấp thức ăn của VINA</div>
                </div>
                <div class="text_right mt-4">
                    <div class="button" style="background-color: #fcac18bc;">
                        <a href="store">
                            <p class="text-white fw-bold" style="line-height: 47px;">Xem danh sách các cửa hàng</p>
                        </a>
                    </div>
                </div>
            </div>
            <div class="line"></div>
            <div class="container">
                <div class="row">
                    <div class="col-md-3 text-left">
                        <div class="h4 text-light mb-3">KẾT NỐI VỚI VINA GROUP</div>
                        <div class="social">
                            <a href=""><img src="${pageContext.request.contextPath}/views/template/assets/images/social/fb.png" alt="" height="40px" width="40px" class="img_social"></a>
                            <a href=""><img src="${pageContext.request.contextPath}/views/template/assets/images/social/zalo.png" alt="" height="40px" width="40px" class="img_social"></a>
                            <a href=""><img src="${pageContext.request.contextPath}/views/template/assets/images/social/youtube.png" alt="" height="40px" width="40px" class="img_social"></a>
                            <a href=""><img src="${pageContext.request.contextPath}/views/template/assets/images/social/tiktok.png" alt="" height="40px" width="40px" class="img_social"></a>
                        </div>
                        <div class="p text-light mt-3">TỔNG ĐÀI MIỄN PHÍ</div>
                        <div class="p text-light mt-2">Tư vấn mua hàng (Miễn phí)</div>
                        <div class="p text-light mt-1">1800.6601 (Nhánh 1)</div>
                        <div class="p text-light mt-3">Hỗ trợ kỹ thuật</div>
                        <div class="p text-light mt-1">1800.6601 (Nhánh 2)</div>
                        <div class="p text-light mt-3">Góp ý, khiếu nại</div>
                        <div class="p text-light mt-1">1800.6616 (8h00 - 22h00)</div>
                    </div>
                    <div class="col-md-2 text-left">
                        <div class="h4 text-light">SẢN PHẨM</div>
                        <c:forEach var="ca" items="${categoriesData}">
                            <a href="list-product?categoryId=${ca.id}">
                                <div class="p mt-3">${ca.name}</div>
                            </a>
                        </c:forEach>
                    </div>
                    <div class="col-md-2">
                        <div class="h4 text-light text-left">VỀ VINAFEED</div>
                        <a href="${pageContext.request.contextPath}/introduction#gioi_thieu_chung">
                            <div class="p mt-3">Giới thiệu chung</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/introduction#lich_su">
                            <div class="p mt-3">Lịch sử hình thành</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/introduction#chien_luoc">
                            <div class="p mt-3">Tầm nhìn chiến lược</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/introduction#quan_diem">
                            <div class="p mt-3">Quan điểm kinh doanh</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/introduction#cam_ket">
                            <div class="p mt-3">Cam kết chất lượng</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/introduction#nhan_luc">
                            <div class="p mt-3">Nguồn nhân lực</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/introduction#van_hoa">
                            <div class="p mt-3">Văn hóa công ty</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/introduction#thanh_tich">
                            <div class="p mt-3">Thành tích</div>
                        </a>
                    </div>
                    <div class="col-md-3">
                        <div class="h4 text-light">THÔNG TIN THÊM</div>
                        <a href="advise">
                            <div class="p mt-3">Tư vấn kỹ thuật</div>
                        </a>
                        <a href="news">
                            <div class="p mt-3">Tin tức</div>
                        </a>
                        <a href="library">
                            <div class="p mt-3">Thư viện</div>
                        </a>
                        <a href="partner">
                            <div class="p mt-3">Đối tác</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/privacy-policy">
                            <div class="p mt-3">Chính sách bảo mật</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/purchasing-policy">
                            <div class="p mt-3">Hướng dẫn mua hàng & thanh toán online</div>
                        </a>
                        <a href="${pageContext.request.contextPath}/contact">
                            <div class="p mt-3">Hỗ trợ khách hàng</div>
                        </a>
                    </div>
                    <div class="col-md-2">
                        <div class="text-light fw-bold mb-3" style="font-size: 18px;">HỖ TRỢ THANH TOÁN</div>
                        <div class="payment">
                            <div class="row mt-2">
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/visa.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/bidv.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/dongABank.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/mbbank.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                            </div>
                            <div class="row mt-2">
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/momo.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/techcombank.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/tpbank.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/viettinbank.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                            </div>
                            <div class="row mt-2">
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/zaloPay.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/vnpay.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/vietcombank.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                                <div class="col-md-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/payment/vibbank.jpg" alt="" width="47px" height="30px" class="img_paynumber"></div>
                            </div>
                        </div>
                        <div class="h6 text-light mt-4 fw-bold">Chứng nhận & Thành tích</div>
                        <div class="row mt-3">
                            <div class="col-md-4"><img src="${pageContext.request.contextPath}/views/template/assets/images/gioi_thieu/thanh_tich1.jpg" alt="" width="65px" height="60px" class="img_paynumber"></div>
                            <div class="col-md-4"><img src="${pageContext.request.contextPath}/views/template/assets/images/gioi_thieu/thanh_tich2.jpg" alt="" width="65px" height="60px" class="img_paynumber"></div>
                            <div class="col-md-4"><img src="${pageContext.request.contextPath}/views/template/assets/images/gioi_thieu/thanh_tich3.jpg" alt="" width="65px" height="60px" class="img_paynumber"></div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-4"><img src="${pageContext.request.contextPath}/views/template/assets/images/gioi_thieu/thanh_tich4.jpg" alt="" width="65px" height="60px" class="img_paynumber"></div>
                            <div class="col-md-4 ms-3"><img src="${pageContext.request.contextPath}/views/template/assets/images/gioi_thieu/chung_nhan.png" alt="" width="100px" height="50px" class="img_paynumber"></div>
                        </div>
                    </div>
                </div>
                <div class="img-footer text-center mt-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/footer/ca.png" alt="" class="me-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/footer/cut.png" alt="" class="me-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/footer/vit.png" alt="" class="me-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/footer/ga.png" alt="" class="me-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/footer/heo.png" alt="" class="me-4">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/footer/bo.png" alt="" class="me-4">
                </div>
            </div>
            <div class="line_footer_bottom"></div>
            <div class="footer-bottom text-center mt-4 pb-4">
                <p>Copyright © 2024 <span class="fw-bold">TẬP ĐOÀN VINAFEED - Nhóm 11</span></p>
            </div>
        </div>
    </footer>