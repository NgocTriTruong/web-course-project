package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.DBConnect;

public class UserDao {
    // Method to fetch a user by their username
    public User getUserByName(String full_name) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM users WHERE full_name = ?";
        try (Connection connection = DBConnect.makeConnect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, full_name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getInt("status"),
                        resultSet.getDate("create_date"),
                        resultSet.getDate("update_date"),
                        resultSet.getInt("role")
                );
            }
        } 
        return null;
    }

    // Method to insert a new user
//    public boolean insertUser(User user) {
//        String sql = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, user.getUsername());
//            statement.setString(2, user.getPassword());
//            statement.setString(3, user.getEmail());
//            statement.setString(4, user.getRole());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace(); // Log the exception
//        }
//        return false;
//    }
//
//    // Method to update user information
//    public boolean updateUser(User user) {
//        String sql = "UPDATE users SET password = ?, email = ?, role = ? WHERE username = ?";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, user.getPassword());
//            statement.setString(2, user.getEmail());
//            statement.setString(3, user.getRole());
//            statement.setString(4, user.getUsername());
//            return statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace(); // Log the exception
//        }
//        return false;
//    }
//
//    // Method to delete a user by username
//    public boolean deleteUser(String username) {
//        String sql = "DELETE FROM users WHERE username = ?";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            statement.setString(1, username);
//            return statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace(); // Log the exception
//        }
//        return false;
//    }
//
//    // Method to list all users
//    public List<User> getAllUsers() {
//        List<User> userList = new ArrayList<>();
//        String sql = "SELECT * FROM users";
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                User user = new User(
//                        resultSet.getInt("id"),
//                        resultSet.getString("username"),
//                        resultSet.getString("password"),
//                        resultSet.getString("email"),
//                        resultSet.getString("role")
//                );
//                userList.add(user);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); // Log the exception
//        }
//        return userList;
//    }
//}
}
