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
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
@WebServlet(name = "EditProductAdmin", value = "/edit-product")
public class EditProductAdmin extends HttpServlet {

    private ProductService productService;
    private CategoryService categoryService;
    private DiscountService discountService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();
        discountService = new DiscountService();
        userService = UserService.getInstance(); // Khởi tạo UserService
    }

    // Kiểm tra quyền PRODUCT_MANAGEMENT
    private boolean hasProductManagementPermission(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return false;
        }
        return userService.hasPermission(userId, "PRODUCT_MANAGEMENT");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !hasProductManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền truy cập trang này.");
            response.sendRedirect("product-manager");
            return;
        }

        try {
            String productId = request.getParameter("productId");
            if (productId == null || productId.trim().isEmpty()) {
                throw new IllegalArgumentException("Product ID is required");
            }
            int id = Integer.parseInt(productId.trim());

            Product product = productService.getProductByIdOfAdmin(id);
            if (product == null) {
                throw new IllegalArgumentException("Product not found");
            }

            List<Category> categories = categoryService.getAll();
            List<Discount> discounts = discountService.getAll();

            request.setAttribute("product", product);
            request.setAttribute("categoriesData", categories);
            request.setAttribute("discountsData", discounts);

            request.getRequestDispatcher("views/admin/productEdit.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("error", e.getMessage());
            response.sendRedirect("product-manager");
        } catch (Exception e) {
            log("Error in doGet: ", e);
            request.getSession().setAttribute("error", "Không thể tải trang chỉnh sửa sản phẩm.");
            response.sendRedirect("product-manager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        if (!hasProductManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền thực hiện thao tác này.");
            response.sendRedirect("product-manager");
            return;
        }

        try {
            // Lấy thông tin từ form
            String productIdParam = getFormFieldValue(request.getPart("productId"));
            if (productIdParam == null || productIdParam.isEmpty()) {
                throw new IllegalArgumentException("Product ID is required");
            }
            int productId = Integer.parseInt(productIdParam);

            String name = getFormFieldValue(request.getPart("name"));
            String description = getFormFieldValue(request.getPart("description"));
            String categoryIdParam = getFormFieldValue(request.getPart("category"));
            String priceParam = getFormFieldValue(request.getPart("price"));
            String quantityParam = getFormFieldValue(request.getPart("quantity"));
            String discountIdParam = getFormFieldValue(request.getPart("discount"));
            String statusParam = getFormFieldValue(request.getPart("status"));

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

            // Xử lý file ảnh
            Part filePart = request.getPart("image");
            String imagePath = null;
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadDir = getServletContext().getRealPath("") + "/uploads";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }
                String filePath = uploadDir + File.separator + fileName;
                filePart.write(filePath);
                imagePath = "/uploads/" + fileName;
            } else {
                // Nếu không có ảnh mới, giữ nguyên ảnh cũ
                Product existingProduct = productService.getProductByIdOfAdmin(productId);
                imagePath = existingProduct.getImg();
            }

            // Tạo đối tượng sản phẩm mới
            Product product = new Product();
            product.setId(productId);
            product.setCat_id(categoryId);
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setStatus(status);
            product.setImg(imagePath);
            product.setDiscountId(discountId);

            // Cập nhật sản phẩm
            productService.updateProduct(product, userId);

            // Chuyển hướng thành công
            session.setAttribute("message", "Cập nhật sản phẩm thành công!");
            response.sendRedirect("product-manager");

        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("edit-product?productId=" + getFormFieldValue(request.getPart("productId")));
        } catch (Exception e) {
            log("Error in doPost: ", e);
            session.setAttribute("error", "Có lỗi xảy ra khi cập nhật sản phẩm: " + e.getMessage());
            response.sendRedirect("edit-product?productId=" + getFormFieldValue(request.getPart("productId")));
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