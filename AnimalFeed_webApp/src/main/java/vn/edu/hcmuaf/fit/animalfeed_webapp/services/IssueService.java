package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.IssueDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Issue;

import java.util.List;

public class IssueService {
    private IssueDao issueDao = new IssueDao();

    public List<Issue> getAllIssues() {
        return issueDao.getAllIssues();
    }

    public List<Issue> searchIssues(String searchTerm) {
        return issueDao.searchIssues(searchTerm);
    }

    public void deleteIssue(int id) {
        issueDao.deleteIssue(id);
    }

    public int insertIssue(Issue issue) {
        return issueDao.insertIssue(issue);
    }

    public boolean updateIssue(Issue issue) {
        issueDao.updateIssue(issue);
        return true;
    }

    public Issue getIssueById(int id) {
        return issueDao.getIssueById(id);
    }
}
