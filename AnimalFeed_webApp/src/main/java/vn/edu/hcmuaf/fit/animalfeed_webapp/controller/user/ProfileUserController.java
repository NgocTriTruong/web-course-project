package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProfileUserController", value = "/profile-user")
public class ProfileUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        List<Category> categories = categoryService.getAll();

        // Kiểm tra session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            // Nếu chưa đăng nhập, chuyển về trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Lấy thông tin user từ session
        User user = (User) session.getAttribute("user");

        // Set thông tin user vào request
        request.setAttribute("user", user);

        request.setAttribute("categoriesData", categories);

        request.getRequestDispatcher("views/web/chi_tiet_ca_nhan/thong_tin_ca_nhan.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}