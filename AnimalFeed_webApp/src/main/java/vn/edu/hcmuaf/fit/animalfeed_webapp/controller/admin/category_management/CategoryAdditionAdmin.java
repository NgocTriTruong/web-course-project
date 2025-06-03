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
@WebServlet(name = "CategoryAdditionAdmin", value = "/category-addition-admin")
public class CategoryAdditionAdmin extends HttpServlet {

    private static final String BASE_UPLOAD_DIRECTORY = "/views/template/assets/images/category/";
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

        request.getRequestDispatcher("views/admin/categoryAddition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        try {
            String name = request.getParameter("category");
            String statusStr = request.getParameter("status");

            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Tên danh mục không được để trống.");
            }

            int status = (statusStr != null && !statusStr.trim().isEmpty()) ? Integer.parseInt(statusStr) : 1;
            if (status != 0 && status != 1) {
                throw new IllegalArgumentException("Trạng thái không hợp lệ.");
            }

            Category category = new Category();
            category.setName(name.trim());
            category.setStatus(status);

            String imagePath = handleFileUpload(request);
            category.setImg(imagePath != null ? imagePath : "/views/template/assets/images/category/default.jpg");

            categoryService.insertCategory(category, userId);

            session.setAttribute("message", "Thêm danh mục thành công!");
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/category-addition-admin");
        } catch (Exception e) {
            System.out.println("Error in doPost: " + e.getMessage());
            session.setAttribute("error", "Có lỗi xảy ra khi thêm danh mục: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/category-addition-admin");
        }
    }

    private String handleFileUpload(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("avatar");
        if (filePart == null || filePart.getSize() == 0) {
            return null; // Sử dụng ảnh mặc định nếu không có file
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