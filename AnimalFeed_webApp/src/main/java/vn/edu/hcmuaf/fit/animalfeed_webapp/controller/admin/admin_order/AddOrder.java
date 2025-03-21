package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddOrder", value = "/order-add")
public class AddOrder extends HttpServlet {
    private OrderService orderService;
    private UserService userService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        userService = new UserService();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");
        if (phone != null) { // Xử lý AJAX request để lấy thông tin khách hàng
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            try {
                User user = userService.getUserByPhone(phone);
                if (user != null) {
                    response.getWriter().write(gson.toJson(new UserResponse(user.getFullName())));
                } else {
                    response.getWriter().write("{\"fullName\": null}");
                }
            } catch (Exception e) {
                // Ghi log lỗi để kiểm tra
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Đã xảy ra lỗi khi lấy thông tin khách hàng: " + e.getMessage() + "\"}");
            }
        } else {
            request.getRequestDispatcher("/views/admin/orderAddition.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String phone = request.getParameter("phone");
            String customerName = request.getParameter("customerName");
            String address = request.getParameter("address");
            String[] productIds = request.getParameterValues("productIds[]");
            String[] quantities = request.getParameterValues("quantities[]");
            int status = Integer.parseInt(request.getParameter("status"));
            double totalPrice = 0;

            // Kiểm tra số điện thoại và lấy hoặc tạo userId
            int userId;
            User existingUser = userService.getUserByPhone(phone);
            if (existingUser != null) {
                userId = existingUser.getId();
                if (customerName == null || customerName.trim().isEmpty()) {
                    customerName = existingUser.getFullName();
                }
            } else {
                userService.registerUser(customerName, phone, "defaultPassword");
                User newUser = userService.getUserByPhone(phone);
                userId = newUser.getId();
            }

            // Tạo đối tượng Order
            Order order = new Order();
            order.setUserId(userId);
            order.setAddress(address);
            order.setStatus(status);
            order.setOrderDate(LocalDateTime.now());
            order.setShippingPrice(0);

            // Tạo danh sách OrderDetail và tính tổng giá
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (int i = 0; i < productIds.length; i++) {
                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                Product product = productService.getProductById(productId);
                if (product == null) {
                    throw new Exception("Sản phẩm với ID " + productId + " không tồn tại hoặc không hoạt động.");
                }
                if (product.getQuantity() < quantity) {
                    throw new Exception("Số lượng sản phẩm " + product.getName() + " không đủ (còn: " + product.getQuantity() + ").");
                }
                double productPrice = product.getPrice();
                totalPrice += productPrice * quantity;

                OrderDetail detail = new OrderDetail();
                detail.setProductId(productId);
                detail.setQuantity(quantity);
                detail.setTotalPrice(productPrice * quantity);
                orderDetails.add(detail);
            }

            order.setTotalPrice(totalPrice);
            order.setTotalQuantity(calculateTotalQuantity(quantities));

            // Lưu Order và lấy ID
            int orderId = orderService.insertOrder(order);
            for (OrderDetail detail : orderDetails) {
                detail.setOrderId(orderId);
                orderService.insertOrderDetails(detail);
            }

            // Cập nhật số lượng sản phẩm trong kho
            for (OrderDetail detail : orderDetails) {
                Product product = productService.getProductById(detail.getProductId());
                product.setQuantity(product.getQuantity() - detail.getQuantity());
                productService.updateProduct(product, userId); // Giả định userId là admin
            }

            // Redirect về trang quản lý với thông báo thành công
            request.getSession().setAttribute("message", "Thêm đơn hàng thành công!");
            response.sendRedirect(request.getContextPath() + "/order-manager");

        } catch (Exception e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/views/admin/orderAddition.jsp").forward(request, response);
        }
    }

    // Tính tổng số lượng
    private int calculateTotalQuantity(String[] quantities) {
        int total = 0;
        for (String qty : quantities) {
            total += Integer.parseInt(qty);
        }
        return total;
    }

    // Class để trả về JSON response
    private static class UserResponse {
        private String fullName;

        public UserResponse(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }
    }
}