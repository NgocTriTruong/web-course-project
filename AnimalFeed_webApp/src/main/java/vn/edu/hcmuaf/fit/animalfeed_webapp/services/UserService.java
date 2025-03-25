package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.UserDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserService {

    private UserDao userDao;

    public UserService() {
        userDao = new UserDao(); // Khởi tạo UserDao khi service được tạo
    }

    // Phương thức login (sử dụng email thay vì phone)
    public User login(String email, String password) {
        User user = userDao.login(email, password); // Giả sử UserDao đã được cập nhật để dùng email
        if (user == null) {
            throw new RuntimeException("Email hoặc mật khẩu không đúng.");
        }
        return user;
    }

    // Đăng nhập bằng Google (sử dụng email - giữ nguyên)
    public User loginWithGoogle(String email) {
        Optional<User> optionalUser = userDao.getUserByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Không tìm thấy tài khoản với email " + email);
        }
        return optionalUser.get();
    }

    // Kiểm tra email đã tồn tại (thay cho kiểm tra số điện thoại)
    public boolean isEmailExists(String email) {
        return userDao.getUserByEmail(email).isPresent();
    }

    // Kiểm tra mật khẩu xác nhận (giữ nguyên)
    public boolean isPasswordMatch(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    // Tạo và lưu người dùng mới (sử dụng email thay vì phone)
    public void registerUser(String fullName, String email, String password) {
        if (isEmailExists(email)) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email); // Thay setPhone bằng setEmail
        newUser.setPassword(password);
        newUser.setRole(0); // Mặc định là user (role = 0)
        newUser.setStatus(1); // Mặc định là active (status = 1)
        newUser.setCreateDate(new Date());
        newUser.setUpdateDate(new Date());
        userDao.insertUser(newUser);
    }

    // Đăng ký người dùng qua Google (giữ nguyên)
    public void registerUserWithGoogle(User user) {
        userDao.insertUser(user);
    }

    // Lấy người dùng theo email (thay cho phone)
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userDao.getUserByEmail(email);
        return optionalUser.orElse(null);
    }

    // Kiểm tra mật khẩu mạnh (giữ nguyên)
    public boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

    // Thêm người dùng mới (với quyền admin - giữ nguyên)
    public void addUser(User user, int adminUserId) {
        userDao.addUser(user, adminUserId);
    }

    // Cập nhật thông tin người dùng (giữ nguyên)
    public void updateUserByUser(User user) {
        userDao.updateUserByUser(user);
    }

    // Cập nhật thông tin người dùng (với quyền admin - giữ nguyên)
    public boolean updateUser(User user, int adminUserId) {
        User adminUser = userDao.getUserById(adminUserId);
        if (adminUser == null || adminUser.getRole() != 1) {
            return false;
        }
        try {
            userDao.updateUser(user, adminUserId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa người dùng (với quyền admin - giữ nguyên)
    public boolean deleteUser(int userId, int adminUserId) {
        User adminUser = userDao.getUserById(adminUserId);
        if (adminUser == null || adminUser.getRole() != 1) {
            return false;
        }
        try {
            User userToDelete = userDao.getUserById(userId);
            if (userToDelete == null) {
                throw new RuntimeException("Không tìm thấy người dùng cần xóa.");
            }
            if (userId == adminUserId) {
                throw new RuntimeException("Không thể xóa tài khoản của chính mình.");
            }
            userDao.deleteUser(userId, adminUserId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa người dùng: " + e.getMessage());
        }
    }

    // Thay đổi mật khẩu (giữ nguyên)
    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        return userDao.updatePassword(userId, currentPassword, newPassword);
    }

    // Lấy tất cả người dùng (giữ nguyên)
    public List<User> getAllUsers() {
        return userDao.loadUsers();
    }

    // Lấy người dùng theo ID (giữ nguyên)
    public User getById(int userId) {
        return userDao.getUserById(userId);
    }

    // Cập nhật mật khẩu theo email (thay cho phone)
    public boolean updatePasswordByEmail(String email, String newPassword) {
        if (!isPasswordStrong(newPassword)) {
            throw new IllegalArgumentException("Mật khẩu không đủ mạnh.");
        }
        try {
            userDao.updatePasswordByEmail(email, newPassword); // Giả sử UserDao có phương thức này
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Tìm kiếm người dùng (giữ nguyên)
    public List<User> searchUsers(String searchTerm) {
        return userDao.searchUsers(searchTerm);
    }

    // Lấy số lượng người dùng (giữ nguyên)
    public int getTotalUser() {
        return userDao.getTotalUser();
    }

    // Kiểm tra người dùng tồn tại theo email hoặc phone (cập nhật để ưu tiên email)
    public boolean isUserExists(String contactInfo) {
        User user = userDao.getUserByPhoneOrEmail(contactInfo);
        return user != null;
    }

    // Kiểm tra email tồn tại (giữ nguyên)
    public boolean isUserExistsEmail(String email) {
        return userDao.getUserByEmail(email).isPresent();
    }

    // Cập nhật mật khẩu theo email (giữ nguyên)
    public boolean updatePassword(String email, String newPassword) {
        userDao.updatePassword(email, newPassword);
        return true;
    }

    // Lấy người dùng theo ID (giữ nguyên)
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }
}