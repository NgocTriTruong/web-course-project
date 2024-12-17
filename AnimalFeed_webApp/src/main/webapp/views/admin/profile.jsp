<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="en">

<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Hồ sơ của bạn</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

    <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>

    <!-- js add header -->
     <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
</head>

<body>
<!-- Start your project here-->
<!--Main Navigation-->
<%@ include file="layout/header.jsp" %>

<!--Main Navigation-->

<!--Main layout-->
<main class="mb-5 pb-5">
    <section class="mb-5 text-center text-md-start">
        <!-- Background gradient -->
        <div class="p-5" style="height: 200px;
                                background: linear-gradient(
                                to right,
                                hsl(78, 50%, 48%),
                                hsl(78, 50%, 68%)
                                );">
        </div>
        <!-- Background gradient -->
    
        <div class="container px-4">
          <div class="card shadow-0" style="
                                            margin-top: -100px;
                                            ">
            <div class="card-body py-5 px-5">
              <div class="row gx-lg-4 align-items-center">
                <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                  <h1 class="">Thông tin cá nhân</h1>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    <!-- Container for demo purpose -->
    <div class="container mb-5">
        <section class="module">
            <form class="form-horizontal">
                <fieldset class="fieldset">
                    <div class="form-group avatar row">
                        <figure class="figure col-md-2 ms-5">
                            <img class="img-rounded img-responsive"
                                 src="/assets/images/news/news1.jpg" alt="" height="200px" width="200px" style="border-radius: 50%;">
                        </figure>
                        <div class="form-inline col-md-8">
                            <input type="file" class="file-uploader pull-left mt-5 ms-5 pt-4">
                            <button type="file" class="btn btn-sm btn-default-alt pull-left bg_green me-5 text-white fw-bold">
                                Cập Nhật Hình Ảnh
                            </button>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-md-3 col-sm-3 col-xs-12 control-label text-center h5 mt-4">Số điện thoại :</label>
                        <div class="col-md-7 col-sm-9 col-xs-12 mt-4">
                            <input type="text" class="form-control" value="0797534971" disabled>
                        </div>
                    </div>

                </fieldset>
                <fieldset class="fieldset">
                    <h3 class="fieldset-title mt-3 ms-5">Thông tin liên hệ</h3>
                    <div class="form-group row mb-3">
                        <label class="col-md-3 col-sm-3 col-xs-12 control-label text-center h5 mt-4">Họ và tên: </label>
                        <div class="col-md-7 col-sm-9 col-xs-12 mt-4">
                            <input type="text" class="form-control" value="Trương Ngọc Trí">
                        </div>
                    </div>
                    <div class="form-group row mb-3">
                        <label class="col-md-3  col-sm-3 col-xs-12 control-label text-center h5">Số điện thoại: </label>
                        <div class="col-md-7 col-sm-9 col-xs-12">
                            <input type="number" class="form-control" value="0797534971">
                        </div>
                    </div>
                </fieldset>
                <hr>
                <div class="form-group row">
                    <div class="col-md-10 col-sm-9 col-xs-12 col-md-push-2 col-sm-push-3 col-xs-push-0">
                        <button class="btn bg_green ms-5 text-white fw-bold" type="button">Cập Nhật</button>
                    </div>
                </div>
            </form>
        </section>
    </div>
    <!-- Container for demo purpose -->
</main>
<!--Main layout-->
<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer><footer></footer>

</body>
</html>
