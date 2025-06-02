package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.category_management;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ActionLogDao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

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

    private boolean hasCategoryManagementPermission(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("Checking permission for userId: " + userId + ", Session: " + session);
        if (userId == null) {
            System.out.println("UserId is null in session");
            return false;
        }
        boolean hasPermission = userService.hasPermission(userId, "CATEGORY_MANAGEMENT");
        System.out.println("User Role: " + (userService.getUserById(userId) != null ? userService.getUserById(userId).getRole() : "null")
                + ", Sub Role: " + (userService.getUserById(userId) != null ? userService.getUserById(userId).getSub_role() : "null")
                + ", Has CATEGORY_MANAGEMENT permission: " + hasPermission);
        return hasPermission;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        System.out.println("doGet - Session: " + session + ", userId: " + session.getAttribute("userId")
                + ", Session Attributes: " + (session != null ? session.getAttributeNames().toString() : "null"));

        if (session == null || session.getAttribute("userId") == null) {
            System.out.println("Session or userId is null, redirecting to login");
            session = request.getSession(true);
            session.setAttribute("error", "Vui lòng đăng nhập để truy cập.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!hasCategoryManagementPermission(session)) {
            System.out.println("No permission, redirecting to category-management-admin");
            session.setAttribute("error", "Bạn không có quyền truy cập trang này (yêu cầu quyền CATEGORY_MANAGEMENT).");
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
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

        if (!hasCategoryManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền thực hiện thao tác này.");
            response.sendRedirect(request.getContextPath() + "/category-management-admin");
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

            // Ghi log hành động
            ActionLog actionLog = new ActionLog();
            actionLog.setUser_id(userId);
            actionLog.setAction_type("CREATE");
            actionLog.setEntity_type("CATEGORY");
            actionLog.setEntity_id(category.getId()); // Giả định category.getId() được gán sau khi insert
            actionLog.setCreated_at(new Date());
            actionLog.setDescription("User " + userId + " created category " + name);
            actionLog.setBefore_data("null");
            actionLog.setAfter_data(category.toString());
            ActionLogDao.logAction(actionLog);

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