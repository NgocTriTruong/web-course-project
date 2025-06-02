package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
import com.google.gson.Gson;

import java.io.IOException;

@WebServlet(name = "AddOrderController", value = "/add-order-management")
public class AddOrderController extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        // Lấy userId từ session
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            request.getSession().setAttribute("error", "Vui lòng đăng nhập để truy cập.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Kiểm tra quyền ORDER_MANAGEMENT
        if (!userService.hasPermission(userId, "ORDER_MANAGEMENT")) {
            request.getSession().setAttribute("error", "Bạn không có quyền truy cập trang thêm đơn hàng (yêu cầu quyền ORDER_MANAGEMENT).");
            response.sendRedirect(request.getContextPath() + "/order-manager");
            return;
        }

        if (email != null) { // Xử lý AJAX request để lấy thông tin khách hàng
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            try {
                User user = userService.getUserByEmail(email);
                if (user != null) {
                    response.getWriter().write(gson.toJson(new UserResponse(user.getFullName(), user.getPhone())));
                } else {
                    response.getWriter().write("{\"fullName\": null, \"phone\": null}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Đã xảy ra lỗi khi lấy thông tin khách hàng: " + e.getMessage() + "\"}");
            }
        } else {
            // Hiển thị trang JSP khi không có tham số email
            request.getRequestDispatcher("/views/admin/orderAddition.jsp").forward(request, response);
        }
    }

    // Class để trả về JSON response
    private static class UserResponse {
        private String fullName;
        private String phone;

        public UserResponse(String fullName, String phone) {
            this.fullName = fullName;
            this.phone = phone;
        }

        public String getFullName() {
            return fullName;
        }

        public String getPhone() {
            return phone;
        }
    }
}