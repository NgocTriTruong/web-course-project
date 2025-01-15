package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "ListProductController", value = "/list-product")
public class ListProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        ProductService productService = new ProductService();
        CategoryService categoryService = new CategoryService();

        // **Cập nhật discount_id trước khi lấy danh sách sản phẩm**
        productService.updateDiscount();

        // Lấy categoryId và trang hiện tại từ request
        String catId = request.getParameter("categoryId");
        String pageParam = request.getParameter("page");
        String discountPageParam = request.getParameter("discountPage");
        String sellingPageParam = request.getParameter("sellingPage");
        String newPageParam = request.getParameter("newPage");

        // Xử lý phân trang cho tất cả sản phẩm
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
        int categoryId = (catId != null && !catId.isEmpty()) ? Integer.parseInt(catId) : 0;

        int countPro = productService.getByCatId(categoryId).size();
        int endPage = countPro / 16 ;
        if(countPro % 16 != 0){
            endPage++;
        }

        // Xử lý phân trang cho sản phẩm giảm giá
        int discountPage = (discountPageParam != null && !discountPageParam.isEmpty()) ?
                Integer.parseInt(discountPageParam) : 1;
        int countDiscountPro = productService.getByCatIdOfDiscount(categoryId).size();
        int endDiscountPage = countDiscountPro / 12;
        if(countDiscountPro % 12 != 0){
            endDiscountPage++;
        }

        // Lay danh sach san pham theo trang
        List<Product> products = productService.getProductByPage(page, categoryId);

        //Lay danh muc
        List<Category> categories = categoryService.getAll();

        //Hiển thị sản phẩm giảm giá theo trang
        List<ProductWithDiscountDTO> discountProducts  = productService.getProductByPageOfDiscount(discountPage, categoryId);

        //hien thi san pham moi
        List<Product> newProducts = productService.getNewProduct(categoryId);

        //hiển th sản phẩm bán chạy
        List<Product> getBestSellingProducts = productService.getBestSellingProducts(categoryId);


        request.setAttribute("categoriesData", categories);
        request.setAttribute("productsData", products);
        request.setAttribute("selectedCategoryId", catId);
        request.setAttribute("currentPage", page);
        request.setAttribute("endPage", endPage);
        request.setAttribute("discountPage", discountPage);
        request.setAttribute("endDiscountPage", endDiscountPage);
        request.setAttribute("discountProducts", discountProducts);


        //hien thi san pham moi
        request.setAttribute("newProducts", newProducts);
        //hiển thị sản phẩm bán chạy
        request.setAttribute("bestSellingProducts", getBestSellingProducts);

        request.getRequestDispatcher("views/web/each_product/product_pig.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }

}