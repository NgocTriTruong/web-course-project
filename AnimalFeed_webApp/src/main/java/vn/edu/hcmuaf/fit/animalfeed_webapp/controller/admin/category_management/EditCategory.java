package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.category_management;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,    // 5MB
        maxRequestSize = 1024 * 1024 * 10, // 10MB
        fileSizeThreshold = 1024 * 1024    // 1MB
)
@WebServlet(name = "EditCategory", value = "/edit-category")
public class EditCategory extends HttpServlet {

    private static final String BASE_UPLOAD_DIRECTORY = "/views/template/assets/images/category/";
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
            session.setAttribute("error", "Bạn không có quyền chỉnh sửa danh mục (yêu cầu quyền CATEGORY_MANAGEMENT).");
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
            return;
        }

        String categoryIdStr = request.getParameter("categoryId");
        if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            session.setAttribute("error", "Không tìm thấy danh mục để chỉnh sửa.");
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
            return;
        }

        int categoryId = Integer.parseInt(categoryIdStr);
        Category category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            session.setAttribute("error", "Danh mục không tồn tại.");
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
            return;
        }

        request.setAttribute("category", category);
        request.getRequestDispatcher("/views/admin/categoryEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!hasCategoryManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền thực hiện thao tác này.");
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        try {
            String categoryIdStr = request.getParameter("categoryId");
            String name = request.getParameter("category");
            String statusStr = request.getParameter("status");

            if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("ID danh mục không hợp lệ.");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên danh mục không được để trống.");
            }

            int categoryId = Integer.parseInt(categoryIdStr);
            int status = (statusStr != null && !statusStr.trim().isEmpty()) ? Integer.parseInt(statusStr) : 1;
            if (status != 0 && status != 1) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ.");
            }

            Category category = categoryService.getCategoryById(categoryId);
            if (category == null) {
                throw new IllegalArgumentException("Danh mục không tồn tại.");
            }

            category.setName(name.trim());
            category.setStatus(status);

            String imagePath = handleFileUpload(request);
            if (imagePath != null) {
                category.setImg(imagePath);
            }

            categoryService.updateCategoryStatus(category, userId);

            session.setAttribute("message", "Chỉnh sửa danh mục thành công!");
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/edit-category?categoryId=" + request.getParameter("categoryId"));
        } catch (Exception e) {
            session.setAttribute("error", "Có lỗi xảy ra khi chỉnh sửa danh mục: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/edit-category?categoryId=" + request.getParameter("categoryId"));
        }
    }

    private String handleFileUpload(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("avatar");
        if (filePart == null || filePart.getSize() == 0) {
            return null; // Không cập nhật ảnh nếu không có file
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + BASE_UPLOAD_DIRECTORY;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String finalFileName = System.currentTimeMillis() + "_" + fileName;
        String filePath = uploadPath + File.separator + finalFileName;
        filePart.write(filePath);

        return BASE_UPLOAD_DIRECTORY + finalFileName;
    }
}