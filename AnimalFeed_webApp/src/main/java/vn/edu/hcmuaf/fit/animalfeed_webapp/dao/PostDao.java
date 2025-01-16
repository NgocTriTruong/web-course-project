package vn.edu.hcmuaf.fit.animalfeed_webapp.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.db.JdbiConnect;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PostDao {
    private static final Jdbi jdbi = JdbiConnect.getJdbi();

    public List<Post> getAllPosts() {
        String query = "SELECT * FROM posts ORDER BY create_date DESC";

        return jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .map(new PostMapper())
                        .list()
        );
    }

    public Optional<Post> getPostById(int id) {
        String query = "SELECT * FROM posts WHERE id = :id";

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
                        .bind("status", post.getStatus())
                        .executeAndReturnGeneratedKeys("id")
                        .mapTo(Integer.class)
                        .one();

                // Ghi lại hành động vào bảng action_log
                handle.createUpdate("INSERT INTO action_log (user_id, action_type, entity_type, entity_id, created_at, description) " +
                                "VALUES (:userId, :actionType, :entityType, :entityId, CURRENT_DATE, :description)")
                        .bind("userId", adminUserId)
                        .bind("actionType", "CREATE")
                        .bind("entityType", "POST")
                        .bind("entityId", postId)
                        .bind("description", "Admin user " + adminUserId + " created post " + postId)
                        .execute();
            });
        } else {
            throw new IllegalArgumentException("User with ID " + adminUserId + " is not authorized to add posts.");
        }
    }
}