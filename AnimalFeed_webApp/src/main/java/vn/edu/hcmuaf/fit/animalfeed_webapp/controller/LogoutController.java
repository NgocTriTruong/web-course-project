package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogoutController", value = "/logout")
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session hiện tại, không tạo mới
        if (session != null) {
            session.invalidate(); // Hủy session
        }

        // Lấy URL của trang hiện tại từ Referrer
        String currentURL = request.getHeader("Referer");
        if (currentURL == null || currentURL.isEmpty()) {
            currentURL = request.getContextPath() + "/home"; // Nếu không có, chuyển về trang chủ
        }

        response.sendRedirect(currentURL); // Chuyển hướng về trang hiện tại
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}