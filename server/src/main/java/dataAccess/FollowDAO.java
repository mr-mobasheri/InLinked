package dataAccess;

import models.Follow;
import models.Post;
import models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class FollowDAO extends DAO {
    public List<User> getFollowings(String username) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserDAO userDAO = new UserDAO();
            String hql = "FROM Follow f WHERE f.follower = :name";
            List<Follow> followings = session.createQuery(hql, Follow.class)
                    .setParameter("name", username)
                    .getResultList();
            List<User> users = new ArrayList<>();
            for (Follow follow : followings) {
                users.add(userDAO.getUserByUsername(follow.getFollowing()));
            }
            transaction.commit();
            return users;
        }
    }

    public List<User> getFollowers(String username) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            UserDAO userDAO = new UserDAO();
            String hql = "FROM Follow f WHERE f.following = :name";
            List<Follow> followings = session.createQuery(hql, Follow.class)
                    .setParameter("name", username)
                    .getResultList();
            List<User> users = new ArrayList<>();
            for (Follow follow : followings) {
                users.add(userDAO.getUserByUsername(follow.getFollowing()));
            }
            transaction.commit();
            return users;
        }
    }

    public void follow(String follower, String following) {
        Follow follow = new Follow();
        follow.setFollowing(following);
        follow.setFollower(follower);
        this.insert(follow);
    }

    public void unfollow(String follower, String following) {
        Follow follow = new Follow();
        follow.setFollowing(following);
        follow.setFollower(follower);
        this.delete(follow);
    }
}
