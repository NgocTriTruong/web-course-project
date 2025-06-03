package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.category_management;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;

@WebServlet(name = "DeleteCategory", value = "/delete-category")
public class DeleteCategory extends HttpServlet {

    private CategoryService categoryService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        categoryService = new CategoryService();
        userService = UserService.getInstance();
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

        Integer userId = (Integer) session.getAttribute("userId");
        String categoryIdStr = request.getParameter("categoryId");

        try {
            if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("ID danh mục không hợp lệ.");
            }

            int categoryId = Integer.parseInt(categoryIdStr);
            categoryService.deleteCategory(categoryId, userId);

            session.setAttribute("message", "Xóa danh mục thành công!");
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
        } catch (Exception e) {
            session.setAttribute("error", "Có lỗi xảy ra khi xóa danh mục: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/category-management-admin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}