package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;

@WebServlet(name = "ProductDetailocntroller", value = "/product-detail")
public class ProductDetailocntroller extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        String pid = request.getParameter("pid");
        ProductService productService = new ProductService();
        Product detail = productService.getDetail(pid);
        request.setAttribute("product", detail);
        request.getRequestDispatcher("product-detail.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }

}