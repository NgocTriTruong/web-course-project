package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PostService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/news/detail")
public class NewsDetailController extends HttpServlet {
    private PostService postService;

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/news");
            return;
        }

        int postId;
        try {
            postId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/news");
            return;
        }

        Optional<Post> postOpt = postService.getPostById(postId);
        if (postOpt.isPresent()) {
            Post post = postOpt.get();
            request.setAttribute("post", post);
            request.getRequestDispatcher("/views/web/tin_tuc/chi_tiet_tin_tuc.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/news");
        }
    }
}