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
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email"); // Thay "phone" thành "email"
        if (email != null) { // Xử lý AJAX request để lấy thông tin khách hàng
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            try {
                User user = userService.getUserByEmail(email); // Sử dụng getUserByEmail
                if (user != null) {
                    response.getWriter().write(gson.toJson(new UserResponse(user.getFullName(), null))); // Không trả về phone vì không lưu được
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
        private String phone; // Giữ trường phone nhưng sẽ trả về null

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