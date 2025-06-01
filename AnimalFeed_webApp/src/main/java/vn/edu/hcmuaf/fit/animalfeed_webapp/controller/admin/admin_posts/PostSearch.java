package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_posts;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PostService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/post-admin")
public class PostSearch extends HttpServlet {
    private PostService postService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();
        userService = UserService.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null || !userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }

            String action = request.getParameter("action");
            String keyword = request.getParameter("keyword");

            if ("search".equals(action) && keyword != null && !keyword.trim().isEmpty()) {
                // Tìm kiếm bài viết dựa trên keyword
                List<?> posts = postService.searchPosts(keyword); // Giả định PostService có phương thức này
                request.setAttribute("posts", posts);
                request.setAttribute("keyword", keyword); // Giữ từ khóa để hiển thị lại trong input
            } else {
                // Nếu không có keyword, lấy tất cả bài viết
                request.setAttribute("posts", postService.getAllPosts());
                request.setAttribute("keyword", ""); // Xóa từ khóa cũ
            }

            // Chuyển hướng về trang quản lý
            request.getRequestDispatcher("/views/admin/postManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi tìm kiếm: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/post-management");
        }
    }
}
