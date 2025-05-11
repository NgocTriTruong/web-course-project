package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.ghncontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ghn_service.GHNService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ProvinceController", value = "/api/ghn/provinces")
public class ProvinceController extends HttpServlet {
    private final GHNService ghnService = new GHNService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            JSONArray provinces = ghnService.getProvinces();
            out.print(provinces.toString());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(new JSONObject().put("error", "Failed to fetch provinces: " + e.getMessage()).toString());
        } finally {
            out.flush();
        }
    }
}