package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cartController;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.CartDetail;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {
    private CartService cartService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        cartService = new CartService();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Get product details
            int productId = Integer.parseInt(request.getParameter("productId"));
            Product product = productService.getDetail(productId);

            if (product == null) {
                response.sendRedirect("list-product?addCart=false");
                return;
            }

            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.sendRedirect("login");
                return;
            }

            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            // Lấy số lượng người dùng chọn, mặc định là 1
            int requestedQuantity = 1;
            if (request.getParameter("quantity") != null && !request.getParameter("quantity").isEmpty()) {
                requestedQuantity = Integer.parseInt(request.getParameter("quantity"));
                if (requestedQuantity < 1) requestedQuantity = 1;
            }

            // Lấy số lượng trong giỏ hàng
            int currentQuantity = 0;
            if (cartService.getCDById(productId, user.getId())) {
                CartItem existingCartItem = cart.getCartDetails().stream()
                        .filter(item -> item.getProductId() == productId)
                        .findFirst()
                        .orElse(null);
                if (existingCartItem != null) {
                    currentQuantity = existingCartItem.getQuantity();
                }
            }

            int newQuantity = currentQuantity + requestedQuantity;

            // Kiểm tra tồn kho
            int availableQuantity = productService.getInventoryQuantity(productId);
            if (newQuantity > availableQuantity) {
                newQuantity = availableQuantity;
                requestedQuantity = availableQuantity - currentQuantity;
                if (requestedQuantity < 0) requestedQuantity = 0;
                session.setAttribute("cartError", "Số lượng yêu cầu vượt quá tồn kho. Số lượng tối đa có thể thêm: " + requestedQuantity);
                response.sendRedirect("product-detail?pid=" + productId + "&addCart=false&quantity=" + requestedQuantity);
                return;
            } else {
                session.removeAttribute("cartError");
            }

            CartDetail cartDetail = new CartDetail();
            cartDetail.setUserId(user.getId());
            cartDetail.setProductId(product.getId());
            cartDetail.setQuantity(newQuantity);
            cartDetail.setTotal(cart.getDiscountedPrice(product) * newQuantity);
            cartDetail.setStatus(0);

            if (cartService.getCDById(productId, user.getId())) {
                cartService.updateQuantity(cartDetail.getProductId(), cartDetail.getUserId(), newQuantity);
            } else {
                cartService.insertCD(cartDetail);
            }
            cart.addProduct(product, user.getId(), newQuantity);

            session.setAttribute("cart", cart);

            response.sendRedirect("product-detail?pid=" + productId + "&addCart=true&quantity=" + requestedQuantity);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("list-product?addCart=false&error=" + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}