package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "NewPasswordController", value = "/new-password")
public class NewPasswordController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        // Lấy danh sách danh mục
        List<Category> categories = categoryService.getAll();

        request.setAttribute("categories", categories);
        request.getRequestDispatcher("views/web/chi_tiet_ca_nhan/thay_doi_pass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu mới và xác nhận mật khẩu không khớp!");
            request.getRequestDispatcher("views/web/chi_tiet_ca_nhan/thay_doi_pass.jsp").forward(request, response);
            return;
        }

        // Thay đổi mật khẩu trong database (giả sử có UserDAO)
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("errorMessage", "Vui lòng đăng nhập trước khi thay đổi mật khẩu.");
            request.getRequestDispatcher("/views/web/login.jsp").forward(request, response);
            return;
        }

        UserService UserDAO = new UserService();
        boolean isUpdated = UserDAO.updatePassword(user.getId(), currentPassword, newPassword);

        request.setAttribute("user", user);

        // Kiểm tra kết quả và trả thông báo tương ứng
        if (isUpdated) {
            // Nếu thay đổi mật khẩu thành công
            request.setAttribute("successMessage", "Thay đổi mật khẩu thành công!");
            request.getRequestDispatcher("/views/web/chi_tiet_ca_nhan/thay_doi_pass.jsp").forward(request, response);
        } else {
            // Nếu mật khẩu hiện tại không đúng
            request.setAttribute("errorMessage", "Mật khẩu hiện tại không đúng!");
            request.getRequestDispatcher("/views/web/chi_tiet_ca_nhan/thay_doi_pass.jsp").forward(request, response);
        }
    }

}