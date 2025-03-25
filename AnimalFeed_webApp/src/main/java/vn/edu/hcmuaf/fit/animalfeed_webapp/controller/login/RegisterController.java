package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet(name = "RegisterController", value = "/register")
public class RegisterController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Boolean verified = (Boolean) session.getAttribute("verified");
        Long lockUntil = (Long) session.getAttribute("lockUntil");

        // Kiểm tra khóa
        if (lockUntil != null && System.currentTimeMillis() < lockUntil) {
            long remainingTime = (lockUntil - System.currentTimeMillis()) / 1000 / 60;
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Trang đăng ký bị khóa trong " + remainingTime + " phút!");
            return;
        }

        // Kiểm tra trạng thái xác minh
        if (verified == null || !verified) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Vui lòng xác minh email trước khi đăng ký!");
            return;
        }

        // Lấy dữ liệu từ form
        String fullName = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String gRecaptchaResponse = req.getParameter("g-recaptcha-response");

        // Kiểm tra reCAPTCHA
        if (!verifyRecaptcha(gRecaptchaResponse)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Vui lòng xác nhận bạn không phải là robot!");
            return;
        }

        // Kiểm tra nếu email đã tồn tại
        if (userService.isEmailExists(email)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Email đã được đăng ký!");
            return;
        }

        // Đăng ký người dùng
        userService.registerUser(fullName, email, password);

        // Lấy người dùng từ email để đăng nhập
        User newUser = userService.getUserByEmail(email);
        session.setAttribute("user", newUser);
        session.removeAttribute("verificationCode");
        session.removeAttribute("codeExpiration");
        session.removeAttribute("verified");
        session.removeAttribute("wrongAttempts");
        session.removeAttribute("lockUntil");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long lockUntil = (Long) session.getAttribute("lockUntil");
        if (lockUntil != null && System.currentTimeMillis() < lockUntil) {
            req.setAttribute("error", "Trang đăng ký bị khóa do nhập sai mã quá 5 lần!");
        }
        req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
    }

    private boolean verifyRecaptcha(String gRecaptchaResponse) {
        String secretKey = "6LciYeoqAAAAAB9XEXALqLD7b7H8UCZBEOy2BryL";
        String url = "https://www.google.com/recaptcha/api/siteverify";

        try {
            String postParams = "secret=" + secretKey + "&response=" + gRecaptchaResponse;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(postParams.getBytes());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.getBoolean("success");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}