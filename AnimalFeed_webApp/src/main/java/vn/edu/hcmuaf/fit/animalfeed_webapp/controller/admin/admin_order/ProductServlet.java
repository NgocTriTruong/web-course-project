package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDTO;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductWithDiscountDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.ProductDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", value = "/api/product")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    private ProductWithDiscountDao productWithDiscountDao;

    @Override
    public void init() {
        productService = new ProductService();
        productWithDiscountDao = new ProductWithDiscountDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

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

                // Kiểm tra xem sản phẩm có giảm giá không
                double finalPrice = product.getPrice(); // Giá gốc
                List<ProductWithDiscountDTO> discountedProducts = productWithDiscountDao.discountedProducts();
                System.out.println("Discounted products retrieved: " + discountedProducts.size());
                for (ProductWithDiscountDTO discountedProduct : discountedProducts) {
                    if (discountedProduct.getId() == product.getId()) {
                        finalPrice = discountedProduct.getDiscountedPrice(); // Giá đã giảm
                        System.out.println("Discount applied. Original Price: " + product.getPrice() + ", Discounted Price: " + finalPrice);
                        break;
                    }
                }

                // Trả về ProductDTO với giá đã giảm
                ProductDTO productDTO = new ProductDTO(product.getId(), product.getName(), finalPrice);
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