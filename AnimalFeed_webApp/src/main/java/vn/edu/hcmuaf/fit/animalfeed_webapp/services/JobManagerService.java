package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.JobManagerDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.JobManager;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.List;
import java.util.Optional;

public class JobManagerService {
    private JobManagerDao jobManagerDao = new JobManagerDao();

    //add a new job
    public void createJob(JobManager job) {
        jobManagerDao.createJob(job);
    }

    //get all jobs
    public List<JobManager> getAllJobs() {
        return jobManagerDao.getAllJobs();
    }

    // get a job by id
    public JobManager getJobById(int id) {
        return jobManagerDao.getJobById(id);
    }

    // Sửa job
    public void updateJob(int productId, JobManager jobManager, int userId) {
        jobManagerDao.updateJob(productId, jobManager, userId);
    }

    // delete a job by id
    public void deleteJob(int id, int userId) {
        jobManagerDao.deleteJob(id, userId);
    }

    //search jobs by keyword
    public List<JobManager> searchJobs(String keyword) {
        return jobManagerDao.searchJobs(keyword);
    }
}
