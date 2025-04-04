package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.OrderDetailsWithProduct;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "OrderEditServlet", value = "/orderEdit")
public class OrderEditController extends HttpServlet {
    private OrderService orderService;
    private UserService userService;
    private ProductService productService;
    private ProductWithDiscountDao productWithDiscountDao;

    @Override
    public void init() throws ServletException {
        orderService = new OrderService();
        userService = new UserService();
        productService = new ProductService();
        productWithDiscountDao = new ProductWithDiscountDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy orderId từ tham số
            String orderIdStr = request.getParameter("id");
            if (orderIdStr == null || orderIdStr.trim().isEmpty()) {
                throw new Exception("Order ID không hợp lệ");
            }
            int orderId = Integer.parseInt(orderIdStr);

            // Lấy thông tin đơn hàng
            Order order = orderService.getOrderById(orderId);
            if (order == null) {
                throw new Exception("Đơn hàng không tồn tại");
            }

            // Lấy thông tin khách hàng
            User user = userService.getUserById(order.getUserId());
            if (user == null) {
                throw new Exception("Khách hàng không tồn tại");
            }

            // Lấy danh sách chi tiết đơn hàng kèm tên sản phẩm
            List<OrderDetailsWithProduct> orderDetails = orderService.getOrderDetailsWithProductByOrderId(orderId);
            if (orderDetails == null || orderDetails.isEmpty()) {
                throw new Exception("Không tìm thấy chi tiết đơn hàng");
            }

            // Đặt các thuộc tính để hiển thị trên JSP
            request.setAttribute("order", order);
            request.setAttribute("user", user);
            request.setAttribute("orderDetails", orderDetails);
            request.getRequestDispatcher("/views/admin/orderEdit.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/orderManagement.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String address = request.getParameter("address");
            int status = Integer.parseInt(request.getParameter("status"));
            String[] productIds = request.getParameterValues("productIds[]");
            String[] quantities = request.getParameterValues("quantities[]");
            String[] prices = request.getParameterValues("prices[]");
            double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));
            int totalQuantity = Integer.parseInt(request.getParameter("totalQuantity"));

            // Kiểm tra dữ liệu đầu vào
            if (address == null || address.trim().isEmpty()) {
                throw new Exception("Địa chỉ không được để trống");
            }
            if (productIds == null || quantities == null || prices == null || productIds.length == 0) {
                throw new Exception("Danh sách sản phẩm không hợp lệ");
            }
            if (status < 1 || status > 5) {
                throw new Exception("Trạng thái đơn hàng không hợp lệ");
            }

            // Lấy đơn hàng hiện tại
            Order oldOrder = orderService.getOrderById(orderId);
            if (oldOrder == null) {
                throw new Exception("Đơn hàng không tồn tại");
            }

            // Lấy danh sách chi tiết đơn hàng hiện tại
            List<OrderDetail> currentOrderDetails = orderService.getOrderDetails(orderId);
            Map<Integer, Integer> currentProductQuantities = new HashMap<>();
            for (OrderDetail detail : currentOrderDetails) {
                currentProductQuantities.put(detail.getProductId(), detail.getQuantity());
            }

            // Tạo danh sách chi tiết đơn hàng mới từ form
            List<OrderDetail> newOrderDetails = new ArrayList<>();
            Map<Integer, Integer> newProductQuantities = new HashMap<>();
            List<ProductWithDiscountDTO> discountedProducts = productWithDiscountDao.discountedProducts();

            for (int i = 0; i < productIds.length; i++) {
                int productId = Integer.parseInt(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                double price = Double.parseDouble(prices[i]);

                Product product = productService.getProductById(productId);
                if (product == null) {
                    throw new Exception("Sản phẩm với ID " + productId + " không tồn tại hoặc không hoạt động.");
                }
                // Kiểm tra số lượng tồn kho trước
                int availableQuantity = product.getQuantity() + currentProductQuantities.getOrDefault(productId, 0);
                if (availableQuantity < quantity) {
                    throw new Exception("Số lượng sản phẩm " + product.getName() + " không đủ (còn: " + availableQuantity + ").");
                }

                // Kiểm tra giá đã giảm
                double finalPrice = product.getPrice();
                for (ProductWithDiscountDTO discountedProduct : discountedProducts) {
                    if (discountedProduct.getId() == productId) {
                        finalPrice = discountedProduct.getDiscountedPrice();
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
                newOrderDetails.add(detail);

                newProductQuantities.put(productId, quantity);
            }

            // Kiểm tra xem danh sách sản phẩm có thay đổi không
            boolean productsChanged = false;
            if (currentProductQuantities.size() != newProductQuantities.size()) {
                productsChanged = true;
            } else {
                for (Map.Entry<Integer, Integer> entry : currentProductQuantities.entrySet()) {
                    int productId = entry.getKey();
                    int oldQuantity = entry.getValue();
                    int newQuantity = newProductQuantities.getOrDefault(productId, 0);
                    if (oldQuantity != newQuantity) {
                        productsChanged = true;
                        break;
                    }
                    if (!newProductQuantities.containsKey(productId)) {
                        productsChanged = true;
                        break;
                    }
                }
            }

            if (productsChanged) {
                // Nếu danh sách sản phẩm thay đổi, tạo đơn hàng mới
                Order newOrder = new Order();
                newOrder.setUserId(oldOrder.getUserId());
                newOrder.setAddress(address);
                newOrder.setStatus(status);
                newOrder.setOrderDate(oldOrder.getOrderDate());
                newOrder.setShippingPrice(oldOrder.getShippingPrice());
                newOrder.setTotalPrice(totalPrice);
                newOrder.setTotalQuantity(totalQuantity);
                newOrder.setShipperId(oldOrder.getShipperId());
                newOrder.setShippingDate(oldOrder.getShippingDate());

                // Tạo đơn hàng mới
                int newOrderId = orderService.insertOrder(newOrder);
                System.out.println("Created new order with ID: " + newOrderId);

                // Thêm chi tiết đơn hàng mới
                for (OrderDetail detail : newOrderDetails) {
                    detail.setOrderId(newOrderId);
                    orderService.insertOrderDetails(detail);
                    System.out.println("Inserted OrderDetail for Product ID: " + detail.getProductId());
                }

                // Đặt trạng thái đơn hàng cũ thành "Đã hủy"
                orderService.updateOrderStatus(orderId, 5);
                System.out.println("Set old order ID " + orderId + " to status 'Đã hủy'");

                // Cập nhật số lượng tồn kho
                for (Map.Entry<Integer, Integer> entry : newProductQuantities.entrySet()) {
                    int productId = entry.getKey();
                    int newQuantity = entry.getValue();
                    int oldQuantity = currentProductQuantities.getOrDefault(productId, 0);

                    Product product = productService.getProductById(productId);
                    int quantityChange = newQuantity - oldQuantity;
                    product.setQuantity(product.getQuantity() - quantityChange);
                    productService.updateProduct(productId, product, oldOrder.getUserId());
                    System.out.println("Updated product quantity for Product ID: " + productId + ", Quantity change: " + quantityChange);
                }

                // Chuyển hướng về trang quản lý đơn hàng
                request.getSession().setAttribute("message", "Đã tạo đơn hàng mới với ID " + newOrderId + " và hủy đơn hàng cũ!");
                response.sendRedirect(request.getContextPath() + "/order-manager");

            } else {
                // Nếu không thay đổi danh sách sản phẩm, chỉ cập nhật địa chỉ và trạng thái
                oldOrder.setAddress(address);
                oldOrder.setStatus(status);
                oldOrder.setTotalPrice(totalPrice);
                oldOrder.setTotalQuantity(totalQuantity);
                orderService.updateOrder(oldOrder);

                // Chuyển hướng về trang quản lý đơn hàng
                request.getSession().setAttribute("message", "Chỉnh sửa đơn hàng thành công!");
                response.sendRedirect(request.getContextPath() + "/order-manager");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            doGet(request, response);
        }
    }
}