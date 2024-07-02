package dataAccess;

import models.Post;
import models.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PostDAO extends DAO {
    public List<Post> getPostsByUsername(String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
//            Initialize the posts collection if it's lazy-loaded
                Hibernate.initialize(user.getPosts());
                for (Post post : user.getPosts()) {
                    Hibernate.initialize(post.getMediaPaths());
                }
                transaction.commit();
                return user.getPosts();
            }
            transaction.commit();
            return null;
        }
    }

    public void addPost(Post post, String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
//            Initialize the posts collection if it's lazy-loaded
                post.setSender(user);
                user.getPosts().add(post);
                session.update(user);
            }
            transaction.commit();
        }
    }
}
