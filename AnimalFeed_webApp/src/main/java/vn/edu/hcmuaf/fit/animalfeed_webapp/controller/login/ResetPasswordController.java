package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.EmailService;

import java.io.IOException;

@WebServlet(name = "ResetPasswordController", value = "/reset-password")
public class ResetPasswordController extends HttpServlet {
    private UserService userService;
    private EmailService emailService;

    @Override
    public void init() {
        userService = new UserService();
        emailService = new EmailService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        HttpSession session = request.getSession();
        Boolean verified = (Boolean) session.getAttribute("verified");
        String email = (String) session.getAttribute("email");

        if (verified == null || !verified || email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Bạn chưa xác minh mã hoặc phiên làm việc đã hết hạn!");
            return;
        }

        String newPassword = request.getParameter("password");

        if (newPassword == null || newPassword.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Mật khẩu không được để trống!");
            return;
        }

        if (newPassword.length() < 6) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Mật khẩu phải có ít nhất 6 ký tự!");
            return;
        }

        try {
            // Cập nhật mật khẩu
            userService.updatePassword(email, newPassword);

            // Gửi thông báo đổi mật khẩu thành công (không bắt buộc)
            try {
                emailService.sendPasswordChangedNotification(email);
            } catch (Exception e) {
                // Lỗi gửi email thông báo không ảnh hưởng đến việc đổi mật khẩu
                e.printStackTrace();
            }

            session.invalidate(); // Xóa session sau khi đổi mật khẩu
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Có lỗi xảy ra khi cập nhật mật khẩu!");
        }
    }

}