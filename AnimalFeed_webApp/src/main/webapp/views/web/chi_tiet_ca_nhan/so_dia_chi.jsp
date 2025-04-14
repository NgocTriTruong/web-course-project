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
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .form-header { border-bottom: 1px solid #e9ecef; }
        .form-footer { border-top: 1px solid #e9ecef; }
        .suggestions {
            position: absolute;
            z-index: 1000;
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 4px;
            display: none;
        }
        .suggestions.show {
            display: block;
        }
        .suggestions .dropdown-item {
            padding: 8px 12px;
            cursor: pointer;
        }
        .suggestions .dropdown-item:hover {
            background-color: #f8f9fa;
        }
        body.modal-open {
            overflow: hidden;
        }
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
                                                <button class="btn btn-sm btn-outline-primary ms-2" onclick="editAddress(${address.id}, '${address.province}', '${address.district}', '${address.ward}', '${address.detail}', '${address.note}')">
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
                                <!-- Autocomplete cho Tỉnh -->
                                <div class="form-item mb-3 position-relative">
                                    <label for="provinceInput" class="form-label">Chọn Tỉnh:</label>
                                    <input type="text" id="provinceInput" class="form-control" placeholder="--Chọn Tỉnh--">
                                    <div id="provinceSuggestions" class="suggestions dropdown-menu w-100" style="max-height: 200px; overflow-y: auto;"></div>
                                    <input type="hidden" id="provinceCode" value="">
                                </div>
                                <!-- Autocomplete cho Huyện -->
                                <div class="form-item mb-3 position-relative">
                                    <label for="districtInput" class="form-label">Chọn Huyện:</label>
                                    <input type="text" id="districtInput" class="form-control" placeholder="--Chọn Huyện--" disabled>
                                    <div id="districtSuggestions" class="suggestions dropdown-menu w-100" style="max-height: 200px; overflow-y: auto;"></div>
                                    <input type="hidden" id="districtCode" value="">
                                </div>
                                <!-- Autocomplete cho Xã -->
                                <div class="form-item mb-3 position-relative">
                                    <label for="wardInput" class="form-label">Chọn Xã:</label>
                                    <input type="text" id="wardInput" class="form-control" placeholder="--Chọn Xã--" disabled>
                                    <div id="wardSuggestions" class="suggestions dropdown-menu w-100" style="max-height: 200px; overflow-y: auto;"></div>
                                    <input type="hidden" id="wardCode" value="">
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
        // loadProvinces() đã được gọi trong call-api-address.js, chỉ cần kiểm tra kết quả
        console.log("Waiting for provinces to load...");
        const checkProvinces = setInterval(() => {
            if (provincesData.length > 0) {
                console.log("Provinces loaded:", provincesData);
                clearInterval(checkProvinces);
            }
        }, 100);

        document.getElementById('closeAddressForm').addEventListener('click', closeAddressForm);
    });

    function openAddressForm() {
        document.getElementById('addressFormModal').style.display = 'block';
        document.body.classList.add('modal-open');
        resetForm();
    }

    function closeAddressForm() {
        document.getElementById('addressFormModal').style.display = 'none';
        document.body.classList.remove('modal-open');
        resetForm();
    }

    function resetForm() {
        document.getElementById('provinceInput').value = '';
        document.getElementById('districtInput').value = '';
        document.getElementById('districtInput').disabled = true;
        document.getElementById('wardInput').value = '';
        document.getElementById('wardInput').disabled = true;
        document.getElementById('addressDetails').value = '';
        document.getElementById('addressNote').value = '';
        document.querySelector('#addressFormModal .form-header h5').textContent = 'Thêm địa chỉ nhận hàng';
        const saveButton = document.getElementById('saveAddressButton');
        saveButton.removeEventListener('click', updateAddress);
        saveButton.addEventListener('click', saveAddress);
    }

    function saveAddress() {
        const province = document.getElementById('provinceInput').value;
        const district = document.getElementById('districtInput').value;
        const ward = document.getElementById('wardInput').value;
        const addressDetails = document.getElementById('addressDetails').value.trim();
        const addressNote = document.getElementById('addressNote').value.trim();

        if (!province || !district || !ward) {
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

    function editAddress(id, province, district, ward, detail, note) {
        openAddressForm();
        document.getElementById('provinceInput').value = province;
        document.getElementById('districtInput').value = district;
        document.getElementById('districtInput').disabled = false;
        document.getElementById('wardInput').value = ward;
        document.getElementById('wardInput').disabled = false;
        document.getElementById('addressDetails').value = detail || '';
        document.getElementById('addressNote').value = note || '';
        document.querySelector('#addressFormModal .form-header h5').textContent = 'Chỉnh sửa địa chỉ nhận hàng';
        const saveButton = document.getElementById('saveAddressButton');
        saveButton.removeEventListener('click', saveAddress);
        saveButton.addEventListener('click', function() { updateAddress(id); });

        // Tải lại dữ liệu cho autocomplete khi chỉnh sửa
        const selectedProvince = provincesData.find(p => p.name === province);
        if (selectedProvince) {
            loadDistricts(selectedProvince.code).then(() => {
                const selectedDistrict = districtsData.find(d => d.name === district);
                if (selectedDistrict) loadWards(selectedDistrict.code);
            });
        }
    }

    function updateAddress(addressId) {
        const province = document.getElementById('provinceInput').value;
        const district = document.getElementById('districtInput').value;
        const ward = document.getElementById('wardInput').value;
        const addressDetails = document.getElementById('addressDetails').value.trim();
        const addressNote = document.getElementById('addressNote').value.trim();

        if (!province || !district || !ward) {
            alert('Vui lòng chọn đầy đủ Tỉnh, Huyện và Xã');
            return;
        }

        const data = {
            id: addressId,
            province: province,
            district: district,
            ward: ward,
            detail: addressDetails,
            note: addressNote
        };

        fetch('${pageContext.request.contextPath}/update-address', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(result => {
                if (result.success) {
                    alert('Địa chỉ đã được cập nhật thành công!');
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
                    window.location.reload();
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