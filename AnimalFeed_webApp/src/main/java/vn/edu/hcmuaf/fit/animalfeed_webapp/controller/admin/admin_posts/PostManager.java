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
import java.util.List;

@WebServlet("/post-management")
public class PostManager extends HttpServlet {
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
        System.out.println("PostManager Servlet: doGet called at " + new java.util.Date());

        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                request.getSession().setAttribute("error", "Vui lòng đăng nhập để xem danh sách bài viết.");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if (!userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
                request.getSession().setAttribute("error", "Bạn không có quyền xem danh sách tin tức (yêu cầu quyền NEWS_MANAGEMENT).");
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            String keyword = (String) request.getSession().getAttribute("searchKeyword");
            List<Post> posts;
            if (keyword != null && !keyword.trim().isEmpty()) {
                System.out.println("Loading posts with keyword: " + keyword);
                posts = postService.searchPosts(keyword);
            } else {
                posts = postService.getAllPosts();
            }
            request.setAttribute("posts", posts);
            request.setAttribute("keyword", keyword != null ? keyword : "");

            System.out.println("Posts loaded: " + (posts != null ? posts.size() : "null") + ", Last post ID: " +
                    (posts != null && !posts.isEmpty() ? posts.get(0).getId() : "N/A"));
            request.getRequestDispatcher("/views/admin/postManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi tải danh sách bài viết: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/post-management");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                request.getSession().setAttribute("error", "Vui lòng đăng nhập để thực hiện thao tác này.");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            if (!userService.hasPermission(userId, "NEWS_MANAGEMENT")) {
                request.getSession().setAttribute("error", "Bạn không có quyền truy cập (yêu cầu quyền NEWS_MANAGEMENT).");
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            String action = request.getParameter("action");
            if ("search".equals(action)) {
                String keyword = request.getParameter("keyword");
                request.getSession().setAttribute("searchKeyword", keyword);
            }
            response.sendRedirect(request.getContextPath() + "/post-management");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã xảy ra lỗi khi xử lý yêu cầu: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/post-management");
        }
    }
}