package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.CartDetailDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ProductDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.io.IOException;

@WebServlet(name = "UpdateCart", value = "/update-cart")
public class Update extends HttpServlet {
    private CartDetailDao cartDetailDao;
    private ProductDao productDao;

    @Override
    public void init() throws ServletException {
        cartDetailDao = new CartDetailDao();
        productDao = new ProductDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get parameters
            int cartDetailId = Integer.parseInt(request.getParameter("cartDetailId"));
            String action = request.getParameter("action"); // "updateQuantity" or "updateStatus"

            // Get user from session
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user == null) {
                request.setAttribute("error", "Please login first");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            // Get cart detail
            CartDetail cartDetail = cartDetailDao.getCDById(cartDetailId);

            if (cartDetail == null || cartDetail.getUserId() != user.getId()) {
                request.setAttribute("error", "Invalid cart item");
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
                return;
            }

            switch (action) {
                case "updateQuantity":
                    handleQuantityUpdate(request, response, cartDetail);
                    break;

                case "updateStatus":
                    handleStatusUpdate(request, response, cartDetail);
                    break;

                default:
                    request.setAttribute("error", "Invalid action");
                    request.getRequestDispatcher("/cart.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        }
    }

    private void handleQuantityUpdate(HttpServletRequest request,
                                      HttpServletResponse response,
                                      CartDetail cartDetail) throws ServletException, IOException {
        try {
            int newQuantity = Integer.parseInt(request.getParameter("quantity"));

            // Validate quantity
            Product product = productDao.getById(cartDetail.getProductId());
            if (product == null || newQuantity > product.getQuantity()) {
                request.setAttribute("error", "Invalid quantity or insufficient stock");
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
                return;
            }

            // Update quantity in database
            cartDetailDao.updateQuantity(cartDetail.getId(), newQuantity);

            // Update total price
            double newTotal = product.getPrice() * newQuantity;
            cartDetail.setTotal(newTotal);

            // Update session cart
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                cart.updateQuantity(product.getId(), newQuantity);
                session.setAttribute("cart", cart);
            }

            request.setAttribute("message", "Quantity updated successfully");
            response.sendRedirect("cart.jsp");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid quantity format");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        }
    }

    private void handleStatusUpdate(HttpServletRequest request,
                                    HttpServletResponse response,
                                    CartDetail cartDetail) throws ServletException, IOException {
        try {
            int newStatus = Integer.parseInt(request.getParameter("status"));

            // Validate status (assuming valid status values are 0-3)
            if (newStatus < 0 || newStatus > 3) {
                request.setAttribute("error", "Invalid status value");
                request.getRequestDispatcher("/cart.jsp").forward(request, response);
                return;
            }

            // Update status in database
            cartDetail.setStatus(newStatus);
//            cartDetailDao.updateStatus(cartDetail.getId(), newStatus);

            request.setAttribute("message", "Status updated successfully");
            response.sendRedirect("cart.jsp");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid status format");
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to cart page
        response.sendRedirect("cart.jsp");
    }
}