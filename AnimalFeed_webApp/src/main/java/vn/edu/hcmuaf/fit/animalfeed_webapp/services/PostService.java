package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.PostDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostDao postDao;
    private final UserService userService = UserService.getInstance(); // Thêm UserService để kiểm tra quyền

    public PostService() {
        this.postDao = new PostDao();
    }

    //lay ra tat ca post
    public List<Post> getAllPosts() {
        return postDao.getAllPosts();
    }

    //lay theo id post
    public Optional<Post> getPostById(int id) {
        return postDao.getPostById(id);
    }

    //add post
    public void addPost(Post post, int userId) {
        // Kiểm tra quyền PRODUCT_MANAGEMENT
        if (!userService.hasPermission(userId, "POST_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thực hiện thao tác này.");
        }
        postDao.addPost(post, userId);
    }

    //delete post
    public void deletePost(int postId, int userId) {
        // Kiểm tra quyền PRODUCT_MANAGEMENT
        if (!userService.hasPermission(userId, "POST_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thực hiện thao tác này.");
        }
        postDao.deletePost(postId, userId);
    }

    //edit post
    public void updatePost(Post post, int userId) {
        // Kiểm tra quyền PRODUCT_MANAGEMENT
        if (!userService.hasPermission(userId, "POST_MANAGEMENT")) {
            throw new RuntimeException("Bạn không có quyền thực hiện thao tác này.");
        }
        postDao.updatePost(post, userId);
    }

    //search posst
    public List<Post> searchPosts(String keyword) {
        return postDao.searchPosts(keyword);
    }
}