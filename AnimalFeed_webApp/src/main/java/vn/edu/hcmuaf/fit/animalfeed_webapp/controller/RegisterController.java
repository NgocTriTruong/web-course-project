package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.UserDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy dữ liệu từ form
        String fullName = req.getParameter("username");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        // Kiểm tra mật khẩu xác nhận
        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            req.getRequestDispatcher("register").forward(req, resp);
            return;
        }

        // Kiểm tra nếu số điện thoại đã tồn tại
        UserDao userDao = new UserDao();
        User existingUser = userDao.getUserByPhone(phone);
        if (existingUser != null) {
            req.setAttribute("error", "Số điện thoại đã được đăng ký!");
            req.getRequestDispatcher("register").forward(req, resp);
            return;
        }

        // Tạo đối tượng User và lưu vào cơ sở dữ liệu
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setPhone(phone);
        newUser.setPassword(password);
        newUser.setRole(0); // Mặc định là user (role = 0)
        newUser.setStatus(1); // Mặc định là active (status = 1)
        newUser.setCreateDate(new Date());
        newUser.setUpdateDate(new Date());

        userDao.insertUser(newUser);

        // Đăng nhập ngay sau khi đăng ký thành công
        HttpSession session = req.getSession();
        session.setAttribute("user", newUser);
        resp.sendRedirect("home"); // Chuyển hướng về trang chính
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
    }
}
