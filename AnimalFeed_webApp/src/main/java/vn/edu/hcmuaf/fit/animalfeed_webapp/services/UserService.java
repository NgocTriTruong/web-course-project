package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import org.mindrot.jbcrypt.BCrypt;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.UserDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class UserService {

    private static final UserService instance = new UserService();
    private final UserDao userDao;
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    public UserService() {
        userDao = new UserDao();
    }

    public static UserService getInstance() {
        return instance;
    }

    // Đăng ký người dùng qua Google
    public void registerUserWithGoogle(User user) {
        // Validate required fields
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty for Google registration.");
        }
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            user.setFullName("Google User"); // Default name if not provided
        }

        // Set default values for Google login users
        user.setRole(0); // Mặc định là user thường
        user.setSub_role(0); // Mặc định sub_role = 0
        user.setStatus(1); // Mặc định trạng thái hoạt động
        user.setForce_logout(false); // Mặc định force_logout = false
        user.setCreateDate(new Date()); // Set current date
        user.setUpdateDate(new Date()); // Set current date

        try {
            userDao.insertUser(user);
            LOGGER.info("Successfully registered Google user: " + user.getEmail());
        } catch (Exception e) {
            LOGGER.severe("Failed to register Google user: " + user.getEmail() + ". Error: " + e.getMessage());
            throw new RuntimeException("Error registering Google user: " + e.getMessage(), e);
        }
    }

    // Đăng nhập bằng Google
    public User loginWithGoogle(String email) {
        Optional<User> optionalUser = userDao.getUserByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Không tìm thấy tài khoản với email " + email);
        }
        User user = optionalUser.get();

        // Kiểm tra trạng thái user
        if (user.getStatus() != 1) {
            throw new RuntimeException("Tài khoản đã bị vô hiệu hóa");
        }

        // Đặt lại force_logout khi đăng nhập thành công
        resetForceLogout(user.getId());
        return user;
    }

    // Đăng nhập bằng Facebook
    public User loginWithFacebook(String email) {
        Optional<User> optionalUser = userDao.getUserByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new RuntimeException("Không tìm thấy tài khoản với email " + email);
        }
        User user = optionalUser.get();
        // Đặt lại force_logout khi đăng nhập thành công
        resetForceLogout(user.getId());
        return user;
    }

    // Phương thức login
    public User login(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email và mật khẩu không được để trống.");
        }
        User user = userDao.getUserByEmailDirect(email);
        if (user == null) {
            throw new RuntimeException("Email không tồn tại.");
        }
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng.");
        }
        // Đặt lại force_logout khi đăng nhập thành công
        resetForceLogout(user.getId());
        return user;
    }

    // Kiểm tra email đã tồn tại
    public boolean isEmailExists(String email) {
        return email != null && userDao.getUserByEmail(email).isPresent();
    }

    // Kiểm tra mật khẩu xác nhận
    public boolean isPasswordMatch(String password, String confirmPassword) {
        return password != null && confirmPassword != null && password.equals(confirmPassword);
    }

    // Tạo và lưu người dùng mới
    public void registerUser(String fullName, String email, String password) {
        if (isEmailExists(email)) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }
        if (!isPasswordStrong(password)) {
            throw new IllegalArgumentException("Mật khẩu không đủ mạnh!");
        }
        User newUser = new User();
        newUser.setFullName(fullName);
        newUser.setEmail(email);
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        newUser.setPassword(hashedPassword);
        newUser.setRole(0); // Mặc định là user
        newUser.setSub_role(0); // Mặc định sub_role = 0
        newUser.setStatus(1); // Mặc định là active
        newUser.setCreateDate(new Date());
        newUser.setUpdateDate(new Date());
        newUser.setForce_logout(false);
        userDao.insertUser(newUser);
    }

    // Đăng ký người dùng qua Facebook
    public void registerUserWithFacebook(User user) {
        user.setForce_logout(false);
        userDao.insertUser(user);
    }

    // Lấy người dùng theo email
    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userDao.getUserByEmail(email);
        return optionalUser.orElse(null);
    }

    // Kiểm tra mật khẩu mạnh
    public boolean isPasswordStrong(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*");
    }

    // Thêm người dùng mới
    public void addUser(User user, int adminUserId) {
        if (user.getPassword() != null) {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }
        user.setForce_logout(false);
        userDao.addUser(user, adminUserId);
    }

    // Cập nhật thông tin người dùng
    public void updateUserByUser(User user) {
        userDao.updateUserByUser(user);
    }

    // Cập nhật thông tin người dùng (với quyền admin)
    public boolean updateUser(User user, int adminUserId) {
        try {
            User existingUser = userDao.getUserById(user.getId());
            if (existingUser == null) {
                return false;
            }
            userDao.updateUser(user, adminUserId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Xóa người dùng
    public boolean deleteUser(int userId, int adminUserId) {
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

    // Thay đổi mật khẩu
    public boolean updatePassword(int userId, String currentPassword, String newPassword) {
        if (!isPasswordStrong(newPassword)) {
            throw new IllegalArgumentException("Mật khẩu mới không đủ mạnh.");
        }
        User user = userDao.getUserById(userId);
        if (user == null) {
            return false;
        }
        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            return false;
        }
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return userDao.updatePassword1(userId, hashedNewPassword);
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userDao.loadUsers();
    }

    // Lấy người dùng theo ID
    public User getById(int userId) {
        return userDao.getUserById(userId);
    }

    // Cập nhật mật khẩu theo email
    public boolean updatePasswordByEmail(String email, String newPassword) {
        if (!isPasswordStrong(newPassword)) {
            throw new IllegalArgumentException("Mật khẩu không đủ mạnh.");
        }
        try {
            String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            userDao.updatePasswordByEmail(email, hashedNewPassword);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Tìm kiếm người dùng
    public List<User> searchUsers(String searchTerm) {
        return userDao.searchUsers(searchTerm);
    }

    // Lấy số lượng người dùng
    public int getTotalUser() {
        return userDao.getTotalUser();
    }

    // Kiểm tra người dùng tồn tại
    public boolean isUserExists(String contactInfo) {
        return contactInfo != null && userDao.getUserByPhoneOrEmail(contactInfo) != null;
    }

    // Kiểm tra email tồn tại
    public boolean isUserExistsEmail(String email) {
        return userDao.getUserByEmail(email).isPresent();
    }

    // Cập nhật mật khẩu theo email
    public boolean updatePassword(String email, String newPassword) {
        if (!isPasswordStrong(newPassword)) {
            throw new IllegalArgumentException("Mật khẩu không đủ mạnh.");
        }
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        userDao.updatePassword(email, hashedNewPassword);
        return true;
    }

    // Lấy người dùng theo ID
    public User getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    // Kiểm tra quyền
    public boolean hasPermission(int userId, String permission) {
        User user = getUserById(userId);
        System.out.println("Checking permission for userId: " + userId + ", permission: " + permission);
        if (user == null) {
            System.out.println("User is null");
            return false;
        }
        System.out.println("User Role: " + user.getRole() + ", Sub Role: " + user.getSub_role());
        if (user.getSub_role() == 0) {
            System.out.println("Super Admin has all permissions");
            return true;
        }
        switch (permission) {
            case "USER_MANAGEMENT":
                boolean hasUserManagement = user.getSub_role() == 1;
                System.out.println("Has USER_MANAGEMENT permission: " + hasUserManagement);
                return hasUserManagement;
            case "PRODUCT_MANAGEMENT":
                boolean hasProductManagement = user.getSub_role() == 2;
                System.out.println("Has PRODUCT_MANAGEMENT permission: " + hasProductManagement);
                return hasProductManagement;
            case "CATEGORY_MANAGEMENT":
                boolean hasCategoryManagement = user.getSub_role() == 3;
                System.out.println("Has CATEGORY_MANAGEMENT permission: " + hasCategoryManagement);
                return hasCategoryManagement;
            case "ORDER_MANAGEMENT":
                boolean hasOrderManagement = user.getSub_role() == 4;
                System.out.println("Has ORDER_MANAGEMENT permission: " + hasOrderManagement);
                return hasOrderManagement;
            case "NEWS_MANAGEMENT":
                boolean hasNewsManagement = user.getSub_role() == 5;
                System.out.println("Has NEWS_MANAGEMENT permission: " + hasNewsManagement);
                return hasNewsManagement;
            case "JOB_MANAGEMENT":
                boolean hasJobManagement = user.getSub_role() == 6;
                System.out.println("Has JOB_MANAGEMENT permission: " + hasJobManagement);
                return hasJobManagement;
            default:
                return false;
        }
    }

    // Đánh dấu người dùng cần đăng xuất
    public void markUserForLogout(int userId) {
        userDao.markUserForLogout(userId, true);
    }

    // Đặt lại force_logout
    private void resetForceLogout(int userId) {
        userDao.markUserForLogout(userId, false);
    }

    // Kiểm tra trạng thái force_logout
    public boolean isForceLogout(int userId) {
        return userDao.isForceLogout(userId);
    }

    // Hash mật khẩu hiện có
    public void hashExistingPasswords() {
        List<User> users = getAllUsers();
        for (User user : users) {
            String plainPassword = user.getPassword();
            if (!plainPassword.startsWith("$2a$")) {
                String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                userDao.updatePassword1(user.getId(), hashedPassword);
            }
        }
    }
    //kiểm tra xem người dùng có phải là admin hay không
    public boolean checkIfAdmin(int userId) {
        return userDao.checkIfAdmin(userId);
    }
}