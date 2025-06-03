package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.DiscountService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductManagerAdmin", value = "/product-manager")
public class ProductManagerAdmin extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;
    private DiscountService discountService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
        discountService = new DiscountService();
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            session.setAttribute("error", "Vui lòng đăng nhập để truy cập.");
            response.sendRedirect("login");
            return;
        }

        try {
            List<Product> products = productService.getAllProductOfAdmin();
            List<Category> categories = categoryService.getAll();
            List<Discount> discounts = discountService.getAll();

            String pageParam = request.getParameter("page");
            int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
            int countPro = products.size();
            int endPage = (int) Math.ceil((double) countPro / 10);

            int startIndex = (page - 1) * 10;
            int endIndex = Math.min(startIndex + 10, countPro);
            List<Product> productsForPage = products.subList(startIndex, endIndex);

            request.setAttribute("products", productsForPage);
            request.setAttribute("categories", categories);
            request.setAttribute("discountsData", discounts);
            request.setAttribute("currentPage", page);
            request.setAttribute("endPage", endPage);

            request.getRequestDispatcher("views/admin/productManagement.jsp").forward(request, response);

        } catch (Exception e) {
            session.setAttribute("error", "Không thể tải trang quản lý sản phẩm.");
            response.sendRedirect("dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}