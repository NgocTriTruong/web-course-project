package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PostService;

import java.io.IOException;
import java.util.List;

@WebServlet("/news")
public class NewsController extends HttpServlet {
    private PostService postService;
    CategoryService categoryService = new CategoryService();

    @Override
    public void init() throws ServletException {
        super.init();
        postService = new PostService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        try {
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
            }
        } catch (NumberFormatException e) {
            page = 1; // Mặc định trang 1 nếu có lỗi
        }

        int pageSize = 5; // Số bài viết mỗi trang
        int totalPosts = postService.getTotalPosts(); // Cần thêm phương thức này trong PostService
        int totalPages = (int) Math.ceil((double) totalPosts / pageSize);
        int offset = (page - 1) * pageSize;

        List<Post> posts = postService.getPostsWithPagination(offset, pageSize); // Cần thêm phương thức này
        //Lay danh muc
        List<Category> categories = categoryService.getAll();

        request.setAttribute("posts", posts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("categoriesData", categories);
        request.getRequestDispatcher("/views/web/tin_tuc/tin_tuc.jsp").forward(request, response);
    }
}