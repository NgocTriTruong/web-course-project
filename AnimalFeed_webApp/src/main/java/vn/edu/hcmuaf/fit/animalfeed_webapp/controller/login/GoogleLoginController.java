package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Logger;

@WebServlet(name = "GoogleLoginController", value = "/login-google")
public class GoogleLoginController extends HttpServlet {

    private static final String CLIENT_ID = "302393049408-s9uh3l3rrqvo83d2s5gfa0khba6h8q0p.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "https://animalsfeeds.online/login-google";
    private static final String AUTH_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";
    private static final String USERINFO_ENDPOINT = "https://www.googleapis.com/oauth2/v2/userinfo";
    private static final Logger LOGGER = Logger.getLogger(GoogleLoginController.class.getName());

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            redirectByRole(resp, user);
            return;
        }

        String code = req.getParameter("code");
        if (code == null || code.isEmpty()) {
            String authUrl = AUTH_ENDPOINT +
                    "?client_id=" + CLIENT_ID +
                    "&redirect_uri=" + REDIRECT_URI +
                    "&response_type=code" +
                    "&scope=openid%20email%20profile" +
                    "&access_type=online";
            resp.sendRedirect(authUrl);
        } else {
            String accessToken = getAccessToken(code);
            if (accessToken == null) {
                LOGGER.warning("Không lấy được access token từ Google.");
                req.setAttribute("error", "Không thể kết nối với Google. Vui lòng thử lại.");
                req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
                return;
            }

            JSONObject userInfo = getUserInfo(accessToken);
            if (userInfo == null || !userInfo.has("email")) {
                LOGGER.warning("Không lấy được thông tin người dùng từ Google.");
                req.setAttribute("error", "Không thể lấy thông tin từ Google. Vui lòng thử lại.");
                req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
                return;
            }

            String email = userInfo.getString("email");
            String fullName = userInfo.optString("name", "Google User");

            User user;
            try {
                if (!userService.isUserExistsEmail(email)) {
                    // Tạo tài khoản mới nếu email chưa tồn tại
                    user = new User();
                    user.setEmail(email); // Gán email
                    user.setFullName(fullName); // Gán tên
                    user.setRole(0); // Người dùng thường
                    user.setStatus(1); // Trạng thái hoạt động
                    user.setCreateDate(new Date());
                    user.setUpdateDate(new Date());
                    userService.registerUserWithGoogle(user);
                } else {
                    // Đăng nhập nếu email đã tồn tại
                    user = userService.loginWithGoogle(email);
                }

                session = req.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                redirectByRole(resp, user);
            } catch (Exception e) {
                LOGGER.severe("Lỗi khi xử lý đăng nhập Google: " + e.getMessage());
                req.setAttribute("error", "Đăng nhập bằng Google thất bại: " + e.getMessage());
                req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
            }
        }
    }

    private String getAccessToken(String code) {
        try {
            URL url = new URL(TOKEN_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String params = "code=" + code +
                    "&client_id=" + CLIENT_ID +
                    "&client_secret=" + CLIENT_SECRET +
                    "&redirect_uri=" + REDIRECT_URI +
                    "&grant_type=authorization_code";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(params.getBytes("UTF-8"));
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                LOGGER.warning("Lỗi khi lấy access token: HTTP " + responseCode);
                return null;
            }

            try (Scanner inStream = new Scanner(conn.getInputStream())) {
                StringBuilder response = new StringBuilder();
                while (inStream.hasNextLine()) {
                    response.append(inStream.nextLine());
                }
                JSONObject json = new JSONObject(response.toString());
                return json.optString("access_token", null);
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi lấy access token: " + e.getMessage());
            return null;
        }
    }

    private JSONObject getUserInfo(String accessToken) {
        try {
            URL url = new URL(USERINFO_ENDPOINT + "?access_token=" + accessToken);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                LOGGER.warning("Lỗi khi lấy thông tin người dùng: HTTP " + responseCode);
                return null;
            }

            try (Scanner inStream = new Scanner(conn.getInputStream())) {
                StringBuilder response = new StringBuilder();
                while (inStream.hasNextLine()) {
                    response.append(inStream.nextLine());
                }
                return new JSONObject(response.toString());
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
            return null;
        }
    }

    private void redirectByRole(HttpServletResponse resp, User user) throws IOException {
        if (user.getRole() == 1) {
            resp.sendRedirect("dashboard");
        } else {
            resp.sendRedirect("home");
        }
    }
}