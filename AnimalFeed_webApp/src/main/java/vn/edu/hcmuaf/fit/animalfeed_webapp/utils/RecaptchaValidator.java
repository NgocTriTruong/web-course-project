//package vn.edu.hcmuaf.fit.animalfeed_webapp.utils;
//
//import org.json.JSONObject;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Scanner;
//
//public class RecaptchaValidator {
//    private static final String SECRET_KEY = "6LciYeoqAAAAAB9XEXALqLD7b7H8UCZBEOy2BryL";
//
//    public static boolean verify(String recaptchaResponse) {
//        try {
//            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//
//            String postParams = "secret=" + SECRET_KEY + "&response=" + recaptchaResponse;
//            try (OutputStream os = conn.getOutputStream()) {
//                os.write(postParams.getBytes());
//            }
//
//            Scanner scanner = new Scanner(conn.getInputStream());
//            String response = scanner.useDelimiter("\\A").next();
//            scanner.close();
//
//            JSONObject json = new JSONObject(response);
//            return json.getBoolean("success");
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
//

package vn.edu.hcmuaf.fit.animalfeed_webapp.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecaptchaValidator {
    public static boolean verify(String secretKey, String response) {
        try {
            URL url = new URL("https://www.google.com/recaptcha/api/siteverify");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            String params = "secret=" + secretKey + "&response=" + response;

            try (OutputStream os = conn.getOutputStream()) {
                os.write(params.getBytes());
                os.flush();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();

            while ((line = br.readLine()) != null) {
                output.append(line);
            }

            return output.toString().contains("\"success\": true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}