package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.partner;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "PartnerController", value = "/partner")
public class PartnerController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException, ServletException {
        request.getRequestDispatcher("views/web/partner.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {

    }

}