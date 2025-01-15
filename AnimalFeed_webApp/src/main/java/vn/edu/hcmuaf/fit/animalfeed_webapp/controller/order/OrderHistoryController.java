package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.OrderDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    private void showOrderHistory(HttpServletRequest request, HttpServletResponse response, int userId)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        // Lấy danh sách đơn hàng của user
        ArrayList<Order> orders = orderService.getOrdersByUserId(userId);

        System.out.println("Found " + orders.size() + " orders for user " + userId);

        for (Order order : orders) {
            System.out.println("Order ID: " + order.getId() +
                    ", Status: " + order.getStatus() +
                    ", Time: " + order.getOrderDate() +
                    ", Total: " + order.getTotalPrice());

        }

        // Sắp xếp theo thời gian mới nhất
        orders.sort((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/views/web/order/order-history.jsp").forward(request, response);
    }

//    private void showOrderDetail(HttpServletRequest request, HttpServletResponse response, int userId)
//            throws ServletException, IOException, SQLException, ClassNotFoundException {
//        String orderId = request.getParameter("id");
//        if (orderId == null || orderId.trim().isEmpty()) {
//            response.sendRedirect(request.getContextPath() + "/order-history");
//            return;
//        }
//
//        // Lấy thông tin đơn hàng
//        Order order = orderService.getODsByOrderId(Integer.parseInt(orderId));
//
//        // Kiểm tra xem đơn hàng có thuộc về user hiện tại không
//        if (order == null || order.getUserId() != userId) {
//            response.sendRedirect(request.getContextPath() + "/order-history");
//            return;
//        }
//
//        // Lấy chi tiết đơn hàng
//        ArrayList<OrderDetail> orderDetails = orderDetailDao.getOrderDetailsByOrderId(order.getId());
//
//        request.setAttribute("order", order);
//        request.setAttribute("orderDetails", orderDetails);
//        request.getRequestDispatcher("/views/web/order-detail.jsp").forward(request, response);
//    }
}
