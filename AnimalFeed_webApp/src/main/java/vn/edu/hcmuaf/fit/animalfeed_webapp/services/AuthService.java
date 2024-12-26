package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.UserDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.util.Objects;

public class AuthService {

    private UserDao userDao = new UserDao();

    // Phương thức đăng nhập
    public User login(String username, String password) {
        User user = userDao.login(username, password);
        if (user != null) {
            return user;
        }
        return null;
    }

    // Phân quyền người dùng dựa trên vai trò
    public boolean hasPermission(User user, String requiredRole) {
        return Objects.equals(user.getRole(), requiredRole);
    }
}
