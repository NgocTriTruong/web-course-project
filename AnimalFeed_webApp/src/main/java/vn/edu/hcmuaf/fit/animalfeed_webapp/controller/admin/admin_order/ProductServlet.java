package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_order;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        String idParam = request.getParameter("id");
        int productId = Integer.parseInt(idParam);
        Product product = productService.getProductById(productId);

        Gson gson = new Gson();
        if (product != null) {
            response.getWriter().write(gson.toJson(product));
        } else {
            response.getWriter().write("{\"error\": \"Sản phẩm không tồn tại\"}");
        }
    }
}