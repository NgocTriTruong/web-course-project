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

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Tăng giới hạn file upload lên 5MB
@WebServlet(name = "AddProductAdmin", value = "/add-product")
public class AddProductAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // Lấy danh sách danh mục và khuyến mãi
            CategoryService categoryService = new CategoryService();
            DiscountService discountService = new DiscountService();

            List<Category> categories = categoryService.getAll();
            List<Discount> discounts = discountService.getAll();

            request.setAttribute("categoriesData", categories);
            request.setAttribute("discountsData",discounts);

            // Chuyển đến trang thêm sản phẩm
            request.getRequestDispatcher("views/admin/productAddition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy thông tin từ form
            int categoryId = Integer.parseInt(request.getParameter("category"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int discountId = Integer.parseInt(request.getParameter("discount"));
            String description = request.getParameter("description");

            // Xử lý file upload
            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/uploads");

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            // Tạo đối tượng Product
            Product product = new Product();
            product.setCat_id(categoryId);
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setImg("/uploads/" + fileName);
            product.setCreateDate(LocalDate.now());
            product.setDiscountId(discountId);

            // Lấy userId từ session
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Thêm sản phẩm vào database
            ProductService productService = new ProductService();
            productService.insertProduct(product, userId);

            // Chuyển hướng về trang quản lý sản phẩm
            response.sendRedirect("product-manager?success=true");
        } catch (NumberFormatException e) {
            // Xử lý lỗi dữ liệu không hợp lệ
            request.setAttribute("error", "Dữ liệu nhập vào không hợp lệ.");
            request.getRequestDispatcher("views/admin/productAddition.jsp").forward(request, response);
        }
    }
}
