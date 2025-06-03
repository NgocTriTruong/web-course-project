package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Issue;

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
                handle.createQuery(
                                "SELECT i.*, u.full_name AS admin_name, p.name AS product_name " +
                                        "FROM issues i " +
                                        "JOIN users u ON i.user_id = u.id " +
                                        "JOIN products p ON i.product_id = p.id"
                        )
                        .map((rs, ctx) -> {
                            Issue issue = new Issue();
                            issue.setId(rs.getInt("id"));
                            issue.setUserId(rs.getInt("user_id"));
                            issue.setProductId(rs.getInt("product_id"));
                            issue.setReason(rs.getString("reason"));
                            issue.setQuantity(rs.getInt("quantity"));
                            issue.setStatus(rs.getInt("status"));
                            issue.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
                            issue.setAdminName(rs.getString("admin_name"));
                            issue.setProductName(rs.getString("product_name"));
                            return issue;
                        })
                        .list()
        );
    }

    // Method to get issue by ID
    public Issue getIssueById(int issueId) {
        return jdbi.withHandle(handle ->
                handle.createQuery(
                                "SELECT i.*, u.full_name AS admin_name, p.name AS product_name " +
                                        "FROM issues i " +
                                        "JOIN users u ON i.user_id = u.id " +
                                        "JOIN products p ON i.product_id = p.id " +
                                        "WHERE i.id = :issueId"
                        )
                        .bind("issueId", issueId)
                        .map((rs, ctx) -> {
                            Issue issue = new Issue();
                            issue.setId(rs.getInt("id"));
                            issue.setUserId(rs.getInt("user_id"));
                            issue.setProductId(rs.getInt("product_id"));
                            issue.setReason(rs.getString("reason"));
                            issue.setQuantity(rs.getInt("quantity"));
                            issue.setStatus(rs.getInt("status"));
                            issue.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
                            issue.setAdminName(rs.getString("admin_name"));
                            issue.setProductName(rs.getString("product_name"));
                            return issue;
                        })
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
                handle.createQuery(
                                "SELECT i.*, u.full_name AS admin_name, p.name AS product_name " +
                                        "FROM issues i " +
                                        "JOIN users u ON i.user_id = u.id " +
                                        "JOIN products p ON i.product_id = p.id " +
                                        "WHERE i.reason LIKE :searchTerm OR i.id = :id"
                        )
                        .bind("searchTerm", "%" + searchTerm + "%")
                        .bind("id", searchTerm.matches("\\d+") ? Integer.parseInt(searchTerm) : -1)
                        .map((rs, ctx) -> {
                            Issue issue = new Issue();
                            issue.setId(rs.getInt("id"));
                            issue.setUserId(rs.getInt("user_id"));
                            issue.setProductId(rs.getInt("product_id"));
                            issue.setReason(rs.getString("reason"));
                            issue.setQuantity(rs.getInt("quantity"));
                            issue.setStatus(rs.getInt("status"));
                            issue.setCreateDate(rs.getTimestamp("create_date").toLocalDateTime());
                            issue.setAdminName(rs.getString("admin_name"));
                            issue.setProductName(rs.getString("product_name"));
                            return issue;
                        })
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