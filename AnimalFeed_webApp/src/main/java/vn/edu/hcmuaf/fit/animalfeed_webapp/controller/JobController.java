package vn.edu.hcmuaf.fit.animalfeed_webapp.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.JobDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.JobManager;

import java.io.IOException;
import java.util.List;

@WebServlet("/tuyendung")
public class JobController extends HttpServlet {

    private JobDao jobDao;

    @Override
    public void init() throws ServletException {
        Jdbi jdbi = (Jdbi) getServletContext().getAttribute("jdbi");
        jobDao = new JobDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String location = req.getParameter("location");
        List<JobManager> jobList;

        if (location != null && !location.isEmpty()) {
            jobList = jobDao.getJobsByLocation(location);
        } else {
            jobList = jobDao.getAllJobs();
        }

        req.setAttribute("jobList", jobList);
        req.getRequestDispatcher("/views/web/recruitment.jsp").forward(req, resp);
    }
}
