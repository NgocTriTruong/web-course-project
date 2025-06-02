package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.ghncontroller;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ghn_service.GHNService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OrderStatusServlet", value = "/api/order-status")
public class OrderStatusServlet extends HttpServlet {
    private GHNService ghnService;

    @Override
    public void init() throws ServletException {
        super.init();
        ghnService = new GHNService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            JSONObject json = new JSONObject(sb.toString());
            String ghnOrderCode = json.getString("ghnOrderCode");

            String status = ghnService.getOrderStatus(ghnOrderCode);

            JSONObject result = new JSONObject();
            result.put("success", true);
            result.put("status", status);
            out.print(result.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", "Không thể lấy trạng thái đơn hàng: " + e.getMessage());
            out.print(error.toString());
        } finally {
            out.flush();
        }
    }

}