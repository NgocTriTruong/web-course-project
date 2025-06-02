package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.category_management;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "CategoryManagementAdmin", value = "/category-management-admin")
public class CategoryManagementAdmin extends HttpServlet {

    private CategoryService categoryService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        categoryService = new CategoryService();
        userService = UserService.getInstance();
    }

    private boolean hasCategoryManagementPermission(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return false;
        }
        return userService.hasPermission(userId, "CATEGORY_MANAGEMENT");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            session = request.getSession(true);
            session.setAttribute("error", "Vui lòng đăng nhập để truy cập.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!hasCategoryManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền truy cập trang này (yêu cầu quyền CATEGORY_MANAGEMENT).");
            response.sendRedirect(request.getContextPath() + "/"); // Chuyển về trang chính
            return;
        }

        String action = request.getParameter("action");
        String searchTerm = request.getParameter("searchTerm");

        List<Category> categories = categoryService.getAll();
        if ("search".equals(action) && searchTerm != null && !searchTerm.trim().isEmpty()) {
            categories = categories.stream()
                    .filter(c -> c.getName().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        }

        request.setAttribute("categories", categories);
        request.getRequestDispatcher("views/admin/categoryManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Chuển hướng về doGet()
        doGet(request, response);
    }
}