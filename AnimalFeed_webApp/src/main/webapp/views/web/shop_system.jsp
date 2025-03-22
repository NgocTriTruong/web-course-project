<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "f" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cửa hàng</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/he_thong_cua_hang/he_thong_cua_hang.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>

     <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js"></script>

    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger
            intent="WELCOME"
            chat-title="VinaFeed_chat"
            agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264"
            language-code="vi"
    ></df-messenger>


     <style>
        .address-form {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }
    
        .address-form-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
    
        .form-header {
            border-bottom: 1px solid #e9ecef;
        }   
    
        .form-item label {
            margin-bottom: 0.5rem;
        }
    
        .form-details textarea {
            margin-top: 1rem;
        }
    
        .form-footer {
            border-top: 1px solid #e9ecef;
        }
    </style>


</head>
<body>
<%@ include file="layout/header.jsp" %>

    <main style="margin-top: 90px;">
        <div class="cua_hang bg-light pt-2">
            <div class="container">
                <div class="h3 mt-4">Hệ thống 1.200 đại lý và cửa hàng của VINAFEED</div>
                <div class="p">Thời gian hoạt động: 7:30 - 22:00 hằng ngày (Thay đổi theo từng cửa hàng).</div>
                <div class="row">
                    <div class="cua_hang_left col-md-3 mt-4 bg-white me-3">
                        <div class="p text-center mt-4">Tìm kiếm cửa hàng</div>
                        <div class="line_red mt-2 mb-2"></div>  
                        <div class="input-group rounded">
                            <span class="input-group-text border-0" id="search-addon">
                                <i class="fas fa-search" type="submit"></i>
                            </span>
                            <input type="text" name="q" placeholder="Tìm kiếm tên đường và tỉnh thành" class="form-control rounded"
                                aria-label="Search" aria-describedby="search-addon" />
                        </div>

                        <div class="mt-4" style="border: 1px solid #888;"></div>

                        <div class="form-group m-3 position-relative">
                            <input type="text" class="form-control chose_location" placeholder="Tỉnh/Thành Phố" readonly onclick="openAddressForm()">
                            <i class="" onclick="openAddressForm()"></i>
                            
                            <div class="address-form" id="addressFormModal">
                                <div class="address-form-content bg-white">
                                    <div class="form-header d-flex justify-content-between align-items-center p-3">
                                        <h5 class="m-0">Địa chỉ cửa hàng</h5>
                                        <button class="btn-close" onclick="closeAddressForm()">&times;</button>
                                    </div>
                                    
                                    <div class="form-container p-3">
                                        <div class="form-item mb-3">
                                            <label for="province" class="form-label">Chọn Tỉnh:</label>
                                            <select id="province" class="form-select" onchange="loadDistricts()">
                                                <option value="">--Chọn Tỉnh--</option>
                                            </select>
                                        </div>
                                        
                                        <div class="form-item mb-3">
                                            <label for="district" class="form-label">Chọn Huyện:</label>
                                            <select id="district" class="form-select" onchange="loadWards()">
                                                <option value="">--Chọn Huyện--</option>
                                            </select>
                                        </div>
                                        
                                        <div class="form-item mb-3">
                                            <label for="ward" class="form-label">Chọn Xã:</label>
                                            <select id="ward" class="form-select">
                                                <option value="">--Chọn Xã--</option>
                                            </select>
                                        </div>
                                    </div>
                                    
                                    <div class="form-footer p-3">
                                        <button class="btn w-100" onclick="saveAddress()" style="background-color: #fcae18;">Lưu địa chỉ</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="h6">Cửa hàng gợi ý</div>
                        <div class="container" style="border: 1px solid #333; cursor: pointer;">
                            <div class="p mt-3 pb-3" style="border-bottom: 1px solid #333;">261 Khánh Hội, P.2, Q.4, TP.Hồ Chí Minh</div>
                            <div class="p mt-3 pb-3" style="border-bottom: 1px solid #333;">Tam Kỳ, Quảng Nam</div>
                            <div class="p mt-3 pb-3" style="border-bottom: 1px solid #333;">Phường Linh Trung, Tp.Thủ Đức, Tp.Hồ Chí Mình</div>
                            <div class="p mt-3 pb-3" >Phường Trung Đông,Quận Linh Chiểu, Đà Nẵng</div>
                        </div>
                        <div class="text-center mt-3" >Xem thêm</div>

                    </div>
                    <div class="cua_hang_right col-md-8 bg-white mt-4 mb-5 pb-2 ms-2">
                        <div class="cua_hang_right_80">
                            <div class="p mt-4">VINAFEED hệ thống bán lẻ khắp ba miền, trải khắp 63 tỉnh thành luôn luôn mở rộng để phục vụ Quý Khách trên toàn quốc. 
                                VINAFEED cung cấp dịch vụ bán hàng và phục vụ hàng đầu:
                            </div>
                            <div class="row">
                                <div class="cua_hang_right_number d-flex mt-4">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/nhan_vien_nhiet_tinh_icon_5d2e570b30.svg" alt="" height="50px">
                                    <div class="cua_hang_right_number_text ms-3">
                                        <div class="p fw-bold">Nhân viên nhiệt tình</div>
                                        <div class="p">Đỗ xe tiện lợi, phục vụ nhiệt tình, Quý Khách có thể hoàn toàn tin tưởng để mua sắm.</div>
                                    </div>
                                </div>
                                <div class="cua_hang_right_number d-flex mt-4">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/cua_hang_chinh_hang_icon_5f653abe67.svg" alt="" height="50px">
                                    <div class="cua_hang_right_number_text ms-3">
                                        <div class="p fw-bold">Cửa hàng chính hãng</div>
                                        <div class="p">Nhà phân phối các sản phẩm VINA, HAPPY... chính hãng.</div>
                                    </div>
                                </div>
                                <div class="cua_hang_right_number d-flex mt-4">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/giao_hang_mien_phi_icon_e1f184de65.svg" alt="" height="50px">
                                    <div class="cua_hang_right_number_text ms-3">
                                        <div class="p fw-bold">Giao hàng miễn phí</div>
                                        <div class="p">Giao hàng tận nơi và miễn phí trong phạm vi bán kính lên đến 20km.</div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="cua_hang_right_number d-flex mt-5">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/thanh_toan_tien_loi_icon_99f781c7a5.svg" alt="" height="50px">
                                    <div class="cua_hang_right_number_text ms-3">
                                        <div class="p fw-bold">Thanh toán tiện lợi</div>
                                        <div class="p">Thanh toán nhanh chóng từ tiền mặt đến thẻ ATM nội địa, VISA, MasterCard,...</div>
                                    </div>
                                </div>
                                <div class="cua_hang_right_number d-flex mt-5">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/thong_tin_san_pham_icon_163124fa49.svg" alt="" height="50px">
                                    <div class="cua_hang_right_number_text ms-3">
                                        <div class="p fw-bold">Thông tin sản phẩm</div>
                                        <div class="p">Kiểm tra thông tin sản phẩm nhanh chóng tại các cửa hàng trên toàn quốc.</div>
                                    </div>
                                </div>
                                <div class="cua_hang_right_number d-flex mt-5">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/chinh_sach_bao_hanh_icon_b1d7a5f1d5.svg" alt="" height="50px">
                                    <div class="cua_hang_right_number_text ms-3">
                                        <div class="p fw-bold">Chính sách bảo mật</div>
                                        <div class="p">Bảo mật tuyệt đối cho khách hàng của VINAFEED</div>
                                    </div>
                                </div>
                            </div>
                            <div class="p fw-bold mt-3">Hình ảnh nhận diện hệ thống phân phối VINAFEED</div>
                            <div class="row d-flex mt-3 mb-5">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/nha_phan_phoi1.jpg" alt="" height="100px" style="width: 210px;">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/nha_phan_phoi2.jpg" alt="" height="100px" style="width: 210px;">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/nha_phan_phoi3.jpg" alt="" height="100px" style="width: 210px;">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/he_thong_cua_hang/nha_phan_phoi4.jpg" alt="" height="100px" style="width: 210px;">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <%@ include file="layout/near_footer.jsp" %>
    <%@ include file="layout/footer.jsp" %>

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/call-api-address.js"></script>
</body>
</html>