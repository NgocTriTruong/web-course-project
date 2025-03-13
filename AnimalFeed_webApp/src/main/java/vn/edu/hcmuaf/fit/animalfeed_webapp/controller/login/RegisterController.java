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
        // Lấy dữ liệu từ form
        String fullName = req.getParameter("username");
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String gRecaptchaResponse = req.getParameter("g-recaptcha-response");

        // Kiểm tra reCAPTCHA
        if (!verifyRecaptcha(gRecaptchaResponse)) {
            req.setAttribute("error", "Vui lòng xác nhận bạn không phải là robot!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra mật khẩu xác nhận
        if (!userService.isPasswordMatch(password, confirmPassword)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
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
        User newUser = userService.getUserByPhone(phone);
        HttpSession session = req.getSession();
        session.setAttribute("user", newUser);
        resp.sendRedirect("home");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
    }

    // Phương thức xác thực reCAPTCHA với Google
    private boolean verifyRecaptcha(String gRecaptchaResponse) {
        String secretKey = "6LciYeoqAAAAAB9XEXALqLD7b7H8UCZBEOy2BryL";  // Thay bằng Secret Key từ Google reCAPTCHA
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
