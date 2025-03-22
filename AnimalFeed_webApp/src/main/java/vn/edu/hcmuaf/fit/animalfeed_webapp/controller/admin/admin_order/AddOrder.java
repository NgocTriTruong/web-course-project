package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AddOrder", value = "/add-order")
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Received POST request to /add-order");
        try {
            // Lấy dữ liệu từ form
            String phone = request.getParameter("phone");
            String customerName = request.getParameter("customerName");
            String address = request.getParameter("address");
            String[] productIds = request.getParameterValues("productIds[]");
            String[] quantities = request.getParameterValues("quantities[]");
            String[] prices = request.getParameterValues("prices[]");
            double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
            int totalQuantity = Integer.parseInt(request.getParameter("totalQuantity"));

            // Kiểm tra dữ liệu đầu vào
            if (productIds == null || quantities == null || prices == null || productIds.length == 0) {
                throw new Exception("Danh sách sản phẩm không hợp lệ");
            }
            if (address == null || address.trim().isEmpty()) {
                throw new Exception("Địa chỉ không được để trống");
            }

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
            order.setStatus(1); // Đang xử lý
            order.setOrderDate(LocalDateTime.now());
            order.setShippingPrice(0);
            order.setTotalPrice(totalPrice);
            order.setTotalQuantity(totalQuantity);
            order.setShipperId(0); // Chưa có shipper
            order.setShippingDate(null); // Chưa có ngày giao

            // Tạo danh sách OrderDetail
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (int i = 0; i < productIds.length; i++) {
                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                double price = Double.parseDouble(prices[i]);

                Product product = productService.getProductById(productId);
                if (product == null) {
                    throw new Exception("Sản phẩm với ID " + productId + " không tồn tại hoặc không hoạt động.");
                }
                if (product.getQuantity() < quantity) {
                    throw new Exception("Số lượng sản phẩm " + product.getName() + " không đủ (còn: " + product.getQuantity() + ").");
                }

                OrderDetail detail = new OrderDetail();
                detail.setProductId(productId);
                detail.setQuantity(quantity);
                detail.setTotalPrice(price * quantity);
                orderDetails.add(detail);
            }

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
}