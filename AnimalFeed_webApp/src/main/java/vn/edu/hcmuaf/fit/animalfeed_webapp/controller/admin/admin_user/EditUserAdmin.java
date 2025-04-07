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

@WebServlet("/userEdit")
public class EditUserAdmin extends HttpServlet {
    private UserService userService;
    private EmailService emailService;

    @Override
    public void init() {
        userService = new UserService();
        emailService = new EmailService();
    }

    // Kiểm tra xem người dùng có phải là admin và có quyền chỉnh sửa người dùng không
    private boolean hasUserManagementPermission(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return false;
        }
        return userService.hasPermission(userId, "USER_MANAGEMENT");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");

            if (idStr == null || idStr.trim().isEmpty()) {
                throw new ServletException("ID is required");
            }

            // Kiểm tra và lấy giá trị id từ session
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                response.sendRedirect("login");
                return;
            }

            // Kiểm tra quyền USER_MANAGEMENT
            if (!hasUserManagementPermission(session)) {
                session.setAttribute("error", "Bạn không có quyền truy cập trang này.");
                response.sendRedirect("dashboard");
                return;
            }

            // Lấy sub_role của người dùng hiện tại và lưu vào session
            User currentUser = userService.getById(userId);
            if (currentUser != null) {
                session.setAttribute("subRole", currentUser.getSub_role());
            }

            // Nếu có userId trong session, tiếp tục xử lý
            int id = Integer.parseInt(idStr);
            User user = userService.getById(id);

            if (user != null) {
                request.setAttribute("user", user);
                request.setAttribute("userService", userService); // Đưa userService vào request scope để JSP sử dụng
                request.getRequestDispatcher("/views/admin/userEdit.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Không tìm thấy người dùng.");
                response.sendRedirect("userManagement");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "ID không hợp lệ");
            response.sendRedirect("userManagement");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
            response.sendRedirect("userManagement");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String step = request.getParameter("step");

        if ("sendOtp".equals(step)) {
            // Bước 1: Gửi mã OTP
            try {
                // Kiểm tra quyền USER_MANAGEMENT
                if (!hasUserManagementPermission(session)) {
                    session.setAttribute("error", "Bạn không có quyền chỉnh sửa người dùng.");
                    response.sendRedirect("dashboard");
                    return;
                }

                // Lấy các tham số từ request
                String idStr = request.getParameter("id");
                String fullName = request.getParameter("fullName");
                String phone = request.getParameter("phone");
                String email = request.getParameter("email");
                String roleStr = request.getParameter("role");
                String sub_roleStr = request.getParameter("sub_role");
                String statusStr = request.getParameter("status");

                // Kiểm tra nếu có tham số rỗng
                if (idStr == null || idStr.trim().isEmpty() ||
                        fullName == null || fullName.trim().isEmpty() ||
                        phone == null || phone.trim().isEmpty() ||
                        email == null || email.trim().isEmpty()) {
                    throw new ServletException("Thiếu các trường bắt buộc.");
                }

                // Chuyển các tham số về kiểu số
                int id = Integer.parseInt(idStr);
                int role = (roleStr != null && !roleStr.trim().isEmpty()) ? Integer.parseInt(roleStr) : 0;
                int sub_role = (sub_roleStr != null && !sub_roleStr.trim().isEmpty()) ? Integer.parseInt(sub_roleStr) : 0;
                int status = (statusStr != null && !statusStr.trim().isEmpty()) ? Integer.parseInt(statusStr) : 1;

                // Kiểm tra giá trị status hợp lệ
                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái không hợp lệ.");
                }

                // Lấy thông tin người dùng hiện tại để so sánh
                User existingUser = userService.getById(id);
                if (existingUser == null) {
                    throw new ServletException("Không tìm thấy người dùng.");
                }

                // Kiểm tra quyền chỉnh sửa role, sub_role, status
                Integer userId = (Integer) session.getAttribute("userId");
                User currentUser = userService.getById(userId);
                if (currentUser.getSub_role() != 0) { // Không phải Super Admin
                    // Giữ nguyên role, sub_role, status của người dùng hiện tại
                    role = existingUser.getRole();
                    sub_role = existingUser.getSub_role();
                    status = existingUser.getStatus();
                }

                // Tạo đối tượng User mới với thông tin đã chỉnh sửa
                User updatedUser = new User(id, fullName, existingUser.getPassword(), phone, email, status, existingUser.getCreateDate(), new Date(), role, sub_role);

                // Lưu thông tin người dùng đã chỉnh sửa, admin ID và user ID vào session
                session.setAttribute("updatedUser", updatedUser);
                session.setAttribute("adminUserId", userId);
                session.setAttribute("originalUser", existingUser);
                session.setAttribute("userIdToEdit", id); // Lưu ID người dùng cần chỉnh sửa

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
                response.sendRedirect("userEdit?step=verify&id=" + id);
            } catch (Exception e) {
                session.setAttribute("error", "Lỗi: " + e.getMessage());
                response.sendRedirect("userEdit?id=" + request.getParameter("id"));
            }
        } else if ("verify".equals(step)) {
            // Bước 2: Xác minh OTP và cập nhật thông tin
            try {
                String enteredOtp = request.getParameter("otp");
                String storedOtp = (String) session.getAttribute("otp");
                Long otpExpiration = (Long) session.getAttribute("otpExpiration");
                User updatedUser = (User) session.getAttribute("updatedUser");
                User originalUser = (User) session.getAttribute("originalUser");
                Integer adminUserId = (Integer) session.getAttribute("adminUserId");
                Integer userIdToEdit = (Integer) session.getAttribute("userIdToEdit");

                if (storedOtp == null || otpExpiration == null || updatedUser == null || originalUser == null || adminUserId == null || userIdToEdit == null) {
                    throw new IllegalStateException("Phiên xác minh đã hết hạn. Vui lòng thử lại.");
                }

                if (System.currentTimeMillis() > otpExpiration) {
                    throw new IllegalStateException("Mã OTP đã hết hạn. Vui lòng yêu cầu mã mới.");
                }

                if (!storedOtp.equals(enteredOtp)) {
                    throw new IllegalArgumentException("Mã OTP không đúng. Vui lòng thử lại.");
                }

                // Thực hiện cập nhật thông tin người dùng
                boolean updated = userService.updateUser(updatedUser, adminUserId);
                if (!updated) {
                    throw new RuntimeException("Cập nhật thông tin người dùng thất bại.");
                }

                // Tạo danh sách các trường đã chỉnh sửa
                StringBuilder updatedFields = new StringBuilder();
                if (!originalUser.getFullName().equals(updatedUser.getFullName())) {
                    updatedFields.append("<li><strong>Tên đầy đủ:</strong> ").append(originalUser.getFullName()).append(" → ").append(updatedUser.getFullName()).append("</li>");
                }
                if (!originalUser.getPhone().equals(updatedUser.getPhone())) {
                    updatedFields.append("<li><strong>Số điện thoại:</strong> ").append(originalUser.getPhone()).append(" → ").append(updatedUser.getPhone()).append("</li>");
                }
                if (!originalUser.getEmail().equals(updatedUser.getEmail())) {
                    updatedFields.append("<li><strong>Email:</strong> ").append(originalUser.getEmail()).append(" → ").append(updatedUser.getEmail()).append("</li>");
                }
                if (originalUser.getRole() != updatedUser.getRole()) {
                    updatedFields.append("<li><strong>Vai trò:</strong> ").append(originalUser.getRole() == 1 ? "Quản trị viên" : "Người dùng").append(" → ").append(updatedUser.getRole() == 1 ? "Quản trị viên" : "Người dùng").append("</li>");
                }
                if (originalUser.getSub_role() != updatedUser.getSub_role()) {
                    String originalSubRole = getSubRoleName(originalUser.getSub_role());
                    String updatedSubRole = getSubRoleName(updatedUser.getSub_role());
                    updatedFields.append("<li><strong>Loại quản trị viên:</strong> ").append(originalSubRole).append(" → ").append(updatedSubRole).append("</li>");
                }
                if (originalUser.getStatus() != updatedUser.getStatus()) {
                    updatedFields.append("<li><strong>Trạng thái:</strong> ").append(originalUser.getStatus() == 1 ? "Đang hoạt động" : "Ngưng hoạt động").append(" → ").append(updatedUser.getStatus() == 1 ? "Đang hoạt động" : "Ngưng hoạt động").append("</li>");
                }

                // Gửi email thông báo cập nhật thành công
                if (updatedFields.length() > 0) {
                    emailService.sendUpdateNotification(updatedUser.getEmail(), updatedFields.toString());
                }

                // Xóa thông tin trong session
                session.removeAttribute("otp");
                session.removeAttribute("otpExpiration");
                session.removeAttribute("updatedUser");
                session.removeAttribute("originalUser");
                session.removeAttribute("adminUserId");
                session.removeAttribute("userIdToEdit");

                session.setAttribute("message", "Cập nhật thông tin người dùng thành công!");
                response.sendRedirect("userManagement");
            } catch (Exception e) {
                Integer userIdToEdit = (Integer) session.getAttribute("userIdToEdit");
                if (userIdToEdit != null) {
                    session.setAttribute("error", "Lỗi: " + e.getMessage());
                    response.sendRedirect("userEdit?step=verify&id=" + userIdToEdit);
                } else {
                    session.setAttribute("error", "Lỗi: " + e.getMessage());
                    response.sendRedirect("userManagement");
                }
            }
        }
    }

    // Phương thức hỗ trợ để lấy tên sub_role
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