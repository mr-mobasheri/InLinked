package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dataAccess.FollowDAO;
import dataAccess.PostDAO;
import models.Comment;
import models.Post;
import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PostController extends Controller {
    PostDAO postDAO = new PostDAO();
    public String getFollowingsPosts(String username) {
        List<Post> posts = new ArrayList<>();
        FollowDAO followDAO = new FollowDAO();
        for (User user : followDAO.getFollowings(username)) {
            posts.addAll(postDAO.getPostsByUsername(user.getUsername()));
        }
        posts.sort(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return Long.compare(o1.getDateOfCreat(), o2.getDateOfCreat())*-1;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(posts);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String searchPost(String username) {
        List<Post> posts = new ArrayList<>();
        FollowDAO followDAO = new FollowDAO();
        for (User user : followDAO.getFollowings(username)) {
            posts.addAll(postDAO.getPostsByUsername(user.getUsername()));
        }
        posts.sort(new Comparator<Post>() {
            @Override
            public int compare(Post o1, Post o2) {
                return Long.compare(o1.getDateOfCreat(), o2.getDateOfCreat())*-1;
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(posts);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPosts(String username) {
        List<Post> posts = postDAO.getPostsByUsername(username);
        if(posts != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.writeValueAsString(posts);

            } catch (JsonProcessingException e) {
                return null;
            }
        }
        return null;
    }

    public void addPost(Post post, String username) {
        postDAO.addPost(post, username);
    }

    public boolean like(int id, String username) {
        return postDAO.likePost(id, username);
    }

    public boolean unlike(int id, String username) {
        return postDAO.unlikePost(id, username);
    }

    public boolean comment(int id, String text, String username) {
        return postDAO.comment(id, text, username);
    }

    public String getPostByID(int id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(postDAO.getPostByID(id));

        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String getCommentPostByID(int id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(postDAO.getCommentPostByID(id));

        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
