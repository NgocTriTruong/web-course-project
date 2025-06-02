package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.ghncontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ghn_service.GHNService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "WardController", value = "/api/ghn/wards")
public class WardController extends HttpServlet {
    private final GHNService ghnService = new GHNService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            StringBuilder sb = new StringBuilder();
            String line;
            try (var reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            try {
                JSONObject json = new JSONObject(sb.toString());
                int districtId = json.getInt("district_id");
                JSONArray wards = ghnService.getWards(districtId);
                out.print(wards.toString());
            } catch (JSONException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(new JSONObject().put("error", "Invalid district_id: " + e.getMessage()).toString());
            }
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(new JSONObject().put("error", "Failed to fetch wards: " + e.getMessage()).toString());
        } finally {
            out.flush();
        }
    }
}