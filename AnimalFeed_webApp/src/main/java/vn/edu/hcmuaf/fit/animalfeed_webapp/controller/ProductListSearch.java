package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductListSearch", value = "/product-list-search")
public class ProductListSearch extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        ProductService productService = new ProductService();

        // Lấy các tham số từ request
        String keyword = request.getParameter("keyword");
        String description = request.getParameter("description");

        Double minPrice = null;
        Double maxPrice = null;

        try {
            if (request.getParameter("minPrice") != null && !request.getParameter("minPrice").isEmpty()) {
                minPrice = Double.valueOf(request.getParameter("minPrice"));
            }
            if (request.getParameter("maxPrice") != null && !request.getParameter("maxPrice").isEmpty()) {
                maxPrice = Double.valueOf(request.getParameter("maxPrice"));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Xử lý categoryId và phân trang
        String catId = request.getParameter("categoryId");
        String pageParam = request.getParameter("page");

        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int categoryId = (catId != null && !catId.isEmpty()) ? Integer.parseInt(catId) : 0;

        int productsPerPage = 16; // Số sản phẩm trên mỗi trang
        int totalProducts = productService.getByCatId(categoryId).size();
        int endPage = (int) Math.ceil((double) totalProducts / productsPerPage);

        // Tìm kiếm sản phẩm
        List<Product> products = productService.searchProducts(keyword, minPrice, maxPrice, description, categoryId, page, productsPerPage);

        // Lấy danh sách danh mục
        List<Category> categories = categoryService.getAll();

        // Gửi dữ liệu tới JSP
        request.setAttribute("categoriesData", categories);
        request.setAttribute("productsData", products);
        request.setAttribute("selectedCategoryId", catId);
        request.setAttribute("currentPage", page);
        request.setAttribute("endPage", endPage);

        request.getRequestDispatcher("views/web/each_product/product_pig_search.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
