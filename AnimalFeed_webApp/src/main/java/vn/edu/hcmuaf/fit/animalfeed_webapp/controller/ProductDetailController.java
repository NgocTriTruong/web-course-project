package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.DiscountDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetailController", value = "/product-detail")
public class ProductDetailController extends HttpServlet {
    private ProductDao productDao;
    private DiscountDao discountDao;

    @Override
    public void init() throws ServletException {
        productDao = new ProductDao();
        discountDao = new DiscountDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();
        //Lay danh muc
        List<Category> categories = categoryService.getAll();
        request.setAttribute("categoriesData", categories);
        try {
            // Get product ID from request
            int productId = Integer.parseInt(request.getParameter("pid"));
            Product product = productDao.getById(productId);

            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            // Set product in request scope
            request.setAttribute("product", product);

            // Fetch discount if applicable
            if (product.getDiscountId() > 1) {
                Discount discount = discountDao.getDiscountById(productId);
                if (discount == null) {
                    // Handle case where discount_id is invalid
                    product.setDiscountId(1); // Fallback to no discount
                } else {
                    request.setAttribute("discounts", discount);
                }
            }

            // Forward to JSP
            request.getRequestDispatcher("views/web/each_product/product_details/product-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request");
        }
    }
}