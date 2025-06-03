package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    private int getUserIdFromSession(HttpServletRequest request) throws ServletException {
        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;
        if (userId == null) {
            throw new ServletException("Người dùng không có quyền truy cập hoặc chưa đăng nhập.");
        }
        return userId;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserManagerForAdmin: Processing request for /userManagement");
        if (response.isCommitted()) {
            System.out.println("UserManagerForAdmin: Response already committed, cannot forward");
            return;
        }

        String action = request.getParameter("action");
        String searchTerm = request.getParameter("searchTerm");
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        if (action == null) {
            action = "";
        }

        try {
            switch (action) {
                case "delete":
                    handleDelete(request, response);
                    return;
                case "edit":
                    handleEdit(request, response);
                    return;
                case "search":
                    handleSearch(request, response, page);
                    return;
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
                    request.setAttribute("userService", userService);

                    if (response.isCommitted()) {
                        System.out.println("UserManagerForAdmin: Response already committed before forwarding");
                        return;
                    }

                    System.out.println("UserManagerForAdmin: Forwarding to /views/admin/userManagement.jsp");
                    request.getRequestDispatcher("/views/admin/userManagement.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            System.out.println("UserManagerForAdmin: Error processing request: " + e.getMessage());
            request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
            response.sendRedirect("userManagement");
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int userId = getUserIdFromSession(request);
            int targetUserId = Integer.parseInt(request.getParameter("id"));
            userService.deleteUser(targetUserId, userId);
            request.getSession().setAttribute("message", "Xóa người dùng thành công.");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi xóa người dùng: " + e.getMessage());
        }
        response.sendRedirect("userManagement");
    }

    private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int targetUserId = Integer.parseInt(request.getParameter("id"));
            User user = userService.getById(targetUserId);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/views/admin/userEdit.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "Không tìm thấy người dùng.");
                response.sendRedirect("userManagement");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
            response.sendRedirect("userManagement");
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
        request.setAttribute("userService", userService);

        if (response.isCommitted()) {
            System.out.println("UserManagerForAdmin: Response already committed before forwarding in handleSearch");
            return;
        }

        System.out.println("UserManagerForAdmin: Forwarding to /views/admin/userManagement.jsp (search)");
        request.getRequestDispatcher("/views/admin/userManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("UserManagerForAdmin: Processing POST request for /userManagement");
        if (response.isCommitted()) {
            System.out.println("UserManagerForAdmin: Response already committed in doPost");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                handleAdd(request, response);
            } else if ("update".equals(action)) {
                handleUpdate(request, response);
            } else {
                response.sendRedirect("userManagement?error=invalid_action");
            }
        } catch (Exception e) {
            System.out.println("UserManagerForAdmin: Error processing POST request: " + e.getMessage());
            request.getSession().setAttribute("error", "Lỗi: " + e.getMessage());
            response.sendRedirect("userManagement");
        }
    }

    private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

            int userId = getUserIdFromSession(request);
            userService.addUser(newUser, userId);
            request.getSession().setAttribute("message", "Thêm người dùng thành công.");
            response.sendRedirect("userManagement");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi thêm người dùng: " + e.getMessage());
            response.sendRedirect("userManagement");
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            int targetUserId = Integer.parseInt(request.getParameter("id"));
            User updatedUser = userService.getById(targetUserId);
            if (updatedUser == null) {
                throw new ServletException("Không tìm thấy người dùng để cập nhật.");
            }

            updatedUser.setFullName(request.getParameter("fullName"));
            updatedUser.setPhone(request.getParameter("phone"));
            String password = request.getParameter("password");
            if (password != null && !password.trim().isEmpty()) {
                updatedUser.setPassword(password);
            }
            updatedUser.setStatus(Integer.parseInt(request.getParameter("status")));
            updatedUser.setRole(Integer.parseInt(request.getParameter("role")));
            updatedUser.setSub_role(Integer.parseInt(request.getParameter("sub_role")));
            updatedUser.setUpdateDate(new Date());

            int userId = getUserIdFromSession(request);
            userService.updateUser(updatedUser, userId);
            request.getSession().setAttribute("message", "Cập nhật người dùng thành công.");
            response.sendRedirect("userManagement");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Lỗi khi cập nhật người dùng: " + e.getMessage());
            response.sendRedirect("userManagement");
        }
    }
}