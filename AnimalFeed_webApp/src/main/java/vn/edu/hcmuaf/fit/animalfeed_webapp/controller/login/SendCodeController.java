package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.EmailService;

import java.io.IOException;
import java.util.Random;

@WebServlet(name = "SendCodeController", value = "/send-code")
public class SendCodeController extends HttpServlet {
    private EmailService emailService = new EmailService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String code = generateVerificationCode();
        request.getSession().setAttribute("verificationCode", code);

        emailService.sendVerificationEmail(email, code);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }
}