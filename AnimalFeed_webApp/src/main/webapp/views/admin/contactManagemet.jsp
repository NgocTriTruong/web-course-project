<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>Quản lý liên hệ</title>
    <!-- Bootstrap CSS -->
    <link href="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/views/template/bootstrap/bootstrap.bundle.min.js"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/template/fonts/fontawesome-free-6.6.0-web/css/all.min.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/mdb.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/home.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/views/admin/assets/css/header.css">

    <script src="${pageContext.request.contextPath}/views/admin/assets/js/mdb.min.js"></script>

    <script src="${pageContext.request.contextPath}/views/admin/assets/js/add_header.js" defer></script>
</head>

<body>
<%@ include file="layout/header.jsp" %>

<main class="mb-5">
    <section class="mb-5 text-center text-md-start">
        <div class="p-5" style="height: 200px; background: linear-gradient(to right, hsl(78, 50%, 48%), hsl(78, 50%, 68%));"></div>
        <div class="container px-4">
            <div class="card shadow-0" style="margin-top: -100px;">
                <div class="card-body py-5 px-5">
                    <div class="row gx-lg-4 align-items-center">
                        <div class="col-lg-6 mb-4 mb-lg-0 text-center text-lg-start">
                            <h1>Quản lý liên hệ</h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <c:if test="${not empty error}">
        <div class="container px-4">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="container px-4">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${success}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </div>
    </c:if>

    <div class="container px-4 mb-5">
        <div class="datatable">
            <table class="table align-middle mb-0 bg-white">
                <thead class="bg-light">
                <tr class="h6">
                    <th>Số thứ tự</th>
                    <th>Người liên hệ</th>
                    <th>Số điện thoại</th>
                    <th>Email</th>
                    <th>Địa chỉ</th>
                    <th>Nội dung</th>
                    <th>Hành động</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="contact" items="${contacts}">
                    <tr>
                        <td><span class="h6 ms-2">${contact.id}</span></td>
                        <td><p class="fw-bold mb-1 h6">${contact.contact_user}</p></td>
                        <td><span class="ms-1 h6">${contact.phone}</span></td>
                        <td><p class="fw-normal mb-1 h6">${contact.email}</p></td>
                        <td><span class="h6">${contact.address}</span></td>
                        <td><span class="h6">${contact.content}</span></td>
                        <td>
                            <div class="justify-content-center">
                                <button type="button" class="btn bg_yellow btn-floating ms-3" onclick="deleteContact(${contact.id})">
                                    <i class="far fa-trash-can"></i>
                                </button>
                                <button type="button" class="btn bg_green btn-floating ms-3" onclick="prepareReply('${contact.email}')">
                                    <i class="far fa-envelope"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div class="container px-4" style="margin-bottom: 150px">
        <div class="mb-3 bg_green p-2 w-50">
            <span class="fw-bold h4">Trả lời tin nhắn</span>
        </div>
        <form id="replyForm" class="border p-2 w-50" onsubmit="sendEmail(event)">
            <div class="row">
                <div class="col-md-11">
                    <div class="mb-3">
                        <label for="email" class="form-label"><b>Gmail</b></label>
                        <input type="email" class="form-control" id="email" name="email" placeholder="Nhập gmail..." required readonly>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-11">
                    <div class="mb-3">
                        <label for="subject" class="form-label"><b>Chủ đề</b></label>
                        <input type="text" class="form-control" id="subject" name="subject" placeholder="Nhập chủ đề..." required>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-11">
                    <div class="mb-3">
                        <label class="form-label"><b>Nội dung</b></label>
                        <textarea class="form-control" id="content" name="content" rows="7" placeholder="Nội dung..." required></textarea>
                    </div>
                </div>
            </div>

            <button type="submit" class="btn bg_green fw-bold h5">Trả lời</button>
        </form>
    </div>
</main>

<footer class="bottom-0 w-100 text-center py-2 bg-light">
    <p class="pt-3" style="color: rgba(0, 0, 0, 0.5); margin-left: 150px;">©2024 Group-11</p>
</footer>

<script>
    function prepareReply(email) {
        document.getElementById('email').value = email;
        document.getElementById('subject').value = '';
        document.getElementById('content').value = '';
        // Scroll to reply form
        document.getElementById('replyForm').scrollIntoView({ behavior: 'smooth' });
    }

    function sendEmail(event) {
        event.preventDefault();

        const submitBtn = event.target.querySelector('button[type="submit"]');
        submitBtn.disabled = true;
        submitBtn.innerHTML = 'Đang gửi...';

        const form = document.getElementById('replyForm');
        const formData = new FormData(form);
        formData.append('action', 'sendEmail'); // Đảm bảo action là sendEmail

        fetch('${pageContext.request.contextPath}/contact-admin', {
            method: 'POST',
            body: formData,
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(response => {
                // Kiểm tra xem server có trả về mã lỗi không
                if (!response.ok) {
                    throw new Error('Server lỗi: ' + response.status);
                }
                // Kiểm tra xem phản hồi có phải là JSON không
                return response.json();
            })
            .then(data => {
                if (data.status === 'success') {
                    alert(data.message);
                    form.reset();
                } else {
                    alert('Lỗi: ' + data.message);
                }
            })
            .catch(error => {
                alert('Có lỗi xảy ra: ' + error.message);
            })
            .finally(() => {
                submitBtn.disabled = false;
                submitBtn.innerHTML = 'Trả lời';
            });
    }

</script>
</body>
</html>
