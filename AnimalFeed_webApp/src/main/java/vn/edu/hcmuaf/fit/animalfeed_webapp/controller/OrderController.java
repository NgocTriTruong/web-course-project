package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "Order", value = "/create-order")
public class OrderController extends HttpServlet {
    private OrderService orderService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();
        orderService = new OrderService();
        cartService = new CartService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Get form data
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String deliveryMethod = request.getParameter("deliveryMethod");
            String address = buildAddress(request);
            String note = request.getParameter("note");
            String paymentMethod = request.getParameter("paymentMethod");

            // Get cart data
            Cart cart = (Cart) session.getAttribute("cart");
            List<CartDetail> selectedItems = cart.getConfirmedCartItem(); // Gets items with status = 1

            if (selectedItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            double totalPrice = selectedItems.stream()
                    .mapToDouble(CartDetail::getTotal)
                    .sum();

            int totalQuantity = selectedItems.stream()
                    .mapToInt(CartDetail::getQuantity)
                    .sum();

            // Create order
            Order order = new Order();
            order.setUserId(user.getId());
            order.setStatus(1); // Initial order status (e.g., Pending)
            order.setAddress(address);
            order.setTotalPrice(totalPrice);
            order.setTotalQuantity(totalQuantity);
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setShippingPrice(0.0); // Free shipping as per UI

            // Insert order and get generated ID
            orderService.insertOrder(order);

            // Create order details for selected items
            for (CartDetail cartItem : selectedItems) {
                // Remove item from cart
                cart.removeProduct(cartItem.getProductId());

                // Delete from cart_details table
                cartService.deleteCD(cartItem.getProductId(), user.getId());

                // Create corresponding order detail
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(order.getId());
                orderDetail.setProductId(cartItem.getProductId());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setTotalPrice(cartItem.getTotal());

                // Insert order detail
                orderService.insertOrderDetail(orderDetail);
            }

            // Update session cart
            session.setAttribute("cart", cart);

            // Redirect to order success page
            response.sendRedirect(request.getContextPath() + "/order-success");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your order");
            request.getRequestDispatcher("/views/web/confirm_order.jsp")
                    .forward(request, response);
        }
    }

    private String buildAddress(HttpServletRequest request) {
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        String ward = request.getParameter("ward");
        String addressDetails = request.getParameter("addressDetails");

        return String.format("%s, %s, %s, %s",
                addressDetails, ward, district, province);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}