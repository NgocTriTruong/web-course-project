package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.UserDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.io.IOException;
import java.util.Date;

@WebServlet(value = "/addUser")
public class AddUserControllerForAdmin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String fullname = request.getParameter("fullname");
            int role = Integer.parseInt(request.getParameter("role"));
            int status = Integer.parseInt(request.getParameter("status"));

            // Tạo đối tượng User
            User user = new User();
            user.setPhone(phone);
            user.setPassword(password);
            user.setFullName(fullname);
            user.setRole(role);
            user.setStatus(status);
            user.setCreateDate(new Date());
            user.setUpdateDate(new Date());

            // Gọi phương thức insertUser
            UserDao userDao = new UserDao();
            userDao.insertUser(user);

            // Điều hướng sau khi thêm thành công
            response.sendRedirect("userManagement?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("userManagement?error=true");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("views/admin/userAddition.jsp").forward(request, response);
    }
}