package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_user;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "AddUser", value = "/addUser")
public class AddUser extends HttpServlet {

    private final UserService userService;

    public AddUser() {
        this.userService = new UserService(); // Khởi tạo UserService
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/userAddition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                throw new IllegalArgumentException("Mật khẩu phải có ít nhất 6 ký tự, bao gồm chữ hoa, chữ thường, số và kí tự đặc biệt.");
            }

            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null || currentUser.getRole() != 1) {
                throw new SecurityException("Bạn không có quyền thực hiện thao tác này.");
            }

            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setPhone(phone);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setRole(role);
            newUser.setStatus(status);
            newUser.setCreateDate(new Date());
            newUser.setUpdateDate(new Date());

            userService.addUser(newUser, currentUser.getId());

            response.sendRedirect("addUser?success=true");
        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/views/admin/userAddition.jsp").forward(request, response);
        }
    }
}