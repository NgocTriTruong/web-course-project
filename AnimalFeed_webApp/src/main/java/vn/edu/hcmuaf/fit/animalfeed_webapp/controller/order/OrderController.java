package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PaymentService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Order", value = "/create-order")
public class OrderController extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;
    private PaymentService paymentService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = new CartService();
        orderService = new OrderService();
        paymentService = new PaymentService();
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
            List<CartItem> selectedItems = new ArrayList<>();

            // Check if there are items in the cart or use confirmedItems from session (for Buy Now)
            if (cart != null && !cart.getConfirmedCartItem().isEmpty()) {
                selectedItems.addAll(cart.getConfirmedCartItem());
            } else {
                // For Buy Now scenario, use confirmedItems from session
                List<CartItem> confirmedItems = (List<CartItem>) session.getAttribute("confirmedItems");
                if (confirmedItems != null && !confirmedItems.isEmpty()) {
                    selectedItems.addAll(confirmedItems);
                } else {
                    response.sendRedirect(request.getContextPath() + "/cart");
                    return;
                }
            }

            if (selectedItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Calculate total price and quantity
            double totalPrice = selectedItems.stream()
                    .mapToDouble(CartItem::getTotal)
                    .sum();

            int totalQuantity = selectedItems.stream()
                    .mapToInt(CartItem::getQuantity)
                    .sum();

            System.out.println("Address: " + address);

            // Create order
            Order order = new Order();
            order.setUserId(user.getId());
            order.setStatus(1); // Initial order status (e.g., Pending)
            order.setAddress(address);
            order.setTotalPrice(totalPrice);
            order.setTotalQuantity(totalQuantity);
            order.setOrderDate(LocalDateTime.now());

            double shippingFee = Double.parseDouble(request.getParameter("shippingFee"));
            order.setShippingPrice(shippingFee);
            order.setTotalPrice(totalPrice + shippingFee);

            int orderId = orderService.insertOrder(order);
            order.setId(orderId);

            // Create payment
            Payment payment = new Payment();
            payment.setPayDate(LocalDateTime.now());
            payment.setMethod(paymentMethod);
            payment.setOrderId(orderId);
            payment.setUserId(user.getId());
            payment.setStatus(0);

            paymentService.addPayment(payment);

            // Create order details for selected items
            for (CartItem cartItem : selectedItems) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setProductId(cartItem.getProductId());
                orderDetail.setQuantity(cartItem.getQuantity());
                orderDetail.setTotalPrice(cartItem.getTotal());

                orderService.insertOrderDetails(orderDetail);

                // For cart items, remove them from the cart and database
                if (cart != null) {
                    cart.removeProduct(cartItem.getProductId());
                    cartService.deleteCD(cartItem.getProductId(), user.getId());
                }
            }

            // Update session cart (only if cart was used)
            if (cart != null) {
                session.setAttribute("cart", cart);
            }

            // Store order details in session
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

            // Clear confirmedItems after order creation (for Buy Now)
            session.removeAttribute("confirmedItems");

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
        // No changes needed for doGet unless you want to support GET requests
    }
}
