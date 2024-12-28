package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CartDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.io.IOException;

@WebServlet(name = "RemoveCart", value = "/remove-cart")
public class RemoveCart extends HttpServlet {
    private CartDetailDao cartDetailDao;

    @Override
    public void init() throws ServletException {
        cartDetailDao = new CartDetailDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters
            int cartDetailId = Integer.parseInt(request.getParameter("cartDetailId"));

            // Get user from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                request.setAttribute("error", "Please login first");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            // Get cart detail to verify ownership and get product ID
            CartDetail cartDetail = cartDetailDao.getCDById(cartDetailId);

            if (cartDetail == null || cartDetail.getUserId() != user.getId()) {
                request.setAttribute("error", "Invalid cart item");
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
                return;
            }

            // Remove from session cart
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                cart.removeProduct(cartDetail.getProductId());
                session.setAttribute("cart", cart);
            }

            // Remove from database
            cartDetailDao.deleteCD(cartDetailId);

            // Redirect back to cart page with success message
            response.sendRedirect("cart.jsp?removed=true");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid cart item ID");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error removing item: " + e.getMessage());
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Handle removal via GET request (e.g., from a direct link)
        doPost(request, response);
    }
}