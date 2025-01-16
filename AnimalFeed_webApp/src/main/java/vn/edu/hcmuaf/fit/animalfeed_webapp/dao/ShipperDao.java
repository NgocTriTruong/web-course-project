package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Shipper;

import java.util.List;

public class ShipperDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    //Kiểm tra xem user có phải là admin hay không
    public static boolean checkIfAdmin(int userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT role FROM users WHERE id = :userId")
                        .bind("userId", userId)
                        .mapTo(Integer.class).findOne().
                        orElse(0) == 1    // role = 1 là admin
        );
    }

    public void addShipper(Shipper shipper, int adminUserId) {
        // Kiểm tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(adminUserId);

        if (isAdmin) {
            Jdbi jdbi = JdbiConnect.getJdbi();

            // Thực hiện thêm shipper và ghi log
            jdbi.useTransaction(handle -> {
                // Chèn shipper mới vào bảng shippers
                int shipperId = handle.createUpdate("INSERT INTO shippers (name, phone, salary, status) " +
                                "VALUES (:name, :phone, :salary, :status)") // Sửa lại câu lệnh này
                        .bind("name", shipper.getName())  // Sử dụng đúng các trường của đối tượng
                        .bind("phone", shipper.getPhone())
                        .bind("salary", shipper.getSalary())
                        .bind("status", shipper.getStatus())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one(); // Lấy id shipper vừa được tạo

                // Ghi log hành động vào bảng action_log
                handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) " +
                                "VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                        .bind("userId", adminUserId)
                        .bind("actionType", "CREATE")
                        .bind("entityType", "SHIPPER")
                        .bind("entityId", shipperId)
                        .bind("description", "Admin user " + adminUserId + " created shipper " + shipperId)
                        .execute();
            });
        }
    }


    public void deleteShipper(int shipperId, int adminUserId) {
        // Kiểm tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(adminUserId);

        if (isAdmin) {
            Jdbi jdbi = JdbiConnect.getJdbi();

            // Thực hiện xóa shipper và ghi log
            jdbi.useTransaction(handle -> {
                // Xóa shipper khỏi bảng shippers
                handle.createUpdate("DELETE FROM shippers WHERE id = :id")
                        .bind("id", shipperId)
                        .execute();

                // Ghi log hành động vào bảng action_log
                handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) " +
                                "VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                        .bind("userId", adminUserId)
                        .bind("actionType", "DELETE")
                        .bind("entityType", "SHIPPER")
                        .bind("entityId", shipperId)
                        .bind("description", "Admin user " + adminUserId + " deleted shipper " + shipperId)
                        .execute();
            });
        }
    }

    public void updateShipper(Shipper shipper, int adminUserId) {
        // Kiểm tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(adminUserId);

        if (isAdmin) {
            Jdbi jdbi = JdbiConnect.getJdbi();

            // Thực hiện cập nhật shipper và ghi log
            jdbi.useTransaction(handle -> {
                // Cập nhật thông tin shipper trong bảng shippers
                handle.createUpdate("UPDATE shippers SET name = :fullName, phone = :phone, salary = :salary, " +
                                "status = :status, update_date = :updateDate WHERE id = :id")
                        .bindBean(shipper)
                        .bind("id", shipper.getId())
                        .execute();

                // Ghi log hành động vào bảng action_log
                handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) " +
                                "VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                        .bind("userId", adminUserId)
                        .bind("actionType", "UPDATE")
                        .bind("entityType", "SHIPPER")
                        .bind("entityId", shipper.getId())
                        .bind("description", "Admin user " + adminUserId + " updated shipper " + shipper.getId())
                        .execute();
            });
        }
    }


    // Lấy tất cả shipper
    public List<Shipper> getAllShippers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM shippers")
                        .mapToBean(Shipper.class)
                        .list()
        );
    }

    public Shipper getShipperById(int shipperId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM shippers WHERE id = :id")
                        .bind("id", shipperId)
                        .mapTo(Shipper.class) // Map kết quả truy vấn thành đối tượng Shipper
                        .findOnly() // Lấy kết quả đầu tiên, nếu không có sẽ ném lỗi
        );
    }
}
