package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.FollowDAO;
import dataAccess.PostDAO;
import models.Post;
import models.User;

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
                return Long.compare(o1.getDateOfCreat(), o2.getDateOfCreat());
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(posts);

        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
