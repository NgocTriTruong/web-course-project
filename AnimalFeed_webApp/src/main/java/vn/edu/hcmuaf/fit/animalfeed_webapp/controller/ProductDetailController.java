package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.DiscountDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductDetailController", value = "/product-detail")
public class ProductDetailController extends HttpServlet {
    private ProductService productService;
    private DiscountDao discountDao;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        discountDao = new DiscountDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get product ID from request
            int productId = Integer.parseInt(request.getParameter("pid"));
            Product product = productService.getProductById(productId);

            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                return;
            }

            // Set product in request scope
            request.setAttribute("product", product);

            // Fetch discount if applicable
            if (product.getDiscountId() > 1) {
                Discount discount = discountDao.getDiscountById(product.getDiscountId()); // Fix: Use discountId, not productId
                if (discount == null) {
                    // Handle case where discount_id is invalid
                    product.setDiscountId(1); // Fallback to no discount
                } else {
                    request.setAttribute("discounts", discount);
                }
            }

            // Fetch related products based on category
            List<Product> relatedProducts = productService.getRelatedProducts(product.getCat_id(), productId);
            request.setAttribute("relatedProducts", relatedProducts);

            // Forward to JSP
            request.getRequestDispatcher("/views/web/each_product/product_details/product-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing your request");
        }
    }
}