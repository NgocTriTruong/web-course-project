package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.sql.SQLException;
import java.util.*;

public class UserDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Method to fetch a user by their username
    public ArrayList<User> getUserByName(String name) throws SQLException, ClassNotFoundException {
        return (ArrayList<User>) jdbi.withHandle(handle -> handle.createQuery("select * from users like :namePattern").bind("pattern", "%" + name).mapToBean(User.class).list());
    }

    // Method to insert a new user
    public User getUserById(int id) throws SQLException, ClassNotFoundException {
        return jdbi.withHandle(handle -> handle.createQuery("select * from users where id = :id").bind("id", id).mapToBean(User.class).findOne().orElse(null));
    }

    public void insertUser(User user) {
        int rowsInserted = jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO users (fullName, password, phone, status, createDate, updateDate, role) VALUES (:fullName, :password, :phone, :status, :createDate, :updateDate, :role)")
                        .bind("fullName", user.getFullName())
                        .bind("password", user.getPassword())
                        .bind("phone", user.getPhone())
                        .bind("status", user.getStatus())
                        .bind("createDate", user.getCreateDate())
                        .bind("updateDate", user.getUpdateDate())
                        .bind("role", user.getRole())
                        .execute()
        );
        System.out.println(rowsInserted);
    }

    // Method to update user information
    public void updateUser(User user) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE users SET fullName = :fullName, password = :password, phone = :phone, status = :status, createDate = :createDate, updateDate = :updateDate, role = :role WHERE id = :id")
                        .bind("fullName", user.getFullName())
                        .bind("password", user.getPassword())
                        .bind("phone", user.getPhone())
                        .bind("status", user.getStatus())
                        .bind("createDate", user.getCreateDate())
                        .bind("updateDate", user.getUpdateDate())
                        .bind("role", user.getRole())
                        .bind("id", user.getId())
                        .execute()
        );
        System.out.println(rowsUpdated);
    }

    // Method to delete a user by username
    public void deleteUser(int id) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM users WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsDeleted);
    }

    // Method to list all users
    public ArrayList<User> getAllUsers() {
        return (ArrayList<User>) jdbi.withHandle(handle -> handle.createQuery("select * from users").mapToBean(User.class).list());
    }

    public User login(String phone, String password) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE phone = :phone AND password = :password")
                        .bind("phone", phone)
                        .bind("password", password)
                        .mapToBean(User.class)
                        .findOne() // Tìm user đầu tiên khớp (nếu có)
                        .orElse(null) // Trả về null nếu không tìm thấy
        );
    }
    // Kiểm tra dữ liệu
    public void checkDatabase(String phone, String password) {
        User user = login(phone, password);
        if (user != null) {
            System.out.println("Đăng nhập thành công!");
            System.out.println("Thông tin người dùng:");
            System.out.println("ID: " + user.getId());
            System.out.println("Full Name: " + user.getFullName());
            System.out.println("Phone: " + user.getPhone());
            System.out.println("Role: " + user.getRole());
            System.out.println("Status: " + user.getStatus());
        } else {
            System.out.println("Không tìm thấy người dùng hoặc mật khẩu không đúng.");
        }
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        userDao.checkDatabase("0989898989", "123456");
    }
}
