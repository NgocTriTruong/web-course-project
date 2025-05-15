package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.category_management;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.File;
import java.io.IOException;

@MultipartConfig(maxFileSize = 1024 * 1024 *5) // 5MB
@WebServlet(name = "CategoryAdditionAdmin", value = "/category-addition-admin")
public class CategoryAdditionAdmin extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // Kiểm tra đăng nhập
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Kiểm tra quyền CATEGORY_MANAGEMENT
        if (!userService.hasPermission(userId, "CATEGORY_MANAGEMENT")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập chức năng này.");
            return;
        }

        request.getRequestDispatcher("views/admin/categoryAddition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // Kiểm tra đăng nhập
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Kiểm tra quyền CATEGORY_MANAGEMENT
        if (!userService.hasPermission(userId, "CATEGORY_MANAGEMENT")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền thêm danh mục.");
            return;
        }

        // Lấy dữ liệu từ form
        String categoryName = request.getParameter("category");
        int status = Integer.parseInt(request.getParameter("status"));
        Part filePart = request.getPart("avatar");
        String fileName = filePart != null ? filePart.getSubmittedFileName() : "";

        // Kiểm tra đầu vào
        if (categoryName == null || categoryName.trim().isEmpty()) {
            request.setAttribute("error", "Tên danh mục không được để trống.");
            request.getRequestDispatcher("views/admin/categoryAddition.jsp").forward(request, response);
            return;
        }

        // Xử lý upload file
        String imgPath = "";
        if (fileName != null && !fileName.isEmpty()) {
            // Kiểm tra định dạng file
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
                request.setAttribute("error", "Chỉ hỗ trợ file .jpg hoặc .png.");
                request.getRequestDispatcher("views/admin/categoryAddition.jsp").forward(request, response);
                return;
            }
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            imgPath = "uploads/" + fileName;
        }

        // Tạo đối tượng Category
        Category category = new Category();
        category.setName(categoryName);
        category.setImg(imgPath);
        category.setStatus(status);

        try {
            // Thêm danh mục và ghi log
            categoryService.insertCategory(category, userId);

            // Chuyển hướng về trang quản lý danh mục
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi thêm danh mục: " + e.getMessage());
            request.getRequestDispatcher("views/admin/categoryAddition.jsp").forward(request, response);
        }
    }
}