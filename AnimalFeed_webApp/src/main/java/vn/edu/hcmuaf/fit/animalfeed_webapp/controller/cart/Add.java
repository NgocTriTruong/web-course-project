package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CartDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {
    private CartDetailDao cartDetailDao;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        cartDetailDao = new CartDetailDao();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get product details
            String productId =  request.getParameter("productId");
            Product product = productService.getDetail(productId);

            if (product == null) {
                response.sendRedirect("list-product?addCart=false");
                return;
            }

            // Get or create cart session
            HttpSession session = request.getSession(true);
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            // Get user from session (you should implement proper user session management)
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login"); // Redirect to login if user not logged in
                return;
            }

            // Add to cart session
            cart.addProduct(product, user.getId());
            session.setAttribute("cart", cart);

            // Create cart detail for database
            CartDetail cartDetail = new CartDetail();
            cartDetail.setUserId(user.getId());
            cartDetail.setProductId(product.getId());
            cartDetail.setQuantity(1);
            cartDetail.setTotal(product.getPrice());
            cartDetail.setStatus(1);

            // Save to database
            cartDetailDao.insertCD(cartDetail);

            response.sendRedirect("list-product?addCart=true");

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