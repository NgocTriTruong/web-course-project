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

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");

        UserDao userDao = new UserDao();
        User user = userDao.login(phone, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user); // Lưu thông tin người dùng vào session

            // Chuyển hướng tới trang chính (dashboard)
            if (user.getRole() == 1) {
                resp.sendRedirect("views/admin/home.jsp"); // Admin được chuyển đến trang quản trị
            } else {
                resp.sendRedirect("index.jsp"); // User được chuyển đến trang người dùng
            }
        } else {
            req.setAttribute("error", "Số điện thoại hoặc mật khẩu không đúng!");
            req.getRequestDispatcher("/views/web/login.jsp#rs").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/web/login.jsp#lo").forward(req, resp);
    }
}
