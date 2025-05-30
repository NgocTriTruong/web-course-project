package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_job;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.JobManager;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.JobManagerService;

import java.io.IOException;

@WebServlet(name = "EditJob", value = "/edit-job-admin")
public class EditJob extends HttpServlet {
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
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String jobIdStr = request.getParameter("jobId");
        try {
            if (jobIdStr == null || jobIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("ID công việc không hợp lệ.");
            }

            int jobId = Integer.parseInt(jobIdStr);
            JobManager job = jobManagerService.getJobById(jobId);
            if (job == null) {
                session.setAttribute("error", "Không tìm thấy công việc với ID: " + jobId);
                response.sendRedirect(request.getContextPath() + "/job-management-admin");
                return;
            }

            request.setAttribute("job", job);
            request.getRequestDispatcher("/views/admin/jobAddtion.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            session.setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/job-management-admin");
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi khi tải thông tin công việc: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/job-management-admin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        String jobIdStr = request.getParameter("jobId");
        String position = request.getParameter("position");
        String location = request.getParameter("location");
        String phone = request.getParameter("phone");

        try {
            int jobId = Integer.parseInt(jobIdStr);
            if (position == null || position.trim().isEmpty() ||
                    location == null || location.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty()) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin.");
            }
            JobManager job = new JobManager();
            job.setId(jobId);
            job.setJob_position(position);
            job.setLocation(location);
            job.setPhone(phone);
            jobManagerService.updateJob(jobId, job, userId);
            session.setAttribute("message", "Cập nhật công việc thành công!");
            response.sendRedirect(request.getContextPath() + "/job-managemet-admin");
        } catch (NumberFormatException e) {
            session.setAttribute("error", "ID công việc không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/job-managemet-admin");
        } catch (Exception e) {
            session.setAttribute("error", "Lỗi khi cập nhật công việc: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/job-managemet-admin");
        }
    }

}