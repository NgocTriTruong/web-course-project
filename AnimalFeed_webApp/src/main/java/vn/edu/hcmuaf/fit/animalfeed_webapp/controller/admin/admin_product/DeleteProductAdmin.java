package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_product;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;

@WebServlet(name = "DeleteProductAdmin", value = "/delete-product")
public class DeleteProductAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        String productId = request.getParameter("productId");

        // Lấy userId từ session
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        int id = Integer.parseInt(productId.trim());

        productService.deleteProduct(id, userId);

        response.sendRedirect("product-manager");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}