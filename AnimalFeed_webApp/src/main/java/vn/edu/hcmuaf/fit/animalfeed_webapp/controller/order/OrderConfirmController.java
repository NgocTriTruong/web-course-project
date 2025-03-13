package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
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

        // Try to get confirmed items from session (for Buy Now)
        List<CartItem> confirmedItems = (List<CartItem>) session.getAttribute("confirmedItems");
        Double totalPrice = (Double) session.getAttribute("totalPrice");
        Integer totalQuantity = (Integer) session.getAttribute("totalQuantity");

        // If no Buy Now data, fall back to cart
        if (confirmedItems == null || totalPrice == null || totalQuantity == null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            confirmedItems = cart.getConfirmedCartItem();
            if (confirmedItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            totalPrice = cart.getTotalPrice();
            totalQuantity = cart.getTotalQuantity();
        }

        // Set attributes for the JSP
        request.setAttribute("confirmedItems", confirmedItems);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("totalQuantity", totalQuantity);
        request.setAttribute("customerInfo", session.getAttribute("customerInfo"));

        // Forward to the order confirmation page
        request.getRequestDispatcher("/views/web/order/confirm_order.jsp")
                .forward(request, response);
    }
}