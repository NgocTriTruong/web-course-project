package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cartController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;

import java.io.IOException;

@WebServlet(name = "RemoveCart", value = "/remove-cart")
public class Remove extends HttpServlet {
    private CartService cartService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = -1;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            response.sendRedirect("/cart");
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            request.setAttribute("error", "Please login first");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        cart.removeProduct(productId);

        session.setAttribute("cart", cart);

        cartService.deleteCD(productId, user.getId());

        response.sendRedirect("cart.jsp?removed=true");

        response.sendRedirect("/cart");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

}