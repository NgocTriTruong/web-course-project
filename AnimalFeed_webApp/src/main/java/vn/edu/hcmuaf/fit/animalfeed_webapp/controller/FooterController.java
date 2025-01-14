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

@WebServlet(name = "FooterController", value = "/footer")
public class FooterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        CategoryService categoryService = new CategoryService();
        ProductService productService = new ProductService();

        String catId = request.getParameter("categoryId");
        int categoryId = (catId != null && !catId.isEmpty()) ? Integer.parseInt(catId) : -1;

        List<Category> categories = categoryService.getAll();
        List<Product> products = (categoryId == -1) ? productService.getAllProducts() : productService.getByCatId(categoryId);

        request.setAttribute("categoriesData", categories);
        request.setAttribute("productsData", products);
        request.getRequestDispatcher("views/web/layout/footer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }

}