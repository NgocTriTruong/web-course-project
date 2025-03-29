package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user.address;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.AddressDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Address;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/update-address")
public class UpdateAddressController extends HttpServlet {
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
        String detail = jsonObject.getString("detail");
        String ward = jsonObject.getString("ward");
        String district = jsonObject.getString("district");
        String province = jsonObject.getString("province");
        String note = jsonObject.optString("note", "");

        Address address = addressDao.getAddressById(id);
        if (address != null) {
            address.setDetail(detail);
            address.setWard(ward);
            address.setDistrict(district);
            address.setProvince(province);
            address.setNote(note);
            boolean success = addressDao.updateAddress(address);

            JSONObject responseJson = new JSONObject();
            responseJson.put("success", success);
            if (!success) {
                responseJson.put("message", "Failed to update address");
            }
            response.getWriter().write(responseJson.toString());
        } else {
            response.getWriter().write("{\"success\": false, \"message\": \"Address not found\"}");
        }
    }
}