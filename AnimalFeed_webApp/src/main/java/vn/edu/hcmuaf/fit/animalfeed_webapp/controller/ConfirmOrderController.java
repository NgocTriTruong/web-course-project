package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CartDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderConfirmation", value = "/order-confirm")
public class ConfirmOrderController extends HttpServlet {
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getCartDetails().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        List<CartDetail> confirmedItems = cart.getConfirmedCartItem();

        request.setAttribute("confirmedItems", confirmedItems);
        request.setAttribute("totalPrice", cart.getTotalPrice());
        request.setAttribute("totalQuantity", cart.getTotalQuantity());

        // Forward to confirmation page
        request.getRequestDispatcher("/views/web/confirm_order.jsp")
                .forward(request, response);
    }
}