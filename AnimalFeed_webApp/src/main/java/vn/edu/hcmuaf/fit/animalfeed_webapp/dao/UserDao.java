package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    //lay user theo id
    public User getUserById(int userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE id = :id")
                        .bind("id", userId)
                        .mapToBean(User.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Lấy người dùng theo số điện thoại
    public User getUserByPhone(String phone) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE phone = :phone")
                        .bind("phone", phone)
                        .mapToBean(User.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    //load data user
    public List<User> loadUsers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users")
                        .mapToBean(User.class)
                        .list()
        );
    }

    // Login method
    public User login(String phone, String password) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE phone = :phone AND password = :password")
                        .bind("phone", phone)    // Gắn giá trị biến 'phone'
                        .bind("password", password)  // Gắn giá trị biến 'password'
                        .mapToBean(User.class)  // Tự động ánh xạ kết quả vào lớp User
                        .findOne()  // Lấy kết quả đầu tiên, nếu có
                        .orElse(null) // Nếu không tìm thấy, trả về null
        );
    }

    //Kiểm tra xem user có phải là admin hay không
    public static boolean checkIfAdmin(int userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT role FROM users WHERE id = :userId")
                        .bind("userId", userId)
                        .mapTo(Integer.class).findOne().
                        orElse(0) == 1    // role = 1 là admin
        );
    }

    //them user
    public void addUser(User user, int adminUserId) {
        // Kiểm tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(adminUserId);

        if (isAdmin) {
            Jdbi jdbi = JdbiConnect.getJdbi();

            // Thực hiện thêm người dùng và ghi log
            jdbi.useTransaction(handle -> {
                // Thực hiện chèn người dùng mới vào bảng users
                int userId = handle.createUpdate("INSERT INTO users (full_name, phone, password, role, status, create_date, update_date) " +
                                "VALUES (:fullName, :phone, :password, :role, :status, :createDate, :updateDate)")
                        .bindBean(user)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one(); // Lấy id người dùng vừa được tạo

                // Ghi log hành động vào bảng action_log
                handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) " +
                                "VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                        .bind("userId", adminUserId)
                        .bind("actionType", "CREATE")
                        .bind("entityType", "USER")
                        .bind("entityId", userId)
                        .bind("description", "Admin user " + adminUserId + " created user " + userId)
                        .execute();
            });
        }
    }

    public void deleteUser(int userId, int adminUserId) {
        // Kiểm tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(adminUserId);

        if (isAdmin) {
            Jdbi jdbi = JdbiConnect.getJdbi();
            // Thực hiện xóa mềm người dùng và ghi log
            jdbi.useTransaction(handle -> {
                // Cập nhật trạng thái người dùng thành 'deleted' (status = 0)
                int updatedRows = handle.createUpdate("UPDATE users SET status = :status WHERE id = :userId")
                        .bind("status", "2") // Trạng thái 'deleted'
                        .bind("userId", userId)
                        .execute();

                // Ghi log hành động vào bảng action_log
                if (updatedRows > 0) {
                    handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) " +
                                    "VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                            .bind("userId", adminUserId)
                            .bind("actionType", "DELETE")
                            .bind("entityType", "USER")
                            .bind("entityId", userId)
                            .bind("description", "Admin user " + adminUserId + " deleted user " + userId)
                            .execute();
                } else {
                    throw new RuntimeException("Failed to delete user with ID: " + userId);
                }
            });
        }
    }

    public void updateUser(User user, int adminUserId) {
        // Kiểm tra quyền admin
        boolean isAdmin = UserDao.checkIfAdmin(adminUserId);
        System.out.println("Admin check result for user " + adminUserId + ": " + isAdmin);  // In ra kết quả kiểm tra

        if (!isAdmin) {
            throw new RuntimeException("User is not authorized to update.");
        }

        // Nếu đã là admin, tiếp tục thực hiện cập nhật
        Jdbi jdbi = JdbiConnect.getJdbi();
        jdbi.useTransaction(handle -> {
            int updatedRows = handle.createUpdate("UPDATE users SET full_name = :fullName, phone = :phone, role = :role, status = :status, update_date = NOW() WHERE id = :id")
                    .bind("fullName", user.getFullName())
                    .bind("phone", user.getPhone())
                    .bind("status", user.getStatus())
                    .bind("role", user.getRole())
                    .bind("id", user.getId())
                    .execute();

            if (updatedRows > 0) {
                handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) " +
                                "VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                        .bind("userId", adminUserId)
                        .bind("actionType", "UPDATE")
                        .bind("entityType", "USER")
                        .bind("entityId", user.getId())
                        .bind("description", "Admin user " + adminUserId + " updated user " + user.getId())
                        .execute();
            } else {
                throw new RuntimeException("Failed to update user with ID: " + user.getId());
            }
        });
    }

    // Chèn người dùng mới vào cơ sở dữ liệu
    public void insertUser(User user) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO users (full_name, phone, password, role, status, create_date, update_date) " +
                                "VALUES (:fullName, :phone, :password, :role, :status, :createDate, :updateDate)")
                        .bindBean(user)
                        .execute()
        );
    }

    //tim kiem user
    public List<User> searchUsers(String searchTerm) {
        String searchQuery = "%" + searchTerm + "%";
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE (full_name LIKE :search OR phone LIKE :search) AND status != -1")
                        .bind("search", searchQuery)
                        .mapToBean(User.class)
                        .list()
        );
    }
}