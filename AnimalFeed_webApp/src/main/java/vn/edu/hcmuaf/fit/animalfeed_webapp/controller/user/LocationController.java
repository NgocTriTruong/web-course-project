package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.user;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LocationController", value = "/location_user")
public class LocationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = new CategoryService();

        List<Category> categories = categoryService.getAll();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        request.setAttribute("user", user);

        request.setAttribute("categories", categories);
        request.getRequestDispatcher("views/web/chi_tiet_ca_nhan/so_dia_chi.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}