package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Order", value = "/create-order")
public class OrderController extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = new CartService();
        orderService = new OrderService();
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
            List<CartItem> selectedItems = cart.getConfirmedCartItem(); // Gets items with status = 1

            if (selectedItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            double totalPrice = selectedItems.stream()
                    .mapToDouble(CartItem::getTotal)
                    .sum();

            int totalQuantity = selectedItems.stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            System.out.println(address);

            // Create order
            Order order = new Order();
            order.setUserId(user.getId());
            order.setStatus(1); // Initial order status (e.g., Pending)
            order.setAddress(address);
            order.setTotalPrice(totalPrice);
            order.setTotalQuantity(totalQuantity);
            order.setOrderDate(LocalDateTime.now());
            order.setShippingPrice(0.0); // Free shipping as per UI

            int orderId = orderService.insertOrder(order);
            order.setId(orderId);
            // Create order details for selected items
            for (CartItem cartItem : selectedItems) {
                // Create corresponding order detail
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setProductId(cartItem.getProductId());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setTotalPrice(cartItem.getTotal());

                orderService.insertOrderDetails(orderDetail);

                // Remove item from cart
                cart.removeProduct(cartItem.getProductId());
                // Delete from cart_details table
                cartService.deleteCD(cartItem.getProductId(), user.getId());

            }

            // Update session cart
            session.setAttribute("cart", cart);
            session.setAttribute("successOrder", order);
            session.setAttribute("orderItems", selectedItems);
            session.setAttribute("customerInfo", Map.of(
                    "fullName", fullName,
                    "phone", phone,
                    "email", email,
                    "address", address,
                    "deliveryMethod", deliveryMethod,
                    "note", note,
                    "paymentMethod", paymentMethod
            ));

            // Redirect to order success page
            response.sendRedirect(request.getContextPath() + "/order-success");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while processing your order");
            request.getRequestDispatcher("/views/web/order/confirm_order.jsp")
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