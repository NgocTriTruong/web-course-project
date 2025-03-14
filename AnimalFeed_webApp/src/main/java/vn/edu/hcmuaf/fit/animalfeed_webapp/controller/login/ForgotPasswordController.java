    package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;

    import jakarta.servlet.*;
    import jakarta.servlet.http.*;
    import jakarta.servlet.annotation.*;
    import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
    import vn.edu.hcmuaf.fit.animalfeed_webapp.services.EmailService;

    import java.io.IOException;
    import java.util.Random;

    @WebServlet(name = "ForgotPasswordController", value = "/forgot-password")
    public class ForgotPasswordController extends HttpServlet {
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
            String email = request.getParameter("email");

            // Kiểm tra email có tồn tại không
            if (email == null || email.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Vui lòng nhập email!");
                return;
            }

            if (!userService.isUserExists(email)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Email không tồn tại trong hệ thống!");
                return;
            }

            // Tạo mã xác nhận
            String verificationCode = generateVerificationCode();
            HttpSession session = request.getSession();
            session.setAttribute("verificationCode", verificationCode);
            session.setAttribute("email", email);
            session.setAttribute("codeExpiration", System.currentTimeMillis() + 300000); // 5 phút

            // Gửi mã qua email
            try {
                boolean sent = emailService.sendVerificationEmail(email, verificationCode);
                if (sent) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Lỗi gửi email! Vui lòng thử lại sau.");
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Lỗi gửi email! Vui lòng thử lại sau.");
            }
        }

        private String generateVerificationCode() {
            return String.format("%06d", new Random().nextInt(999999));
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.getRequestDispatcher("/views/web/forgot_password.jsp").forward(request, response);
        }

    }