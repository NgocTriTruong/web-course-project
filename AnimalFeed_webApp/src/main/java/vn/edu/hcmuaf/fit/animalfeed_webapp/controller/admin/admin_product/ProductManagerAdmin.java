package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.DiscountDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.DiscountService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductManagerAdmin", value = "/product-manager")
public class ProductManagerAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        CategoryService categoryService = new CategoryService();
        DiscountService discountService = new DiscountService();

        List<Product> products = productService.getAllProductOfAdmin();
        List<Category> categories = categoryService.getAll();
        List<Discount> discounts = discountService.getAll();

        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int countPro = productService.getAllProductOfAdmin().size();
        // Tính số trang cuối cùng
        int endPage = (int) Math.ceil((double) countPro / 10);

        // Lấy sản phẩm cho trang hiện tại
        int startIndex = (page - 1) * 10;
        int endIndex = Math.min(startIndex + 10, countPro);
        List<Product> productsForPage = products.subList(startIndex, endIndex);

        if (discounts == null || discounts.isEmpty()) {
            System.out.println("Discount list is empty or null");
        }

        request.setAttribute("products", productsForPage);
        request.setAttribute("categories", categories);
        request.setAttribute("discountsData", discounts);

        request.setAttribute("currentPage", page);
        request.setAttribute("endPage", endPage);

        request.getRequestDispatcher("views/admin/productManagement.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}