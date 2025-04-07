package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_home;

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
import java.util.List;

@WebServlet(name = "DashBoarAdmin", value = "/dashboard")
public class DashBoarAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Sử dụng getInstance() thay vì new
        ProductService productService = new ProductService();
        UserService userService = new UserService();
        OrderService orderService = new OrderService();

        int totalOrder = orderService.getTotalOrder();
        double totalRevenue = orderService.getTotalRevenue();
        int totalUser = userService.getTotalUser();
        int totalProduct = productService.getTotalProduct();
        List<UserStats> getUserStats = orderService.getUserStats();

        // Đặt các thuộc tính vào request
        request.setAttribute("totalProduct", totalProduct);
        request.setAttribute("totalUser", totalUser);
        request.setAttribute("totalOrder", totalOrder);
        request.setAttribute("totalRevenue", totalRevenue);
        request.setAttribute("getUserStats", getUserStats);

        // Đặt getUserStats vào session
        request.getSession().setAttribute("getUserStats", getUserStats);

        // Đưa userService vào request scope để JSP có thể sử dụng
        request.setAttribute("userService", userService);

        request.getRequestDispatcher("views/admin/home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý POST nếu cần
    }
}