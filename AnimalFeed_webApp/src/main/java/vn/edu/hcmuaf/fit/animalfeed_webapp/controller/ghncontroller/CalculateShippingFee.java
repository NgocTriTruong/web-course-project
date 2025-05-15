package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.ghncontroller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ghn_service.GHNService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CalculateShippingFeeController", value = "/calculate-shipping-fee")
public class CalculateShippingFee extends HttpServlet {
    private final GHNService ghnService = new GHNService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();
        String line;
        try (var reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        JSONObject jsonResponse = new JSONObject();
        try {
            JSONObject json = new JSONObject(sb.toString());
            String province = json.getString("province");
            String district = json.getString("district");
            String ward = json.getString("ward");
            String wardCode = json.getString("wardCode");

            int provinceId = ghnService.getProvinceIdByName(province);
            int toDistrictId = ghnService.getDistrictIdByName(district, provinceId);
            int fromDistrictId = ghnService.getDistrictIdByName("Thủ Đức", ghnService.getProvinceIdByName("Hồ Chí Minh"));
            int totalWeight = 25000; // Example: 25kg per item, adjust based on cart

            double shippingFee = ghnService.calculateShippingFee(fromDistrictId, toDistrictId, wardCode, totalWeight);

            jsonResponse.put("success", true);
            jsonResponse.put("shippingFee", shippingFee);
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }
}
