package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.sql.ResultSet;
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
                            String fullName = rs.getString("full_name"); // Tên cột: full_name
                            String password = rs.getString("password");  // Tên cột: password
                            String phone = rs.getString("phone");        // Tên cột: phone
                            int status = rs.getInt("status");            // Tên cột: status
                            Date createDate = rs.getDate("create_date"); // Tên cột: create_date
                            Date updateDate = rs.getDate("update_date"); // Tên cột: update_date
                            int role = rs.getInt("role");                // Tên cột: role
                            return new User(id, fullName, password, phone, status, createDate, updateDate, role);
                        })
                        .list()
        );
    }

    //login
    public User login(String phone, String password) {
        Jdbi jdbi = JdbiConnect.getJdbi();
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM users WHERE phone = :phone AND password = :password")
                        .bind("phone", phone)
                        .bind("password", password)
                        .map((rs, ctx) -> {
                            int id = rs.getInt("id");
                            String fullName = rs.getString("full_name");
                            int status = rs.getInt("status");
                            Date createDate = rs.getDate("create_date");
                            Date updateDate = rs.getDate("update_date");
                            int role = rs.getInt("role");
                            return new User(id, fullName, password, phone, status, createDate, updateDate, role);
                        })
                        .findOne()
                        .orElse(null) // Nếu không tìm thấy, trả về null
        );
    }



    public static void main (String[]args){
            Scanner scanner = new Scanner(System.in);

            System.out.println("Vui lòng nhập số điện thoại:");
            String phone = scanner.nextLine();

            System.out.println("Vui lòng nhập mật khẩu:");
            String password = scanner.nextLine();

            UserDao myClass = new UserDao(); // Lớp chứa hàm login()
            User user = myClass.login(phone, password);

            if (user != null) {
                System.out.println("Đăng nhập thành công!");
                System.out.println("Xin chào, " + user.getFullName());
            } else {
                System.out.println("Số điện thoại hoặc mật khẩu không đúng. Vui lòng thử lại!");
            }
        }
    }

