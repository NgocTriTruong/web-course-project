<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liên_hệ</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/gioi_thieu.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/chi_tiet_product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/ho_tro.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">


    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>

    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    
  <script src="${pageContext.request.contextPath}/views/template/scripts/confirm_login.js"></script>

</head>
<body>

<%@ include file="layout/header.jsp" %>

<main style="margin: 90px auto;">
    <div class="text-center ho_tro">
        <div class="background-section">
            <h1 class="outline">THÔNG TIN LIÊN HỆ</h1>
        </div>
        <div class="h1 mt-4">CÔNG TY TNHH VINA</div>
        <div class="p mt-3 text-center w-75 m-auto">
            Sau đây là tất cả các thông tin liên lạc cần thiết và bản đồ đi đến VINA GROUP.
            Nếu bạn có thắc mắc, hãy liên hệ nhanh với chúng tôi qua form bên dưới hoặc liên hệ nhanh qua điện thoại, chúng tôi luôn sẵn sàng hỗ trợ bạn.
        </div>
        <div class="row">
            <div class="col-md-6" style="margin: 0 auto;">
                <div class="content_bg bg-light">
                    <div class="content d-flex between mt-4">
                        <div class="h3" style="color: #94b83d;">VINA MIỀN BẮC</div>
                        <div class="line_white"></div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="ti-location-pin ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Địa chỉ: </span>64 Quốc lộ 51, Phường Phước Tân, TP. Biên Hòa, Tỉnh Đồng Nai</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="fa-solid fa-phone-volume ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Điện thoại: </span>(+84) 251 3930 601 - (+84) 251 3930 684 - 0933 507 933</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="fa-solid fa-fax ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Fax: </span>(+84) 251 3930 403</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="ti-email ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Email: </span>info@vinafeed.com.vn</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <a href="https://www.google.com/maps/place/64+QL51,+Ph%C6%B0%E1%BB%9Bc+T%C3%A2n,+Bi%C3%AAn+H%C3%B2a,+%C4%90%E1%BB%93ng+Nai,+Vi%E1%BB%87t+Nam/@10.8889536,106.8947578,17z/data=!4m15!1m8!3m7!1s0x3174dfc258ff4829:0x46d3762dd9448366!2zNjQgUUw1MSwgUGjGsOG7m2MgVMOibiwgQmnDqm4gSMOyYSwgxJDhu5NuZyBOYWksIFZp4buHdCBOYW0!3b1!8m2!3d10.8889536!4d106.8973327!16s%2Fg%2F11kb0mykm_!3m5!1s0x3174dfc258ff4829:0x46d3762dd9448366!8m2!3d10.8889536!4d106.8973327!16s%2Fg%2F11kb0mykm_?hl=vi-VN&entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%3D%3D" target="_blank" class="d-flex">
                            <i class="fa-solid fa-map ms-3" style="line-height: 45px;"></i>
                            <div class="p ms-3"><span class="fw-bold">Bản đồ </span></div>
                        </a>
                    </div>
                </div>

                <div class="content_bg bg-light mt-4">
                    <div class="content d-flex between mt-4">
                        <div class="h3" style="color: #fcae18;">VINA MIỀN TRUNG</div>
                        <div class="line_white"></div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="ti-location-pin ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Địa chỉ: </span>64 Quốc lộ 51, Phường Phước Tân, TP. Biên Hòa, Tỉnh Đồng Nai</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="fa-solid fa-phone-volume ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Điện thoại: </span>(+84) 251 3930 601 - (+84) 251 3930 684 - 0933 507 933</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="fa-solid fa-fax ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Fax: </span>(+84) 251 3930 403</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="ti-email ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Email: </span>info@vinafeed.com.vn</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <a href="https://www.google.com/maps/place/64+QL51,+Ph%C6%B0%E1%BB%9Bc+T%C3%A2n,+Bi%C3%AAn+H%C3%B2a,+%C4%90%E1%BB%93ng+Nai,+Vi%E1%BB%87t+Nam/@10.8889536,106.8947578,17z/data=!4m15!1m8!3m7!1s0x3174dfc258ff4829:0x46d3762dd9448366!2zNjQgUUw1MSwgUGjGsOG7m2MgVMOibiwgQmnDqm4gSMOyYSwgxJDhu5NuZyBOYWksIFZp4buHdCBOYW0!3b1!8m2!3d10.8889536!4d106.8973327!16s%2Fg%2F11kb0mykm_!3m5!1s0x3174dfc258ff4829:0x46d3762dd9448366!8m2!3d10.8889536!4d106.8973327!16s%2Fg%2F11kb0mykm_?hl=vi-VN&entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%3D%3D" target="_blank" class="d-flex">
                            <i class="fa-solid fa-map ms-3" style="line-height: 45px;"></i>
                            <div class="p ms-3"><span class="fw-bold">Bản đồ </span></div>
                        </a>
                    </div>
                </div>

                <div class="content_bg bg-light mt-4">
                    <div class="content d-flex between mt-4">
                        <div class="h3" style="color: #b83d3d;">VINA MIỀN NAM</div>
                        <div class="line_white"></div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="ti-location-pin ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Địa chỉ: </span>64 Quốc lộ 51, Phường Phước Tân, TP. Biên Hòa, Tỉnh Đồng Nai</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="fa-solid fa-phone-volume ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Điện thoại: </span>(+84) 251 3930 601 - (+84) 251 3930 684 - 0933 507 933</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="fa-solid fa-fax ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Fax: </span>(+84) 251 3930 403</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <i class="ti-email ms-3" style="line-height: 45px;"></i>
                        <div class="p ms-3"><span class="fw-bold">Email: </span>info@vinafeed.com.vn</div>
                    </div>
                    <div class="line_gray ms-4 me-4"></div>
                    <div class="number_lien_he d-flex">
                        <a href="https://www.google.com/maps/place/64+QL51,+Ph%C6%B0%E1%BB%9Bc+T%C3%A2n,+Bi%C3%AAn+H%C3%B2a,+%C4%90%E1%BB%93ng+Nai,+Vi%E1%BB%87t+Nam/@10.8889536,106.8947578,17z/data=!4m15!1m8!3m7!1s0x3174dfc258ff4829:0x46d3762dd9448366!2zNjQgUUw1MSwgUGjGsOG7m2MgVMOibiwgQmnDqm4gSMOyYSwgxJDhu5NuZyBOYWksIFZp4buHdCBOYW0!3b1!8m2!3d10.8889536!4d106.8973327!16s%2Fg%2F11kb0mykm_!3m5!1s0x3174dfc258ff4829:0x46d3762dd9448366!8m2!3d10.8889536!4d106.8973327!16s%2Fg%2F11kb0mykm_?hl=vi-VN&entry=ttu&g_ep=EgoyMDI0MTEyNC4xIKXMDSoASAFQAw%3D%3D" target="_blank" class="d-flex">
                            <i class="fa-solid fa-map ms-3" style="line-height: 45px;"></i>
                            <div class="p ms-3"><span class="fw-bold">Bản đồ </span></div>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-md-5" style="margin: 0 auto;">
                <div class="container mt-5">
                    <div class="row justify-content-center">
                        <div class="card shadow-sm">
                            <div class="card-body">
                                <h2 class="text-center mb-4">Gửi tin nhắn</h2>
                                <form>
                                    <!-- Họ tên -->
                                    <div class="mb-3">
                                        <input type="text" class="form-control" id="name" placeholder="Nhập họ tên *">
                                    </div>
                                    <!-- Điện thoại -->
                                    <div class="mb-3">
                                        <input type="text" class="form-control" id="phone" placeholder="Nhập số điện thoại *">
                                    </div>
                                    <!-- Email -->
                                    <div class="mb-3">
                                        <input type="email" class="form-control" id="email" placeholder="Nhập email">
                                    </div>
                                    <!-- Địa chỉ -->
                                    <div class="mb-3">
                                        <input type="text" class="form-control" id="address" placeholder="Nhập địa chỉ">
                                    </div>
                                    <!-- Nội dung -->
                                    <div class="mb-3">
                                        <textarea class="form-control" id="message" rows="4" placeholder="Nhập nội dung *"></textarea>
                                    </div>
                                    <!-- reCAPTCHA -->
                                    <div class="mb-3">
                                        <div class="g-recaptcha" data-sitekey="your-site-key"></div>
                                    </div>
                                    <!-- Gửi -->
                                    <div class="text-center">
                                        <button type="submit" class="btn px-5" style="background-color: #fcae18;">Gửi</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

<%@ include file="layout/footer.jsp" %>
</body>
</html>