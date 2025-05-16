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
//import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Shop;

public class GHNService {
    private static final String BASE_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api";
    private static final String TOKEN = "61680476-2db9-11f0-9b81-222185cb68c8";
    private static final int SHOP_ID = 196542;

    public String getCurrentShopId() {
        return String.valueOf(SHOP_ID);
    }

    // chuyển tên tỉnh thành id
    public int getProvinceIdByName(String provinceName) throws IOException {
        JSONArray provinces = getProvinces();
        String normalizedProvinceName = normalizeName(provinceName);
        System.out.println("Tìm ProvinceID cho: " + provinceName + " (normalized: " + normalizedProvinceName + ")");
        for (int i = 0; i < provinces.length(); i++) {
            JSONObject province = provinces.getJSONObject(i);
            String apiProvinceName = normalizeName(province.getString("ProvinceName"));
            if (apiProvinceName.equals(normalizedProvinceName) ||
                    apiProvinceName.contains(normalizedProvinceName) ||
                    normalizedProvinceName.contains(apiProvinceName)) {
                int provinceId = province.getInt("ProvinceID");
                System.out.println("Tìm thấy ProvinceID: " + provinceId + " cho " + province.getString("ProvinceName"));
                return provinceId;
            }
        }
        throw new IOException("Không tìm thấy ProvinceID cho " + provinceName);
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
        //test getProvinces
        try {
            JSONArray provinces = ghnService.getProvinces();
            for (int i = 0; i < provinces.length(); i++) {
                JSONObject province = provinces.getJSONObject(i);
                System.out.println("Province ID: " + province.getInt("ProvinceID") + ", Name: " + province.getString("ProvinceName"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
