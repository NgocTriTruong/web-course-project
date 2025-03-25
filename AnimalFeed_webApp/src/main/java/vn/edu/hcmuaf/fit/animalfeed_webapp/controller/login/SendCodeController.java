package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.EmailService;

import java.io.IOException;
import java.util.Random;

@WebServlet(name = "SendCodeController", value = "/send-code")
public class SendCodeController extends HttpServlet {
    private EmailService emailService = new EmailService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String code = generateVerificationCode();
        HttpSession session = request.getSession();
        session.setAttribute("verificationCode", code);
        session.setAttribute("codeExpiration", System.currentTimeMillis() + 5 * 60 * 1000); // Hết hạn sau 5 phút

        emailService.sendVerificationEmail(email, code);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}