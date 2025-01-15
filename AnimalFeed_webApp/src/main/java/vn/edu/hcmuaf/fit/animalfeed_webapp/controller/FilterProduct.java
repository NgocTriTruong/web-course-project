package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "FilterProduct", value = "/filter-product")
public class FilterProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();

        String brand = request.getParameter("brand");
        String priceOrder = request.getParameter("priceOrder");

        // Lấy danh sách sản phẩm theo bộ lọc
        List<Product> productsFillter = productService.getFilteredProducts(brand, priceOrder);

        // Lấy danh sách thương hiệu (giả sử đã có sẵn trong DAO)
        List<String> brandList = productService.getBrands();

        // Gửi dữ liệu tới JSP
        request.setAttribute("productsFillter", productsFillter);
        request.setAttribute("brandList", brandList);

        response.sendRedirect("/list-product");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}