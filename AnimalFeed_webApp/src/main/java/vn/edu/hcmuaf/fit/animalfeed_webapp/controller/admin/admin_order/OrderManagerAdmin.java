package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.OrderDetailsWithProduct;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "OrderManagerAdmin", value = "/order-manager")
public class OrderManagerAdmin extends HttpServlet {
    private OrderService orderService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            orderService = new OrderService();
            userService = UserService.getInstance();
        } catch (Exception e) {
            throw new ServletException("Error initializing services", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy userId từ session
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                request.getSession().setAttribute("error", "Vui lòng đăng nhập để thực hiện thao tác này.");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Kiểm tra quyền ORDER_MANAGEMENT
            if (!userService.hasPermission(userId, "ORDER_MANAGEMENT")) {
                request.getSession().setAttribute("error", "Bạn không có quyền truy cập quản lý đơn hàng (yêu cầu quyền ORDER_MANAGEMENT).");
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }

            String action = request.getParameter("action");

            if (action == null) {
                listOrders(request, response);
            } else {
                switch (action) {
                    case "view":
                        viewOrderDetails(request, response);
                        break;
                    case "search":
                        searchOrders(request, response);
                        break;
                    default:
                        listOrders(request, response);
                        break;
                }
            }
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            // Set error message
            request.setAttribute("errorMessage", "An error occurred while processing your request.");
            // Forward to error page or main page with error message
            request.getRequestDispatcher("/views/admin/orderManagement.jsp").forward(request, response);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get all orders with null check
            List<Order> allOrders = orderService.getAllOrders();
            if (allOrders == null) {
                allOrders = new ArrayList<>(); // Create empty list if null
            }

            // Sắp xếp đơn hàng theo trạng thái và ngày đặt hàng
            allOrders.sort((o1, o2) -> {
                // Định nghĩa thứ tự ưu tiên trạng thái
                List<Integer> statusOrder = Arrays.asList(1, 2, 3, 4, 0);
                int index1 = statusOrder.indexOf(o1.getStatus());
                int index2 = statusOrder.indexOf(o2.getStatus());

                // So sánh trạng thái
                if (index1 != index2) {
                    return Integer.compare(index1, index2);
                }

                // Nếu trạng thái giống nhau, so sánh ngày đặt hàng từ mới đến cũ
                return o2.getOrderDate().compareTo(o1.getOrderDate());
            });

            // Get page parameter with validation
            int page = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    // Invalid page parameter, default to 1
                    page = 1;
                }
            }

            // Calculate pagination with bounds checking
            int countOrders = allOrders.size();
            int ordersPerPage = 10;
            int endPage = Math.max(1, (int) Math.ceil((double) countOrders / ordersPerPage));

            // Adjust page if it exceeds the maximum
            if (page > endPage) {
                page = endPage;
            }

            // Calculate indices with bounds checking
            int startIndex = (page - 1) * ordersPerPage;
            int endIndex = Math.min(startIndex + ordersPerPage, countOrders);

            // Ensure valid sublist bounds
            if (startIndex < 0) startIndex = 0;
            if (startIndex > countOrders) startIndex = Math.max(0, countOrders - ordersPerPage);
            if (endIndex < startIndex) endIndex = startIndex;

            List<Order> ordersForPage = countOrders > 0 ? allOrders.subList(startIndex, endIndex) : new ArrayList<>();

            // Set request attributes
            request.setAttribute("orders", ordersForPage);
            request.setAttribute("currentPage", page);
            request.setAttribute("endPage", endPage);
            request.setAttribute("totalOrders", countOrders);

            request.getRequestDispatcher("/views/admin/orderManagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving orders.");
            request.setAttribute("orders", new ArrayList<>());
            request.setAttribute("currentPage", 1);
            request.setAttribute("endPage", 1);
            request.setAttribute("totalOrders", 0);
            request.getRequestDispatcher("/views/admin/orderManagement.jsp").forward(request, response);
        }
    }

    private void viewOrderDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String orderId = request.getParameter("id");
            if (orderId != null) {
                int parsedOrderId = Integer.parseInt(orderId);
                Order order = orderService.getOrderById(parsedOrderId);
                List<OrderDetailsWithProduct> orderDetails = orderService.getOrderDetailsWithProductByOrderId(parsedOrderId);

                if (orderDetails == null) {
                    orderDetails = new ArrayList<>();
                }

                System.out.println("Order: " + order);
                System.out.println("OrderDetails: " + orderDetails);

                if (order != null) {
                    request.setAttribute("order", order);
                    request.setAttribute("orderDetails", orderDetails);
                    request.getRequestDispatcher("/views/admin/orderDetailManagement.jsp").forward(request, response);
                    return;
                }
            }
            response.sendRedirect("order-manager");
        } catch (SQLException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
            throw new ServletException("Lỗi khi lấy chi tiết đơn hàng", e);
        }
    }

    private void searchOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        List<Order> searchResults = orderService.searchOrders(searchTerm);

        if (searchResults == null) {
            searchResults = new ArrayList<>();
        }

        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        int countOrders = searchResults.size();
        int ordersPerPage = 10;
        int endPage = (int) Math.ceil((double) countOrders / ordersPerPage);

        int startIndex = (page - 1) * ordersPerPage;
        int endIndex = Math.min(startIndex + ordersPerPage, countOrders);
        List<Order> ordersForPage = countOrders > 0 ? searchResults.subList(startIndex, endIndex) : new ArrayList<>();

        request.setAttribute("orders", ordersForPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("endPage", endPage);
        request.setAttribute("searchTerm", searchTerm);

        request.getRequestDispatcher("/views/admin/orderManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}