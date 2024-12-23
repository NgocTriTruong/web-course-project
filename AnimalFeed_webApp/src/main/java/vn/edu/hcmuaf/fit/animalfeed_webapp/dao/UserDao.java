//package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;
//
//import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.DBConnect;
//import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.*;
//
//public class UserDao {
//    // Method to fetch a user by their username
//    public ArrayList<User> getUserByName(String full_name) throws SQLException, ClassNotFoundException {
//        Statement statement = DBConnect.get();
//        ResultSet re = null;
//        ArrayList<User> listUser = new ArrayList<>();
//
//        try {
//            re =  statement.executeQuery("SELECT * FROM users WHERE full_name = '" + full_name + "'");
//            while (re.next()) {
//                System.out.println(re.getInt("id")
//                        + "; " + re.getString("full_name")
//                        + "; " + re.getString("password")
//                        + "; " + re.getString("phone")
//                        + "; " + re.getInt("status")
//                        + "; " + re.getDate("create_date")
//                        + ";"  + re.getDate("update_date")
//                        + "; " + re.getInt("role"));
//            }
//            return listUser;
//
//        } catch (SQLException e) {
//            return listUser;
//        }
//    }
//
//    // Method to insert a new user
////    public ArrayList<User> getUserById(int id) throws SQLException, ClassNotFoundException {
////        Statement statement = DBConnect.get();
////        ResultSet re = null;
////        ArrayList<User> listUser = new ArrayList<>();
////
////        try {
////            re =  statement.executeQuery("SELECT * FROM users WHERE full_name = '" + full_name + "'");
////            while (re.next()) {
////                System.out.println(re.getInt("id")
////                        + "; " + re.getInt("cat_Id")
////                        + "; " + re.getString("name")
////                        + "; " + re.getDouble("price")
////                        + "; " + re.getString("description")
////                        + "; " + re.getInt("quantity")
////                        + ";"  + re.getInt("status")
////                        + "; " + re.getString("img")
////                        + "; " + re.getDate("create_date"));
////            }
////            return listUser;
////
////        } catch (SQLException e) {
////            return listUser;
////        }
////    }
////
////    // Method to update user information
////    public boolean updateUser(User user) {
////        String sql = "UPDATE users SET password = ?, email = ?, role = ? WHERE username = ?";
////        try (Connection connection = DatabaseConnection.getConnection();
////             PreparedStatement statement = connection.prepareStatement(sql)) {
////            statement.setString(1, user.getPassword());
////            statement.setString(2, user.getEmail());
////            statement.setString(3, user.getRole());
////            statement.setString(4, user.getUsername());
////            return statement.executeUpdate() > 0;
////        } catch (SQLException e) {
////            e.printStackTrace(); // Log the exception
////        }
////        return false;
////    }
////
////    // Method to delete a user by username
////    public boolean deleteUser(String username) {
////        String sql = "DELETE FROM users WHERE username = ?";
////        try (Connection connection = DatabaseConnection.getConnection();
////             PreparedStatement statement = connection.prepareStatement(sql)) {
////            statement.setString(1, username);
////            return statement.executeUpdate() > 0;
////        } catch (SQLException e) {
////            e.printStackTrace(); // Log the exception
////        }
////        return false;
////    }
////
////    // Method to list all users
////    public List<User> getAllUsers() {
////        List<User> userList = new ArrayList<>();
////        String sql = "SELECT * FROM users";
////        try (Connection connection = DatabaseConnection.getConnection();
////             PreparedStatement statement = connection.prepareStatement(sql)) {
////            ResultSet resultSet = statement.executeQuery();
////            while (resultSet.next()) {
////                User user = new User(
////                        resultSet.getInt("id"),
////                        resultSet.getString("username"),
////                        resultSet.getString("password"),
////                        resultSet.getString("email"),
////                        resultSet.getString("role")
////                );
////                userList.add(user);
////            }
////        } catch (SQLException e) {
////            e.printStackTrace(); // Log the exception
////        }
////        return userList;
////    }
////}
//}
