<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thức ăn cho bò</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/each_product.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/assets/css/layout/footer.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/themify-icons/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css">

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/add_layout/add_layout.js" defer></script>
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/product/product_sale_new.js"></script>
    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/product/tab_product_all.js"></script>

    <script src="${pageContext.request.contextPath}/views/template/assets/scripts/confirm_login.js"></script>
</head>
<body>
<%@ include file="../layout/header.jsp" %>

    <main style="margin-top: 95px;" class="product bg-light">

        <div class="position-relative text-center mb-4">
            <img src="${pageContext.request.contextPath}/views/template/assets/images/background/product-main-bg.jpg" class="img-fluid w-100" alt="Thức ăn cho vịt">
            <h1 class="overlay-text">THỨC ĂN CHO BÒ</h1>
        </div>

        <div class="container">
            <div class="d-flex mb-3">
                <button class="d-flex product_button_icon ms-3" onclick="window.location.href='${pageContext.request.contextPath}/views/web/each_product/product_pig.jsp'">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-pig.png" alt="pig-icon" width="30px" height="30px">
                    <p class="ms-1 text-white">
                        Thức Ăn Cho Heo
                    </p>
                </button>
                <button class="d-flex product_button_icon " onclick="window.location.href='${pageContext.request.contextPath}/views/web/each_product/product_chicken.jsp'">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-chicken.png" alt="chicken-icon" width="30px" height="30px">
                    <p class="ms-1 text-white">
                        Thức Ăn Cho Gà
                    </p>
                </button>
                <button class="d-flex product_button_icon " onclick="window.location.href='${pageContext.request.contextPath}/views/web/each_product/product_duck.jsp'">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-duck.png" alt="duck-icon" width="30px" height="30px">
                    <p class="ms-1 text-white">
                        Thức Ăn Cho Vịt
                    </p>
                </button>
                <button class="d-flex product_button_icon " style="background-color: #fcae18;" onclick="window.location.href='${pageContext.request.contextPath}/views/web/each_product/product_cow.jsp'">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-cow.png" alt="cow-icon" width="30px" height="30px">
                    <p class="ms-1 text-white fw-bold">
                        Thức Ăn Cho Bò
                    </p>
                </button>
                <button class="d-flex product_button_icon " onclick="window.location.href='${pageContext.request.contextPath}/views/web/each_product/product_shirmp.jsp'">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-shrimp.png" alt="shrimp-icon" width="30px" height="30px">
                    <p class="ms-1 text-white">
                        Thức Ăn Cho Tôm
                    </p>
                </button>
                <button class="d-flex product_button_icon " onclick="window.location.href='${pageContext.request.contextPath}/views/web/each_product/product_fish.jsp'">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-fish.png" alt="fish-icon" width="30px" height="30px">
                    <p class="ms-1 text-white">
                        Thức Ăn Cho Cá
                    </p>
                </button>
                <button class="d-flex product_button_icon " onclick="window.location.href='${pageContext.request.contextPath}/views/web/each_product/product_goat.jsp'">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/represent-images/icon-goat.png" alt="goat-icon" width="30px" height="30px">
                    <p class="ms-1 text-white">
                        Thức Ăn Cho Dê
                    </p>
                </button>
            </div>
        </div>

        <!--product sale and new -->
        <div class="container bg-white mb-4 pb-4" style="border-radius: 20px;">
            <!-- Tab -->
            <div class="product_sale_new d-flex pt-4">
                <div id="sale-tab" class="d-flex product_sale active-tab">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/share_all/product_sale.png" alt="" width="40px" height="40px">
                    <p class="ms-2">Khuyến mãi Hot</p>
                </div>
                <div id="new-tab" class="d-flex product_new">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/share_all/product_new.png" alt="" width="40px" height="40px">
                    <p class="ms-2">Sản phẩm Mới</p>
                </div>
                <div id="best-tab" class="d-flex product_best">
                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/share_all/ban_chay.png" alt="" width="40px" height="40px">
                    <p class="ms-2">Sản phẩm Bán chạy</p>
                </div>
            </div>
            <div class="line mb-4"></div>

            <!-- Product Sections -->
            <!-- Sale -->
            <div id="sale-products">
                <div class="row">
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <a href="">
                            <div class="sale_all">
                                <div class="sale_badge">20% OFF</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">HAPPY 222</div>
                            <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                            <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                            <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="sale_badge">20% OFF</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/223_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 223</div>
                        <div class="p mb-2 text-p">Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                        <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="sale_badge">20% OFF</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/901_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 901</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                        <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="sale_badge">20% OFF</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p">Dùng cho vịt từ 22 ngày tuổi đến khi xuất bán</div>
                        <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                        <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <a href="">
                            <div class="sale_all">
                                <div class="sale_badge">20% OFF</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">HAPPY 222</div>
                            <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                            <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                            <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="sale_badge">20% OFF</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/223_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 223</div>
                        <div class="p mb-2 text-p">Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                        <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="sale_badge">20% OFF</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/901_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 901</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                        <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="sale_badge">20% OFF</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p">Dùng cho vịt từ 22 ngày tuổi đến khi xuất bán</div>
                        <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                        <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div id="more_product_sale" class="more_product_sale">
                    <div class="row">
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <a href="">
                                <div class="sale_all">
                                    <div class="sale_badge">20% OFF</div>
                                </div>
                                <div class="product-img">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                                </div>
                                <div class="h5 text-h">HAPPY 222</div>
                                <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                                <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                                <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                                <div class="p text-start ms-3">Đã bán 1,1k</div>
                                <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                    <i class="fa-solid fa-truck mt-1"></i>
                                    <p class="ms-2">2 -4 ngày</p>
                                </div>
                            </a>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="sale_badge">20% OFF</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/223_Font.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">HAPPY 223</div>
                            <div class="p mb-2 text-p">Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                            <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                            <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="sale_badge">20% OFF</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/901_Font.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">VINA 901</div>
                            <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                            <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                            <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="sale_badge">20% OFF</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">VINA 910</div>
                            <div class="p mb-2 text-p">Dùng cho vịt từ 22 ngày tuổi đến khi xuất bán</div>
                            <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                            <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <a href="">
                                <div class="sale_all">
                                    <div class="sale_badge">20% OFF</div>
                                </div>
                                <div class="product-img">
                                    <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                                </div>
                                <div class="h5 text-h">HAPPY 222</div>
                                <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                                <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                                <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                                <div class="p text-start ms-3">Đã bán 1,1k</div>
                                <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                    <i class="fa-solid fa-truck mt-1"></i>
                                    <p class="ms-2">2 -4 ngày</p>
                                </div>
                            </a>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="sale_badge">20% OFF</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/223_Font.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">HAPPY 223</div>
                            <div class="p mb-2 text-p">Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                            <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                            <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="sale_badge">20% OFF</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/901_Font.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">VINA 901</div>
                            <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                            <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                            <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="sale_badge">20% OFF</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">VINA 910</div>
                            <div class="p mb-2 text-p">Dùng cho vịt từ 22 ngày tuổi đến khi xuất bán</div>
                            <div class="p text-start ms-3 text-secondary price_sale" style="font-size: 18px;"><del> 400.000 <u>đ</u></del><span style="color: red; font-size: 14px; margin-left: 5px;">-20%</span></div>
                            <div class="h4 text-start ms-3" style="color: red; margin-top: -2px;">320.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                    </div>
                </div>
                <a href="javascript:void(0)" id="toggle-product-sale" class="toggle-button-sale text-center mt-2">Xem tất cả <i class="fa-solid fa-chevron-down ms-2"></i></a>
            </div>

            <!-- New -->
            <div id="new-products" style="display: none;">
                <div class="row">
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="new_badge">New</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="new_badge">New</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a61---25kg---13.02.23.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 61</div>
                        <div class="p mb-2 text-p">Dùng cho bò vỗ béo</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="new_badge">New</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B60.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B60</div>
                        <div class="p mb-2 text-p">Dùng cho bò thịt vỗ béo trên 6 tháng tuổi</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="new_badge">New</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B62S.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B62S</div>
                        <div class="p mb-2 text-p">Dùng cho bò thịt cao sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="new_badge">New</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="new_badge">New</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B77.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B77</div>
                        <div class="p mb-2 text-p">Dùng cho bò thịt cao sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="new_badge">New</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/br_MGB_print-34.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">5305</div>
                        <div class="p mb-2 text-p">Dùng cho bò con từ 0 đến 4 tháng tuổi</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="sale_all">
                            <div class="new_badge">New</div>
                        </div>
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/br_MGB_print-35.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">555</div>
                        <div class="p mb-2 text-p">Dùng cho bò thịt, bò vỗ béo</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div id="more_product_new" class="more_product_sale">
                    <div class="row">
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="new_badge">New</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/br_MGB_print-39.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">5605</div>
                            <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="new_badge">New</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/hp60.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">HP 60</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt, bò vỗ béo</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="new_badge">New</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/hp62-1.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">HP 62M</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt, bò vỗ béo</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="new_badge">New</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/hp62.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">HP 62</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="new_badge">New</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">TPD B75</div>
                            <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="new_badge">New</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B77.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">TPD B77</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt cao sản</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="new_badge">New</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/br_MGB_print-34.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">5305</div>
                            <div class="p mb-2 text-p">Dùng cho bò con từ 0 đến 4 tháng tuổi</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="sale_all">
                                <div class="new_badge">New</div>
                            </div>
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/br_MGB_print-35.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">555</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt, bò vỗ béo</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                    </div>
                </div>
                <a href="javascript:void(0)" id="toggle-product-new" class="toggle-button-sale text-center mt-2">Xem tất cả <i class="fa-solid fa-chevron-down ms-2"></i></a>
            </div>

            <!-- Ban chay -->
            <div id="best-products" style="display: none;">
                <div class="row">
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a61---25kg---13.02.23.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 61</div>
                        <div class="p mb-2 text-p">Dùng cho bò vỗ béo</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B60.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B60</div>
                        <div class="p mb-2 text-p">Dùng cho bò thịt vỗ béo trên 6 tháng tuổi</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B62S.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B62S</div>
                        <div class="p mb-2 text-p">Dùng cho bò thịt cao sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a61---25kg---13.02.23.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 61</div>
                        <div class="p mb-2 text-p">Dùng cho bò vỗ béo</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B60.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B60</div>
                        <div class="p mb-2 text-p">Dùng cho bò thịt vỗ béo trên 6 tháng tuổi</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-light text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B62S.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B62S</div>
                        <div class="p mb-2 text-p">Dùng cho bò thịt cao sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div id="more_product_best" class="more_product_sale">
                    <div class="row">
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">ANOVA 60</div>
                            <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a61---25kg---13.02.23.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">ANOVA 61</div>
                            <div class="p mb-2 text-p">Dùng cho bò vỗ béo</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B60.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">TPD B60</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt vỗ béo trên 6 tháng tuổi</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B62S.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">TPD B62S</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt cao sản</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">ANOVA 60</div>
                            <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a61---25kg---13.02.23.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">ANOVA 61</div>
                            <div class="p mb-2 text-p">Dùng cho bò vỗ béo</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B60.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">TPD B60</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt vỗ béo trên 6 tháng tuổi</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                        <div class="col-md-3 bg-light text-center m-3 col product_number">
                            <div class="product-img">
                                <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B62S.png" alt="TOP 01" height="250px" width="200px">
                            </div>
                            <div class="h5 text-h">TPD B62S</div>
                            <div class="p mb-2 text-p">Dùng cho bò thịt cao sản</div>
                            <div class="h4 text-start ms-3" style="color: red;">420.000 <u>đ</u></div>
                            <div class="p text-start ms-3">Đã bán 1,1k</div>
                            <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                                <i class="fa-solid fa-truck mt-1"></i>
                                <p class="ms-2">2 -4 ngày</p>
                            </div>
                        </div>
                    </div>
                </div>
                <a href="javascript:void(0)" id="toggle-product-best" class="toggle-button-sale text-center mt-2">Xem tất cả <i class="fa-solid fa-chevron-down ms-2"></i></a>
            </div>
        </div>
        
        <!-- Filter -->
        <div class="containers my-4">
            <div class="row justify-content-center mb-3 pt-3">
                <div class="col-md-3">
                    <select id="group" class="form-select" style="height: 50px; font-size: 20px;">
                        <option value="" selected>Nhóm sản phẩm</option>
                        <option value="Bò thịt">Thức ăn cho bò thịt</option>
                        <option value="Bò sữa">Thức ăn cho bò sữa</option>
                        <option value="Bò con">Thức ăn cho bò con</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <select id="brand" class="form-select" style="height: 50px; font-size: 20px;">
                        <option value="" selected>Thương Hiệu</option>
                        <option value="Vina">Vina</option>
                        <option value="Happy">Happy</option>
                        <option value="TPD">Tân Phương Đông</option>
                        <option value="HP">HPFood</option>
                        <option value="Anova">Anova</option>
                        <option value="Kyodo">Kyodo</option>
                    </select>
                </div>
                <div class="col-md-3">
                    <select  class="form-select" id="" style="height: 50px; font-size: 20px;">
                        <option value="" selected>Sắp xếp giá</option>
                        <option value="">Tăng dần</option>
                        <option value="">Giảm dần</option>
                    </select>
                </div>
            </div>
        </div>

        <!-- product all -->
        <div class="container">
            <!--product one -->
            <div id="product_all_one">
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
            </div>

            <!--product two -->
            <div id="product_all_two" style="display: none;">
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
            </div>

            <!--product three -->
            <div id="product_all_three" style="display: none;">
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/222_1.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">HAPPY 222</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/910_Font.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">VINA 910</div>
                        <div class="p mb-2 text-p"> Dùng cho bò Lai Sind, bò thịt, bò vỗ béo, bê</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/a60---25kg---05.09.22.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">ANOVA 60</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                    <div class="col-md-3 bg-white text-center m-3 col product_number">
                        <div class="product-img">
                            <img src="${pageContext.request.contextPath}/views/template/assets/images/product/cow/B75.png" alt="TOP 01" height="250px" width="200px">
                        </div>
                        <div class="h5 text-h">TPD B75</div>
                        <div class="p mb-2 text-p">Dùng cho bò sữa, bò sinh sản</div>
                        <div class="h4 text-start ms-3" style="color: red;">400.000 <u>đ</u></div>
                        <div class="p text-start ms-3">Đã bán 1,1k</div>
                        <div class="d-flex text-start ms-3 mt-2" style="color: #198754;">
                            <i class="fa-solid fa-truck mt-1"></i>
                            <p class="ms-2">2 -4 ngày</p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Tab -->
            <div class="d-flex pt-4 product-tab">
                <div id="product-tab-one" class="tab product-active">
                    <h4>1</h4>
                </div>
                <div id="product-tab-two" class="tab">
                    <h4>2</h4>
                </div>
                <div id="product-tab-three" class="tab">
                    <h4>3</h4>
                </div>
            </div>
        </div>

        <!-- Text -->
        <div class="container mt-5">
            <div class="p text-center fw-bold mb-4" style="font-size: 22px;">VINAFEED GROUP LUÔN HIỂU RÕ NHU CẦU DINH DƯỠNG CHO BÒ</div>
            <img src="${pageContext.request.contextPath}/views/template/assets/images/gioi_thieu/nha_may_sx.png" alt="" height="500px" width="100%" style="border-radius: 10px;">
            <div class="p mt-4" style="text-indent: 30px;">
                Nâng cao hiệu quả kinh tế và giá trị chăn nuôi từ đàn bò của mình, luôn là mong muốn của toàn thể người chăn nuôi. Để đàn bò của mình phát triển toàn diện, và cho năng suất cao thì
                người chăn nuôi cần nắm rõ và vận dụng nhiều yếu tố hỗ trợ trong chăn nuôi bò. Những yếu tố như: <span class="fw-bold">Thức ăn cho bò</span>, kỹ thuật và sức khỏe vật nuôi đều góp phần tạo nên kết quả đó.
                <div class="mt-1" style="text-indent: 30px;">
                    <span class="fw-bold" style="text-indent: 30px;">Vinafeed Group</span> luôn cung cấp những giải pháp tốt nhất hỗ trợ người chăn nuôi trong từng giai đoạn. Đặc biệt, những giải pháp thông qua thức ăn XANH - SẠCH sẽ đảm bảo trại bò của người chăn nuôi đạt được kết quả toàn diện nhất.
                </div>
            </div>
            <div class="p mt-3 fw-bold" style="font-size: 22px;">Thức ăn chăn nuôi dành cho bò qua từng giai đoạn</div>
            <div id="more_text_product" class="more_text_product">
                <div class="p mt-2" style="text-indent: 30px;">
                    Hiểu rõ được nhu cầu dinh dưỡng thiết yếu cho từng giai đoạn sinh trưởng của bò, Vinafeed Group đã nghiên cứu, phát triển và sản xuất các dòng thức ăn chăn nuôi bò riêng biệt cho từng giai đoạn:
                    <p style="text-indent: 50px;">
                        - Thức ăn cho bò thịt.
                    </p>
                    <p style="text-indent: 50px;">
                        - Thức ăn cho bò sữa.
                    </p>
                </div>
                <div class="p mt-3 fw-bold mb-3" style="font-size: 22px;">Thức ăn chăn nuôi bò XANH - SẠCH</div>
                <img src="${pageContext.request.contextPath}/views/template/assets/images/banner/Home_Banner_Web.jpg" alt="" width="70%" height="500px" style="margin-left: 200px; border-radius: 10px;">
                <p class="mt-3" style="text-indent: 30px;">XANH - SẠCH là yếu tố chủ chốt và là kim chỉ nam trong suốt gần 30 năm sản xuất thức ăn chăn nuôi bò của Vinafeed Group. </p>
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
                    Thông qua những yêu cầu hỗ trợ mỗi ngày của người chăn nuôi, Vinafeed Group mỗi ngày đều nhận và giải đáp cho hàng nghìn chủ trang trại bò gửi
                    về trên khắp cả nước. Bên cạnh đó, đội ngũ kỹ thuật định kỳ đến thăm hàng trăm trang trại để hỗ trợ trực tiếp cho người chăn nuôi.
                    <p style="text-indent: 30px;">
                        Các chuyên gia của Vinafeed Group đã và đang làm việc liên tục để nắm bắt xu thế chăn nuôi hiện đại. Cũng như sự ảnh hưởng của thay đổi môi trường đến vật nuôi.
                        Từ đó cung cấp những kiến thức và kinh nghiệm thực tiễn về mối liên kết của thức ăn chăn nuôi bò và cách chăn nuôi.
                    </p>
                </div>
                <img src="${pageContext.request.contextPath}/views/template/assets/images/thu_vien/Cham_soc_ga.jpg" alt="Chăm sóc bò" width="100%" height="500px" style="border-radius: 10px;">
                <div class="p mt-3 fw-bold mb-3" style="font-size: 22px;">Tư vấn dinh dưỡng vật nuôi</div>
                <div class="p mt-2" style="text-indent: 30px;">
                    Tất cả sản phẩm thức ăn chăn nuôi bò của Vinafeed Group đều hướng tới mục tiêu duy nhất là ĐẢM BẢO SỨC KHỎE VẬT NUÔI và GIA TĂNG HIỆU QUẢ KINH TẾ BỀN VỮNG cho trang trại. Cùng với đó là những kiến thức về dinh dưỡng vật nuôi từ chuyên gia tập đoàn mà Vinafeed Group
                    cung cấp đến người chăn nuôi bò. Hơn thế, mạng lưới vận chuyển và cung cấp sản phẩm rộng khắp toàn quốc hoàn toàn đáp ứng được nhu cầu của từng người chăn nuôi mọi miền đất nước.
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
    
</body>
</html>