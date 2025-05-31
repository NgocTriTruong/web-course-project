package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_job;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.JobManager;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "JobManagemet", value = "/job-managemet-admin")
public class JobManagemet extends HttpServlet {
    private JobManagerService jobManagerService;

    @Override
    public void init() throws ServletException {
        jobManagerService = new JobManagerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            session = request.getSession(true);
            session.setAttribute("error", "Vui lòng đăng nhập để truy cập.");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String keyword = request.getParameter("keyword");
        List<JobManager> jobs;

        if (keyword != null && !keyword.trim().isEmpty()) {
            jobs = jobManagerService.searchJobs(keyword);
        } else {
            jobs = jobManagerService.getAllJobs();
        }
        request.setAttribute("jobs", jobs);

        request.getRequestDispatcher("views/admin/jobManagemet.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}