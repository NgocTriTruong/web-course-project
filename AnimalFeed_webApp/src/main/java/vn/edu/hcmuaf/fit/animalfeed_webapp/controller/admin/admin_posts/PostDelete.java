package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_posts;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PostService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/post-delete")
public class PostDelete extends HttpServlet {
    private PostService postService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleDelete(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleDelete(request, response);
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            request.getSession().setAttribute("error", "Vui lòng đăng nhập để thực hiện thao tác này.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
            request.getSession().setAttribute("error", "Bạn không có quyền xóa bài viết (yêu cầu quyền NEWS_MANAGEMENT).");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            int postId = Integer.parseInt(request.getParameter("id"));
            Optional<Post> postOpt = postService.getPostById(postId);
            if (!postOpt.isPresent()) {
                request.getSession().setAttribute("error", "Bài viết không tồn tại.");
                response.sendRedirect(request.getContextPath() + "/post-management");
                return;
            }

            Post post = postOpt.get();
            boolean isAdmin = userService.isAdmin(userId);
            if (!isAdmin && post.getUserId() != userId) {
                request.getSession().setAttribute("error", "Bạn chỉ có thể xóa bài viết do chính bạn tạo.");
                response.sendRedirect(request.getContextPath() + "/post-management");
                return;
            }

            // Gọi deletePost và dùng try-catch để kiểm tra thành công/thất bại
            try {
                System.out.println("Deleting post with ID: " + postId + " by user: " + userId);
                postService.deletePost(postId, userId);
                request.getSession().setAttribute("message", "Xóa bài viết thành công!");
                System.out.println("Post deleted successfully, redirecting to /post-management");
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to delete post with ID: " + postId + ", error: " + e.getMessage());
                request.getSession().setAttribute("error", "Xóa bài viết thất bại: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in handleDelete: " + e.getMessage());
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi xóa bài viết: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/post-management");
    }
}