package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_home;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.UserStats;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "DashBoarAdmin", value = "/dashboard")
public class DashBoarAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        int totalOrder = orderService.getTotalOrder();
        double totalRevenue = orderService.getTotalRevenue();
        int totalUser = userService.getTotalUser();
        int totalProduct = productService.getTotalProduct();
        // Định nghĩa một phương thức để lấy thông tin người dùng và thống kê
        List<UserStats> getUserStats = orderService.getUserStats();

        request.setAttribute("totalProduct", totalProduct);
        request.setAttribute("totalUser", totalUser);
        request.setAttribute("totalOrder", totalOrder);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("getUserStats", getUserStats);

        // Đặt getUserStats vào session
        request.getSession().setAttribute("getUserStats", getUserStats);

        request.getRequestDispatcher("views/admin/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}