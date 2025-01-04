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

@WebServlet(name = "ListProductController", value = "/list-product")
public class ListProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        ProductService productService = new ProductService();
        CategoryService categoryService = new CategoryService();

        // Lấy categoryId và trang hiện tại từ request
        String catId = request.getParameter("categoryId");
        String pageParam = request.getParameter("page");

        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        int countPro = productService.getByCatId(Integer.parseInt(catId)).size();
        int endPage = countPro / 16 ;
        if(countPro % 16 != 0){
            endPage++;
        }

        List<Product> products;
        List<Product> totalProducts;

        if (catId != null && !catId.isEmpty()) {
            products = productService.getProductByPage(page, Integer.parseInt(catId));
            totalProducts = productService.getByCatId(Integer.parseInt(catId));
        } else {
            products = productService.getProductByPage(page, 0);
            totalProducts = productService.getAllProducts();
        }

        // Tính toán số trang
//        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        //Lay danh muc
        List<Category> categories = categoryService.getAll();

//        request.setAttribute("categoriesData", categories);
//        request.setAttribute("productsData", products);
//        request.setAttribute("selectedCategoryId", catId);

        request.setAttribute("categoriesData", categories);
        request.setAttribute("productsData", products);
        request.setAttribute("selectedCategoryId", catId);
        request.setAttribute("currentPage", page);
        request.setAttribute("endPage", endPage);
        request.getRequestDispatcher("views/web/each_product/product_pig.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }

}