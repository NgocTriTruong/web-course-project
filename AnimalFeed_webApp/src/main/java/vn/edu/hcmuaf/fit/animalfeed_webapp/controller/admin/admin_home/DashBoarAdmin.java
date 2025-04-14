package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_home;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        // Sử dụng getInstance() thay vì new
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        // Xử lý tham số đầu vào
        int year, month;
        try {
            year = Integer.parseInt(request.getParameter("year"));
        } catch (NumberFormatException e) {
            year = Year.now().getValue(); // Năm hiện tại nếu không có tham số
        }

        try {
            month = Integer.parseInt(request.getParameter("month"));
        } catch (NumberFormatException e) {
            month = 0; // Mặc định là cả năm
        }

        // Lấy dữ liệu thống kê
        int totalOrder = orderService.getTotalOrder();
        double totalRevenue = orderService.getTotalRevenue();
        int totalUser = userService.getTotalUser();
        int totalProduct = productService.getTotalProduct();
        List<UserStats> getUserStats = orderService.getUserStats();

        // Dữ liệu cho biểu đồ doanh thu, phân loại , bán top
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

        // Kiểm tra nếu là AJAX request (chỉ cần dữ liệu biểu đồ)
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
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

            // Chuyển đổi sang JSON và gửi response
            new com.fasterxml.jackson.databind.ObjectMapper().writeValue(response.getWriter(), jsonResponse);
            return;
        }

        // Nếu là request thông thường, xử lý toàn bộ trang
        request.setAttribute("selectedYear", year);
        request.setAttribute("selectedMonth", month);

        // Đặt các thuộc tính vào request
        request.setAttribute("totalProduct", totalProduct);
        request.setAttribute("totalUser", totalUser);
        request.setAttribute("totalOrder", totalOrder);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("getUserStats", getUserStats);

        request.setAttribute("monthlyRevenueLabels", new ArrayList<>(revenueData.keySet()));
        request.setAttribute("monthlyRevenueData", new ArrayList<>(revenueData.values()));

        // Các dữ liệu thống kê khác
        request.setAttribute("orderStatusLabels", new ArrayList<>(orderStatusStats.keySet()));
        request.setAttribute("orderStatusData", new ArrayList<>(orderStatusStats.values()));

        request.setAttribute("topProductsLabels", new ArrayList<>(topProducts.keySet()));
        request.setAttribute("topProductsData", new ArrayList<>(topProducts.values()));

        // Đưa userService vào request scope để JSP có thể sử dụng
        request.setAttribute("userService", userService);

        request.getRequestDispatcher("views/admin/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);

    }
}