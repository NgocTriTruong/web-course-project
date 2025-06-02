package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.OrderDetailsWithProduct;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Payment;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.PaymentService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "OrderHistoryController", urlPatterns = {"/order-history", "/order-detail"})
public class OrderHistoryController extends HttpServlet {
    private OrderService orderService = new OrderService();
    PaymentService paymentService = new PaymentService();
    CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Lay danh muc
        List<Category> categories = categoryService.getAll();
        request.setAttribute("categoriesData", categories);

        String path = request.getServletPath();
        HttpSession session = request.getSession();

        System.out.println("Session ID: " + session.getId());
        System.out.println("User from session: " + session.getAttribute("user"));

        // Check login
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get userId from the User object
        int userId = user.getId();
        if (userId <= 0) { // Assuming a valid user ID is positive
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
            request.setAttribute("error", "An error occurred while loading the page.");
            request.getRequestDispatcher("/views/web/error.jsp").forward(request, response);
        }
    }

    private void showOrderHistory(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        try {
            String statusFilter = request.getParameter("status");
            ArrayList<Order> orders = orderService.getOrdersByUserId(userId);

            System.out.println("Found " + orders.size() + " orders for user " + userId);

            if (statusFilter != null && !statusFilter.equals("all")) {
                try {
                    int status = Integer.parseInt(statusFilter);
                    orders = (ArrayList<Order>) orders.stream()
                            .filter(order -> order.getStatus() == status)
                            .collect(Collectors.toCollection(ArrayList::new));
                    System.out.println("Filtered to " + orders.size() + " orders with status " + status);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid status filter: " + statusFilter);
                }
            }

            for (Order order : orders) {
                String formattedOrderDate = order.getOrderDate() != null
                        ? order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        : "";
                request.setAttribute("formattedOrderDate_" + order.getId(), formattedOrderDate);
            }

            orders.sort((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));

            request.setAttribute("orders", orders);
            request.setAttribute("currentStatus", statusFilter != null ? statusFilter : "all");

            Map<Integer, Long> orderCounts = orders.stream()
                    .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
            request.setAttribute("orderCounts", orderCounts);

            request.setAttribute("user", (User) request.getSession().getAttribute("user"));

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
            response.sendRedirect(request.getContextPath() + "/order-history?error=missing_order_id");
            return;
        }

        try {
            int orderIdInt = Integer.parseInt(orderId);
            Order order = orderService.getOrderById(orderIdInt);
            if (order == null || order.getUserId() != userId) {
                response.sendRedirect(request.getContextPath() + "/order-history?error=invalid_order");
                return;
            }

            ArrayList<OrderDetailsWithProduct> orderDetailsWithProducts =
                    orderService.getOrderDetailsWithProductByOrderId(order.getId());

            String formattedOrderDate = order.getOrderDate() != null
                    ? order.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                    : "";
            request.setAttribute("formattedOrderDate", formattedOrderDate);

            //Lay danh sach phuong thuc thanh toan
            Payment payments = paymentService.getPaymentByOrderId(orderIdInt);
            request.setAttribute("payments", payments);

            request.setAttribute("order", order);
            request.setAttribute("orderDetailsWithProducts", orderDetailsWithProducts);
            request.getRequestDispatcher("/views/web/chi_tiet_ca_nhan/order_details.jsp")
                    .forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/order-history?error=invalid_order_id");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while loading order details.");
            request.getRequestDispatcher("/views/web/error.jsp").forward(request, response);
        }
    }
}