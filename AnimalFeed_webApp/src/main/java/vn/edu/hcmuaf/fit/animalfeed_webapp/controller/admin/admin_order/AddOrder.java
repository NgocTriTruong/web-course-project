package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDao;
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
    private ProductWithDiscountDao productWithDiscountDao;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        userService = UserService.getInstance();
        productService = new ProductService();
        productWithDiscountDao = new ProductWithDiscountDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            System.out.println("Received POST request to /add-order");

            // Lấy userId từ session
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                request.getSession().setAttribute("error", "Vui lòng đăng nhập để thực hiện thao tác này.");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Kiểm tra quyền ORDER_MANAGEMENT
            if (!userService.hasPermission(userId, "ORDER_MANAGEMENT")) {
                request.getSession().setAttribute("error", "Bạn không có quyền thêm đơn hàng (yêu cầu quyền ORDER_MANAGEMENT).");
                response.sendRedirect(request.getContextPath() + "/order-manager");
                return;
            }

            // Lấy dữ liệu từ form
            String email = request.getParameter("email");
            String customerName = request.getParameter("customerName");
            String address = request.getParameter("address");
            String[] productIds = request.getParameterValues("productIds[]");
            String[] quantities = request.getParameterValues("quantities[]");
            String[] prices = request.getParameterValues("prices[]");
            double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
            int totalQuantity = Integer.parseInt(request.getParameter("totalQuantity"));

            System.out.println("Data received - Email: " + email + ", CustomerName: " + customerName + ", Address: " + address);
            System.out.println("Total Price: " + totalPrice + ", Total Quantity: " + totalQuantity);

            // Kiểm tra dữ liệu đầu vào
            if (productIds == null || quantities == null || prices == null || productIds.length == 0) {
                throw new Exception("Danh sách sản phẩm không hợp lệ");
            }
            if (address == null || address.trim().isEmpty()) {
                throw new Exception("Địa chỉ không được để trống");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new Exception("Email không được để trống");
            }

            // Kiểm tra email và lấy hoặc tạo userId
            int orderUserId;
            User existingUser = userService.getUserByEmail(email);
            System.out.println("User fetched: " + (existingUser != null ? existingUser.getFullName() : "null"));
            if (existingUser != null) {
                orderUserId = existingUser.getId();
                if (customerName == null || customerName.trim().isEmpty()) {
                    customerName = existingUser.getFullName();
                }
            } else {
                userService.registerUser(customerName, email, "Amind123..");
                User newUser = userService.getUserByEmail(email);
                orderUserId = newUser.getId();
                System.out.println("New user created with ID: " + orderUserId);
            }

            // Tạo đối tượng Order
            Order order = new Order();
            order.setUserId(orderUserId);
            order.setAddress(address);
            order.setStatus(1);
            order.setOrderDate(LocalDateTime.now());
            order.setShippingPrice(0);
            order.setTotalPrice(totalPrice);
            order.setTotalQuantity(totalQuantity);
            order.setShipperId(0);
            order.setShippingDate(null);

            // Tạo danh sách OrderDetail
            List<OrderDetail> orderDetails = new ArrayList<>();
            List<ProductWithDiscountDTO> discountedProducts = productWithDiscountDao.discountedProducts();
            System.out.println("Discounted products retrieved: " + discountedProducts.size());

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

                // Kiểm tra giá đã giảm
                double finalPrice = product.getPrice();
                for (ProductWithDiscountDTO discountedProduct : discountedProducts) {
                    if (discountedProduct.getId() == productId) {
                        finalPrice = discountedProduct.getDiscountedPrice();
                        System.out.println("Discount applied for Product ID " + productId + ". Original Price: " + product.getPrice() + ", Discounted Price: " + finalPrice);
                        break;
                    }
                }

                if (Math.abs(finalPrice - price) > 0.01) {
                    System.out.println("Warning: Price mismatch for Product ID " + productId + ". Expected: " + finalPrice + ", Received: " + price);
                }

                OrderDetail detail = new OrderDetail();
                detail.setProductId(productId);
                detail.setQuantity(quantity);
                detail.setTotalPrice(price * quantity);
                orderDetails.add(detail);
            }

            // Lưu Order và lấy ID
            int orderId = orderService.insertOrder(order, userId);
            System.out.println("Order inserted with ID: " + orderId);

            for (OrderDetail detail : orderDetails) {
                detail.setOrderId(orderId);
                orderService.insertOrderDetails(detail, userId);
                System.out.println("Inserted OrderDetail for Product ID: " + detail.getProductId());
            }

            // Cập nhật số lượng sản phẩm trong kho
            for (OrderDetail detail : orderDetails) {
                Product product = productService.getProductById(detail.getProductId());
                product.setQuantity(product.getQuantity() - detail.getQuantity());
                productService.updateProduct(product.getId(), product, userId);
                System.out.println("Updated product quantity for Product ID: " + product.getId());
            }

            System.out.println("Order processing completed successfully");
            request.getSession().setAttribute("message", "Thêm đơn hàng thành công!");
            response.sendRedirect(request.getContextPath() + "/order-manager");

        } catch (Exception e) {
            System.err.println("Error in AddOrder: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/add-order-management");
        }
    }
}