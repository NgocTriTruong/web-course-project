<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sổ địa chỉ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/gioi_thieu.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chi_tiet_ca_nhan/thong_tin_ca_nhan.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

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
            background-color: rgba(0, 0, 0, 0.4);
        }
        .address-form-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-header { border-bottom: 1px solid #e9ecef; }
        .form-footer { border-top: 1px solid #e9ecef; }
    </style>

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/call-api-address.js"></script>
    <script src="https://www.gstatic.com/dialogflow-console/fast/messenger/bootstrap.js?v=1"></script>
    <df-messenger intent="WELCOME" chat-title="VinaFeed_chat" agent-id="bcb3e6d9-3aac-4ea5-bfc7-87e324931264" language-code="vi"></df-messenger>
</head>
<body>
<%@ include file="../layout/header.jsp" %>
<main style="margin-top: 65px;">
    <div class="thong_tin_ca_nhan bg-light">
        <div class="container">
            <div class="row">
                <div class="col-md-3 thong_tin_left pt-5">
                    <div class="tt_left_top bg-white">
                        <div class="tt_top_user d-flex">
                            <div class="user me-3">
                                <i class="fa-solid fa-user mt-2 i_user"></i>
                            </div>
                            <div class="p mt-3">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.user}">
                                        ${sessionScope.user.fullName} <br>
                                        ${sessionScope.user.phone}
                                    </c:when>
                                    <c:otherwise>
                                        Đăng nhập để xem thông tin
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <span class="text-primary ms-5 mt-5" onclick="window.location.href='${pageContext.request.contextPath}/profile-user'" style="cursor: pointer;">xem hồ sơ</span>
                        </div>
                    </div>
                    <div class="tt_left_bottom bg-white mt-4 pt-3 pb-3">
                        <div class="tt_bottom_number d-flex mt-2">
                            <i class="me-3 fa-solid fa-key ms-3"></i>
                            <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/new-password'">Thay đổi mật khẩu của bạn</div>
                        </div>
                        <div class="tt_bottom_number d-flex mt-2">
                            <i class="me-3 fa-solid fa-box ms-3"></i>
                            <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/order-history'">Đơn hàng của tôi</div>
                        </div>
                        <div class="tt_bottom_number d-flex mt-2">
                            <i class="me-3 fa-solid fa-location-dot ms-3"></i>
                            <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/location_user'">Sổ địa chỉ nhận hàng</div>
                        </div>
                        <div class="tt_bottom_number d-flex mt-2" id="logout">
                            <i class="me-3 fa-solid fa-right-from-bracket ms-3"></i>
                            <div class="p" onclick="window.location.href='${pageContext.request.contextPath}/logout'">Đăng xuất</div>
                        </div>
                    </div>
                </div>
                <div class="col-md-8 thong_tin_right ms-5 pt-5">
                    <div class="pt-4">
                        <h4 class="text-center">Sổ địa chỉ nhận hàng</h4>
                        <c:choose>
                            <c:when test="${empty addressList}">
                                <div class="text-center">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/background/empty_address_book.png" alt="">
                                    <h6>Bạn chưa lưu địa chỉ nào</h6>
                                    <p>Cập nhật ngay để có trải nghiệm mua hàng nhanh nhất</p>
                                    <button class="btn mt-4 text-white fw-bold" style="background-color: #fcae18;" onclick="openAddressForm()">Thêm địa chỉ mới</button>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="bg-white p-3 mb-3">
                                    <c:forEach var="address" items="${addressList}">
                                        <div class="border-bottom pb-2 mb-2" id="address-${address.id}">
                                            <p>${address.detail}, ${address.ward}, ${address.district}, ${address.province}</p>
                                            <div class="note-section d-flex align-items-center">
                                                <div class="note-display" id="note-display-${address.id}">
                                                    <c:if test="${not empty address.note}">
                                                        <p class="text-muted mb-0"><small>Ghi chú: <span id="note-text-${address.id}">${address.note}</span></small></p>
                                                    </c:if>
                                                    <c:if test="${empty address.note}">
                                                        <p class="text-muted mb-0"><small>Ghi chú: <span id="note-text-${address.id}">Chưa có ghi chú</span></small></p>
                                                    </c:if>
                                                </div>
                                                <button class="btn btn-sm btn-outline-primary ms-2">
                                                    <i class="fas fa-edit"></i>
                                                </button>
                                                <button class="btn btn-sm btn-outline-danger ms-2" onclick="deleteAddress(${address.id})">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </c:forEach>
                                    <button class="btn mt-4 text-white fw-bold" style="background-color: #fcae18;" onclick="openAddressForm()">Thêm địa chỉ mới</button>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Address Form Popup -->
                    <div class="address-form" id="addressFormModal">
                        <div class="address-form-content bg-white">
                            <div class="form-header d-flex justify-content-between align-items-center p-3">
                                <h5 class="m-0">Thêm địa chỉ nhận hàng</h5>
                                <button type="button" id="closeAddressForm" class="btn-close">×</button>
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
                                <div class="form-details mb-3">
                                    <label for="addressDetails" class="form-label">Địa chỉ cụ thể:</label>
                                    <textarea class="form-control" id="addressDetails" placeholder="Nhập địa chỉ cụ thể (vd: Số nhà, đường)" rows="3"></textarea>
                                </div>
                                <div class="form-details mb-3">
                                    <label for="addressNote" class="form-label">Ghi chú:</label>
                                    <textarea class="form-control" id="addressNote" placeholder="Nhập ghi chú (vd: Nhà riêng, Cơ quan)" rows="2"></textarea>
                                </div>
                            </div>
                            <div class="form-footer p-3">
                                <button type="button" id="saveAddressButton" class="btn w-100" style="background-color: #fcae18;">Lưu địa chỉ</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="height: 90px;"></div>
    </div>
</main>
<%@ include file="../layout/near_footer.jsp" %>
<%@ include file="../layout/footer.jsp" %>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        loadProvinces();
        document.getElementById('closeAddressForm').addEventListener('click', closeAddressForm);
        document.getElementById('saveAddressButton').addEventListener('click', saveAddress);
    });

    function openAddressForm() {
        document.getElementById('addressFormModal').style.display = 'block';
    }

    function closeAddressForm() {
        document.getElementById('addressFormModal').style.display = 'none';
    }

    function saveAddress() {
        const province = document.getElementById('province').options[document.getElementById('province').selectedIndex].text;
        const district = document.getElementById('district').options[document.getElementById('district').selectedIndex].text;
        const ward = document.getElementById('ward').options[document.getElementById('ward').selectedIndex].text;
        const addressDetails = document.getElementById('addressDetails').value.trim();
        const addressNote = document.getElementById('addressNote').value.trim();

        if (!province || province.includes('--Chọn') || !district || district.includes('--Chọn') || !ward || ward.includes('--Chọn')) {
            alert('Vui lòng chọn đầy đủ Tỉnh, Huyện và Xã');
            return;
        }

        const data = {
            province: province,
            district: district,
            ward: ward,
            detail: addressDetails,
            note: addressNote
        };

        fetch('${pageContext.request.contextPath}/add-address', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert('Địa chỉ đã được lưu thành công!');
                    window.location.reload();
                } else {
                    alert('Có lỗi xảy ra, vui lòng thử lại.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Có lỗi xảy ra, vui lòng thử lại.');
            });

        closeAddressForm();
    }

    function deleteAddress(addressId) {
        if (!confirm('Bạn có chắc chắn muốn xóa địa chỉ này?')) return;

        fetch('${pageContext.request.contextPath}/delete-address', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ id: addressId })
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    window.location.reload(); // Reload trang để đồng bộ
                } else {
                    alert('Có lỗi xảy ra, vui lòng thử lại.');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Có lỗi xảy ra, vui lòng thử lại.');
            });
    }
</script>
</body>
</html>