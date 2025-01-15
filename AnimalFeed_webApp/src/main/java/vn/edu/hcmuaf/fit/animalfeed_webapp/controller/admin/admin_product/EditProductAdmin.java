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

import java.io.IOException;
import java.util.List;

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
        // Lấy thông tin từ form
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        // Lấy thông tin từ form và kiểm tra null
        String categoryIdParam = request.getParameter("category");
        String priceParam = request.getParameter("price");
        String quantityParam = request.getParameter("quantity");
        String discountIdParam = request.getParameter("discount");
        String statusParam = request.getParameter("status");

        // Kiểm tra và xử lý tham số

        if (categoryIdParam == null || categoryIdParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Category is required");
            return;
        }
        int categoryId = Integer.parseInt(categoryIdParam);

        if (priceParam == null || priceParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Price is required");
            return;
        }
        double price = Double.parseDouble(priceParam);

        if (quantityParam == null || quantityParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Quantity is required");
            return;
        }
        int quantity = Integer.parseInt(quantityParam);

        if (discountIdParam == null || discountIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Discount ID is required");
            return;
        }
        int discountId = Integer.parseInt(discountIdParam);

        if (statusParam == null || statusParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Status is required");
            return;
        }
        int status = Integer.parseInt(statusParam);

        // Cập nhật sản phẩm
        ProductService productService = new ProductService();

        // Kiểm tra nếu có file hình ảnh mới
        Part filePart = request.getPart("image");
        String imagePath = null;

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            String uploadDir = getServletContext().getRealPath("/uploads");
            filePart.write(uploadDir + "/" + fileName);
            imagePath = "/uploads/" + fileName;
        }

        // Tạo đối tượng sản phẩm mới
        Product product = new Product();
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

        // Lấy userId từ session
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        // Thêm sản phẩm vào database
        productService.updateProduct(product, userId);

        // Chuyển hướng về trang quản lý sản phẩm
        response.sendRedirect("product-manager");
    }

}