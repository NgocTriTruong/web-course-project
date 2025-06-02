package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_issue;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Issue;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.IssueService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.ProductService;

import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "EditIssueController", value = "/edit-issue")
public class EditIssueController extends HttpServlet {
    private IssueService issueService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        issueService = new IssueService();
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String issueId = request.getParameter("id");
            if (issueId == null || issueId.isEmpty()) {
                request.getSession().setAttribute("error", "Mã sự cố không hợp lệ.");
                response.sendRedirect(request.getContextPath() + "/issueManagement");
                return;
            }

            int id = Integer.parseInt(issueId);
            Issue issue = issueService.getIssueById(id);
            if (issue == null || issue.getStatus() == 1) {
                request.getSession().setAttribute("error", "Sự cố không tồn tại hoặc đã được giải quyết.");
                response.sendRedirect(request.getContextPath() + "/issueManagement");
                return;
            }

            // Lấy tồn kho hiện tại
            int inventoryQuantity = productService.getInventoryQuantity(issue.getProductId());
            request.setAttribute("issue", issue);
            request.setAttribute("inventoryQuantity", inventoryQuantity);
            request.getRequestDispatcher("/views/admin/issueEdition.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Mã sự cố không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/issueManagement");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã có lỗi xảy ra khi tải form chỉnh sửa.");
            response.sendRedirect(request.getContextPath() + "/issueManagement");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String issueId = request.getParameter("id");
            String productId = request.getParameter("productId");
            String reason = request.getParameter("reason");
            String quantityStr = request.getParameter("quantity");
            int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());

            if (issueId == null || productId == null || reason == null || quantityStr == null) {
                request.getSession().setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
                response.sendRedirect(request.getContextPath() + "/edit-issue?id=" + issueId);
                return;
            }

            int id = Integer.parseInt(issueId);
            int productIdInt = Integer.parseInt(productId);
            int quantity = Integer.parseInt(quantityStr);

            // Kiểm tra số lượng hợp lệ
            int inventoryQuantity = productService.getInventoryQuantity(productIdInt);
            if (quantity <= 0 || quantity > inventoryQuantity) {
                request.getSession().setAttribute("error", "Số lượng không hợp lệ hoặc vượt quá tồn kho (" + inventoryQuantity + ").");
                response.sendRedirect(request.getContextPath() + "/edit-issue?id=" + issueId);
                return;
            }

            // Cập nhật sự cố
            Issue issue = new Issue();
            issue.setId(id);
            issue.setUserId(userId);
            issue.setProductId(productIdInt);
            issue.setReason(reason);
            issue.setQuantity(quantity);
            issue.setStatus(0); // Giữ trạng thái Chưa giải quyết
            issue.setCreateDate(LocalDateTime.now()); // Cập nhật ngày tạo

            boolean updated = issueService.updateIssue(issue);
            if (updated) {
                request.getSession().setAttribute("message", "Cập nhật báo cáo sự cố thành công!");
            } else {
                request.getSession().setAttribute("error", "Cập nhật báo cáo sự cố thất bại.");
            }
            response.sendRedirect(request.getContextPath() + "/issueManagement");

        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Dữ liệu nhập vào không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/issueManagement");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã có lỗi xảy ra khi cập nhật sự cố.");
            response.sendRedirect(request.getContextPath() + "/issueManagement");
        }
    }
}