package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LogOutAdmin", value = "/logout-admin")
public class LogOutAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session nếu có, nếu không thì trả về null

        if (session != null) {
            // Xóa các thuộc tính cụ thể trong session
            session.removeAttribute("user");
            session.removeAttribute("userId");
            session.removeAttribute("userFullName");
            session.removeAttribute("subRole");

            // Hủy session
            session.invalidate();
        }

        // Tạo session mới để lưu thông báo
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("message", "Bạn đã đăng xuất thành công!");

        // Chuyển hướng về trang login
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}