package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(value = "/userManagement")
public class UserManagerForAdmin extends HttpServlet {
    private UserService userService;
    private static final int PAGE_SIZE = 10;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
    }

    private int getAdminUserIdFromSession(HttpServletRequest request) throws ServletException {
        Integer adminUserId = (Integer) request.getSession().getAttribute("adminUserId");
        if (adminUserId == null) {
            throw new ServletException("Admin không có quyền truy cập hoặc chưa đăng nhập.");
        }
        return adminUserId;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String searchTerm = request.getParameter("searchTerm");
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "delete":
                handleDelete(request, response);
                break;
            case "edit":
                handleEdit(request, response);
                break;
            case "search":
                handleSearch(request, response, page);
                break;
            default:
                List<User> users;
                int totalUsers;
                if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                    users = userService.searchUsers(searchTerm);
                    totalUsers = users.size();
                    request.setAttribute("searchTerm", searchTerm);
                } else {
                    users = userService.getAllUsers();
                    totalUsers = users.size();
                }
                int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);
                int start = (page - 1) * PAGE_SIZE;
                int end = Math.min(start + PAGE_SIZE, totalUsers);
                users = users.subList(start, end);

                request.setAttribute("users", users);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("currentPage", page);
                request.getRequestDispatcher("/views/admin/userManagement.jsp").forward(request, response);
                break;
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int adminUserId = getAdminUserIdFromSession(request);
            int userId = Integer.parseInt(request.getParameter("id"));
            userService.deleteUser(userId, adminUserId);
            request.getSession().setAttribute("message", "Xóa người dùng thành công.");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi xóa người dùng: " + e.getMessage());
        }
        response.sendRedirect("userManagement");
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userService.getById(userId);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/views/admin/userEdit.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("userManagement?error=not_found");
        } catch (Exception e) {
            response.sendRedirect("userManagement?error=" + e.getMessage());
        }
    }

    private void handleSearch(HttpServletRequest request, HttpServletResponse response, int page) throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        List<User> users;

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            users = userService.searchUsers(searchTerm);
        } else {
            users = userService.getAllUsers();
        }

        int totalUsers = users.size();
        int totalPages = (int) Math.ceil((double) totalUsers / PAGE_SIZE);
        int start = (page - 1) * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, totalUsers);
        users = users.subList(start, end);

        request.setAttribute("users", users);
        request.setAttribute("searchTerm", searchTerm);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.getRequestDispatcher("/views/admin/userManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request, response);
        } else if ("update".equals(action)) {
            handleUpdate(request, response);
        } else {
            response.sendRedirect("userManagement?error=invalid_action");
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User newUser = new User();
            newUser.setFullName(request.getParameter("fullName"));
            newUser.setPhone(request.getParameter("phone"));
            newUser.setPassword(request.getParameter("password"));
            newUser.setStatus(Integer.parseInt(request.getParameter("status")));
            newUser.setRole(Integer.parseInt(request.getParameter("role")));
            newUser.setSub_role(Integer.parseInt(request.getParameter("sub_role")));
            newUser.setCreateDate(new Date());
            newUser.setUpdateDate(new Date());

            int adminUserId = getAdminUserIdFromSession(request);
            userService.addUser(newUser, adminUserId);
            response.sendRedirect("userManagement?message=add_success");
        } catch (Exception e) {
            response.sendRedirect("userManagement?error=" + e.getMessage());
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = Integer.parseInt(request.getParameter("id"));
            User updatedUser = new User();
            updatedUser.setId(userId);
            updatedUser.setFullName(request.getParameter("fullName"));
            updatedUser.setPhone(request.getParameter("phone"));
            updatedUser.setPassword(request.getParameter("password"));
            updatedUser.setStatus(Integer.parseInt(request.getParameter("status")));
            updatedUser.setRole(Integer.parseInt(request.getParameter("role")));
            updatedUser.setSub_role(Integer.parseInt(request.getParameter("sub_role")));
            updatedUser.setUpdateDate(new Date());

            int adminUserId = getAdminUserIdFromSession(request);
            userService.updateUser(updatedUser, adminUserId);
            request.getSession().setAttribute("message", "Cập nhật người dùng thành công.");
            response.sendRedirect("userManagement");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi cập nhật người dùng: " + e.getMessage());
            response.sendRedirect("userManagement?error=" + e.getMessage());
        }
    }
}