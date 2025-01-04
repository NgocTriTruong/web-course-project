package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cartController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {
    private CartService cartService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Get product ID from request parameter
            String productId = request.getParameter("productId");

            // Check if productId is null or empty
            if (productId == null || productId.isEmpty()) {
                response.sendRedirect("list-product?addCart=false&error=Product ID is missing");
                return;
            }

            // Attempt to parse the productId
            int parsedProductId;
            try {
                parsedProductId = Integer.parseInt(productId);
            } catch (NumberFormatException e) {
                response.sendRedirect("list-product?addCart=false&error=Invalid Product ID format");
                return;
            }

            // Fetch product details
            Product product = productService.getDetail(String.valueOf(parsedProductId));
            if (product == null) {
                response.sendRedirect("list-product?addCart=false&error=Product not found");
                return;
            }

            // Check if user is logged in
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login");
                return;
            }

            // Get or create the cart
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            // Create a CartDetail object and add it to the cart
            CartDetail cartDetail = new CartDetail();
            cartDetail.setUserId(user.getId());
            cartDetail.setProductId(product.getId());
            cartDetail.setQuantity(1);
            cartDetail.setTotal(product.getPrice());
            cartDetail.setStatus(1);

            // Save cart detail to the database
            cartService.insertCD(cartDetail);

            // Add product to cart and update session
            cart.addProduct(product, user.getId());
            session.setAttribute("cart", cart);

            response.sendRedirect("list-product?addCart=true");

        } catch (Exception e) {
            // Log the error and redirect with an error message
            e.printStackTrace();
            response.sendRedirect("list-product?addCart=false&error=" + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}