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

        if (discounts == null || discounts.isEmpty()) {
            System.out.println("Discount list is empty or null");
        }

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("discountsData", discounts);
        request.getRequestDispatcher("views/admin/productManagement.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}