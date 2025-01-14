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
import java.util.List;

@WebServlet(value = "/userManagement")
public class UserManagerForAdmin extends HttpServlet {
    private UserDao userDao;

    @Override
    public void init() throws ServletException {
        // Khởi tạo userDao trong phương thức init()
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            handleDelete(request, response);
        } else if ("edit".equals(action)) {
            handleEdit(request, response);
        } else {
            // Default: display user list
            List<User> users = userDao.loadUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/views/admin/userManagement.jsp").forward(request, response);
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            if (userDao.deleteUser(userId)) {
                request.getSession().setAttribute("message", "Xóa người dùng thành công");
            } else {
                request.getSession().setAttribute("error", "Không thể xóa người dùng");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi xóa người dùng: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/userManagement");
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userDao.getIdUser(userId);
            if (user != null) {
                // Set user data to request for the form
                request.setAttribute("user", user);
                request.getRequestDispatcher("/views/admin/addUser.jsp").forward(request, response);
                return;
            }
            request.getSession().setAttribute("error", "Không tìm thấy người dùng");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi tải thông tin người dùng: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/userManagement");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request, response);
        } else if ("update".equals(action)) {
            handleUpdate(request, response);
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user = new User(
                    0,
                    request.getParameter("fullName"),
                    request.getParameter("phone"),
                    request.getParameter("password"),
                    Integer.parseInt(request.getParameter("status")),
                    new Date(),
                    new Date(),
                    Integer.parseInt(request.getParameter("role"))
            );

            if (userDao.insertUser(user)) {
                request.getSession().setAttribute("message", "Thêm người dùng thành công");
            } else {
                request.getSession().setAttribute("error", "Không thể thêm người dùng");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi thêm người dùng: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/userManagement");
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user = new User(
                    Integer.parseInt(request.getParameter("id")),
                    request.getParameter("fullName"),
                    request.getParameter("phone"),
                    request.getParameter("password"),
                    Integer.parseInt(request.getParameter("status")),
                    new Date(),
                    new Date(),
                    Integer.parseInt(request.getParameter("role"))
            );

            if (userDao.updateUser(user)) {
                request.getSession().setAttribute("message", "Cập nhật người dùng thành công");
            } else {
                request.getSession().setAttribute("error", "Không thể cập nhật người dùng");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi cập nhật người dùng: " + e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/userManagement");
    }
}
