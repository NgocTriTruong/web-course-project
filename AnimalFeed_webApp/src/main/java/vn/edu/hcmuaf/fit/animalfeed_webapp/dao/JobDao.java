package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.JobManager;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class JobDao {
    private final Jdbi jdbi = JdbiConnect.getJdbi(); // Kết nối với Jdbi thông qua lớp JdbiConnect

    // Phương thức lấy tất cả công việc
    public List<JobManager> getAllJobs() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM job_managers")
                        .mapToBean(JobManager.class) // Chuyển kết quả thành đối tượng JobManager
                        .list()
        );
    }

    // Phương thức lấy công việc theo địa điểm
    public List<JobManager> getJobsByLocation(String location) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM job_managers WHERE location = :location")
                        .bind("location", location) // Gắn giá trị cho tham số location
                        .mapToBean(JobManager.class) // Chuyển kết quả thành đối tượng JobManager
                        .list()
        );
    }

    // Phương thức main để kiểm tra
    public static void main(String[] args) {
        JobDao jobDao = new JobDao();

        // Lấy tất cả các công việc
        List<JobManager> allJobs = jobDao.getAllJobs();
        System.out.println("Tất cả các công việc:");
        for (JobManager job : allJobs) {
            System.out.println(job); // In chi tiết công việc
        }

        // Lọc công việc theo địa điểm (ví dụ: Tỉnh Tây Ninh)
        List<JobManager> jobsByLocation = jobDao.getJobsByLocation("Hà Nội");
        System.out.println("\nCác công việc tại Hà Nội:");
        for (JobManager job : jobsByLocation) {
            System.out.println(job); // In chi tiết công việc
        }
    }
}
