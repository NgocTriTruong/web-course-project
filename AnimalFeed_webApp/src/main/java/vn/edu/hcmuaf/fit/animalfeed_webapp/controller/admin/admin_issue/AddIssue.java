package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_issue;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Issue;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Product;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.User;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.IssueService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.UserService;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "AddIssue", value = "/add-issue")
public class AddIssue extends HttpServlet {
    private IssueService issueService;
    private ProductService productService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        issueService = new IssueService();
        productService = new ProductService();
        userService = UserService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/issueAddition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userId");
            if (userId == null || userId == 0) {
                throw new Exception("Vui lòng đăng nhập với tư cách admin");
            }

            int productId = Integer.parseInt(request.getParameter("productId"));
            String reason = request.getParameter("reason");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int status = Integer.parseInt(request.getParameter("status"));

            if (reason == null || reason.trim().isEmpty()) {
                throw new Exception("Lý do sự cố không được để trống");
            }
            if (quantity < 1) {
                throw new Exception("Số lượng phải lớn hơn 0");
            }
            if (status != 0 && status != 1) {
                throw new Exception("Trạng thái không hợp lệ");
            }

            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new Exception("Sản phẩm không tồn tại");
            }

            User admin = userService.getUserById(userId);
            if (admin == null) {
                throw new Exception("Admin không tồn tại");
            }

            Issue issue = new Issue();
            issue.setUserId(userId);
            issue.setProductId(productId);
            issue.setReason(reason);
            issue.setQuantity(quantity);
            issue.setStatus(status);
            issue.setCreateDate(LocalDateTime.now());
            issue.setAdminName(admin.getFullName());
            issue.setProductName(product.getName());

            issueService.insertIssue(issue);

            request.getSession().setAttribute("message", "Thêm báo cáo sự cố thành công!");
            response.sendRedirect(request.getContextPath() + "/issueManagement");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi: " + e.getMessage());
            request.getRequestDispatcher("/views/admin/issueAddition.jsp").forward(request, response);
        }
    }
}