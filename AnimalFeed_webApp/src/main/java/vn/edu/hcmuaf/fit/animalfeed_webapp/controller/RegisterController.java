package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.io.IOException;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(); // Khởi tạo service khi servlet được tạo
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String fullName = req.getParameter("username");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        // Kiểm tra mật khẩu xác nhận
        if (!userService.isPasswordMatch(password, confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            req.getRequestDispatcher("register").forward(req, resp);
            return;
        }

        // Kiểm tra nếu số điện thoại đã tồn tại
        if (userService.isPhoneExists(phone)) {
            req.setAttribute("error", "Số điện thoại đã được đăng ký!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        // Đăng ký người dùng
        userService.registerUser(fullName, phone, password);

        // Lấy người dùng từ số điện thoại để đăng nhập
        User newUser = userService.getUserByPhone(phone); // Lấy lại người dùng mới từ cơ sở dữ liệu
        HttpSession session = req.getSession();
        session.setAttribute("user", newUser);
        resp.sendRedirect("home"); // Chuyển hướng về trang chính
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
    }
}