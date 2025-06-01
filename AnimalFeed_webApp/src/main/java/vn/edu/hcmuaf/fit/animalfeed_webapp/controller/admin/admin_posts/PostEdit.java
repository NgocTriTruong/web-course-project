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

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet("/post-edit")
public class PostEdit extends HttpServlet {
    private PostService postService;
    private UserService userService;
    private static final String UPLOAD_DIRECTORY = "uploads";

    @Override
    public void init() throws ServletException {
        postService = new PostService();
        userService = UserService.getInstance();
    }

    private Post authorizeAndGetPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null || !userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
            request.getSession().setAttribute("error", "Vui lòng đăng nhập và có quyền NEWS_MANAGEMENT để chỉnh sửa bài viết.");
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return null;
        }

        String idParam = request.getParameter("id");
        if (idParam == null) {
            request.getSession().setAttribute("error", "Thiếu ID bài viết.");
            response.sendRedirect(request.getContextPath() + "/post-management");
            return null;
        }

        int postId = Integer.parseInt(idParam);
        Post post = postService.getPostById(postId).orElse(null);

        if (post == null || (!userService.isAdmin(userId) && post.getUserId() != userId)) {
            request.getSession().setAttribute("error", "Bạn không có quyền chỉnh sửa bài viết này.");
            response.sendRedirect(request.getContextPath() + "/post-management");
            return null;
        }

        return post;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Post post = authorizeAndGetPost(request, response);
        if (post == null) return;

        request.setAttribute("post", post);
        request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Post existingPost = authorizeAndGetPost(request, response);
        if (existingPost == null) return;

        Integer userId = (Integer) request.getSession().getAttribute("userId");
        int postId = existingPost.getId();

        String title = request.getParameter("username");
        String content = request.getParameter("content");
        String shortDescription = request.getParameter("shortDescription");
        String statusStr = request.getParameter("status");

        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Tiêu đề không được để trống.");
            request.setAttribute("post", existingPost);
            request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
            return;
        }

        if (content == null || content.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Nội dung không được để trống.");
            request.setAttribute("post", existingPost);
            request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
            return;
        }

        if (statusStr == null) {
            request.setAttribute("errorMessage", "Trạng thái không được để trống.");
            request.setAttribute("post", existingPost);
            request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
            return;
        }

        int status;
        try {
            status = Integer.parseInt(statusStr);
            if (status != 0 && status != 1) throw new IllegalArgumentException();
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Trạng thái không hợp lệ.");
            request.setAttribute("post", existingPost);
            request.getRequestDispatcher("/views/admin/postEdit.jsp").forward(request, response);
            return;
        }

        String imgPath = existingPost.getImg();
        Part filePart = request.getPart("avatar");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);

            imgPath = UPLOAD_DIRECTORY + "/" + fileName;
            filePart.write(uploadPath + "/" + fileName);
        }

        Post updatedPost = new Post();
        updatedPost.setId(postId);
        updatedPost.setTitle(title);
        updatedPost.setContent(content);
        updatedPost.setImg(imgPath);
        updatedPost.setUserId(userId);
        updatedPost.setStatus(status);

        try {
            postService.updatePost(updatedPost, userId);
            request.getSession().setAttribute("message", "Cập nhật bài viết thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Cập nhật bài viết thất bại: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/post-management");
    }
}
