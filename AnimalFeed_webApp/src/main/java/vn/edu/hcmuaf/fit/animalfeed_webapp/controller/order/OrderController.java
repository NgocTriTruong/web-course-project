package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PaymentService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ghn_service.GHNService;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Order", value = "/create-order")
public class OrderController extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;
    private PaymentService paymentService;
    private GHNService ghnService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = new CartService();
        orderService = new OrderService();
        paymentService = new PaymentService();
        ghnService = new GHNService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", "Vui lòng đăng nhập để đặt hàng");
            out.print(error.toString());
            out.flush();
            return;
        }

        try {
            // Get form data
            String fullName = request.getParameter("fullName");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String deliveryMethod = request.getParameter("deliveryMethod");
            String province = request.getParameter("province");
            String district = request.getParameter("district");
            String ward = request.getParameter("ward");
            String wardCode = request.getParameter("wardCode");
            String addressDetails = request.getParameter("addressDetails");
            String note = request.getParameter("note");
            String paymentMethod = request.getParameter("paymentMethod");
            String shippingFeeStr = request.getParameter("shippingFee");

            // Validate form data
            if (fullName == null || phone == null || email == null ||
                    province == null || district == null || wardCode == null ||
                    addressDetails == null || shippingFeeStr == null) {
                response.sendRedirect(request.getContextPath() + "/checkout?error=Thiếu thông tin cần thiết trong form");
                return;
            }

            System.out.println(addressDetails);
            System.out.println(ward);
            System.out.println(district);
            System.out.println(province);
            // Build full address
            String address = buildAddress(addressDetails, ward, district, province);

            // Get cart data
            Cart cart = (Cart) session.getAttribute("cart");
            List<CartItem> selectedItems = new ArrayList<>();

            // Check if there are items in the cart or use confirmedItems from session (for Buy Now)
            if (cart != null && !cart.getConfirmedCartItem().isEmpty()) {
                selectedItems.addAll(cart.getConfirmedCartItem());
            } else {
                List<CartItem> confirmedItems = (List<CartItem>) session.getAttribute("confirmedItems");
                if (confirmedItems != null && !confirmedItems.isEmpty()) {
                    selectedItems.addAll(confirmedItems);
                } else {
                    session.setAttribute("error", "Giỏ hàng trống. Vui lòng thêm sản phẩm trước khi đặt hàng.");
                    response.sendRedirect(request.getContextPath() + "/cart");
                    return;
                }
            }

            if (selectedItems.isEmpty()) {
                session.setAttribute("error", "Không có sản phẩm nào được chọn để đặt hàng.");
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

            // Parse shipping fee
            double shippingFee;
            try {
                shippingFee = Double.parseDouble(shippingFeeStr);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JSONObject error = new JSONObject();
                error.put("success", false);
                error.put("message", "Phí vận chuyển không hợp lệ");
                out.print(error.toString());
                out.flush();
                return;
            }

            // Get province and district IDs for GHN
            int provinceId;
            try {
                provinceId = ghnService.getProvinceIdByName(province);
            } catch (IOException e) {
                response.sendRedirect(request.getContextPath() + "/checkout?error=Không thể lấy ID tỉnh/thành: " + e.getMessage());
                return;
            }

            int districtId;
            try {
                districtId = ghnService.getDistrictIdByName(district, provinceId);
            } catch (IOException e) {
                response.sendRedirect(request.getContextPath() + "/checkout?error=Không thể lấy ID quận/huyện: " + e.getMessage());
                return;
            }

            // Create GHN shipping order
            String ghnOrderCode;
            try {
                ghnOrderCode = ghnService.createShippingOrder(
                        fullName, phone, address, wardCode, districtId, ward, district, province, provinceId, selectedItems, paymentMethod
                );
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject error = new JSONObject();
                error.put("success", false);
                error.put("message", "Không thể tạo đơn hàng GHN: " + e.getMessage());
                out.print(error.toString());
                out.flush();
                return;
            }

            // Create order
            Order order = new Order();
            order.setUserId(user.getId());
            order.setStatus(0); // Initial order status (e.g., Pending)
            order.setAddress(address);
            order.setTotalPrice(totalPrice + shippingFee);
            order.setTotalQuantity(totalQuantity);
            order.setOrderDate(LocalDateTime.now());
            order.setShippingPrice(shippingFee);
            order.setShippingCode(ghnOrderCode);

            int orderId;
            try {
                orderId = orderService.insertOrder(order);
                order.setId(orderId);
            } catch (RuntimeException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject error = new JSONObject();
                error.put("success", false);
                error.put("message", "Không thể lưu đơn hàng: " + e.getMessage());
                out.print(error.toString());
                out.flush();
                return;
            }

            // Create payment
            Payment payment = new Payment();
            payment.setPayDate(LocalDateTime.now());
            payment.setMethod(paymentMethod);
            payment.setOrderId(orderId);
            payment.setUserId(user.getId());
            payment.setStatus("VNPAY".equals(paymentMethod) ? 0 : 1); // 0 for pending VNPAY, 1 for COD

            try {
                paymentService.addPayment(payment);
            } catch (RuntimeException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject error = new JSONObject();
                error.put("success", false);
                error.put("message", "Không thể lưu thông tin thanh toán: " + e.getMessage());
                out.print(error.toString());
                out.flush();
                return;
            }

            // Create order details for selected items
            try {
                for (CartItem cartItem : selectedItems) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(orderId);
                    orderDetail.setProductId(cartItem.getProductId());
                    orderDetail.setQuantity(cartItem.getQuantity());
                    orderDetail.setTotalPrice(cartItem.getTotal());

                    orderService.insertOrderDetails(orderDetail);

                    if (cart != null) {
                        cart.removeProduct(cartItem.getProductId());
                        cartService.deleteCD(cartItem.getProductId(), user.getId());
                    }
                }
            } catch (RuntimeException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject error = new JSONObject();
                error.put("success", false);
                error.put("message", "Không thể lưu chi tiết đơn hàng: " + e.getMessage());
                out.print(error.toString());
                out.flush();
                return;
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

            // Set success message
            session.setAttribute("message", "Đơn hàng đã được tạo thành công với mã #" + orderId + "!");

            // Redirect to order success page
            if ("VNPAY".equals(paymentMethod)) {
                response.sendRedirect(request.getContextPath() + "/payment?orderId=" + orderId);
            } else {
                response.sendRedirect(request.getContextPath() + "/order-success");
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Thêm logging nếu dùng SLF4J hoặc Log4j
            // Logger.getLogger(OrderController.class).error("Error creating order", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", "Đã xảy ra lỗi khi xử lý đơn hàng: " + e.getMessage());
            out.print(error.toString());
            out.flush();
        }
    }

    private String buildAddress(String addressDetails, String ward, String district, String province) {
        return String.format("%s, %s, %s, %s",
                addressDetails != null && !addressDetails.trim().isEmpty() ? addressDetails : "Không xác định",
                ward != null && !ward.trim().isEmpty() ? ward : "Không xác định",
                district != null && !district.trim().isEmpty() ? district : "Không xác định",
                province != null && !province.trim().isEmpty() ? province : "Không xác định");

    }
}