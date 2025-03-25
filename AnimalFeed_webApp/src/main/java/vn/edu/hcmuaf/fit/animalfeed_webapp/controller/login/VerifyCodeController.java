package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "VerifyCodeController", value = "/verify-code")
public class VerifyCodeController extends HttpServlet {
    private static final int MAX_WRONG_ATTEMPTS = 5; // Giới hạn 5 lần nhập sai
    private static final long LOCK_DURATION = 10 * 60 * 1000; // 10 phút (ms)

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userCode = request.getParameter("code");

        // Lấy số lần nhập sai hiện tại
        Integer wrongAttempts = (Integer) session.getAttribute("wrongAttempts");
        if (wrongAttempts == null) {
            wrongAttempts = 0;
        }

        // Kiểm tra khóa
        Long lockUntil = (Long) session.getAttribute("lockUntil");
        if (lockUntil != null && System.currentTimeMillis() < lockUntil) {
            long remainingTime = (lockUntil - System.currentTimeMillis()) / 1000 / 60; // phút còn lại
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Bạn đã nhập sai quá 5 lần. Trang đăng ký bị khóa trong " + remainingTime + " phút!");
            return;
        }

        // Lấy mã OTP từ session
        String storedCode = (String) session.getAttribute("verificationCode");
        Long codeExpiration = (Long) session.getAttribute("codeExpiration");

        if (storedCode == null || codeExpiration == null || System.currentTimeMillis() > codeExpiration) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Mã xác minh hết hạn hoặc không hợp lệ!");
            return;
        }

        if (!storedCode.equals(userCode)) {
            wrongAttempts++;
            session.setAttribute("wrongAttempts", wrongAttempts);

            if (wrongAttempts >= MAX_WRONG_ATTEMPTS) {
                // Khóa trang trong 10 phút
                session.setAttribute("lockUntil", System.currentTimeMillis() + LOCK_DURATION);
                // Đăng xuất (xóa session)
                session.invalidate();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Bạn đã nhập sai quá 5 lần. Trang đăng ký bị khóa trong 10 phút!");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Mã xác minh không chính xác! Còn " + (MAX_WRONG_ATTEMPTS - wrongAttempts) + " lần thử.");
            }
            return;
        }

        // Nếu mã đúng, đặt verified = true và reset số lần nhập sai
        session.setAttribute("verified", true);
        session.setAttribute("wrongAttempts", 0);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}