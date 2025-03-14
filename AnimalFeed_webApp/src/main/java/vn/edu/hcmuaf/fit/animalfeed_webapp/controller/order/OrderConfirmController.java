package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.dto.CartItem;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.DiscountService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="order-confirm", value="/order-confirm")
public class OrderConfirmController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CategoryService categoryService = new CategoryService();
        //Lay danh muc
        List<Category> categories = categoryService.getAll();
        request.setAttribute("categoriesData", categories);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Check if user is logged in
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String productIdStr = request.getParameter("productId");
        String quantityStr = request.getParameter("quantity");

        // Try to get confirmed items from session (for Buy Now)
        List<CartItem> confirmedItems = (List<CartItem>) session.getAttribute("confirmedItems");
        Double totalPrice = (Double) session.getAttribute("totalPrice");
        Integer totalQuantity = (Integer) session.getAttribute("totalQuantity");

        if (productIdStr != null && quantityStr != null) {
            try {
                int productId = Integer.parseInt(productIdStr);
                int quantity = Integer.parseInt(quantityStr);

                // Fetch product details (you'll need a ProductService)
                ProductService productService = new ProductService(); // Implement this service
                vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product product = productService.getProductById(productId);

                if (product == null) {
                    response.sendRedirect(request.getContextPath() + "/cart");
                    return;
                }

                // Create a CartItem for the "Buy Now" product
                CartItem cartItem = new CartItem();
                cartItem.setProductId(productId);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(product.getPrice());
                cartItem.setTotal(product.getPrice() * quantity);
                cartItem.setImg(product.getImg()); // Set the image
                cartItem.setName(product.getName()); // Set the name for display
                cartItem.setDesc(product.getDescription()); // Set the description
                cartItem.setProduct(product);

                DiscountService discountService = new DiscountService();
                double unitPrice = (product.getDiscountId() > 1)
                        ? discountService.calculateDiscountedPrice(product.getPrice(), product.getDiscountId())
                        : product.getPrice();
                cartItem.setUnitPrice(unitPrice);
                cartItem.setTotal(unitPrice * quantity);

                confirmedItems = new ArrayList<>();
                confirmedItems.add(cartItem);

                totalPrice = unitPrice * quantity;
                totalQuantity = quantity;

                // Store in session for the confirmation page
                session.setAttribute("confirmedItems", confirmedItems);
                session.setAttribute("totalPrice", totalPrice);
                session.setAttribute("totalQuantity", totalQuantity);

            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
        } else {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            confirmedItems = cart.getConfirmedCartItem();
            if (confirmedItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            totalPrice = cart.getTotalPrice();
            totalQuantity = cart.getTotalQuantity();
        }

        // Set attributes for the JSP
        request.setAttribute("confirmedItems", confirmedItems);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("totalQuantity", totalQuantity);
        request.setAttribute("customerInfo", session.getAttribute("customerInfo"));

        // Forward to the order confirmation page
        request.getRequestDispatcher("/views/web/order/confirm_order.jsp")
                .forward(request, response);
    }
}