package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.policy;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Category;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PurchasingPolicyController", value = "/purchasing-policy")
public class PurchasingPolicyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        CategoryService categoryService = new CategoryService();

        List<Category> categories = categoryService.getAll();
        request.setAttribute("categoriesData", categories);

        request.getRequestDispatcher("views/web/purchasing_policy.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }

}