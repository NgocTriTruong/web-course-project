package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_posts;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PostService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet(name = "PostAdd", value = "/post-add")
public class PostAdd extends HttpServlet {
    private PostService postService;
    private UserService userService;
    private static final String UPLOAD_DIRECTORY = "/views/template/assets/images/news";

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                request.getSession().setAttribute("error", "Vui lòng đăng nhập để thực hiện thao tác này.");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if (!userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
                request.getSession().setAttribute("error", "Bạn không có quyền truy cập thêm tin tức (yêu cầu quyền NEWS_MANAGEMENT).");
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            System.out.println("Forwarding to postAddition.jsp for userId: " + userId);
            request.getRequestDispatcher("/views/admin/postAddition.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in doGet: " + e.getMessage());
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi truy cập trang thêm bài viết: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/postAddition.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("doPost called for /post-add at " + new Date());
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                request.getSession().setAttribute("error", "Vui lòng đăng nhập để thực hiện thao tác này.");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if (!userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
                request.getSession().setAttribute("error", "Bạn không có quyền thêm tin tức (yêu cầu quyền NEWS_MANAGEMENT).");
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            String title = request.getParameter("username");
            String content = request.getParameter("content");
            String shortDescription = request.getParameter("shortDescription");
            String statusStr = request.getParameter("status");

            System.out.println("Received form data - title: " + title + ", content: " + content + ", status: " + statusStr);

            if (title == null || title.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Tiêu đề không được để trống.");
                request.getRequestDispatcher("/views/admin/postAddition.jsp").forward(request, response);
                return;
            }
            if (content == null || content.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Nội dung không được để trống.");
                request.getRequestDispatcher("/views/admin/postAddition.jsp").forward(request, response);
                return;
            }
            if (statusStr == null) {
                request.setAttribute("errorMessage", "Trạng thái không được để trống.");
                request.getRequestDispatcher("/views/admin/postAddition.jsp").forward(request, response);
                return;
            }

            int status;
            try {
                status = Integer.parseInt(statusStr);
                if (status != 0 && status != 1) {
                    throw new IllegalArgumentException("Trạng thái không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Trạng thái không hợp lệ.");
                request.getRequestDispatcher("/views/admin/postAddition.jsp").forward(request, response);
                return;
            }

            String imgPath = "";
            Part filePart = request.getPart("avatar");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
                Path uploadDir = Paths.get(uploadPath);
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                imgPath = UPLOAD_DIRECTORY + "/" + fileName;
                filePart.write(uploadPath + "/" + fileName);
                System.out.println("File uploaded to: " + imgPath);
            }

            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setImg(imgPath);
            post.setCreateDate(new Date());
            post.setUserId(userId);
            post.setStatus(status);

            System.out.println("Adding post with title: " + title + ", userId: " + userId);
            try {
                postService.addPost(post, userId);
                request.getSession().setAttribute("message", "Thêm bài viết thành công!");
                System.out.println("Redirecting to /post-management after successful add at " + new Date());
                response.sendRedirect(request.getContextPath() + "/post-management");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Post addition failed for title: " + title + ", error: " + e.getMessage());
                request.getSession().setAttribute("error", "Đã xảy ra lỗi khi thêm bài viết: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/post-management");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in doPost: " + e.getMessage());
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi xử lý yêu cầu: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/post-management");
        }
    }
}