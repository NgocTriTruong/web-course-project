package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "OrderManagerAdmin", value = "/order-manager")
public class OrderManagerAdmin extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            orderService = new OrderService();
        } catch (Exception e) {
            throw new ServletException("Error initializing OrderService", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
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

            // Get orders for current page
            List<Order> ordersForPage;
            if (countOrders > 0) {
                ordersForPage = allOrders.subList(startIndex, endIndex);
            } else {
                ordersForPage = new ArrayList<>();
            }

            // Set request attributes
            request.setAttribute("orders", ordersForPage);
            request.setAttribute("currentPage", page);
            request.setAttribute("endPage", endPage);
            request.setAttribute("totalOrders", countOrders);

            // Forward to the JSP
            request.getRequestDispatcher("/views/admin/orderManagement.jsp").forward(request, response);

        } catch (Exception e) {
            // Log the error
            e.printStackTrace();

            // Set error message and empty orders list
            request.setAttribute("errorMessage", "An error occurred while retrieving orders.");
            request.setAttribute("orders", new ArrayList<>());
            request.setAttribute("currentPage", 1);
            request.setAttribute("endPage", 1);
            request.setAttribute("totalOrders", 0);

            // Forward to JSP with error message
            request.getRequestDispatcher("/views/admin/orderManagement.jsp").forward(request, response);
        }
    }

    private void viewOrderDetails(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String orderId = request.getParameter("id");
            if (orderId != null) {
                Order order = orderService.getOrderById(Integer.parseInt(orderId));
                List<OrderDetail> orderDetails = orderService.getOrderDetails(Integer.parseInt(orderId));

                System.out.println(order);

                System.out.println(orderDetails);

                if (order != null) {
                    request.setAttribute("order", order);
                    request.setAttribute("orderDetails", orderDetails);
                    request.getRequestDispatcher("/views/admin/orderDetailManagement.jsp").forward(request, response);
                    return;
                }
            }
            response.sendRedirect("orders");
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException("Error retrieving order details", e);
        }
    }

    private void searchOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        List<Order> searchResults = orderService.searchOrders(searchTerm);

        // Handle pagination for search results
        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        int countOrders = searchResults.size();
        int ordersPerPage = 10;
        int endPage = (int) Math.ceil((double) countOrders / ordersPerPage);

        int startIndex = (page - 1) * ordersPerPage;
        int endIndex = Math.min(startIndex + ordersPerPage, countOrders);
        List<Order> ordersForPage = searchResults.subList(startIndex, endIndex);

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