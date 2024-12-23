package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.sql.SQLException;
import java.util.*;

public class UserDao {
    // Method to fetch a user by their username
    public ArrayList<User> getUserByName(String name) throws SQLException, ClassNotFoundException {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return (ArrayList<User>) jdbi.withHandle(handle -> handle.createQuery("select * from users like :namePattern").bind("pattern", "%" + name).mapToBean(User.class).list());
    }

    // Method to insert a new user
    public User getUserById(int id) throws SQLException, ClassNotFoundException {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle -> handle.createQuery("select * from users where id = :id").bind("id", id).mapToBean(User.class).findOne().orElse(null));
    }

    // Method to update user information
    public void updateUser(User user) {
        Jdbi jdbi = JdbiConnect.getJdbi();
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
        Jdbi jdbi = JdbiConnect.getJdbi();
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM users WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        System.out.println(rowsDeleted);
    }

    // Method to list all users
    public ArrayList<User> getAllUsers() {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return (ArrayList<User>) jdbi.withHandle(handle -> handle.createQuery("select * from users").mapToBean(User.class).list());
    }
}
