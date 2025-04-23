package vn.edu.hcmuaf.fit.animalfeed_webapp.controller.admin.admin_issue;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Issue;
import vn.edu.hcmuaf.fit.animalfeed_webapp.services.IssueService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "IssueController", value = "/issueManagement")
public class IssueController extends HttpServlet {
    private IssueService issueService;

    @Override
    public void init() throws ServletException {
        issueService = new IssueService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if (action == null) {
                listIssues(request, response);
            } else if (action.equals("search")) {
                searchIssues(request, response);
            } else {
                listIssues(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your request.");
            request.getRequestDispatcher("/views/admin/issueManagement.jsp").forward(request, response);
        }
    }

    private void listIssues(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Issue> allIssues = issueService.getAllIssues();
            if (allIssues == null) {
                allIssues = new ArrayList<>();
            }

            int page = 1;
            String pageParam = request.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            int countIssues = allIssues.size();
            int issuesPerPage = 10;
            int endPage = Math.max(1, (int) Math.ceil((double) countIssues / issuesPerPage));

            if (page > endPage) {
                page = endPage;
            }

            int startIndex = (page - 1) * issuesPerPage;
            int endIndex = Math.min(startIndex + issuesPerPage, countIssues);

            if (startIndex < 0) startIndex = 0;
            if (startIndex > countIssues) startIndex = Math.max(0, countIssues - issuesPerPage);
            if (endIndex < startIndex) endIndex = startIndex;

            List<Issue> issuesForPage;
            if (countIssues > 0) {
                issuesForPage = allIssues.subList(startIndex, endIndex);
            } else {
                issuesForPage = new ArrayList<>();
            }

            request.setAttribute("issues", issuesForPage);
            request.setAttribute("currentPage", page);
            request.setAttribute("endPage", endPage);
            request.setAttribute("totalIssues", countIssues);

            request.getRequestDispatcher("/views/admin/issueManagement.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while retrieving issues.");
            request.setAttribute("issues", new ArrayList<>());
            request.setAttribute("currentPage", 1);
            request.setAttribute("endPage", 1);
            request.setAttribute("totalIssues", 0);
            request.getRequestDispatcher("/views/admin/issueManagement.jsp").forward(request, response);
        }
    }

    private void searchIssues(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("searchTerm");
        List<Issue> searchResults = issueService.searchIssues(searchTerm);
        if (searchResults == null) {
            searchResults = new ArrayList<>();
        }

        String pageParam = request.getParameter("page");
        int page = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;

        int countIssues = searchResults.size();
        int issuesPerPage = 10;
        int endPage = Math.max(1, (int) Math.ceil((double) countIssues / issuesPerPage));

        if (page > endPage) {
            page = endPage;
        }

        int startIndex = (page - 1) * issuesPerPage;
        int endIndex = Math.min(startIndex + issuesPerPage, countIssues);
        List<Issue> issuesForPage;
        if (countIssues > 0) {
            issuesForPage = searchResults.subList(startIndex, endIndex);
        } else {
            issuesForPage = new ArrayList<>();
        }

        request.setAttribute("issues", issuesForPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("endPage", endPage);
        request.setAttribute("searchTerm", searchTerm);

        request.getRequestDispatcher("/views/admin/issueManagement.jsp").forward(request, response);
    }
}