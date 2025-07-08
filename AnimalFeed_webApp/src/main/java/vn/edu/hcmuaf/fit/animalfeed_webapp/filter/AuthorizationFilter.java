package vn.edu.hcmuaf.fit.animalfeed_webapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.ActionLogDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.util.Date;

@WebFilter(urlPatterns = {
        "/dashboard/*",
        "/home/*",
        "/logout",
        "/logout-admin",
        "/userManagement/*",
        "/addUser",
        "/userEdit",
        "/deleteUser",
        "/product-manager/*",
        "/add-product",
        "/edit-product",
        "/delete-product",
        "/category-management-admin/*",
        "/category-addition-admin",
        "/edit-category",
        "/delete-category",
        "/order-manager/*",
        "/orderEdit",
        "/add-order-management",
        "/post-management/*",
        "/post-add",
        "/post-edit",
        "/post-delete",
        "/job-managemet-admin/*",
        "/job-addtion-admin",
        "/edit-job-admin",
        "/delete-job-admin"
})
public class AuthorizationFilter implements Filter {
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) {
        userService = UserService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String requestURI = req.getRequestURI();
        String contextPath = req.getContextPath();
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;
        User user = (userId != null) ? userService.getById(userId) : null;

        System.out.println("AuthorizationFilter: Processing URI: " + requestURI + ", userId: " + userId + ", role: " + (user != null ? user.getRole() : "null"));

        if (resp.isCommitted()) {
            System.out.println("AuthorizationFilter: Response already committed, cannot proceed");
            return;
        }
                    
        if (requestURI.contains("/home")) {
           chain.doFilter(request, response);
           return;
        }

        // Allow access to /login and /login-google
        if (requestURI.endsWith("/login") || requestURI.endsWith("/login-google")) {
            chain.doFilter(request, response);
            return;
        }

        // Handle logout
        if (requestURI.endsWith("/logout") || requestURI.endsWith("/logout-admin")) {
            if (user != null) {
                logAction(user.getId(), "LOGOUT", "USER", user.getId(), "User " + user.getId() + " logged out");
            }
            chain.doFilter(request, response);
            return;
        }

        // Check if user is not logged in or forced to logout
        if (user == null || (user != null && userService.isForceLogout(user.getId()))) {
            // Invalidate session and redirect to login for all cases
            if (session != null) {
                session.invalidate();
            }
            resp.sendRedirect(contextPath + "/login?error=Please login again due to role change or session timeout");
            System.out.println("AuthorizationFilter: Redirecting to /login (user null or force_logout)");
            return;
        }

        // Log login action
        if (session.getAttribute("loginLogged") == null) {
            logAction(user.getId(), "LOGIN", "USER", user.getId(), "User " + user.getId() + " logged in");
            session.setAttribute("loginLogged", true);

            if (user.getRole() == 1) {
                String redirectPath = contextPath + "/dashboard";
                if (user.getSub_role() == 0) {
                    redirectPath = contextPath + "/dashboard";
                } else if (userService.hasPermission(user.getId(), "USER_MANAGEMENT")) {
                    redirectPath = contextPath + "/userManagement";
                } else if (userService.hasPermission(user.getId(), "PRODUCT_MANAGEMENT")) {
                    redirectPath = contextPath + "/product-manager";
                } else if (userService.hasPermission(user.getId(), "CATEGORY_MANAGEMENT")) {
                    redirectPath = contextPath + "/category-management-admin";
                } else if (userService.hasPermission(user.getId(), "ORDER_MANAGEMENT")) {
                    redirectPath = contextPath + "/order-manager";
                } else if (userService.hasPermission(user.getId(), "NEWS_MANAGEMENT")) {
                    redirectPath = contextPath + "/post-management";
                } else if (userService.hasPermission(user.getId(), "JOB_MANAGEMENT")) {
                    redirectPath = contextPath + "/job-managemet-admin";
                }
                if (!requestURI.equals(redirectPath)) {
                    resp.sendRedirect(redirectPath);
                    System.out.println("AuthorizationFilter: Redirecting to " + redirectPath + " after login");
                    return;
                }
            } else {
                if (!requestURI.contains("/home")) {
                    resp.sendRedirect(contextPath + "/home");
                    System.out.println("AuthorizationFilter: Redirecting to /home after login");
                    return;
                }
            }
        }

        // Check access based on role and sub_role
        if (user.getRole() == 1) { // Admin
            if (requestURI.contains("/dashboard")) {
                if (user.getSub_role() != 0 && !hasAccessToAdminPage(user, requestURI)) {
                    String redirectPath = getRedirectPath(user, contextPath);
                    if (!requestURI.equals(redirectPath)) {
                        session.setAttribute("error", "Bạn không có quyền truy cập trang này.");
                    }
                    resp.sendRedirect(redirectPath);
                    System.out.println("AuthorizationFilter: Redirecting to " + redirectPath + " (no permission)");
                    return;
                }
                chain.doFilter(request, response);
            } else if (requestURI.contains("/home")) {
                session.setAttribute("error", "Admin không được phép truy cập trang người dùng.");
                resp.sendRedirect(contextPath + "/dashboard");
                System.out.println("AuthorizationFilter: Redirecting admin to /dashboard");
                return;
            } else if (requestURI.contains("/userManagement") ||
                    requestURI.contains("/addUser") ||
                    requestURI.contains("/userEdit") ||
                    requestURI.contains("/deleteUser")) {
                if (!hasAccessToAdminPage(user, "/userManagement")) {
                    session.setAttribute("error", "Bạn không có quyền truy cập trang quản lý người dùng.");
                    resp.sendRedirect(getRedirectPath(user, contextPath));
                    System.out.println("AuthorizationFilter: Redirecting to dashboard (no USER_MANAGEMENT permission)");
                    return;
                }
                session.removeAttribute("error");
                chain.doFilter(request, response);
            } else if (requestURI.contains("/product-manager") ||
                    requestURI.contains("/add-product") ||
                    requestURI.contains("/edit-product") ||
                    requestURI.contains("/delete-product")) {
                if (!hasAccessToAdminPage(user, "/product-manager")) {
                    session.setAttribute("error", "Bạn không có quyền truy cập trang quản lý sản phẩm.");
                    resp.sendRedirect(getRedirectPath(user, contextPath));
                    System.out.println("AuthorizationFilter: Redirecting to dashboard (no PRODUCT_MANAGEMENT permission)");
                    return;
                }
                session.removeAttribute("error");
                chain.doFilter(request, response);
            } else if (requestURI.contains("/category-management-admin") ||
                    requestURI.contains("/category-addition-admin") ||
                    requestURI.contains("/edit-category") ||
                    requestURI.contains("/delete-category")) {
                if (!hasAccessToAdminPage(user, "/category-management-admin")) {
                    session.setAttribute("error", "Bạn không có quyền truy cập trang quản lý danh mục.");
                    resp.sendRedirect(getRedirectPath(user, contextPath));
                    System.out.println("AuthorizationFilter: Redirecting to dashboard (no CATEGORY_MANAGEMENT permission)");
                    return;
                }
                session.removeAttribute("error");
                chain.doFilter(request, response);
            } else if (requestURI.contains("/order-manager") ||
                    requestURI.contains("/orderEdit") ||
                    requestURI.contains("/add-order-management")) {
                if (!hasAccessToAdminPage(user, "/order-manager")) {
                    session.setAttribute("error", "Bạn không có quyền truy cập trang quản lý đơn hàng.");
                    resp.sendRedirect(getRedirectPath(user, contextPath));
                    System.out.println("AuthorizationFilter: Redirecting to dashboard (no ORDER_MANAGEMENT permission)");
                    return;
                }
                session.removeAttribute("error");
                chain.doFilter(request, response);
            } else if (requestURI.contains("/post-management") ||
                    requestURI.contains("/post-add") ||
                    requestURI.contains("/post-edit") ||
                    requestURI.contains("/post-delete")) {
                if (!hasAccessToAdminPage(user, "/post-management")) {
                    session.setAttribute("error", "Bạn không có quyền truy cập trang quản lý tin tức.");
                    resp.sendRedirect(getRedirectPath(user, contextPath));
                    System.out.println("AuthorizationFilter: Redirecting to dashboard (no NEWS_MANAGEMENT permission)");
                    return;
                }
                session.removeAttribute("error");
                chain.doFilter(request, response);
            } else if (requestURI.contains("/job-managemet-admin") ||
                    requestURI.contains("/job-addtion-admin") ||
                    requestURI.contains("/edit-job-admin") ||
                    requestURI.contains("/delete-job-admin")) {
                if (!hasAccessToAdminPage(user, "/job-managemet-admin")) {
                    session.setAttribute("error", "Bạn không có quyền truy cập trang quản lý tuyển dụng.");
                    resp.sendRedirect(getRedirectPath(user, contextPath));
                    System.out.println("AuthorizationFilter: Redirecting to dashboard (no JOB_MANAGEMENT permission)");
                    return;
                }
                session.removeAttribute("error");
                chain.doFilter(request, response);
            } else {
                if (user.getSub_role() != 0 && !hasAccessToAdminPage(user, requestURI)) {
                    String redirectPath = getRedirectPath(user, contextPath);
                    if (!requestURI.equals(redirectPath)) {
                        session.setAttribute("error", "Bạn không có quyền truy cập trang này.");
                    }
                    resp.sendRedirect(redirectPath);
                    System.out.println("AuthorizationFilter: Redirecting to " + redirectPath + " (no permission)");
                    return;
                }
                chain.doFilter(request, response);
            }
        } else { // Regular user (role == 0)
            if (requestURI.contains("/dashboard") ||
                    requestURI.contains("/userManagement") ||
                    requestURI.contains("/addUser") ||
                    requestURI.contains("/userEdit") ||
                    requestURI.contains("/deleteUser") ||
                    requestURI.contains("/product-manager") ||
                    requestURI.contains("/add-product") ||
                    requestURI.contains("/edit-product") ||
                    requestURI.contains("/delete-product") ||
                    requestURI.contains("/category-management-admin") ||
                    requestURI.contains("/category-addition-admin") ||
                    requestURI.contains("/edit-category") ||
                    requestURI.contains("/delete-category") ||
                    requestURI.contains("/order-manager") ||
                    requestURI.contains("/orderEdit") ||
                    requestURI.contains("/add-order-management") ||
                    requestURI.contains("/post-management") ||
                    requestURI.contains("/post-add") ||
                    requestURI.contains("/post-edit") ||
                    requestURI.contains("/post-delete") ||
                    requestURI.contains("/job-managemet-admin") ||
                    requestURI.contains("/job-addtion-admin") ||
                    requestURI.contains("/edit-job-admin") ||
                    requestURI.contains("/delete-job-admin")) {
                session.setAttribute("error", "Bạn không có quyền truy cập trang quản trị.");
                resp.sendRedirect(contextPath + "/home");
                System.out.println("AuthorizationFilter: Redirecting user to /home");
                return;
            } else if (requestURI.contains("/home")) {
                chain.doFilter(request, response);
            }
        }
    }

    private boolean hasAccessToAdminPage(User user, String requestURI) {
        System.out.println("Checking access for userId: " + user.getId() + ", sub_role: " + user.getSub_role() + ", URI: " + requestURI);
        if (user.getSub_role() == 0) { // Super Admin
            return true;
        }

        if (requestURI.contains("/userManagement") ||
                requestURI.contains("/addUser") ||
                requestURI.contains("/userEdit") ||
                requestURI.contains("/deleteUser")) {
            return userService.hasPermission(user.getId(), "USER_MANAGEMENT");
        } else if (requestURI.contains("/product-manager") ||
                requestURI.contains("/add-product") ||
                requestURI.contains("/edit-product") ||
                requestURI.contains("/delete-product")) {
            return userService.hasPermission(user.getId(), "PRODUCT_MANAGEMENT");
        } else if (requestURI.contains("/category-management-admin") ||
                requestURI.contains("/category-addition-admin") ||
                requestURI.contains("/edit-category") ||
                requestURI.contains("/delete-category")) {
            return userService.hasPermission(user.getId(), "CATEGORY_MANAGEMENT");
        } else if (requestURI.contains("/order-manager") ||
                requestURI.contains("/orderEdit") ||
                requestURI.contains("/add-order-management")) {
            return userService.hasPermission(user.getId(), "ORDER_MANAGEMENT");
        } else if (requestURI.contains("/post-management") ||
                requestURI.contains("/post-add") ||
                requestURI.contains("/post-edit") ||
                requestURI.contains("/post-delete")) {
            return userService.hasPermission(user.getId(), "NEWS_MANAGEMENT");
        } else if (requestURI.contains("/job-managemet-admin") ||
                requestURI.contains("/job-addtion-admin") ||
                requestURI.contains("/edit-job-admin") ||
                requestURI.contains("/delete-job-admin")) {
            return userService.hasPermission(user.getId(), "JOB_MANAGEMENT");
        } else if (requestURI.contains("/dashboard")) {
            return user.getSub_role() == 0;
        }

        return false;
    }

    private String getRedirectPath(User user, String contextPath) {
        if (user.getSub_role() == 0) {
            return contextPath + "/dashboard";
        } else if (userService.hasPermission(user.getId(), "USER_MANAGEMENT")) {
            return contextPath + "/userManagement";
        } else if (userService.hasPermission(user.getId(), "PRODUCT_MANAGEMENT")) {
            return contextPath + "/product-manager";
        } else if (userService.hasPermission(user.getId(), "CATEGORY_MANAGEMENT")) {
            return contextPath + "/category-management-admin";
        } else if (userService.hasPermission(user.getId(), "ORDER_MANAGEMENT")) {
            return contextPath + "/order-manager";
        } else if (userService.hasPermission(user.getId(), "NEWS_MANAGEMENT")) {
            return contextPath + "/post-management";
        } else if (userService.hasPermission(user.getId(), "JOB_MANAGEMENT")) {
            return contextPath + "/job-managemet-admin";
        }
        return contextPath + "/dashboard";
    }

    private void logAction(int userId, String actionType, String entityType, int entityId, String description) {
        ActionLog actionLog = new ActionLog();
        actionLog.setUser_id(userId);
        actionLog.setAction_type(actionType);
        actionLog.setEntity_type(entityType);
        actionLog.setEntity_id(entityId);
        actionLog.setCreated_at(new Date());
        actionLog.setDescription(description);
        ActionLogDao.logAction(actionLog);
    }

    @Override
    public void destroy() {
    }
}
