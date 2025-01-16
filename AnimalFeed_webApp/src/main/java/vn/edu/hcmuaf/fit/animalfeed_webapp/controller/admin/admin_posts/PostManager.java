package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_posts;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PostService;

import java.io.IOException;
import java.util.List;

@WebServlet("/post-admin")
public class PostManager extends HttpServlet {
    PostService postService = new PostService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("PostManager Servlet: doGet called");
        try {
            List<Post> posts = postService.getAllPosts();
            System.out.println("Posts loaded: " + (posts != null ? posts.size() : "null"));
            request.setAttribute("posts", posts);

            // Log the JSP path
            String jspPath = "/views/admin/postManagement.jsp";
            System.out.println("Forwarding to: " + jspPath);

            request.getRequestDispatcher(jspPath).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}