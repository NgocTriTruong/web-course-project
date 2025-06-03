package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_issue;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.IssueService;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Issue;


import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "SolveIssueController", value = "/solve-issue")
public class SolveIssueController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(SolveIssueController.class.getName());
    private IssueService issueService;

    @Override
    public void init() throws ServletException {
        issueService = new IssueService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String issueId = request.getParameter("id");
            if (issueId == null || issueId.isEmpty()) {
                LOGGER.warning("Invalid issue ID: " + issueId);
                request.getSession().setAttribute("error", "Mã sự cố không hợp lệ.");
                response.sendRedirect(request.getContextPath() + "/issueManagement");
                return;
            }

            int id = Integer.parseInt(issueId);
            Integer userId = (Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                LOGGER.warning("User not logged in for issue ID: " + id);
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // Kiểm tra sự cố tồn tại và chưa giải quyết
            Issue issue = issueService.getIssueById(id);
            if (issue == null || issue.getStatus() == 1) {
                LOGGER.warning("Issue not found or already solved: " + id);
                request.getSession().setAttribute("error", "Sự cố không tồn tại hoặc đã được giải quyết.");
                response.sendRedirect(request.getContextPath() + "/issueManagement");
                return;
            }

            // Cập nhật trạng thái thành Đã giải quyết
            issue.setStatus(1);
            boolean updated = issueService.updateIssue(issue);
            if (updated) {
                LOGGER.info("Issue ID " + id + " marked as solved.");
                request.getSession().setAttribute("message", "Báo cáo đã được đánh dấu đã giải quyết!");
            } else {
                LOGGER.warning("Failed to update issue ID: " + id);
                request.getSession().setAttribute("error", "Không thể đánh dấu báo cáo đã giải quyết.");
            }
            response.sendRedirect(request.getContextPath() + "/issueManagement");

        } catch (NumberFormatException e) {
            LOGGER.warning("Invalid issue ID format: " + request.getParameter("id"));
            request.getSession().setAttribute("error", "Mã sự cố không hợp lệ.");
            response.sendRedirect(request.getContextPath() + "/issueManagement");
        } catch (Exception e) {
            LOGGER.severe("Error processing solve issue: " + e.getMessage());
            e.printStackTrace();
            request.getSession().setAttribute("error", "Đã có lỗi xảy ra khi xử lý yêu cầu.");
            response.sendRedirect(request.getContextPath() + "/issueManagement");
        }
    }
}