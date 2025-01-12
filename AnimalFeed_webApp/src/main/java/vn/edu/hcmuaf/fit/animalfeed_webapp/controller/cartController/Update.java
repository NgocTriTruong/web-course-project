package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cartController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;

import java.io.IOException;

@WebServlet(name = "Update", value = "/update-cart")
public class Update extends HttpServlet {
    CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int productId = -1;
        int quantity = -1;
        int status = -1;

        try {
            productId = Integer.parseInt(request.getParameter("productId"));
            if (request.getParameter("quantity") != null) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            }
            if (request.getParameter("status") != null) {
                status = Integer.parseInt(request.getParameter("status"));
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("/cart");
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        if (quantity != -1) {
            cart.updateQuantity(productId, quantity);
            cartService.updateQuantity(productId, user.getId(), quantity);
        }

        if (status != -1) {
            cart.updateStatus(productId, status);
            cartService.updateStatus(productId, user.getId(), status);
        }

        session.setAttribute("cart", cart);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}