package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "AddUser", value = "/addUser")
public class AddUser extends HttpServlet {

    private final UserService userService;

    public AddUser() {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (Integer) session.getAttribute("userId");
        User currentUser = userService.getById(userId);
        if (currentUser != null) {
            System.out.println("AddUser: Current User ID: " + userId + ", Role: " + currentUser.getRole() + ", Sub Role: " + currentUser.getSub_role());
            session.setAttribute("subRole", currentUser.getSub_role());
        } else {
            System.out.println("AddUser: Current User is null for userId: " + userId);
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
        HttpSession session = request.getSession();

        Integer userId = (Integer) session.getAttribute("userId");
        User currentUser = userService.getById(userId);
        if (currentUser == null) {
            System.out.println("AddUser: Current User is null for userId: " + userId);
            session.setAttribute("error", "Không tìm thấy thông tin người dùng. Vui lòng đăng nhập lại.");
            response.sendRedirect("login");
            return;
        }

        try {
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String roleStr = request.getParameter("role");
            String subRoleStr = request.getParameter("sub_role");
            String statusStr = request.getParameter("status");

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

            // Kiểm tra mật khẩu mạnh
            if (!userService.isPasswordStrong(password)) {
                throw new IllegalArgumentException("Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.");
            }

            // Kiểm tra email đã tồn tại
            if (userService.isEmailExists(email)) {
                throw new IllegalArgumentException("Email đã tồn tại.");
            }

            // Gán giá trị mặc định nếu các tham số không được cung cấp
            int role = (roleStr != null && !roleStr.trim().isEmpty()) ? Integer.parseInt(roleStr) : 0;
            int subRole = (subRoleStr != null && !subRoleStr.trim().isEmpty()) ? Integer.parseInt(subRoleStr) : 0;
            int status = (statusStr != null && !statusStr.trim().isEmpty()) ? Integer.parseInt(statusStr) : 1;

            // Nếu không phải Super Admin (sub_role != 0), đặt mặc định role, sub_role, status
            if (currentUser.getSub_role() != 0) {
                role = 0;
                subRole = 0;
                status = 1;
            }

            // Kiểm tra trạng thái hợp lệ
            if (status != 0 && status != 1) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ.");
            }

            // Tạo user mới
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

            // Thêm user vào hệ thống
            userService.addUser(newUser, currentUser.getId());

            session.setAttribute("message", "Thêm người dùng thành công!");
            response.sendRedirect("userManagement");
        } catch (Exception e) {
            System.out.println("AddUser: Error adding user: " + e.getMessage());
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("addUser");
        }
    }

    private String getSubRoleName(int subRole) {
        switch (subRole) {
            case 0: return "Super Admin";
            case 1: return "User Admin";
            case 2: return "Category Admin";
            case 3: return "Product Admin";
            case 4: return "Order Admin";
            case 5: return "News Admin";
            case 6: return "Job Admin";
            case 7: return "Contact Admin";
            case 8: return "Issue Admin";
            default: return "Unknown";
        }
    }
}