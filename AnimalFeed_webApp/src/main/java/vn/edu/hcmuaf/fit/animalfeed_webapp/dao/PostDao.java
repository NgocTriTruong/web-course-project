package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.ActionLog;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PostDao {
    private static final Jdbi jdbi = JdbiConnect.getJdbi();
    private static final ActionLogDao actionLogDao = new ActionLogDao(); // Giả định ActionLogDao đã được định nghĩa

    public List<Post> getAllPosts() {
        String query = "SELECT * FROM posts WHERE status = 1 ORDER BY create_date DESC";

        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .map(new PostMapper())
                        .list()
        );
    }

    public Optional<Post> getPostById(int id) {
        String query = "SELECT * FROM posts WHERE id = :id AND status = 1";

        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .bind("id", id)
                        .map(new PostMapper())
                        .findFirst()
        );
    }

    private static class PostMapper implements RowMapper<Post> {
        @Override
        public Post map(ResultSet rs, StatementContext ctx) throws SQLException {
            return new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("img"),
                    rs.getDate("create_date"),
                    rs.getInt("user_id"),
                    rs.getInt("status")
            );
        }
    }

    // Kiểm tra xem user có phải là admin hay không
    public static boolean checkIfAdmin(int userId) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT role FROM users WHERE id = :userId")
                        .bind("userId", userId)
                        .mapTo(Integer.class)
                        .findOne()
                        .orElse(0) == 1 // Nếu role = 1 là admin
        );
    }

    // Thêm bài viết mới
    public void addPost(Post post, int adminUserId) {
        if (checkIfAdmin(adminUserId)) {
            jdbi.useTransaction(handle -> {
                // Thêm bài viết và lấy ID của bài viết mới
                int postId = handle.createUpdate("INSERT INTO posts (title, content, img, create_date, user_id, status) " +
                                "VALUES (:title, :content, :img, NOW(), :userId, :status)")
                        .bind("title", post.getTitle())
                        .bind("content", post.getContent())
                        .bind("img", post.getImg())
                        .bind("userId", post.getUserId())
                        .bind("status", 1) // Trạng thái active
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one();

                // Ghi log hành động vào bảng action_log
                ActionLog actionLog = new ActionLog(
                        adminUserId, "CREATE", "POST", postId,
                        "User " + adminUserId + " created post " + postId,
                        post.toString(), null
                );
                actionLogDao.logAction(actionLog);
            });
        } else {
            throw new IllegalArgumentException("User with ID " + adminUserId + " is not authorized to add posts.");
        }
    }

    // Xóa bài viết (xóa mềm)
    public void deletePost(int postId, int adminUserId) {
        if (checkIfAdmin(adminUserId)) {
            Post deletedPost = getPostById(postId).orElse(null);
            if (deletedPost == null) {
                throw new IllegalArgumentException("Post with ID " + postId + " does not exist.");
            }

            jdbi.useTransaction(handle -> {
                // Cập nhật trạng thái sản phẩm thành 'deleted' (status = 0)
                int updatedRows = handle.createUpdate("UPDATE posts SET status = :status WHERE id = :productId")
                        .bind("status", 0) // Trạng thái 'deleted'
                        .bind("productId", postId)
                        .execute();

                if (updatedRows > 0) {
                    // Ghi log hành động vào bảng action_log
                    ActionLog actionLog = new ActionLog(
                            adminUserId, "DELETE", "POST", postId,
                            "User " + adminUserId + " deleted post " + postId,
                            deletedPost.toString(), null
                    );
                    actionLogDao.logAction(actionLog);
                } else {
                    throw new RuntimeException("Failed to delete post with ID: " + postId);
                }
            });
        } else {
            throw new IllegalArgumentException("User with ID " + adminUserId + " is not authorized to delete posts.");
        }
    }

    // Sửa bài viết
    public void updatePost(Post post, int adminUserId) {
        if (checkIfAdmin(adminUserId)) {
            Post existingPost = getPostById(post.getId()).orElse(null);
            if (existingPost == null) {
                throw new IllegalArgumentException("Post with ID " + post.getId() + " does not exist.");
            }

            jdbi.useTransaction(handle -> {
                // Cập nhật thông tin bài viết
                int updatedRows = handle.createUpdate("UPDATE posts SET title = :title, content = :content, img = :img, status = :status WHERE id = :id")
                        .bind("id", post.getId())
                        .bind("title", post.getTitle())
                        .bind("content", post.getContent())
                        .bind("img", post.getImg())
                        .bind("status", post.getStatus())
                        .execute();

                if (updatedRows > 0) {
                    // Ghi log hành động vào bảng action_log
                    ActionLog actionLog = new ActionLog(
                            adminUserId, "UPDATE", "POST", post.getId(),
                            "User " + adminUserId + " updated post " + post.getId(),
                            existingPost.toString(), post.toString()
                    );
                    actionLogDao.logAction(actionLog);
                } else {
                    throw new RuntimeException("Failed to update post with ID: " + post.getId());
                }
            });
        } else {
            throw new IllegalArgumentException("User with ID " + adminUserId + " is not authorized to update posts.");
        }
    }

    //search post
    public List<Post> searchPosts(String keyword) {
        String query = "SELECT * FROM posts WHERE status = 1 AND (title LIKE :keyword OR content LIKE :keyword) ORDER BY create_date DESC";
        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .bind("keyword", "%" + keyword + "%")
                        .map(new PostMapper())
                        .list()
        );
    }
}