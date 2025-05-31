package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_job;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.CategoryService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.JobManagerService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;

@WebServlet(name = "DeleteJob", value = "/delete-job-admin")
public class DeleteJob extends HttpServlet {
    private JobManagerService jobManagerService;

    @Override
    public void init() throws ServletException {
        jobManagerService = new JobManagerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        String jobIdStr = request.getParameter("jobId");

        try {
            if (jobIdStr == null || jobIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("ID công việc không hợp lệ.");
            }

            int jobId = Integer.parseInt(jobIdStr);
            jobManagerService.deleteJob(jobId, userId);
            session.setAttribute("message", "Xóa công việc thành công!");
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
        } catch (Exception e) {
            session.setAttribute("error", "Có lỗi xảy ra khi xóa công việc: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/job-managemet-admin");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}