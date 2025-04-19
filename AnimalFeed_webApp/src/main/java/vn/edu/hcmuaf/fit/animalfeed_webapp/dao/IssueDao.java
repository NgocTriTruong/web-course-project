package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import com.sun.tools.javac.Main;
import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueDao {
    private static Jdbi jdbi = JdbiConnect.getJdbi();

    // Method to insert a new issue
    public int insertIssue(Issue issue) {
        return jdbi.withHandle(handle ->
                handle.createUpdate("INSERT INTO issues (user_id, product_id, reason, quantity, status, create_date) VALUES (:userId, :productId, :reason, :quantity, :status, :createDate)")
                        .bind("userId", issue.getUserId())
                        .bind("productId", issue.getProductId())
                        .bind("reason", issue.getReason())
                        .bind("quantity", issue.getQuantity())
                        .bind("status", issue.getStatus())
                        .bind("createDate", issue.getCreateDate())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(int.class)
                        .one()
        );
    }

    // Method to get all issues
    public static List<Issue> getAllIssues() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM issues")
                        .mapToBean(Issue.class)
                        .list()
        );
    }

    // Method to get issue by ID
    public Issue getIssueById(int issueId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM issues WHERE id = :issueId")
                        .bind("issueId", issueId)
                        .mapToBean(Issue.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    // Method to update an issue
    public void updateIssue(Issue issue) {
        int rowsUpdated = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE issues SET user_id = :userId, product_id = :productId, reason = :reason, quantity = :quantity, status = :status WHERE id = :id")
                        .bind("userId", issue.getUserId())
                        .bind("productId", issue.getProductId())
                        .bind("reason", issue.getReason())
                        .bind("quantity", issue.getQuantity())
                        .bind("status", issue.getStatus())
                        .bind("id", issue.getId())
                        .execute()
        );
        System.out.println("Rows updated: " + rowsUpdated);
    }

    // Method to delete an issue
    public void deleteIssue(int id) {
        int rowsDeleted = jdbi.withHandle(handle ->
                handle.createUpdate("UPDATE issues SET status = 1 WHERE id = :id")
                        .bind("id", id)
                        .execute()
        );
        System.out.println("Rows deleted: " + rowsDeleted);
    }

    // Method to search issues by term (reason or id)
    public List<Issue> searchIssues(String searchTerm) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM issues WHERE reason LIKE :searchTerm OR id = :id")
                        .bind("searchTerm", "%" + searchTerm + "%")
                        .bind("id", searchTerm.matches("\\d+") ? Integer.parseInt(searchTerm) : -1)
                        .mapToBean(Issue.class)
                        .list()
        );
    }

    // Method to get total number of issues
    public int getTotalIssues() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT COUNT(*) FROM issues")
                        .mapTo(int.class)
                        .one()
        );
    }

    public static void main(String[] args) {
        System.out.println(getAllIssues());
    }
}