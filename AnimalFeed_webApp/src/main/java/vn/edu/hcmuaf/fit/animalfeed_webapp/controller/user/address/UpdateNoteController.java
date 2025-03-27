package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user.address;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.AddressDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Address;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/update-note")
public class UpdateNoteController extends HttpServlet {
    private AddressDao addressDao;

    @Override
    public void init() throws ServletException {
        addressDao = new AddressDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Đọc dữ liệu JSON từ request
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        // Parse JSON
        JSONObject jsonObject = new JSONObject(jsonBuffer.toString());
        int id = jsonObject.getInt("id");
        String note = jsonObject.getString("note");

        // Cập nhật ghi chú
        Address address = addressDao.getAddressById(id);
        if (address != null) {
            address.setNote(note);
            boolean success = addressDao.updateAddress(address);

            // Trả về phản hồi JSON
            JSONObject responseJson = new JSONObject();
            responseJson.put("success", success);
            if (!success) {
                responseJson.put("message", "Failed to update note");
            }
            response.getWriter().write(responseJson.toString());
        } else {
            response.getWriter().write("{\"success\": false, \"message\": \"Address not found\"}");
        }
    }
}