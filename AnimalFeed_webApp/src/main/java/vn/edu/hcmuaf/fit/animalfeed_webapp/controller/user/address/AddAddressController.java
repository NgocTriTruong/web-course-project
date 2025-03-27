package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user.address;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.AddressDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Address;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/add-address")
public class AddAddressController extends HttpServlet {
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
        String province = jsonObject.getString("province");
        String district = jsonObject.getString("district");
        String ward = jsonObject.getString("ward");
        String detail = jsonObject.getString("detail");
        String note = jsonObject.optString("note", "");

        // Lấy userId từ session
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.getWriter().write("{\"success\": false, \"message\": \"User not logged in\"}");
            return;
        }
        int userId = user.getId();

        // Tạo đối tượng Address
        Address address = new Address(0, userId, detail, ward, district, province, note);

        // Lưu vào cơ sở dữ liệu
        boolean success = addressDao.addAddress(address);

        // Trả về phản hồi JSON
        JSONObject responseJson = new JSONObject();
        responseJson.put("success", success);
        if (!success) {
            responseJson.put("message", "Failed to save address");
        }
        response.getWriter().write(responseJson.toString());
    }
}