package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.FollowDAO;
import models.User;

import java.util.List;

public class FollowController extends Controller {
    FollowDAO followDAO = new FollowDAO();

    public void follow(String follower, String following) {
        followDAO.follow(follower, following);
    }

    public void unfollow(String follower, String following) {
        followDAO.unfollow(follower, following);
    }

    public String getFollowing(String username) {
        List<User> users = followDAO.getFollowings(username);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String getFollower(String username) {
        List<User> users = followDAO.getFollowings(username);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}
