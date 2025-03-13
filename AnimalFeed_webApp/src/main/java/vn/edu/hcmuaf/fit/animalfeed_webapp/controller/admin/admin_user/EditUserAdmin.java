package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_user;

import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

@WebServlet("/userEdit")
public class EditUserAdmin extends HttpServlet {
    private UserService userService;

    // Kiểm tra xem người dùng có phải là admin không
    private boolean isAdmin(int userId) {
        // Bạn cần lấy đối tượng User từ service hoặc dao của bạn dựa trên userId
        User user = userService.getById(userId);  // Giả sử userService đã có phương thức này
        return user != null && user.getRole() == 1; // Nếu role = 1 thì là admin
    }

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");

            // Kiểm tra xem người dùng có phải là admin không
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");

            // Nếu không phải admin, từ chối yêu cầu
            if (userId == null || !isAdmin(userId)) {
                request.getSession().setAttribute("error", "Bạn không có quyền cập nhật người dùng.");
                response.sendRedirect("userManagement");
                return;
            }

            // Lấy các tham số từ request
            String idStr = request.getParameter("id");
            String statusStr = request.getParameter("status");
            String roleStr = request.getParameter("role");

            // Kiểm tra nếu có tham số rỗng
            if (idStr == null || idStr.trim().isEmpty() ||
                    statusStr == null || statusStr.trim().isEmpty() ||
                    roleStr == null || roleStr.trim().isEmpty()) {
                throw new ServletException("Missing required parameters: ID, Status or Role");
            }

            // Chuyển các tham số về kiểu số
            int id = Integer.parseInt(idStr);
            int status = Integer.parseInt(statusStr);
            int role = Integer.parseInt(roleStr);
            String password = request.getParameter("password");

            // Kiểm tra giá trị status hợp lệ
            if (status != 1 && status != 2) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ.");
            }

            // Các tham số khác
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            if (fullName == null || fullName.trim().isEmpty()) {
                throw new ServletException("Full name is required");
            }

            if (phone == null || phone.trim().isEmpty()) {
                throw new ServletException("Phone number is required");
            }

            if (email == null || email.trim().isEmpty()) {
                throw new ServletException("Email is required");
            }

            // Cập nhật thông tin người dùng
            User user = new User(id, fullName, password, phone, email, status, new Date(), new Date(), role);
            boolean updated = userService.updateUser(user, userId); // Thực hiện cập nhật

            if (updated) {
                request.getSession().setAttribute("message", "Cập nhật thành công!");
            } else {
                request.getSession().setAttribute("error", "Cập nhật thất bại!");
            }

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Dữ liệu không hợp lệ");
        } catch (ServletException e) {
            request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
        }

        response.sendRedirect("userManagement");
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
                // Nếu không có userId trong session, có thể chuyển hướng đến trang đăng nhập hoặc báo lỗi
                response.sendRedirect("login");
                return;
            }

            // Nếu có userId trong session, tiếp tục xử lý
            int id = Integer.parseInt(idStr);
            User user = userService.getById(id);

            if (user != null) {
                request.setAttribute("user", user);
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
}
