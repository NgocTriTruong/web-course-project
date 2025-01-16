package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_shipper;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ShipperDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Shipper;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ShipperService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShipperManager", value = "/shipper-manager")
public class ShipperManager extends HttpServlet {

    private ShipperService shipperService;

    @Override
    public void init() {
        // Khởi tạo service
        shipperService = new ShipperService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            // Xử lý chỉnh sửa shipper
            int shipperId = Integer.parseInt(request.getParameter("id"));
            Shipper shipper = shipperService.getShipperById(shipperId); // Lấy shipper theo ID
            request.setAttribute("shipper", shipper); // Đưa shipper vào request

            // Chuyển đến trang edit
            request.getRequestDispatcher("/views/admin/shipperEdit.jsp").forward(request, response);
        } else {
            // Lấy danh sách shipper
            List<Shipper> shipperList = shipperService.getAllShippers();
            if (shipperList != null && !shipperList.isEmpty()) {
                System.out.println("Danh sách shipper: " + shipperList); // Kiểm tra dữ liệu
            } else {
                System.out.println("Danh sách shipper rỗng");
            }
            request.setAttribute("shipperList", shipperList); // Gửi danh sách vào request
            request.getRequestDispatcher("/views/admin/shipperManagemet.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        double salary = Double.parseDouble(request.getParameter("salary"));
        int status = Integer.parseInt(request.getParameter("status"));
        int shipperId = Integer.parseInt(request.getParameter("id"));

        // Cập nhật shipper
        int adminUserId = 1; // Giả sử adminUserId đã có, hoặc có thể lấy từ session
        shipperService.updateShipper(shipperId, name, salary, phone, status, adminUserId);

        // Chuyển hướng về trang quản lý shipper
        response.sendRedirect(request.getContextPath() + "/shipper-manager");
    }
}