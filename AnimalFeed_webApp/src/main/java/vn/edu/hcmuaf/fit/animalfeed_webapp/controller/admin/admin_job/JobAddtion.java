package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_job;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.JobManager;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.JobManagerService;

import java.io.IOException;

@WebServlet(name = "JobAddtion", value = "/job-addtion-admin")
public class JobAddtion extends HttpServlet {
    private JobManagerService jobManagerService;

    @Override
    public void init() throws ServletException {
        jobManagerService = new JobManagerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/admin/jobAddtion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String position = request.getParameter("position");
        String location = request.getParameter("location");
        String phone = request.getParameter("phone");

        try {
            JobManager job = new JobManager();
            job.setJob_position(position);
            job.setLocation(location);
            job.setPhone(phone);
            jobManagerService.createJob(job);
            request.getSession().setAttribute("message", "Thêm công việc thành công!");
            response.sendRedirect(request.getContextPath() + "/job-managemet-admin");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi thêm công việc: " + e.getMessage());
            request.getRequestDispatcher("views/admin/jobAddtion.jsp").forward(request, response);
        }
    }

}