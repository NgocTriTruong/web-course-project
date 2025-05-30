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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "UpdateOrderStatusController", urlPatterns = "/update-order-status")
public class UpdateOrderStatusController extends HttpServlet {
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

        int userId = user.getId();
        String ghnOrderCode = request.getParameter("ghnOrderCode");
        String orderIdStr = request.getParameter("orderId");
        System.out.println("Updating status for orderId: " + orderIdStr + ", GHN Order Code: " + ghnOrderCode);

        if (ghnOrderCode == null || ghnOrderCode.trim().isEmpty() || orderIdStr == null || orderIdStr.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Mã đơn GHN hoặc ID đơn hàng không hợp lệ");
            out.print(jsonResponse.toString());
            out.flush();
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdStr);
            Order order = orderService.getOrderById(orderId);
            if (order == null || order.getUserId() != userId) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Không có quyền truy cập đơn hàng này");
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }

            // Lấy trạng thái từ GHN
            String ghnStatus = ghnService.getOrderStatus(ghnOrderCode);
            System.out.println("GHN status for order " + ghnOrderCode + ": " + ghnStatus);

            // Ánh xạ trạng thái GHN sang trạng thái nội bộ
            int newStatus = mapGhnStatusToInternalStatus(ghnStatus);
            if (newStatus == -1) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Trạng thái GHN không được hỗ trợ: " + ghnStatus);
                out.print(jsonResponse.toString());
                out.flush();
                return;
            }

            // Cập nhật trạng thái trong cơ sở dữ liệu
            orderService.updateOrderStatus(orderId, newStatus);

            // Lấy tên trạng thái để hiển thị
            String statusDisplay = getStatusDisplayName(newStatus);

            jsonResponse.put("success", true);
            jsonResponse.put("status", newStatus);
            jsonResponse.put("statusDisplay", statusDisplay);
            out.print(jsonResponse.toString());
            out.flush();

        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "ID đơn hàng không hợp lệ");
            out.print(jsonResponse.toString());
            out.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi khi lấy trạng thái từ GHN: " + e.getMessage());
            out.print(jsonResponse.toString());
            out.flush();
        } catch (SQLException | ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi khi cập nhật trạng thái: " + e.getMessage());
            out.print(jsonResponse.toString());
            out.flush();
        }
    }

    private int mapGhnStatusToInternalStatus(String ghnStatus) {
        switch (ghnStatus.toLowerCase()) {
            case "ready_to_pick":
                return 1; // Chờ xác nhận
            case "picking":
            case "picked":
                return 2; // Đang chuẩn bị
            case "delivering":
                return 3; // Đang giao hàng
            case "delivered":
                return 4; // Đã giao hàng
            case "cancel":
                return 0; // Đã hủy
            default:
                return -1; // Không hỗ trợ
        }
    }

    private String getStatusDisplayName(int status) {
        switch (status) {
            case 1:
                return "Chờ xác nhận";
            case 2:
                return "Đang chuẩn bị";
            case 3:
                return "Đang giao hàng";
            case 4:
                return "Đã giao hàng";
            case 0:
                return "Đã hủy";
            default:
                return "Không xác định";
        }
    }
}
