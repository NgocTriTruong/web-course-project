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

    private boolean hasUserManagementPermission(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            System.out.println("User ID is null in session");
            return false;
        }
        boolean hasPermission = userService.hasPermission(userId, "USER_MANAGEMENT");
        System.out.println("User ID: " + userId + ", Has USER_MANAGEMENT permission: " + hasPermission);
        return hasPermission;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !hasUserManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền truy cập trang này.");
            response.sendRedirect("dashboard");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        User currentUser = userService.getById(userId);
        if (currentUser != null) {
            System.out.println("Current User ID: " + userId + ", Role: " + currentUser.getRole() + ", Sub Role: " + currentUser.getSub_role());
            session.setAttribute("subRole", currentUser.getSub_role());
        } else {
            System.out.println("Current User is null for userId: " + userId);
            session.setAttribute("error", "Không tìm thấy thông tin người dùng. Vui lòng đăng nhập lại.");
            response.sendRedirect("login");
            return;
        }

        request.setAttribute("userService", userService);
        request.getRequestDispatcher("/views/admin/userAddition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String step = request.getParameter("step");
        HttpSession session = request.getSession();

        if (!hasUserManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền thực hiện thao tác này.");
            response.sendRedirect("dashboard");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        User currentUser = userService.getById(userId);
        if (currentUser == null) {
            System.out.println("Current User is null for userId: " + userId);
            session.setAttribute("error", "Không tìm thấy thông tin người dùng. Vui lòng đăng nhập lại.");
            response.sendRedirect("login");
            return;
        }

        if ("sendOtp".equals(step)) {
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String roleStr = request.getParameter("role");
            String subRoleStr = request.getParameter("sub_role");
            String statusStr = request.getParameter("status");

            try {
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

                if (!userService.isPasswordStrong(password)) {
                    throw new IllegalArgumentException("Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.");
                }

                if (userService.isEmailExists(email)) {
                    throw new IllegalArgumentException("Email đã tồn tại.");
                }

                int role = (roleStr != null && !roleStr.trim().isEmpty()) ? Integer.parseInt(roleStr) : 0;
                int subRole = (subRoleStr != null && !subRoleStr.trim().isEmpty()) ? Integer.parseInt(subRoleStr) : 0;
                int status = (statusStr != null && !statusStr.trim().isEmpty()) ? Integer.parseInt(statusStr) : 1;

                if (currentUser.getSub_role() != 0) {
                    role = 0;
                    subRole = 0;
                    status = 1;
                }

                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái không hợp lệ.");
                }

                User newUser = new User();
                newUser.setFullName(fullName);
                newUser.setPhone(phone);
                newUser.setPassword(password);
                newUser.setEmail(email);
                newUser.setRole(role);
                newUser.setSub_role(subRole);
                newUser.setStatus(status);
                newUser.setCreateDate(new Date());
                newUser.setUpdateDate(new Date());

                session.setAttribute("newUser", newUser);
                session.setAttribute("adminUserId", currentUser.getId());

                String otp = String.valueOf(new Random().nextInt(900000) + 100000);
                boolean emailSent = emailService.sendVerificationEmail(email, otp);
                if (!emailSent) {
                    throw new RuntimeException("Không thể gửi mã OTP qua email. Vui lòng thử lại.");
                }

                session.setAttribute("otp", otp);
                session.setAttribute("otpExpiration", System.currentTimeMillis() + 5 * 60 * 1000);
                response.sendRedirect("addUser?step=verify");
            } catch (Exception e) {
                session.setAttribute("error", e.getMessage());
                response.sendRedirect("addUser");
            }
        } else if ("verify".equals(step)) {
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

                userService.addUser(newUser, adminUserId);

                StringBuilder emailContent = new StringBuilder();
                emailContent.append("<h3>Chào mừng bạn đến với hệ thống!</h3>");
                emailContent.append("<p>Tài khoản của bạn đã được tạo thành công với các thông tin sau:</p>");
                emailContent.append("<ul>");
                emailContent.append("<li><strong>Họ và tên:</strong> ").append(newUser.getFullName()).append("</li>");
                emailContent.append("<li><strong>Email:</strong> ").append(newUser.getEmail()).append("</li>");
                emailContent.append("<li><strong>Số điện thoại:</strong> ").append(newUser.getPhone()).append("</li>");
                emailContent.append("<li><strong>Vai trò:</strong> ").append(newUser.getRole() == 1 ? "Quản trị viên" : "Người dùng").append("</li>");
                if (newUser.getRole() == 1) {
                    emailContent.append("<li><strong>Loại quản trị viên:</strong> ").append(getSubRoleName(newUser.getSub_role())).append("</li>");
                }
                emailContent.append("<li><strong>Trạng thái:</strong> ").append(newUser.getStatus() == 1 ? "Đang hoạt động" : "Ngưng hoạt động").append("</li>");
                emailContent.append("</ul>");
                emailContent.append("<p>Vui lòng đăng nhập bằng email và mật khẩu đã được cung cấp để sử dụng hệ thống.</p>");
                emailService.sendUpdateNotification(newUser.getEmail(), emailContent.toString());

                session.removeAttribute("otp");
                session.removeAttribute("otpExpiration");
                session.removeAttribute("newUser");
                session.removeAttribute("adminUserId");

                session.setAttribute("message", "Thêm người dùng thành công!");
                response.sendRedirect("userManagement");
            } catch (Exception e) {
                session.setAttribute("error", e.getMessage());
                response.sendRedirect("addUser?step=verify");
            }
        }
    }

    private String getSubRoleName(int subRole) {
        switch (subRole) {
            case 0: return "Super Admin";
            case 1: return "User Admin";
            case 2: return "Product Admin";
            case 3: return "Order Admin";
            case 4: return "Shipper Admin";
            case 5: return "News Admin";
            default: return "Unknown";
        }
    }
}