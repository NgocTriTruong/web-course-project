    package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;

    import jakarta.servlet.*;
    import jakarta.servlet.http.*;
    import jakarta.servlet.annotation.*;

    import java.io.IOException;

    @WebServlet(name = "VerifyCodeController", value = "/verify-code")
    public class VerifyCodeController extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String userCode = request.getParameter("code");
            HttpSession session = request.getSession();

            String storedCode = (String) session.getAttribute("verificationCode");
            Long codeExpiration = (Long) session.getAttribute("codeExpiration");

            if (storedCode == null || codeExpiration == null || System.currentTimeMillis() > codeExpiration) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Mã xác minh hết hạn hoặc không hợp lệ!");
                return;
            }

            if (!storedCode.equals(userCode)) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Mã xác minh không chính xác!");
                return;
            }

            session.setAttribute("verified", true);
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
