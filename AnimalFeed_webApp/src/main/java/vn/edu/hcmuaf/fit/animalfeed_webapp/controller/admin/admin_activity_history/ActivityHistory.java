package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_activity_history;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ActionLogService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ActivityHistory", value = "/activity-history")
public class ActivityHistory extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActionLogService actionLogService = new ActionLogService();
        List<ActionLog> actionLogs = actionLogService.getAllActionLogs();
        request.setAttribute("actionLogs", actionLogs);
        request.getRequestDispatcher("views/admin/activity_history.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}