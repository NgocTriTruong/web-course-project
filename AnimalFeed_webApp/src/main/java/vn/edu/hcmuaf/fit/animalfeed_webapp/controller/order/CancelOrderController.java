package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Order;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.OrderService;

import java.io.IOException;

@WebServlet(name = "CancelOrder", value = "/cancel-order")
public class CancelOrderController extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        orderService = new OrderService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int orderId = Integer.parseInt(request.getParameter("id"));
            Order order = orderService.getOrderById(orderId);

            // Verify order exists and belongs to user
            if (order == null || order.getUserId() != user.getId()) {
                session.setAttribute("error", "Không tìm thấy đơn hàng hoặc bạn không có quyền hủy đơn hàng này");
                response.sendRedirect(request.getContextPath() + "/order-history");
                return;
            }

            // Check if order can be cancelled
            if (order.getStatus() != 1 && order.getStatus() != 2) {
                session.setAttribute("error", "Đơn hàng này không thể hủy ở trạng thái hiện tại");
                response.sendRedirect(request.getContextPath() + "/order-history");
                return;
            }

            // Cancel the order
            orderService.updateOrderStatus(orderId, 0);
            session.setAttribute("success", "Đã hủy đơn hàng thành công");

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Mã đơn hàng không hợp lệ");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Đã xảy ra lỗi khi hủy đơn hàng");
        }

        response.sendRedirect(request.getContextPath() + "/order-history");
    }
}
