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

@WebServlet("/userEdit")
public class EditUserAdmin extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");

            if (idStr == null || idStr.trim().isEmpty()) {
                throw new ServletException("ID is required");
            }

            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                response.sendRedirect("login");
                return;
            }

            User currentUser = userService.getById(userId);
            if (currentUser != null) {
                session.setAttribute("subRole", currentUser.getSub_role());
            }

            int id = Integer.parseInt(idStr);
            User user = userService.getById(id);

            if (user != null) {
                request.setAttribute("user", user);
                request.setAttribute("userService", userService);
                request.getRequestDispatcher("/views/admin/userEdit.jsp").forward(request, response);
            } else {
                session.setAttribute("error", "Không tìm thấy người dùng.");
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

        try {
            String idStr = request.getParameter("id");
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String roleStr = request.getParameter("role");
            String sub_roleStr = request.getParameter("sub_role");
            String statusStr = request.getParameter("status");

            // Kiểm tra các trường bắt buộc
            if (idStr == null || idStr.trim().isEmpty() ||
                    fullName == null || fullName.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty() ||
                    email == null || email.trim().isEmpty()) {
                throw new ServletException("Thiếu các trường bắt buộc.");
            }

            int id = Integer.parseInt(idStr);
            int role = (roleStr != null && !roleStr.trim().isEmpty()) ? Integer.parseInt(roleStr) : 0;
            int sub_role = (sub_roleStr != null && !sub_roleStr.trim().isEmpty()) ? Integer.parseInt(sub_roleStr) : 0;
            int status = (statusStr != null && !statusStr.trim().isEmpty()) ? Integer.parseInt(statusStr) : 1;

            if (status != 0 && status != 1) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ.");
            }

            // Lấy thông tin user hiện tại
            User existingUser = userService.getById(id);
            if (existingUser == null) {
                throw new ServletException("Không tìm thấy người dùng.");
            }

            Integer userId = (Integer) session.getAttribute("userId");
            User currentUser = userService.getById(userId);
            if (currentUser == null) {
                throw new ServletException("Không tìm thấy thông tin người dùng hiện tại.");
            }

            // Nếu không phải Super Admin (sub_role != 0), không cho phép thay đổi role, sub_role, status
            if (currentUser.getSub_role() != 0) {
                role = existingUser.getRole();
                sub_role = existingUser.getSub_role();
                status = existingUser.getStatus();
            }

            // Tạo đối tượng user cập nhật
            User updatedUser = new User(id, fullName, existingUser.getPassword(), phone, email, status, existingUser.getCreateDate(), new Date(), role, sub_role, false);

            // Cập nhật user
            boolean updated = userService.updateUser(updatedUser, userId);
            if (!updated) {
                throw new RuntimeException("Cập nhật thông tin người dùng thất bại.");
            }

            // Kiểm tra nếu role thay đổi từ admin (1) sang người dùng thường (0)
            boolean roleChangedToNormalUser = (existingUser.getRole() == 1 && updatedUser.getRole() == 0);
            if (roleChangedToNormalUser) {
                userService.markUserForLogout(id);
                if (id == userId) {
                    session.invalidate();
                    session = request.getSession(true);
                    session.setAttribute("message", "Vai trò của bạn đã thay đổi. Vui lòng đăng nhập lại.");
                    response.sendRedirect("login");
                    return;
                }
            }

            session.setAttribute("message", "Cập nhật thông tin người dùng thành công!");
            response.sendRedirect("userManagement");
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi: " + e.getMessage());
            response.sendRedirect("userEdit?id=" + request.getParameter("id"));
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