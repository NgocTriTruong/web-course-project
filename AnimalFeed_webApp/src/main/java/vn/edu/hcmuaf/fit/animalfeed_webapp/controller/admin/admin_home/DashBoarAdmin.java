package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_home;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.UserStats;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DashBoarAdmin", value = "/dashboard")
public class DashBoarAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("DashBoarAdmin: Processing request for /dashboard");
        if (response.isCommitted()) {
            System.out.println("DashBoarAdmin: Response already committed, cannot forward");
            return;
        }


        // Kiểm tra session
        HttpSession session = request.getSession(false); // false để không tạo session mới nếu chưa có
        if (session == null || session.getAttribute("userId") == null) {
            // Nếu chưa đăng nhập, chuyển hướng đến trang login
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        // Lấy userId từ session
        int userId = (int) session.getAttribute("userId");

        // Kiểm tra vai trò admin
        UserService userService = new UserService();
        if (!userService.checkIfAdmin(userId)) {
            // Nếu không phải admin, trả về mã lỗi 404
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Sử dụng getInstance() thay vì new

        ProductService productService = new ProductService();
        OrderService orderService = new OrderService();

        int year, month;
        try {
            year = Integer.parseInt(request.getParameter("year"));
        } catch (NumberFormatException e) {
            year = Year.now().getValue();
        }

        try {
            month = Integer.parseInt(request.getParameter("month"));
        } catch (NumberFormatException e) {
            month = 0;
        }

        int totalOrder = orderService.getTotalOrder();
        double totalRevenue = orderService.getTotalRevenue();
        int totalUser = userService.getTotalUser();
        int totalProduct = productService.getTotalProduct();
        List<UserStats> getUserStats = orderService.getUserStats();

        Map<String, Double> revenueData;
        Map<String, Integer> orderStatusStats;
        Map<String, Integer> topProducts;
        if (month == 0) {
            revenueData = orderService.getMonthlyRevenue(year);
            orderStatusStats = orderService.getOrderStatusStatsInYear(year);
            topProducts = productService.getTopSellingProductsInYear(10, year);
        } else {
            revenueData = orderService.getMonthlyRevenueInYear(year, month);
            orderStatusStats = orderService.getOrderStatusStatsInMonthYear(month, year);
            topProducts = productService.getTopSellingProducts(10, year, month);
        }

        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            try {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("monthlyRevenueLabels", new ArrayList<>(revenueData.keySet()));
                jsonResponse.put("monthlyRevenueData", new ArrayList<>(revenueData.values()));
                jsonResponse.put("orderStatusLabels", new ArrayList<>(orderStatusStats.keySet()));
                jsonResponse.put("orderStatusData", new ArrayList<>(orderStatusStats.values()));
                jsonResponse.put("topProductsLabels", new ArrayList<>(topProducts.keySet()));
                jsonResponse.put("topProductsData", new ArrayList<>(topProducts.values()));
                jsonResponse.put("year", year);
                jsonResponse.put("month", month);

                new com.fasterxml.jackson.databind.ObjectMapper().writeValue(response.getWriter(), jsonResponse);
                System.out.println("DashBoarAdmin: AJAX response sent");
                return;
            } catch (Exception e) {
                System.out.println("DashBoarAdmin: Error processing AJAX request: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error processing AJAX request: " + e.getMessage());
                return;
            }
        }

        request.setAttribute("selectedYear", year);
        request.setAttribute("selectedMonth", month);
        request.setAttribute("totalProduct", totalProduct);
        request.setAttribute("totalUser", totalUser);
        request.setAttribute("totalOrder", totalOrder);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("getUserStats", getUserStats);
        request.setAttribute("monthlyRevenueLabels", new ArrayList<>(revenueData.keySet()));
        request.setAttribute("monthlyRevenueData", new ArrayList<>(revenueData.values()));
        request.setAttribute("orderStatusLabels", new ArrayList<>(orderStatusStats.keySet()));
        request.setAttribute("orderStatusData", new ArrayList<>(orderStatusStats.values()));
        request.setAttribute("topProductsLabels", new ArrayList<>(topProducts.keySet()));
        request.setAttribute("topProductsData", new ArrayList<>(topProducts.values()));
        request.setAttribute("userService", userService);

        if (response.isCommitted()) {
            System.out.println("DashBoarAdmin: Response already committed before forwarding");
            return;
        }

        System.out.println("DashBoarAdmin: Forwarding to /views/admin/home.jsp");
        request.getRequestDispatcher("/views/admin/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}