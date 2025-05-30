package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.JobManager;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;

import java.util.List;
import java.util.Optional;

public class JobManagerDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();
    private ActionLogDao actionLogDao = new ActionLogDao();

    // Create a new job
    public void createJob(JobManager job) {
        jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO job_managers (location, phone, job_position, status) VALUES (:location, :phone, :job_position, :status)")
                        .bindBean(job)
                        .bind("status", 1)
                        .execute()
        );
    }

    // Retrieve all jobs
    public List<JobManager> getAllJobs() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM job_managers")
                        .mapToBean(JobManager.class)
                        .list()
        );
    }

    // Retrieve a job by ID
    public JobManager getJobById(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM job_managers WHERE id = :id")
                        .bind("id", id)
                        .mapToBean(JobManager.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Update a job
    public void updateJob(int id, JobManager jobManager, int userId) {
        JobManager jobOld = getJobByIdOfAdmin(id);
        // Check if the user is an admin
        boolean isAdmin = UserDao.checkIfAdmin(userId);

        if (isAdmin){
            Jdbi jdbi = JdbiConnect.getJdbi();
            jdbi.useTransaction(handle -> {
                // Cập nhật thông tin sản phẩm và ghi log
                int updatedRows = handle.createUpdate("UPDATE job_managers SET location = :location, phone = :phone, job_position = :job_position WHERE id = :id")
                        .bind("location", jobManager.getLocation())
                        .bind("phone", jobManager.getPhone())
                        .bind("job_position", jobManager.getJob_position())
                        .bind("id", id)
                        .execute();

                // Ghi log hành động vào bảng action_log
                if (updatedRows > 0) {
                    // Ghi log hành động vào bảng action_log
                    ActionLog actionLog = new ActionLog(userId, "UPDATE", "JOB", id, "User " + userId + " updated job " + id, jobOld.toString(), jobManager.toString());
                    actionLogDao.logAction(actionLog);
                } else {
                    throw new RuntimeException("Failed to update job with ID: " + jobManager.getId());
                }
            });
        }
    }

    // Delete a job by ID
    public void deleteJob(int id, int userId) {
        JobManager deleJobManager = getJobByIdOfAdmin(id);
        //kiem tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(userId);
        if (isAdmin) {
            // Thực hiện xóa mềm job và ghi log
            jdbi.useTransaction(handle -> {

                // Cập nhật trạng thái sản phẩm thành 'deleted' ( deleted = 0)
                int updatedRows = handle.createUpdate("UPDATE job_managers SET status = :status WHERE id = :id")
                        .bind("status", "0") // Trạng thái 'deleted'
                        .bind("id", id).execute();

                // Ghi log hành động vào bảng action_log
                if (updatedRows > 0) {
                    // Ghi log hành động vào bảng action_log
                    ActionLog actionLog = new ActionLog(userId, "DELETE", "JOB", id, "User " + userId + " deleted job " + id, deleJobManager.toString(), null);
                    actionLogDao.logAction(actionLog);
                }else {
                    throw new RuntimeException("Failed to delete job with ID: " + id);
                }
            });
        }
    }

    public JobManager getJobByIdOfAdmin(int id) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from job_managers where id = :id")
                        .bind("id", id)
                        .mapToBean(JobManager.class).findOne().orElse(null));
    }

    // Search jobs by job_position or location
    public List<JobManager> searchJobs(String keyword) {
        String searchTerm = "%" + keyword + "%";
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM job_managers WHERE job_position LIKE :keyword OR location LIKE :keyword")
                        .bind("keyword", searchTerm)
                        .mapToBean(JobManager.class)
                        .list()
        );
    }

    public static void main(String[] args) {
        JobManagerDao jobManagerDao = new JobManagerDao();
        //sửa job
        JobManager jobManager = new JobManager();
        jobManager.setJob_position("Quản lý kho");
        jobManagerDao.updateJob(21, jobManager, 28);
    }

}
