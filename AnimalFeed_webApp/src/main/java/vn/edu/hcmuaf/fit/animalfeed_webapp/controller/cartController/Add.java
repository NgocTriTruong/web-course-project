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
            int productId =  Integer.parseInt(request.getParameter("productId"));
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

            CartDetail cartDetail = new CartDetail();
            cartDetail.setUserId(user.getId());
            cartDetail.setProductId(product.getId());
            cartDetail.setQuantity(1);
            cartDetail.setTotal(cart.getDiscountedPrice(product));
            cartDetail.setStatus(0);

            if (cartService.getCDById(productId, user.getId())) {
                cartService.increaseQuantity(cartDetail.getProductId(), cartDetail.getUserId());
            } else {
                cartService.insertCD(cartDetail);
            }
            cart.addProduct(product, user.getId());
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

    }
}