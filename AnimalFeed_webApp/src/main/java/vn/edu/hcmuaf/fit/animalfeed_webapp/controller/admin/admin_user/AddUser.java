package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.EmailService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

@WebServlet(name = "AddUser", value = "/addUser")
public class AddUser extends HttpServlet {

    private final UserService userService;
    private final EmailService emailService;

    public AddUser() {
        this.userService = new UserService();
        this.emailService = new EmailService();
    }

    @Override
    protected   void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/userAddition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String step = request.getParameter("step");
        HttpSession session = request.getSession();

        if ("sendOtp".equals(step)) {
            // Bước 1: Gửi mã OTP
            String fullName = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String roleStr = request.getParameter("role");
            String statusStr = request.getParameter("status");

            try {
                // Kiểm tra các trường bắt buộc
                if (fullName == null || fullName.trim().isEmpty()) {
                    throw new IllegalArgumentException("Họ và tên không được để trống.");
                }
                if (phone == null || phone.trim().isEmpty()) {
                    throw new IllegalArgumentException("Số điện thoại không được để trống.");
                }
                if (password == null || password.trim().isEmpty()) {
                    throw new IllegalArgumentException("Mật khẩu không được để trống.");
                }
                if (email == null || email.trim().isEmpty()) {
                    throw new IllegalArgumentException("Email không được để trống.");
                }
                if (roleStr == null || statusStr == null) {
                    throw new IllegalArgumentException("Vai trò và trạng thái là bắt buộc.");
                }

                int role = Integer.parseInt(roleStr);
                int status = Integer.parseInt(statusStr);

                if (status != 1 && status != 2) {
                    throw new IllegalArgumentException("Trạng thái không hợp lệ.");
                }

                if (!userService.isPasswordStrong(password)) {
                    throw new IllegalArgumentException("Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.");
                }

                if (userService.isEmailExists(email)) {
                    throw new IllegalArgumentException("Email đã tồn tại.");
                }

                User currentUser = (User) session.getAttribute("user");
                if (currentUser == null || currentUser.getRole() != 1) {
                    throw new SecurityException("Bạn không có quyền thực hiện thao tác này.");
                }

                // Lưu thông tin người dùng vào session
                User newUser = new User();
                newUser.setFullName(fullName);
                newUser.setPhone(phone);
                newUser.setPassword(password);
                newUser.setEmail(email);
                newUser.setRole(role);
                newUser.setStatus(status);
                newUser.setCreateDate(new Date());
                newUser.setUpdateDate(new Date());

                session.setAttribute("newUser", newUser);
                session.setAttribute("adminUserId", currentUser.getId());

                // Tạo mã OTP ngẫu nhiên
                String otp = String.valueOf(new Random().nextInt(900000) + 100000); // Mã OTP 6 chữ số

                // Gửi mã OTP qua email
                boolean emailSent = emailService.sendVerificationEmail(email, otp);
                if (!emailSent) {
                    throw new RuntimeException("Không thể gửi mã OTP qua email. Vui lòng thử lại.");
                }

                // Lưu mã OTP và thời gian hết hạn vào session
                session.setAttribute("otp", otp);
                session.setAttribute("otpExpiration", System.currentTimeMillis() + 5 * 60 * 1000); // OTP hết hạn sau 5 phút

                // Chuyển hướng đến bước xác minh OTP
                response.sendRedirect("addUser?step=verify");
            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/views/admin/userAddition.jsp").forward(request, response);
            }
        } else if ("verify".equals(step)) {
            // Bước 2: Xác minh OTP
            String enteredOtp = request.getParameter("otp");
            String storedOtp = (String) session.getAttribute("otp");
            Long otpExpiration = (Long) session.getAttribute("otpExpiration");
            User newUser = (User) session.getAttribute("newUser");
            Integer adminUserId = (Integer) session.getAttribute("adminUserId");

            try {
                if (storedOtp == null || otpExpiration == null || newUser == null || adminUserId == null) {
                    throw new IllegalStateException("Phiên xác minh đã hết hạn. Vui lòng thử lại.");
                }

                if (System.currentTimeMillis() > otpExpiration) {
                    throw new IllegalStateException("Mã OTP đã hết hạn. Vui lòng yêu cầu mã mới.");
                }

                if (!storedOtp.equals(enteredOtp)) {
                    throw new IllegalArgumentException("Mã OTP không đúng. Vui lòng thử lại.");
                }

                // Thêm người dùng vào database
                userService.addUser(newUser, adminUserId);

                // Xóa thông tin trong session
                session.removeAttribute("otp");
                session.removeAttribute("otpExpiration");
                session.removeAttribute("newUser");
                session.removeAttribute("adminUserId");

                response.sendRedirect("addUser?success=true");
            } catch (Exception e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/views/admin/userAddition.jsp").forward(request, response);
            }
        }
    }
}