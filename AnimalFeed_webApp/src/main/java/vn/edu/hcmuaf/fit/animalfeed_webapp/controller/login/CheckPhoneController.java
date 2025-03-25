package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.login;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;

@WebServlet(name = "CheckPhoneController ", value = "/check-phone")
public class CheckPhoneController extends HttpServlet {

    private UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String phone = request.getParameter("phone");
        boolean exists = userService.isEmailExists(phone);

        response.getWriter().write(exists ? "exists" : "not_found");
    }
}