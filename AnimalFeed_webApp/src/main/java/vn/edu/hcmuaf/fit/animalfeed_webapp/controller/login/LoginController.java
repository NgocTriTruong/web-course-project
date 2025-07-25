package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ActionLogDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    private static final String RECAPTCHA_SECRET_KEY = "6LciYeoqAAAAAB9XEXALqLD7b7H8UCZBEOy2BryL"; // Secret Key từ Google reCAPTCHA
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService(); // Khởi tạo UserService
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String gRecaptchaResponse = req.getParameter("g-recaptcha-response");

        // Kiểm tra reCAPTCHA
        if (!verifyRecaptcha(gRecaptchaResponse)) {
            req.setAttribute("error", "Xác minh reCAPTCHA thất bại, vui lòng thử lại.");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
            return;
        }

        // Xác thực người dùng bằng email
        User user = userService.login(email, password);

        if (user != null) {
            // Kiểm tra trạng thái người dùng
            if (user.getStatus() != 1) {
                req.setAttribute("error", "Tài khoản của bạn đã bị ngưng hoạt động. Vui lòng liên hệ quản trị viên.");
                req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
                return;
            }

            // Lưu thông tin người dùng vào session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("userFullName", user.getFullName()); // Lưu tên đầy đủ để hiển thị trên Dashboard
            session.setAttribute("subRole", user.getSub_role()); // Lưu sub_role để phân quyền trên JSP

            // Ghi log đăng nhập
            ActionLog actionLog = new ActionLog();
            actionLog.setUser_id(user.getId());
            actionLog.setAction_type("LOGIN");
            actionLog.setEntity_type("USER");
            actionLog.setEntity_id(user.getId());
            actionLog.setCreated_at(new Date());
            actionLog.setDescription("User " + user.getId() + " logged in");
            ActionLogDao.logAction(actionLog);

            // Chuyển hướng theo vai trò
            if (user.getRole() == 1) {
                resp.sendRedirect("dashboard"); // Admin
            } else {
                resp.sendRedirect("home"); // Người dùng thường
            }
        } else {
            req.setAttribute("error", "Email hoặc mật khẩu không đúng!");
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user != null) {
            if (user.getRole() == 1) {
                resp.sendRedirect("dashboard");
            } else {
                resp.sendRedirect("home");
            }
        } else {
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
        }
    }

    // Xác minh reCAPTCHA với Google API (giữ nguyên)
    private boolean verifyRecaptcha(String gRecaptchaResponse) {
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify";
            String params = "secret=" + RECAPTCHA_SECRET_KEY + "&response=" + gRecaptchaResponse;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            try (OutputStream out = conn.getOutputStream()) {
                out.write(params.getBytes());
            }

            Scanner scanner = new Scanner(conn.getInputStream());
            String responseBody = scanner.useDelimiter("\\A").next();
            scanner.close();

            JSONObject json = new JSONObject(responseBody);
            return json.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}