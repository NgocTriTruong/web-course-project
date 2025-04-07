package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.util.List;
import java.util.Optional;

public class UserDao {
    private static final Jdbi jdbi = JdbiConnect.getJdbi();

    // Lấy user theo id
    public User getUserById(int userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE id = :id")
                        .bind("id", userId)
                        .mapToBean(User.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Lấy user theo email
    public User getUserByEmailDirect(String email) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE email = :email")
                        .bind("email", email)
                        .mapToBean(User.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    // Load data tất cả người dùng
    public List<User> loadUsers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users")
                        .mapToBean(User.class)
                        .list()
        );
    }

    // Đăng nhập
    public User login(String email, String password) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE email = :email AND password = :password")
                        .bind("email", email)
                        .bind("password", password)
                        .mapToBean(User.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Kiểm tra xem user có phải là admin hay không
    public static boolean checkIfAdmin(int userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT role FROM users WHERE id = :userId")
                        .bind("userId", userId)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(0) == 1
        );
    }

    // Thêm người dùng mới
    public void addUser(User user, int adminUserId) {
        if (checkIfAdmin(adminUserId)) {
            jdbi.useTransaction(handle -> {
                int userId = handle.createUpdate("INSERT INTO users (full_name, email, phone, password, role, sub_role, status, create_date, update_date) " +
                                "VALUES (:fullName, :email, :phone, :password, :role, :sub_role, :status, :createDate, :updateDate)")
                        .bindBean(user)
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one();

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

    // Xóa người dùng (xóa mềm)
    public void deleteUser(int userId, int adminUserId) {
        if (checkIfAdmin(adminUserId)) {
            jdbi.useTransaction(handle -> {
                int updatedRows = handle.createUpdate("UPDATE users SET status = :status WHERE id = :userId")
                        .bind("status", "2")
                        .bind("userId", userId)
                        .execute();

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

    // Cập nhật thông tin người dùng
    public void updateUserByUser(User user) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE users SET full_name = :fullName, email = :email, phone = :phone WHERE id = :id")
                        .bindBean(user)
                        .execute()
        );
    }

    // Cập nhật người dùng
    public void updateUser(User user, int adminUserId) {
        if (!checkIfAdmin(adminUserId)) {
            throw new RuntimeException("User is not authorized to update.");
        }

        jdbi.useTransaction(handle -> {
            int updatedRows = handle.createUpdate("UPDATE users SET full_name = :fullName, email = :email, phone = :phone, role = :role, sub_role = :sub_role, status = :status, update_date = NOW() WHERE id = :id")
                    .bind("fullName", user.getFullName())
                    .bind("email", user.getEmail())
                    .bind("phone", user.getPhone())
                    .bind("status", user.getStatus())
                    .bind("role", user.getRole())
                    .bind("sub_role", user.getSub_role())
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

    // Chèn người dùng vào cơ sở dữ liệu
    public void insertUser(User user) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO users (full_name, email, phone, password, role, sub_role, status, create_date, update_date) " +
                                "VALUES (:fullName, :email, :phone, :password, :role, :sub_role, :status, :createDate, :updateDate)")
                        .bindBean(user)
                        .execute()
        );
    }

    // Tìm kiếm người dùng theo tên hoặc email
    public List<User> searchUsers(String searchTerm) {
        String searchQuery = "%" + searchTerm + "%";
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE (full_name LIKE :search OR email LIKE :search)")
                        .bind("search", searchQuery)
                        .mapToBean(User.class)
                        .list()
        );
    }

    // Thay đổi mật khẩu
    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        String checkPasswordQuery = "SELECT password FROM users WHERE id = ?";
        String updatePasswordQuery = "UPDATE users SET password = ? WHERE id = ?";

        String storedPassword = jdbi.withHandle(handle ->
                handle.createQuery(checkPasswordQuery)
                        .bind(0, userId)
                        .mapTo(String.class)
                        .findOnly()
        );

        if (storedPassword == null) {
            return false;
        }

        if (storedPassword.equals(currentPassword)) {
            int rowsUpdated = jdbi.withHandle(handle ->
                    handle.createUpdate(updatePasswordQuery)
                            .bind(0, newPassword)
                            .bind(1, userId)
                            .execute()
            );
            return rowsUpdated > 0;
        }
        return false;
    }

    // Cập nhật mật khẩu theo email
    public void updatePasswordByEmail(String email, String newPassword) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE users SET password = :password, update_date = NOW() WHERE email = :email")
                        .bind("password", newPassword)
                        .bind("email", email)
                        .execute()
        );
    }

    // Lấy số lượng người dùng
    public int getTotalUser() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM users")
                        .mapTo(Integer.class)
                        .findOnly()
        );
    }

    // Lấy user theo phone hoặc email
    public User getUserByPhoneOrEmail(String contactInfo) {
        String query = "SELECT * FROM users WHERE email = :contact OR phone = :contact";
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .bind("contact", contactInfo)
                        .mapToBean(User.class)
                        .findOne()
                        .orElse(null)
        );
    }

    // Lấy user theo email
    public Optional<User> getUserByEmail(String email) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE email = :email")
                        .bind("email", email)
                        .mapToBean(User.class)
                        .findOne()
        );
    }

    // Cập nhật mật khẩu theo email
    public void updatePassword(String email, String newPassword) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE users SET password = :password WHERE email = :email")
                        .bind("password", newPassword)
                        .bind("email", email)
                        .execute()
        );
    }

    // Thay đổi mật khẩu
    public boolean updatePassword1(int userId, String newPassword) {
        String updatePasswordQuery = "UPDATE users SET password = ? WHERE id = ?";
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate(updatePasswordQuery)
                        .bind(0, newPassword)
                        .bind(1, userId)
                        .execute()
        );
        return rowsUpdated > 0;
    }
}