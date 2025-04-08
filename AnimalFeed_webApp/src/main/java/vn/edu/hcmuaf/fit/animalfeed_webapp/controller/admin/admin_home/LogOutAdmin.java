package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_home;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;

@WebServlet(name = "LogOutAdmin", value = "/logout-admin")
public class LogOutAdmin extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(); // Khởi tạo UserService
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Lấy session nếu có, nếu không thì trả về null

        if (session == null) {
            // Nếu không có session, chuyển hướng về trang login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy userId từ session để kiểm tra quyền
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // Nếu không có userId, hủy session và chuyển hướng về login
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Kiểm tra xem người dùng có phải admin không
        User user = userService.getById(userId);
        if (user == null || user.getRole() != 1) {
            // Nếu không phải admin, không cho phép truy cập URL này
            session.setAttribute("error", "Bạn không có quyền truy cập chức năng này.");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        // Xóa các thuộc tính cụ thể trong session
        session.removeAttribute("user");
        session.removeAttribute("userId");
        session.removeAttribute("userFullName");
        session.removeAttribute("subRole");

        // Hủy session
        session.invalidate();

        // Tạo session mới để lưu thông báo
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("message", "Bạn đã đăng xuất thành công!");

        // Chuyển hướng về trang login
        response.sendRedirect(request.getContextPath() + "/login");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Nếu cần xử lý đăng xuất qua POST (ví dụ: từ form), bạn có thể gọi doGet
        doGet(request, response);
    }
}