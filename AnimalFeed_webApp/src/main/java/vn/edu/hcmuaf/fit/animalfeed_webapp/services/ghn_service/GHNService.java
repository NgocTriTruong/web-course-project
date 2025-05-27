package vn.edu.hcmuaf.fit.animalfeed_webapp.services.ghn_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Shop;

public class GHNService {
    private static final String BASE_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api";
    private static final String TOKEN = "61680476-2db9-11f0-9b81-222185cb68c8";
    private static final int SHOP_ID = 196542;
    private static final String SHOP_NAME = "VINAFEED";
    private static final String SHOP_ADDRESS = "22, Hiệp Bình Phước, Thủ Đức, Hồ Chí Minh, Vietnam";

    public String getCurrentShopId() {
        return String.valueOf(SHOP_ID);
    }

    public Shop getCurrentShop() {
        return new Shop(SHOP_ID, SHOP_NAME, SHOP_ADDRESS, "Thủ Đức", "Hồ Chí Minh");
    }

    // chuyển tên tỉnh thành id
    public int getProvinceIdByName(String provinceName) throws IOException {
        JSONArray provinces = getProvinces();
        String normalizedProvinceName = normalizeName(provinceName);
        System.out.println("Tìm ProvinceID cho: " + provinceName + " (normalized: " + normalizedProvinceName + ")");
        for (int i = 0; i < provinces.length(); i++) {
            JSONObject province = provinces.getJSONObject(i);
            String apiProvinceName = normalizeName(province.getString("ProvinceName"));
            if (apiProvinceName.equals(normalizedProvinceName)) {
                int provinceId = province.getInt("ProvinceID");
                System.out.println("Tìm thấy ProvinceID: " + provinceId + " cho " + province.getString("ProvinceName"));
                return provinceId;
            }
        }
        throw new IOException("Không tìm thấy ProvinceID cho " + provinceName);
    }

    // Tính phí vận chuyển
    public double calculateShippingFee(int fromDistrictId, int toDistrictId, String toWardCode, int weight) throws IOException {
        // Kiểm tra dữ liệu đầu vào
        if (fromDistrictId <= 0 || toDistrictId <= 0 || toWardCode == null || toWardCode.isEmpty()) {
            throw new IOException("Invalid input: fromDistrictId=" + fromDistrictId + ", toDistrictId=" + toDistrictId + ", toWardCode=" + toWardCode);
        }

        System.out.println("Calculating shipping fee with weight: " + weight + " grams");

        // Lấy danh sách dịch vụ khả dụng
        JSONArray services = getAvailableServices(fromDistrictId, toDistrictId);
        if (services.length() == 0) {
            throw new IOException("No available services for the selected districts");
        }

        // Tìm dịch vụ xe tải
        int serviceId = -1;
        for (int i = 0; i < services.length(); i++) {
            JSONObject service = services.getJSONObject(i);
            String shortName = service.getString("short_name").toLowerCase();
            if (shortName.contains("truck") || shortName.contains("xe tải") || shortName.contains("heavy")) {
                serviceId = service.getInt("service_id");
                break;
            }
        }
        if (serviceId == -1) {
            // Nếu không tìm thấy dịch vụ xe tải, chọn dịch vụ đầu tiên
            serviceId = services.getJSONObject(0).getInt("service_id");
            System.out.println("Warning: No truck service found, using default service_id: " + serviceId);
        }

        URL url = new URL(BASE_URL + "/v2/shipping-order/fee");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);
        conn.setDoOutput(true);

        // Tạo request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("from_district_id", fromDistrictId);
        requestBody.put("to_district_id", toDistrictId);
        requestBody.put("to_ward_code", toWardCode);
        requestBody.put("weight", weight);
        requestBody.put("service_id", serviceId);
        requestBody.put("length", 50); // Kích thước mỗi bao
        requestBody.put("width", 30);
        requestBody.put("height", 20);

        // Ghi log request body để gỡ lỗi
        System.out.println("GHN Shipping Fee Request: " + requestBody.toString());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            // Đọc thông báo lỗi từ API
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            StringBuilder errorResponse = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine.trim());
            }
            errorReader.close();
            throw new IOException("HTTP " + responseCode + ": " + errorResponse.toString());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        br.close();

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.getInt("code") == 200) {
            JSONObject data = jsonResponse.getJSONObject("data");
            return data.getDouble("total");
        } else {
            throw new IOException("Error calculating shipping fee: " + jsonResponse.getString("message"));
        }
    }

    // Lấy danh sách dịch vụ khả dụng
    private JSONArray getAvailableServices(int fromDistrictId, int toDistrictId) throws IOException {
        URL url = new URL(BASE_URL + "/v2/shipping-order/available-services");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);
        conn.setDoOutput(true);

        JSONObject requestBody = new JSONObject();
        requestBody.put("shop_id", SHOP_ID);
        requestBody.put("from_district", fromDistrictId);
        requestBody.put("to_district", toDistrictId);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder errorResponse = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine.trim());
            }
            throw new IOException("HTTP " + responseCode + ": " + errorResponse.toString());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.getInt("code") == 200) {
            return jsonResponse.getJSONArray("data");
        } else {
            throw new IOException("Error fetching available services: " + jsonResponse.getString("message"));
        }
    }

    // Tạo đơn hàng
    public String createShippingOrder(
            String customerName, String customerPhone, String toAddress,
            String toWardCode, int toDistrictId, String toWardName,
            String toDistrictName, String toProvinceName, int toProvinceId,
            List<CartItem> items, String paymentMethod
    ) throws IOException {
        Shop currentShop = getCurrentShop(); // Đảm bảo currentShop được khởi tạo đúng

        URL url = new URL(BASE_URL + "/v2/shipping-order/create");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);
        conn.setRequestProperty("ShopId", getCurrentShopId());
        conn.setDoOutput(true);

        // Tính tổng trọng lượng
        int totalWeight = 0;
        for (CartItem item : items) {
            totalWeight += item.getQuantity() * 25000; // 25kg mỗi bao
        }

        JSONObject requestBody = new JSONObject();
        requestBody.put("payment_type_id", 2);
        requestBody.put("note", "Đơn hàng thức ăn chăn nuôi");
        requestBody.put("required_note", "KHONGCHOXEMHANG");
        requestBody.put("return_phone", "0987654321");
        requestBody.put("return_address", currentShop.getAddress());
        requestBody.put("return_district_id", JSONObject.NULL);
        requestBody.put("return_ward_code", "");
        requestBody.put("client_order_code", "DH" + System.currentTimeMillis());
        requestBody.put("from_name", currentShop.getName());
        requestBody.put("from_phone", "0919361160");
        requestBody.put("from_address", currentShop.getAddress());
        requestBody.put("from_ward_name", "Hiệp Bình Phước");
        requestBody.put("from_district_name", "Thủ Đức");
        requestBody.put("from_province_name", "Hồ Chí Minh");
        requestBody.put("to_name", customerName);
        requestBody.put("to_phone", customerPhone);
        requestBody.put("to_address", toAddress);
        requestBody.put("to_ward_code", toWardCode);
        requestBody.put("to_district_id", toDistrictId);
        requestBody.put("to_ward_name", toWardName);
        requestBody.put("to_district_name", toDistrictName);
        requestBody.put("to_province_name", toProvinceName);
        requestBody.put("to_province_id", toProvinceId);
        requestBody.put("weight", totalWeight); // Tổng trọng lượng
        requestBody.put("length", 50);
        requestBody.put("width", 30);
        requestBody.put("height", 20);
        requestBody.put("pick_station_id", 1444);
        requestBody.put("deliver_station_id", JSONObject.NULL);
        requestBody.put("service_type_id", 5);
        requestBody.put("pickup_time", System.currentTimeMillis() / 1000);
        requestBody.put("pick_shift", new JSONArray().put(2));
        requestBody.put("cod_amount", "VNPAY".equals(paymentMethod) ? 0 : calculateTotalPrice(items)); // Thu hộ cho COD

        JSONArray itemsArray = new JSONArray();
        for (CartItem item : items) {
            JSONObject itemObj = new JSONObject();
            itemObj.put("name", item.getName());
            itemObj.put("quantity", item.getQuantity());
            itemObj.put("weight", item.getQuantity() * 25000); // 25kg mỗi bao
            itemObj.put("length", 50);
            itemObj.put("width", 30);
            itemObj.put("height", 20);
            itemObj.put("category", new JSONObject().put("level1", "Thức ăn chăn nuôi"));
            itemsArray.put(itemObj);
        }
        requestBody.put("items", itemsArray);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            StringBuilder errorResponse = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine.trim());
            }
            throw new IOException("HTTP " + responseCode + ": " + errorResponse.toString());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.getInt("code") == 200) {
            JSONObject data = jsonResponse.getJSONObject("data");
            return data.getString("order_code");
        } else {
            throw new IOException("Error creating shipping order: " + jsonResponse.getString("message"));
        }
    }

    private double calculateTotalPrice(List<CartItem> items) {
        return items.stream().mapToDouble(item -> item.getTotal()).sum();
    }

    // Hủy đơn hàng
    public void cancelOrder(String orderCode) throws IOException {
        if (orderCode == null) return;

        URL url = new URL(BASE_URL + "/v2/switch-status/cancel");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);
        conn.setRequestProperty("ShopId", getCurrentShopId());
        conn.setDoOutput(true);

        JSONObject requestBody = new JSONObject();
        requestBody.put("order_codes", new JSONArray().put(orderCode));

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            StringBuilder errorResponse = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine.trim());
            }
            throw new IOException("HTTP " + responseCode + ": " + errorResponse.toString());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.getInt("code") != 200) {
            throw new IOException("Error cancelling order: " + jsonResponse.getString("message"));
        }
    }

    // Lấy thông tin đơn hàng
    public String getOrderStatus(String orderCode) throws IOException {
        if (orderCode == null) return "N/A";

        URL url = new URL(BASE_URL + "/v2/shipping-order/detail");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);
        conn.setDoOutput(true);

        JSONObject requestBody = new JSONObject();
        requestBody.put("order_code", orderCode);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.getInt("code") == 200) {
            JSONObject data = jsonResponse.getJSONObject("data");
            return data.getString("status");
        } else {
            throw new IOException("Error getting order status: " + jsonResponse.getString("message"));
        }
    }

    // Lấy danh sách tỉnh thành
    public JSONArray getProvinces() throws IOException {
        URL url = new URL(BASE_URL + "/master-data/province");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Token", TOKEN);

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "utf-8"));
            StringBuilder errorResponse = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine.trim());
            }
            throw new IOException("HTTP " + responseCode + ": " + errorResponse.toString());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.getInt("code") == 200) {
            return jsonResponse.getJSONArray("data");
        } else {
            throw new IOException("Error getting provinces: " + jsonResponse.getString("message"));
        }
    }

    // Lấy danh sách quận huyện theo provinceId
    public JSONArray getDistricts(int provinceId) throws IOException {
        URL url = new URL(BASE_URL + "/master-data/district");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);
        conn.setRequestProperty("ShopId", getCurrentShopId());
        conn.setDoOutput(true);

        JSONObject requestBody = new JSONObject();
        requestBody.put("province_id", provinceId);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        BufferedReader br;
        StringBuilder response = new StringBuilder();

        if (responseCode != 200) {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            String errorLine;
            while ((errorLine = br.readLine()) != null) {
                response.append(errorLine.trim());
            }
            throw new IOException("HTTP " + responseCode + ": " + response.toString());
        }

        br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        br.close();

        // In response để debug
        System.out.println("District API Response: " + response.toString());

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.getInt("code") == 200) {
            // Kiểm tra xem "data" có phải là JSONArray không
            if (jsonResponse.has("data") && !jsonResponse.isNull("data")) {
                Object data = jsonResponse.get("data");
                if (data instanceof JSONArray) {
                    return (JSONArray) data;
                } else {
                    throw new IOException("Expected JSONArray for 'data', but got: " + data.getClass().getSimpleName());
                }
            } else {
                throw new IOException("No 'data' field in response or 'data' is null");
            }
        } else {
            throw new IOException("Error getting districts: " + jsonResponse.getString("message"));
        }
    }

    // Lấy danh sách phường xã theo districtId
    public JSONArray getWards(int districtId) throws IOException {
        URL url = new URL(BASE_URL + "/master-data/ward");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Token", TOKEN);
        conn.setRequestProperty("ShopId", getCurrentShopId());
        conn.setDoOutput(true);

        JSONObject requestBody = new JSONObject();
        requestBody.put("district_id", districtId);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = requestBody.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        JSONObject jsonResponse = new JSONObject(response.toString());
        if (jsonResponse.getInt("code") == 200) {
            return jsonResponse.getJSONArray("data");
        } else {
            throw new IOException("Error getting wards: " + jsonResponse.getString("message"));
        }
    }

    // chuyển tên huyện thành id
    public int getDistrictIdByName(String districtName, int provinceId) throws IOException {
        JSONArray districts = getDistricts(provinceId);
        String normalizedDistrictName = normalizeName(districtName);
        System.out.println("Tìm DistrictID cho: " + districtName + " (normalized: " + normalizedDistrictName + ")");
        for (int i = 0; i < districts.length(); i++) {
            JSONObject district = districts.getJSONObject(i);
            String districtNameFromApi = normalizeName(district.getString("DistrictName"));
            if (districtNameFromApi.equals(normalizedDistrictName)) {
                int districtId = district.getInt("DistrictID");
                System.out.println("Tìm thấy DistrictID: " + districtId + " cho " + district.getString("DistrictName"));
                return districtId;
            }
        }
        throw new IOException("Không tìm thấy DistrictID cho " + districtName);
    }

    // Chuyển tên phường xã thành wardCode
    public String getWardCodeByName(String wardName, int districtId) throws IOException {
        JSONArray wards = getWards(districtId);
        String normalizedWardName = normalizeName(wardName);
        System.out.println("Tìm WardCode cho: " + wardName + " (normalized: " + normalizedWardName + ")");
        for (int i = 0; i < wards.length(); i++) {
            JSONObject ward = wards.getJSONObject(i);
            String wardNameFromApi = normalizeName(ward.getString("WardName"));
            if (wardNameFromApi.equals(normalizedWardName)) {
                String wardCode = ward.getString("WardCode");
                System.out.println("Tìm thấy WardCode: " + wardCode + " cho " + ward.getString("WardName"));
                return wardCode;
            }
        }
        throw new IOException("Không tìm thấy WardCode cho " + wardName);
    }

    private String getDistrictName(int districtId) throws IOException {
        JSONArray districts = getDistricts(getProvinceIdByName("Hồ Chí Minh"));
        for (int i = 0; i < districts.length(); i++) {
            JSONObject district = districts.getJSONObject(i);
            if (district.getInt("DistrictID") == districtId) {
                return district.getString("DistrictName");
            }
        }
        return "Unknown";
    }

    // Chuyển đổi tên thành phố, tỉnh, huyện, phường thành tên chuẩn
    private String normalizeName(String name) {
        if (name == null) return "";
        String normalized = name.toLowerCase()
                .replace("tp.", "thành phố ")
                .replace("tỉnh ", "")
                .replace("quận ", "")
                .replace("huyện ", "")
                .replace("phường ", "")
                .replace("xã ", "")
                .replaceAll("[àáảãạăắằẳẵặâấầẩẫậ]", "a")
                .replaceAll("[èéẻẽẹêếềểễệ]", "e")
                .replaceAll("[ìíỉĩị]", "i")
                .replaceAll("[òóỏõọôốồổỗộơớờởỡợ]", "o")
                .replaceAll("[ùúủũụưứừửữự]", "u")
                .replaceAll("[ỳýỷỹỵ]", "y")
                .replace("đ", "d")
                .trim();
        return normalized;
    }

    public static void main(String[] args) {
        GHNService ghnService = new GHNService();

        try {
            // 1. Lấy thông tin địa chỉ giao hàng
            String provinceName = "Hà Nội";
            String districtName = "Cầu Giấy";
            String wardName = "Dịch Vọng";
            String toAddress = "123 Đường Xuân Thủy, Cầu Giấy, Hà Nội";
            String customerName = "Nguyễn Văn A";
            String customerPhone = "0987654321";
            String paymentMethod = "COD"; // Hoặc "VNPAY"

            // 2. Lấy ProvinceID, DistrictID, WardCode
            int provinceId = ghnService.getProvinceIdByName(provinceName);
            int districtId = ghnService.getDistrictIdByName(districtName, provinceId);
            String wardCode = ghnService.getWardCodeByName(wardName, districtId);

            // 3. Tạo danh sách CartItem mẫu
            List<CartItem> cartItems = new ArrayList<>();
            // CartItem 1: 2 bao thức ăn A, giá 250k/bao
            CartItem item1 = new CartItem();
            item1.setId(1);
            item1.setUserId(1);
            item1.setProductId(101);
            item1.setName("Thức ăn chăn nuôi A");
            item1.setQuantity(2);
            item1.setPrice(250000);
            item1.setTotal(250000 * 2); // 500k
            item1.setStatus(1);
            item1.setImg("image_a.jpg");
            item1.setUnitPrice(250000);
            item1.setDesc("Thức ăn chăn nuôi chất lượng cao");

            // CartItem 2: 1 bao thức ăn B, giá 300k/bao
            CartItem item2 = new CartItem();
            item2.setId(2);
            item2.setUserId(1);
            item2.setProductId(102);
            item2.setName("Thức ăn chăn nuôi B");
            item2.setQuantity(1);
            item2.setPrice(300000);
            item2.setTotal(300000 * 1); // 300k
            item2.setStatus(1);
            item2.setImg("image_b.jpg");
            item2.setUnitPrice(300000);
            item2.setDesc("Thức ăn chăn nuôi hữu cơ");

            cartItems.add(item1);
            cartItems.add(item2);

            // 4. Gọi hàm createShippingOrder
            String orderCode = ghnService.createShippingOrder(
                    customerName,
                    customerPhone,
                    toAddress,
                    wardCode,
                    districtId,
                    wardName,
                    districtName,
                    provinceName,
                    provinceId,
                    cartItems,
                    paymentMethod
            );

            // 5. In kết quả
            System.out.println("Tạo đơn hàng thành công! Order Code: " + orderCode);

            // 6. (Tùy chọn) Kiểm tra trạng thái đơn hàng
            String orderStatus = ghnService.getOrderStatus(orderCode);
            System.out.println("Trạng thái đơn hàng: " + orderStatus);

        } catch (IOException e) {
            System.err.println("Lỗi khi tạo đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
