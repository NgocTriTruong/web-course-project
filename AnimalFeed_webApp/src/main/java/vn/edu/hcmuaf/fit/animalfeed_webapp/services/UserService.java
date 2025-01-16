package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.UserDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.util.Date;
import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService() {
        userDao = new UserDao(); // Khởi tạo UserDao khi service được tạo
    }

    // Phương thức login
    public User login(String phone, String password) {
        User user = userDao.login(phone, password);
        if (user == null) {
            throw new RuntimeException("Số điện thoại hoặc mật khẩu không đúng.");
        }
        return user;
    }

    // Kiểm tra số điện thoại đã tồn tại
    public boolean isPhoneExists(String phone) {
        return userDao.getUserByPhone(phone) != null;
    }

    // Kiểm tra mật khẩu xác nhận
    public boolean isPasswordMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    // Tạo và lưu người dùng mới
    public void registerUser(String fullName, String phone, String password) {
        if (isPhoneExists(phone)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại!");
        }
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setPhone(phone);
        newUser.setPassword(password);
        newUser.setRole(0); // Mặc định là user (role = 0)
        newUser.setStatus(1); // Mặc định là active (status = 1)
        newUser.setCreateDate(new Date());
        newUser.setUpdateDate(new Date());
        userDao.insertUser(newUser);
    }

    // Lấy người dùng theo số điện thoại
    public User getUserByPhone(String phone) {
        return userDao.getUserByPhone(phone);
    }

    // Kiểm tra mật khẩu mạnh
    public boolean isPasswordStrong(String password) {
        // Kiểm tra mật khẩu có ít nhất 8 ký tự, có chữ hoa, chữ thường, và số
        return password != null && password.length() >= 8 &&
                password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

    // Thêm người dùng mới (với quyền admin)
    public void addUser(User user, int adminUserId) {
        userDao.addUser(user, adminUserId); // Thêm người dùng và ghi log nếu admin
    }

    // Cập nhật thông tin người dùng (với quyền admin)
    public boolean updateUser(User user, int adminUserId) {
        // Kiểm tra quyền của admin
        User adminUser = userDao.getUserById(adminUserId);
        if (adminUser == null || adminUser.getRole() != 1) {
            return false; // Nếu không phải admin, trả về false
        }

        // Cập nhật người dùng
        try {
            userDao.updateUser(user, adminUserId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa người dùng (với quyền admin)
    public boolean deleteUser(int userId, int adminUserId) {
        // Kiểm tra quyền của admin
        User adminUser = userDao.getUserById(adminUserId);
        if (adminUser == null || adminUser.getRole() != 1) {
            return false; // Nếu không phải admin, trả về false
        }
        try {
            // Kiểm tra user tồn tại
            User userToDelete = userDao.getUserById(userId);
            if (userToDelete == null) {
                throw new RuntimeException("Không tìm thấy người dùng cần xóa.");
            }

            // Kiểm tra không thể tự xóa chính mình
            if (userId == adminUserId) {
                throw new RuntimeException("Không thể xóa tài khoản của chính mình.");
            }

            userDao.deleteUser(userId, adminUserId);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa người dùng: " + e.getMessage());
        }
        return true;
    }

    //thay đổi pass
    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        return userDao.updatePassword(userId, currentPassword, newPassword);
    }

    public List<User> getAllUsers() {
        return userDao.loadUsers();
    }
    public User getById(int userId) {
        return userDao.getUserById(userId);
    }

//    public List<User> getUsersWithPagination(int page, int pageSize){
//        return userDao.getUsersWithPagination(page, pageSize);
//    }
}
