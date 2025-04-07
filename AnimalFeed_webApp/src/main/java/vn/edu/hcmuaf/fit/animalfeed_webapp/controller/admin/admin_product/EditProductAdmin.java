package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_product;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.DiscountService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
@WebServlet(name = "EditProductAdmin", value = "/edit-product")
public class EditProductAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        CategoryService categoryService = new CategoryService();
        DiscountService discountService = new DiscountService();

        String productId = request.getParameter("productId");
        int id = Integer.parseInt(productId.trim());

        Product products = productService.getProductByIdOfAdmin(id);
        List<Category> categories = categoryService.getAll();
        List<Discount> discounts = discountService.getAll();

        request.setAttribute("product", products);
        request.setAttribute("categoriesData", categories);
        request.setAttribute("discountsData",discounts);

        request.getRequestDispatcher("views/admin/productEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy thông tin từ form
            String name = getFormFieldValue(request.getPart("name"));
            String description = getFormFieldValue(request.getPart("description"));
            String categoryIdParam = getFormFieldValue(request.getPart("category"));
            String priceParam = getFormFieldValue(request.getPart("price"));
            String quantityParam = getFormFieldValue(request.getPart("quantity"));
            String discountIdParam = getFormFieldValue(request.getPart("discount"));
            String statusParam = getFormFieldValue(request.getPart("status"));

            String productIdParam = getFormFieldValue(request.getPart("productId"));
            if (productIdParam == null || productIdParam.isEmpty()) {
                throw new IllegalArgumentException("Product ID is required");
            }
            int productId = Integer.parseInt(productIdParam);

            // Kiểm tra và xử lý tham số
            if (categoryIdParam == null || categoryIdParam.trim().isEmpty()) {
                throw new IllegalArgumentException("Category is required");
            }
            int categoryId = Integer.parseInt(categoryIdParam);

            if (priceParam == null || priceParam.isEmpty()) {
                throw new IllegalArgumentException("Price is required");
            }
            double price = Double.parseDouble(priceParam);

            if (quantityParam == null || quantityParam.isEmpty()) {
                throw new IllegalArgumentException("Quantity is required");
            }
            int quantity = Integer.parseInt(quantityParam);

            if (discountIdParam == null || discountIdParam.isEmpty()) {
                throw new IllegalArgumentException("Discount ID is required");
            }
            int discountId = Integer.parseInt(discountIdParam);

            if (statusParam == null || statusParam.isEmpty()) {
                throw new IllegalArgumentException("Status is required");
            }
            int status = Integer.parseInt(statusParam);

            // Log thông tin file ảnh
            Part filePart = request.getPart("image");
            String imagePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = filePart.getSubmittedFileName();
                System.out.println("File uploaded: " + fileName);
                String uploadDir = getServletContext().getRealPath("/uploads");
                filePart.write(uploadDir + "/" + fileName);
                imagePath = "/uploads/" + fileName;
            } else {
                System.out.println("No file uploaded.");
            }

            // Tạo đối tượng sản phẩm mới
            Product product = new Product();
            product.setId(productId); // Đặt ID sản phẩm
            product.setCat_id(categoryId);
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setStatus(status);
            if (imagePath != null) {
                product.setImg(imagePath);
            }
            product.setDiscountId(discountId);

            System.out.println("Updated product object: " + product);

            // Lấy userId từ session
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                System.out.println("User not logged in. Redirecting to login.");
                response.sendRedirect("login");
                return;
            }
            System.out.println("User ID: " + userId);

            // Thêm sản phẩm vào database
            ProductService productService = new ProductService();
            productService.updateProduct(productId, product, userId);

            // Log success và chuyển hướng
            System.out.println("Product updated successfully. Redirecting to product-manager.");
            response.sendRedirect("product-manager");

        } catch (Exception e) {
            // Log lỗi và trả về thông báo lỗi
            System.err.println("Error during product update: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred: " + e.getMessage());
        }
    }

    // Hàm hỗ trợ lấy giá trị từ Part
    private String getFormFieldValue(Part part) throws IOException {
        if (part == null || part.getSize() == 0) {
            return null;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}