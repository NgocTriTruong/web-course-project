package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_posts;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PostService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
@WebServlet("/post-edit")
public class PostEdit extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null || !userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
            request.getSession().setAttribute("error", "Vui lòng đăng nhập và có quyền NEWS_MANAGEMENT để chỉnh sửa bài viết.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        int postId = Integer.parseInt(request.getParameter("id"));
        Post post = postService.getPostById(postId).orElse(null);
        if (post == null || post.getUserId() != userId) {
            request.getSession().setAttribute("error", "Bạn không có quyền chỉnh sửa bài viết này.");
            response.sendRedirect(request.getContextPath() + "/post-management");
            return;
        }
        request.setAttribute("post", post);
        request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("doPost called for /post-edit at " + new java.util.Date());
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null || !userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
                request.getSession().setAttribute("error", "Vui lòng đăng nhập và có quyền NEWS_MANAGEMENT để chỉnh sửa bài viết.");
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }

            int postId = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("username");
            String content = request.getParameter("content");
            String shortDescription = request.getParameter("shortDescription");
            String statusStr = request.getParameter("status");

            System.out.println("Received form data - title: " + title + ", content: " + content + ", status: " + statusStr);

            if (title == null || title.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Tiêu đề không được để trống.");
                request.setAttribute("post", postService.getPostById(postId).orElse(null));
                request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
                return;
            }
            if (content == null || content.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Nội dung không được để trống.");
                request.setAttribute("post", postService.getPostById(postId).orElse(null));
                request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
                return;
            }
            if (statusStr == null) {
                request.setAttribute("errorMessage", "Trạng thái không được để trống.");
                request.setAttribute("post", postService.getPostById(postId).orElse(null));
                request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
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
                request.setAttribute("post", postService.getPostById(postId).orElse(null));
                request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
                return;
            }

            Post existingPost = postService.getPostById(postId).orElse(null);
            if (existingPost == null || existingPost.getUserId() != userId) {
                request.getSession().setAttribute("error", "Bạn không có quyền chỉnh sửa bài viết này.");
                response.sendRedirect(request.getContextPath() + "/post-management");
                return;
            }

            String imgPath = existingPost.getImg();
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
            post.setId(postId);
            post.setTitle(title);
            post.setContent(content);
            post.setImg(imgPath);
            post.setUserId(userId);
            post.setStatus(status);

            System.out.println("Updating post with ID: " + postId + ", title: " + title);
            try {
                postService.updatePost(post, userId);
                request.getSession().setAttribute("message", "Cập nhật bài viết thành công!");
                System.out.println("Redirecting to /post-management after successful update at " + new java.util.Date());
                response.sendRedirect(request.getContextPath() + "/post-management");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Post update failed for ID: " + postId + ", error: " + e.getMessage());
                request.getSession().setAttribute("error", "Cập nhật bài viết thất bại: " + e.getMessage());
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
