package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.ghncontroller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ghn_service.GHNService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "CancelOrderController", urlPatterns = "/cancel-order-ghn")
public class CancelOrderSeverlet extends HttpServlet {
    private OrderService orderService = new OrderService();
    private GHNService ghnService = new GHNService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Vui lòng đăng nhập để thực hiện thao tác này");
            out.print(jsonResponse.toString());
            out.flush();
            return;
        }

        try {
            // Đọc body JSON
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            String jsonData = buffer.toString();
            System.out.println("JSON Data: " + jsonData);

            if (jsonData.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"message\": \"Dữ liệu JSON rỗng\"}");
                return;
            }

            // Phân tích JSON
            JSONObject jsonObject = new JSONObject(jsonData);
            String ghnOrderCode = jsonObject.getString("ghnOrderCode");
            String orderIdStr = jsonObject.getString("orderId");
            int orderId = Integer.parseInt(orderIdStr);

            // Kiểm tra đơn hàng
            Order order = orderService.getOrderById(orderId);
            if (order == null || order.getUserId() != user.getId()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"message\": \"Không tìm thấy đơn hàng hoặc bạn không có quyền hủy đơn hàng này\"}");
                return;
            }

            // Kiểm tra trạng thái đơn hàng
            if (order.getStatus() != 1 && order.getStatus() != 2) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"message\": \"Đơn hàng này không thể hủy ở trạng thái hiện tại\"}");
                return;
            }

            // Hủy đơn hàng trên GHN
            ghnService.cancelOrder(ghnOrderCode);

            // Hủy đơn hàng
            orderService.updateOrderStatus(orderId, 0);
            response.getWriter().write("{\"success\": true, \"message\": \"Hủy đơn hàng thành công\"}");

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"message\": \"Mã đơn hàng không hợp lệ\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"success\": false, \"message\": \"Đã xảy ra lỗi khi hủy đơn hàng\"}");
        }
    }
}