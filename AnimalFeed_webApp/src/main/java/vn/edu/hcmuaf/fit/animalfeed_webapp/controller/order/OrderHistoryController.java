package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.OrderDetailsWithProduct;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "OrderHistoryController", urlPatterns = {"/order-history", "/order-detail"})
public class OrderHistoryController extends HttpServlet {
    private OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        HttpSession session = request.getSession();

        System.out.println("Session ID: " + session.getId());
        System.out.println("User ID from session: " + session.getAttribute("userId"));

        // Kiểm tra đăng nhập
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            if ("/order-history".equals(path)) {
                showOrderHistory(request, response, userId);
            } else if ("/order-detail".equals(path)) {
                showOrderDetail(request, response, userId);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    private void showOrderHistory(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        try {
            // Lấy parameter lọc trạng thái
            String statusFilter = request.getParameter("status");
            ArrayList<Order> orders = orderService.getOrdersByUserId(userId);

            System.out.println("Found " + orders.size() + " orders for user " + userId);

            // Lọc theo trạng thái nếu có yêu cầu
            if (statusFilter != null && !statusFilter.equals("all")) {
                try {
                    int status = Integer.parseInt(statusFilter);
                    orders = orders.stream()
                            .filter(order -> order.getStatus() == status)
                            .collect(Collectors.toCollection(ArrayList::new));

                    System.out.println("Filtered to " + orders.size() + " orders with status " + status);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid status filter: " + statusFilter);
                }
            }

            // Log chi tiết đơn hàng
            for (Order order : orders) {
                System.out.println("Order ID: " + order.getId() +
                        ", Status: " + order.getStatus() +
                        ", Time: " + order.getOrderDate() +
                        ", Total: " + order.getTotalPrice());
            }

            for (Order order : orders) {
                String formattedOrderDate = order.getOrderDate() != null
                        ? order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        : "";
                order.setOrderDate(order.getOrderDate()); // Keep original orderDate
                request.setAttribute("formattedOrderDate_" + order.getId(), formattedOrderDate); // Unique attribute per order
            }

            // Sắp xếp theo thời gian mới nhất
            orders.sort((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));

            // Set attributes cho JSP
            request.setAttribute("orders", orders);
            request.setAttribute("currentStatus", statusFilter != null ? statusFilter : "all");

            // Thêm thông tin số lượng đơn hàng theo từng trạng thái
            Map<Integer, Long> orderCounts = orders.stream()
                    .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
            request.setAttribute("orderCounts", orderCounts);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            request.setAttribute("user", user);

            // Forward to JSP
            request.getRequestDispatcher("/views/web/chi_tiet_ca_nhan/personal_order.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            System.err.println("Error in showOrderHistory: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        String orderId = request.getParameter("id");
        if (orderId == null || orderId.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/order-history");
            return;
        }

        Order order = orderService.getOrderById(Integer.parseInt(orderId));
        if (order == null || order.getUserId() != userId) {
            response.sendRedirect(request.getContextPath() + "/order-history");
            return;
        }

        ArrayList<OrderDetailsWithProduct> orderDetailsWithProducts =
                orderService.getOrderDetailsWithProductByOrderId(order.getId());

        request.setAttribute("order", order);
        request.setAttribute("orderDetailsWithProducts", orderDetailsWithProducts);
        request.getRequestDispatcher("/views/web/chi_tiet_ca_nhan/order_details.jsp")
                .forward(request, response);
    }
}
