package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cartController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;

import java.io.IOException;

@WebServlet(name = "RemoveSelected", value = "/remove-selected")
public class RemoveSelected extends HttpServlet {
    private CartService cartService = new CartService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String[] productIds = request.getParameterValues("productIds");
        if (productIds != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            for (String productIdStr : productIds) {
                try {
                    int productId = Integer.parseInt(productIdStr);
                    cart.removeProduct(productId);
                    cartService.deleteCD(productId, user.getId());
                } catch (NumberFormatException e) {
                    // Skip invalid product IDs
                    continue;
                }
            }

            session.setAttribute("cart", cart);
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
