package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Discount;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.DiscountService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,    // 5MB
        maxRequestSize = 1024 * 1024 * 10, // 10MB
        fileSizeThreshold = 1024 * 1024   // 1MB
)
@WebServlet(name = "AddProductAdmin", value = "/add-product")
public class AddProductAdmin extends HttpServlet {

    private static final String BASE_UPLOAD_DIRECTORY = "/views/template/assets/images/product/";
    private CategoryService categoryService;
    private DiscountService discountService;
    private ProductService productService;
    private UserService userService;
    private Map<Integer, String> categoryFolderMap;

    @Override
    public void init() throws ServletException {
        categoryService = new CategoryService();
        discountService = new DiscountService();
        productService = new ProductService();
        userService = UserService.getInstance();

        // Khởi tạo map ánh xạ categoryId với tên thư mục
        categoryFolderMap = new HashMap<>();
        for (Category category : categoryService.getAll()) {
            String fullName = category.getName().trim();
            String[] words = fullName.split("\\s+");
            String lastWord = words[words.length - 1];
            categoryFolderMap.put(category.getId(), lastWord.toLowerCase());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            session.setAttribute("error", "Vui lòng đăng nhập để truy cập.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        User currentUser = userService.getById(userId);
        if (currentUser != null) {
            session.setAttribute("subRole", currentUser.getSub_role());
        } else {
            session.setAttribute("error", "Không tìm thấy thông tin người dùng. Vui lòng đăng nhập lại.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<Category> categories = categoryService.getAll();
            List<Discount> discounts = discountService.getAll();

            if (categories == null || categories.isEmpty() || discounts == null || discounts.isEmpty()) {
                throw new ServletException("Không thể tải dữ liệu danh mục hoặc khuyến mãi.");
            }

            request.setAttribute("categoriesData", categories);
            request.setAttribute("discountsData", discounts);
            request.getRequestDispatcher("/views/admin/productAddition.jsp").forward(request, response);
        } catch (Exception e) {
            session.setAttribute("error", "Không thể tải trang thêm sản phẩm: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/product-manager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        User currentUser = userService.getById(userId);
        if (currentUser == null) {
            session.setAttribute("error", "Không tìm thấy thông tin người dùng. Vui lòng đăng nhập lại.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            Product product = extractProductFromRequest(request);
            if (product == null) {
                throw new IllegalArgumentException("Dữ liệu không hợp lệ");
            }

            String imagePath = handleFileUpload(request, product.getCat_id());
            product.setImg(imagePath != null ? imagePath : "/views/template/assets/images/product/default.jpg");
            product.setCreateDate(LocalDate.now());
            product.setStatus(1);

            productService.insertProduct(product, userId);
            session.setAttribute("message", "Thêm sản phẩm thành công.");
            response.sendRedirect(request.getContextPath() + "/product-manager");

        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
            forwardToForm(request, response);
        } catch (RuntimeException e) {
            session.setAttribute("error", "Lỗi khi thêm sản phẩm: " + e.getMessage());
            forwardToForm(request, response);
        } catch (Exception e) {
            session.setAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm: " + e.getMessage());
            forwardToForm(request, response);
        }
    }

    private Product extractProductFromRequest(HttpServletRequest request) {
        Product product = new Product();
        try {
            product.setCat_id(Integer.parseInt(request.getParameter("category")));
            product.setName(validateString(request.getParameter("name"), "Tên sản phẩm"));
            product.setPrice(Double.parseDouble(request.getParameter("price")));
            product.setQuantity(Integer.parseInt(request.getParameter("quantity")));
            product.setDiscountId(Integer.parseInt(request.getParameter("discount")));
            product.setDescription(request.getParameter("description"));

            if (product.getPrice() < 0 || product.getQuantity() < 0) {
                throw new IllegalArgumentException("Giá và số lượng phải lớn hơn hoặc bằng 0");
            }
            return product;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Dữ liệu số không hợp lệ: " + e.getMessage());
        }
    }

    private String handleFileUpload(HttpServletRequest request, int categoryId)
            throws IOException, ServletException {
        Part filePart = request.getPart("image");
        if (filePart == null || filePart.getSize() == 0) {
            return "/views/template/assets/images/product/default.jpg";
        }

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String categoryFolder = categoryFolderMap.getOrDefault(categoryId, "others");
        String uploadPath = getServletContext().getRealPath("") + BASE_UPLOAD_DIRECTORY + categoryFolder;

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String finalFileName = System.currentTimeMillis() + "_" + fileName;
        String filePath = uploadPath + File.separator + finalFileName;
        filePart.write(filePath);

        return BASE_UPLOAD_DIRECTORY + categoryFolder + "/" + finalFileName;
    }

    private String validateString(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " không được để trống");
        }
        return value.trim();
    }

    private void forwardToForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("categoriesData", categoryService.getAll());
        request.setAttribute("discountsData", discountService.getAll());
        request.getRequestDispatcher("/views/admin/productAddition.jsp").forward(request, response);
    }
}