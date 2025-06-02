package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Logger;

@WebServlet(name = "FacebookLoginController", value = "/login-facebook")
public class FacebookLoginController extends HttpServlet {

    private static final String CLIENT_ID = "10059135720792572";
    private static final String CLIENT_SECRET = "fca909fc5fd0f6bf1b42a8a3c5f75cb1";
    private static final String REDIRECT_URI = "https://animalsfeeds.online/login-facebook";
    private static final String AUTH_ENDPOINT = "https://www.facebook.com/v22.0/dialog/oauth";  // URL xác thực
    private static final String TOKEN_ENDPOINT = "https://graph.facebook.com/v22.0/oauth/access_token";  // URL lấy access token
    private static final String USERINFO_ENDPOINT = "https://graph.facebook.com/v22.0/me";
    private static final Logger LOGGER = Logger.getLogger(FacebookLoginController.class.getName());  // ghi log để check lỗi

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            LOGGER.info("User already logged in: " + user.getEmail());
            redirectByRole(response, user);
            return;
        }

        String code = request.getParameter("code");
        LOGGER.info("Received code: " + code);

        // Nếu không có code, chuyển hướng đến trang xác thực Facebook
        if (code == null || code.isEmpty()) {
            String authUrl = AUTH_ENDPOINT +
                    "?client_id=" + URLEncoder.encode(CLIENT_ID.trim(), StandardCharsets.UTF_8.name()) +
                    "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) +
                    "&response_type=code" +
                    "&scope=email,public_profile";
            LOGGER.info("Redirecting to auth URL: " + authUrl);
            response.sendRedirect(authUrl); // Chuyển hướng đến trang đăng nhập Facebook
        } else {
            try {
                String accessToken = getToken(code);
                if (accessToken == null) {
                    LOGGER.warning("Không thể lấy access token từ Facebook.");
                    request.setAttribute("error", "Không thể kết nối với Facebook. Vui lòng thử lại.");
                    request.getRequestDispatcher("/views/web/login.jsp").forward(request, response);
                    return;
                }

                // Lấy thông tin người dùng từ access token
                JSONObject userInfo = getUserInfo(accessToken);
                if (userInfo == null) {
                    LOGGER.warning("Không thể lấy thông tin người dùng từ Facebook.");
                    request.setAttribute("error", "Không thể lấy thông tin người dùng từ Facebook. Vui lòng thử lại.");
                    request.getRequestDispatcher("/views/web/login.jsp").forward(request, response);
                    return;
                }

                // Xử lý thông tin người dùng
                String facebookId = userInfo.getString("id");
                String email = userInfo.optString("email", facebookId + "@facebook.com"); // Fallback nếu không có email
                String fullName = userInfo.optString("name", "Facebook User");

                User user = new User();
                UserService userService = UserService.getInstance();
                LOGGER.info("Checking if user exists with email: " + email);

                if (!userService.isUserExistsEmail(email)) {
                    LOGGER.info("User does not exist, registering new user: " + email);
                    user.setEmail(email);
                    user.setFullName(fullName);
                    user.setRole(0);
                    user.setStatus(1);
                    user.setCreateDate(new Date());
                    user.setUpdateDate(new Date());
                    userService.registerUserWithFacebook(user);
                } else {
                    LOGGER.info("User exists, logging in with email: " + email);
                    user = userService.loginWithFacebook(email);
                }

                // Tạo phiên mới và lưu thông tin người dùng
                session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                session.setMaxInactiveInterval(30 * 60);   // Thời gian sống của phiên: 30 phút
                LOGGER.info("Session created with user: " + user.getEmail());
                redirectByRole(response, user);
            } catch (Exception e) {
                LOGGER.severe("Lỗi khi xử lý đăng nhập Facebook: " + e.getMessage());
                request.setAttribute("error", "Đăng nhập Facebook thất bại: " + e.getMessage());
                request.getRequestDispatcher("/views/web/login.jsp").forward(request, response);
            }
        }
    }

    private String getToken(String code) {
        try {
            // Tạo URL cho yêu cầu POST
            URL url = new URL(TOKEN_ENDPOINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            //tạo body
            String params = "client_id=" + URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8.name()) +
                    "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8.name()) +
                    "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8.name()) +
                    "&code=" + URLEncoder.encode(code, StandardCharsets.UTF_8.name());

            // Gửi body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = params.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Đọc phản hồi từ server
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    JSONObject json = new JSONObject(response.toString());
                    String accessToken = json.optString("access_token", null);
                    if (accessToken == null) {
                        LOGGER.warning("Không tìm thấy access_token trong phản hồi: " + response.toString());
                    }
                    return accessToken;
                }
            } else {
                // Đọc lỗi nếu có
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    LOGGER.severe("Lỗi khi lấy access token, mã HTTP: " + responseCode + ", phản hồi: " + errorResponse.toString());
                }
                return null;
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi lấy access token: " + e.getMessage());
            return null;
        }

    }

    private JSONObject getUserInfo(String accessToken){
        HttpURLConnection conn = null;
        try{
            // Tạo URL với các trường cần lấy (id, name, email)
            String fields = "id,name,email"; // Các trường thông tin cần lấy
            URL url = new URL(USERINFO_ENDPOINT + "?fields=" + fields + "&access_token=" + accessToken);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Đọc phản hồi
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    LOGGER.severe("Lỗi khi lấy thông tin người dùng, mã HTTP: " + responseCode + ", phản hồi: " + errorResponse.toString());
                }
                return null;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                JSONObject userInfo = new JSONObject(response.toString());
                LOGGER.info("Thông tin người dùng: " + userInfo.toString());
                return userInfo;
            }
        } catch (Exception e) {
            LOGGER.severe("Lỗi khi lấy thông tin người dùng: " + e.getMessage());
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
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