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
        return (ArrayList<User>) jdbi.withHandle(handle -> handle.createQuery("select * from users where full_Name like :namePattern")
                .bind("namePattern", "%" + name + "%")
                .mapToBean(User.class)
                .list());
    }

    //check sdt da ton tai hay chua
    public User getUserByPhone(String phone) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM users WHERE phone = :phone")
                .bind("phone", phone).mapToBean(User.class).findOne().orElse(null));

    }

    //Method register
    public void insertUser(User user) {

        jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO users (full_Name, password, phone, status, create_date, update_date, role) VALUES (:full_Name, :password, :phone, :status, :create_date, :update_date, :role)")
                        .bind("full_Name", user.getFullName())
                        .bind("password", user.getPassword())
                        .bind("phone", user.getPhone())
                        .bind("status", user.getStatus())
                        .bind("create_date", user.getCreateDate())
                        .bind("update_date", user.getUpdateDate())
                        .bind("role", user.getRole())
                        .execute()
        );
    }

    //load data user
    public List<User> loadUsers() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users")
                        .map((rs, ctx) -> {
                            int id = rs.getInt("id");
                            String fullName = rs.getString("full_name"); // Tên cột chính xác
                            String password = rs.getString("password");
                            String phone = rs.getString("phone");
                            int status = rs.getInt("status");
                            Date createDate = rs.getDate("create_date");
                            Date updateDate = rs.getDate("update_date");
                            int role = rs.getInt("role");
                            return new User(id, fullName, password, phone, status, createDate, updateDate, role);
                        })
                        .mapToBean(User.class)

                        .list()
        );
    }

    public User login(String phone, String password) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE phone = :phone AND password = :password")
                        .bind("phone", phone)    // Gắn giá trị biến 'phone'
                        .bind("password", password)  // Gắn giá trị biến 'password'
                        .mapToBean(User.class)  // Tự động ánh xạ kết quả vào lớp User
                        .findOne()  // Lấy kết quả đầu tiên, nếu có
                        .orElse(null) // Nếu không tìm thấy, trả về null
        );
    }

    //delete user
    public boolean deleteUser(int userId) {
        jdbi.withHandle(handle ->
                handle.createUpdate("DELETE FROM users WHERE id = :id")
                        .bind("id", userId)
                        .execute()
        );
        return false;
    }

    //get id user
    public User getIdUser(int userID) {
        return jdbi.withHandle(handle -> handle.createQuery("select * from users where id = :id")
                .bind("id", userID).mapToBean(User.class)
                .findOne().orElse(null));

    //Kiểm tra xem user có phải là admin hay không
    public static boolean checkIfAdmin(int userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT role FROM users WHERE id = :userId")
                        .bind("userId", userId)
                        .mapTo(Integer.class).findOne().
                        orElse(0) == 1    // role = 1 là admin
        );

    }

    //update user
    public boolean updateUser(User user) {
        jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE users SET full_Name = :fullName, password = :password, phone = :phone, role = :role, status = :status, update_date = :updateDate WHERE id = :id")
                        .bind("fullName", user.getFullName())
                        .bind("password", user.getPassword())
                        .bind("phone", user.getPhone())
                        .bind("role", user.getRole())
                        .bind("status", user.getStatus())
                        .bind("updateDate", user.getUpdateDate())
                        .bind("id", user.getId())
                        .execute()
        );
        return false;
    }


    public static void main (String[]args){
//            Scanner scanner = new Scanner(System.in);

//            System.out.println("Vui lòng nhập số điện thoại:");
//            String phone = scanner.nextLine();
//
//            System.out.println("Vui lòng nhập mật khẩu:");
//            String password = scanner.nextLine();
        UserDao dao = new UserDao();
        List<User> users = dao.loadUsers();
        System.out.print("Danh sách người dùng: " + users);
//


//            if (user != null) {
//                System.out.println("Đăng nhập thành công!");
//                System.out.println("Xin chào, " + user.getFullName());
//            } else {
//                System.out.println("Số điện thoại hoặc mật khẩu không đúng. Vui lòng thử lại!");
//            }
        }
    }

