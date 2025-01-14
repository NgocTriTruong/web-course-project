package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthService implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String path = req.getRequestURI();

        // Danh sách các URL được phép truy cập mà không cần đăng nhập
        List<String> allowedUrls = Arrays.asList("/login", "/register", "home");

        // Kiểm tra nếu URL hiện tại nằm trong danh sách được phép
        boolean isAllowedUrl = allowedUrls.stream()
                .anyMatch(url -> path.endsWith(url));

        if (isAllowedUrl) {
            chain.doFilter(request, response);
            return;
        }

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        // Kiểm tra quyền truy cập admin area
        if (path.contains("/admin/")) {
            if (user == null || user.getRole() != 1) {
                res.sendRedirect(req.getContextPath() + "login");
                return;
            }
        }

        // Kiểm tra quyền truy cập user area
        else if (path.contains("/user/")) {
            if (user == null || user.getRole() != 0) {
                res.sendRedirect(req.getContextPath() + "login");
                return;
            }
        }

        // Cho phép request tiếp tục
        chain.doFilter(request, response);
    }
}