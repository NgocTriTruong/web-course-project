package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.HelloServlet;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.AuthService;

import java.io.IOException;


@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HelloServlet {

    private AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        User user = authService.login(phone, password);

        if (user != null) {
            // Kiểm tra phân quyền nếu cần thiết
            if (authService.hasPermission(user, "ADMIN")) {
                // Chuyển đến trang quản trị nếu là admin
                response.sendRedirect("/admin/dashboard");
            } else if (authService.hasPermission(user, "USER")) {
                // Chuyển đến trang chính nếu là người dùng bình thường
                response.sendRedirect("/views/web/index.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền truy cập");
            }
        } else {
            // Nếu không đăng nhập được
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Thông tin đăng nhập không chính xác");
        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException {

    }
}
