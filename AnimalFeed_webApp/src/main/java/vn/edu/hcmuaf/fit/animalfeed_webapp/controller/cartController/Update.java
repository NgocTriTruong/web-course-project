package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cartController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CartService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;

@WebServlet(name = "Update", value = "/update-cart")
public class Update extends HttpServlet {
    private CartService cartService = new CartService();
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonResponse = new JSONObject();

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = request.getParameter("quantity") != null ? Integer.parseInt(request.getParameter("quantity")) : -1;
            int status = request.getParameter("status") != null ? Integer.parseInt(request.getParameter("status")) : -1;

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Vui lòng đăng nhập");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            if (quantity != -1) {
                // Kiểm tra số lượng còn lại
                int availableQuantity = productService.getInventoryQuantity(productId);
                int adjustedQuantity = quantity;
                boolean success = true;
                String message = "";

                if (quantity < 1) {
                    adjustedQuantity = 1;
                    success = false;
                    message = "Số lượng tối thiểu là 1.";
                } else if (quantity > availableQuantity) {
                    adjustedQuantity = availableQuantity;
                    success = false;
                    message = "Số lượng tối đa bạn có thể chọn là " + availableQuantity;
                }

                if (success) {
                    cart.updateQuantity(productId, adjustedQuantity);
                    cartService.updateQuantity(productId, user.getId(), adjustedQuantity);
                    session.setAttribute("cart", cart);
                }

                jsonResponse.put("success", success);
                jsonResponse.put("adjustedQuantity", adjustedQuantity);
                jsonResponse.put("message", message);
            }

            if (status != -1) {
                cart.updateStatus(productId, status);
                cartService.updateStatus(productId, user.getId(), status);
                session.setAttribute("cart", cart);
                jsonResponse.put("success", true);
                jsonResponse.put("adjustedQuantity", cart.getCartDetails().stream()
                        .filter(item -> item.getProductId() == productId)
                        .findFirst()
                        .map(item -> item.getQuantity())
                        .orElse(0));
                jsonResponse.put("message", "");
            }

            response.getWriter().write(jsonResponse.toString());
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Dữ liệu không hợp lệ");
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Lỗi server: " + e.getMessage());
            response.getWriter().write(jsonResponse.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}