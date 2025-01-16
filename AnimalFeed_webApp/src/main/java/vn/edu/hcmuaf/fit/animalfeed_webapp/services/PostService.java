package vn.edu.hcmuaf.fit.animalfeed_webapp.services;

import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.PostDao;
import vn.edu.hcmuaf.fit.animalfeed_webapp.dao.model.Post;

import java.util.List;
import java.util.Optional;

public class PostService {
    private final PostDao postDao;

    public PostService() {
        this.postDao = new PostDao();
    }

    public List<Post> getAllPosts() {
        return postDao.getAllPosts();
    }

    public Optional<Post> getPostById(int id) {
        return postDao.getPostById(id);
    }
    public boolean addPost(Post post, int adminUserId) {
        PostDao postDao = new PostDao();
        try {
            postDao.addPost(post, adminUserId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}