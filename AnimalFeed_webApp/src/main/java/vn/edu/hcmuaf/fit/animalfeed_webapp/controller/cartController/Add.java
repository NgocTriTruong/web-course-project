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
            // Get product details
            int productId = Integer.parseInt(request.getParameter("productId"));

            // Get quantity, default to 1 if not specified
            int quantity = 1;
            String quantityParam = request.getParameter("quantity");
            if (quantityParam != null && !quantityParam.isEmpty()) {
                quantity = Math.max(1, Integer.parseInt(quantityParam));
            }

            Product product = productService.getDetail(productId);

            if (product == null) {
                response.sendRedirect("list-product?addCart=false");
                return;
            }

            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login");
                return;
            }

            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            session.setAttribute("cart", cart);

            // Create CartDetail with the specified quantity
            CartDetail cartDetail = new CartDetail();
            cartDetail.setUserId(user.getId());
            cartDetail.setProductId(product.getId());
            cartDetail.setQuantity(quantity);
            cartDetail.setTotal(cart.getDiscountedPrice(product) * quantity);
            cartDetail.setStatus(0);

            // Check if product already exists in cart
            if (cartService.getCDById(productId, user.getId())) {
                // If exists, update the quantity
                cartService.updateQuantity(productId, user.getId(), quantity);
            } else {
                // If not, insert new cart detail
                cartService.insertCD(cartDetail);
            }

            // Add product to cart session
            cart.addProduct(product, user.getId(), quantity);

            session.setAttribute("cart", cart);

            response.sendRedirect("product-detail?pid=" + productId);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            response.sendRedirect("list-product?addCart=false&error=" + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // No post method implementation needed
    }
}