package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_job;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.JobManager;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.JobManagerService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;

@WebServlet(name = "JobAddtion", value = "/job-addtion-admin")
public class JobAddtion extends HttpServlet {
    private JobManagerService jobManagerService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        jobManagerService = new JobManagerService();
        userService = UserService.getInstance(); // Khởi tạo UserService
    }

    // Kiểm tra quyền JOB_MANAGEMENT
    private boolean hasJobManagementPermission(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return false;
        }
        return userService.hasPermission(userId, "JOB_MANAGEMENT");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (!hasJobManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền truy cập trang này.");
            String currentPage = request.getRequestURI();
            response.sendRedirect(currentPage);
            return;
        }
            request.getRequestDispatcher("views/admin/jobAddtion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        if (!hasJobManagementPermission(session)) {
            session.setAttribute("error", "Bạn không có quyền thực hiện thao tác này.");
            String currentPage = request.getRequestURI();
            response.sendRedirect(currentPage);
            return;
        }
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