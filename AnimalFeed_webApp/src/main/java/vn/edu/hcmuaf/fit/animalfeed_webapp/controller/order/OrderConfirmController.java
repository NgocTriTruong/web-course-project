package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name="order-confirm", value="/order-confirm")
public class OrderConfirmController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Get only the confirmed items (status = 1)
        List<CartItem> confirmedItems = cart.getConfirmedCartItem();

        if (confirmedItems.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Calculate total price for confirmed items
        double totalPrice = cart.getTotalPrice();

        int totalQuantity = cart.getTotalQuantity();

        // Set attributes for the JSP
        request.setAttribute("confirmedItems", confirmedItems);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("totalQuantity", totalQuantity);

        // Forward to the order confirmation page
        request.getRequestDispatcher("/views/web/order/confirm_order.jsp")
                .forward(request, response);
    }
}