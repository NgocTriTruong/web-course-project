package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import com.google.gson.Gson;

import java.io.IOException;

@WebServlet(name = "ProductServlet", value = "/api/product")
public class ProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson(); // Không cần TypeAdapter vì DTO không chứa LocalDate

        String value = request.getParameter("id");
        Product product = null;

        try {
            if (value == null || value.trim().isEmpty()) {
                throw new IllegalArgumentException("Tham số 'id' không được để trống");
            }

            try {
                int productId = Integer.parseInt(value);
                System.out.println("Finding product by ID: " + productId);
                product = productService.getProductById(productId);
            } catch (NumberFormatException e) {
                System.out.println("Finding product by name: " + value);
                product = productService.getProductByName(value);
            }

            if (product != null && product.getStatus() == 1) {
                System.out.println("Product found: " + product.getName());
                ProductDTO productDTO = new ProductDTO(product.getId(), product.getName());
                response.getWriter().write(gson.toJson(productDTO));
            } else {
                System.out.println("No active product found for value: " + value);
                response.getWriter().write("{\"error\": \"Sản phẩm không tồn tại hoặc không hoạt động\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Đã xảy ra lỗi khi tìm sản phẩm: " + e.getMessage() + "\"}");
        }
    }
}