package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_home;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogOutAdmin", value = "/logout-admin")
public class LogOutAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xóa tất cả các thuộc tính trong session
        HttpSession session = request.getSession(false); // Lấy session nếu có, nếu không thì trả về null
        if (session != null) {
            session.invalidate(); // Hủy session và xóa tất cả các thuộc tính trong đó
        }

        // Chuyển hướng người dùng về trang login
        response.sendRedirect(request.getContextPath() + "/login"); // Chuyển hướng về trang login
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}