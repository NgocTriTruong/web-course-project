package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;

@WebServlet(name = "DeleteProductAdmin", value = "/delete-product")
public class DeleteProductAdmin extends HttpServlet {

    private ProductService productService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        try {
            String productId = request.getParameter("productId");
            if (productId == null || productId.trim().isEmpty()) {
                throw new IllegalArgumentException("Product ID is required");
            }

            int id = Integer.parseInt(productId.trim());
            productService.deleteProduct(id, userId);

            session.setAttribute("message", "Xóa sản phẩm thành công!");
            response.sendRedirect("product-manager");

        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
            response.sendRedirect("product-manager");
        } catch (Exception e) {
            session.setAttribute("error", "Có lỗi xảy ra khi xóa sản phẩm: " + e.getMessage());
            response.sendRedirect("product-manager");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}