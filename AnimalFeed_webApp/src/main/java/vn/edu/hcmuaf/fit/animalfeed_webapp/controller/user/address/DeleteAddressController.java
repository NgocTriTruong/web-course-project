package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user.address;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.AddressDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/delete-address")
public class DeleteAddressController extends HttpServlet {
    private AddressDao addressDao;

    @Override
    public void init() throws ServletException {
        addressDao = new AddressDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        JSONObject jsonObject = new JSONObject(jsonBuffer.toString());
        int id = jsonObject.getInt("id");

        boolean success = addressDao.deleteAddress(id);

        JSONObject responseJson = new JSONObject();
        responseJson.put("success", success);
        if (!success) {
            responseJson.put("message", "Failed to delete address");
        }
        response.getWriter().write(responseJson.toString());
    }
}