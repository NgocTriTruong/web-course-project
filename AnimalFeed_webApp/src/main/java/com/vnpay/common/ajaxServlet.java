package com.vnpay.common;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Payment;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PaymentService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ghn_service.GHNService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@WebServlet(name = "ajaxServlet", value = "/payment")
public class ajaxServlet extends HttpServlet {
    OrderService orderService = new OrderService();
    PaymentService paymentService = new PaymentService();
    CartService cartService = new CartService();
    private GHNService ghnService = new GHNService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Get user from session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

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

        // Build full address
        String address = buildAddress(addressDetails, ward, district, province);

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

        double totalPrice = selectedItems.stream()
                .mapToDouble(CartItem::getTotal)
                .sum();

        int totalQuantity = selectedItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        int provinceId = ghnService.getProvinceIdByName(province);
        int districtId = ghnService.getDistrictIdByName(district, provinceId);
        String ghnOrderCode = ghnService.createShippingOrder(
                fullName, phone, address, wardCode, districtId, ward, district, province, provinceId, selectedItems, paymentMethod
        );

        // Create order
        Order order = new Order();
        order.setUserId(user.getId());
        order.setStatus(1); // Initial order status (e.g., Pending)
        order.setAddress(address);
        order.setTotalPrice(totalPrice);
        order.setTotalQuantity(totalQuantity);
        order.setOrderDate(LocalDateTime.now());

        double shippingFee = Double.parseDouble(shippingFeeStr);
        order.setShippingPrice(shippingFee);
        order.setShippingCode(ghnOrderCode);


        int orderId = orderService.insertOrder(order);
        order.setId(orderId);

        double finalPrice = totalPrice + shippingFee;
        // Create payment
        Payment payment = new Payment();
        payment.setPayDate(LocalDateTime.now());
        payment.setMethod(paymentMethod);
        payment.setOrderId(orderId);
        payment.setUserId(user.getId());
        payment.setStatus(1);

        paymentService.addPayment(payment);

        // Create order details for selected items
        for (CartItem cartItem : selectedItems) {
            // Create corresponding order detail
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

        // Update session cart (only if cart was used)
        if (cart != null) {
            session.setAttribute("cart", cart);
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


        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = (long) (finalPrice*100);
        String bankCode = request.getParameter("bankCode");

        String vnp_TxnRef = String.valueOf(orderId);
        String vnp_IpAddr = Config.getIpAddress(request);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
        System.out.println(paymentUrl);

        response.sendRedirect(paymentUrl);
    }

    private String buildAddress(String addressDetails, String ward, String district, String province) {
        return String.format("%s, %s, %s, %s",
                addressDetails != null && !addressDetails.trim().isEmpty() ? addressDetails : "Không xác định",
                ward != null && !ward.trim().isEmpty() ? ward : "Không xác định",
                district != null && !district.trim().isEmpty() ? district : "Không xác định",
                province != null && !province.trim().isEmpty() ? province : "Không xác định");

    }

}