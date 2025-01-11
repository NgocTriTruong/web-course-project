package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ProductDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductDetailService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetailController", value = "/product-detail")
public class ProductDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        ProductDetailService productDetailService = new ProductDetailService();
        ProductService productService = new ProductService();
        CategoryService categoryService = new CategoryService();

        String pid = request.getParameter("pid");
        Product product = null;
        ProductDetail productDetail = null;

        if (pid != null && !pid.isEmpty()) {
            try {
                int productId = Integer.parseInt(pid);
                // Lấy thông tin sản phẩm chính
                product = productService.getDetail(productId);

                // Lấy thông tin chi tiết sản phẩm
                productDetail = productDetailService.getDetail(productId);

                if (productDetail == null) {
                    productDetail = new ProductDetail();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return;
            }
        } else {
            return;
        }

        List<Product> relatedProducts = productDetailService.getRelatedProducts(product.getCat_id(), product.getId());

        //Lay danh muc
        List<Category> categories = categoryService.getAll();

        request.setAttribute("product", product);
        request.setAttribute("productDetail", productDetail);
        request.setAttribute("relatedProducts", relatedProducts);
        request.setAttribute("categoriesData", categories);

        request.getRequestDispatcher("views/web/each_product/product_details/product-detail.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }

}