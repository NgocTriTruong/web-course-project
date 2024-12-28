package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.cart.Cart;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "Add", value = "/add-cart")
public class Add extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService productService = new ProductService();
        Product product = productService.getDetail(request.getParameter("productId"));
        if (product == null) {
            response.sendRedirect("list-product?addCart=false");
        }

        HttpSession session = request.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) cart = new Cart();

        cart.addProduct(product, new User(4, "Phạm Văn A", "88888888", "012345678", 1, new Date(), new Date(), 0).getId());
        session.setAttribute("cart", cart);

        response.sendRedirect("list-product?addCart=true");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}