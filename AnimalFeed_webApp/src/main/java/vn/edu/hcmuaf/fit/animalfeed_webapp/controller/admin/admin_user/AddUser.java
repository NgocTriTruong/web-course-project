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
        String roleStr = request.getParameter("role");
        String statusStr = request.getParameter("status");

        try {
            // Kiểm tra các trường bắt buộc
            if (fullName == null || phone == null || password == null || roleStr == null || statusStr == null) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin.");
            }

            int role = Integer.parseInt(roleStr);
            int status = Integer.parseInt(statusStr); // Chuyển đổi status thành int

            // Kiểm tra giá trị status hợp lệ
            if (status != 1 && status != 2) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ.");
            }

            // Kiểm tra độ mạnh mật khẩu
            if (!userService.isPasswordStrong(password)) {
                throw new IllegalArgumentException("Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường và số.");
            }

            // Tạo người dùng mới
            User newUser = new User();
            newUser.setFullName(fullName);
            newUser.setPhone(phone);
            newUser.setPassword(password);
            newUser.setRole(role);
            newUser.setStatus(status); // Gán trực tiếp giá trị int cho status
            newUser.setCreateDate(new Date());
            newUser.setUpdateDate(new Date());

            // Kiểm tra quyền
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null || currentUser.getRole() != 1) {
                throw new SecurityException("Bạn không có quyền thực hiện thao tác này.");
            }

            userService.addUser(newUser, currentUser.getId());

            response.sendRedirect("userManagement?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/views/admin/userAddition.jsp").forward(request, response);
        }
    }
}