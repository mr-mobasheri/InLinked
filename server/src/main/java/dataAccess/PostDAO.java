package dataAccess;

import controllers.PostController;
import models.Comment;
import models.Follow;
import models.Post;
import models.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PostDAO extends DAO {
    public List<Post> getPostsByUsername(String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
                Hibernate.initialize(user.getPosts());
                for (Post post : user.getPosts()) {
                    Hibernate.initialize(post.getLikerUsername());
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

    public boolean likePost(int id, String username) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Post post = (Post) session.get(Post.class, id);
            if (post != null) {
                Hibernate.initialize(post.getLikerUsername());
                post.getLikerUsername().add(username);
                session.update(post);
                transaction.commit();
                return true;
            }
            transaction.commit();
            return false;
        }
    }

    public boolean unlikePost(int id, String username) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Post post = (Post) session.createQuery("FROM Post WHERE id = :id")
                    .setParameter("id", id)
                    .uniqueResult();
            if (post != null) {
                Hibernate.initialize(post.getLikerUsername());
                for (int i = 0; i < post.getLikerUsername().size(); i++) {
                    if (post.getLikerUsername().get(i).equals(username)) {
                        post.getLikerUsername().remove(i);
                    }
                }
                session.update(post);
                transaction.commit();
                return true;
            }
            transaction.commit();
            return false;
        }
    }

    public boolean comment(int id, String text, String username) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Post post = (Post) session.createQuery("FROM Post WHERE id = :id")
                    .setParameter("id", id)
                    .uniqueResult();
            User user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (post != null && user != null) {
                Comment comment = new Comment();
                comment.setPostID(id);
                comment.setText(text);
                comment.setSenderUsername(username);
                comment.setSenderProfilePath(user.getProfilePath());
                session.save(comment);
                transaction.commit();
                return true;
            }
            transaction.commit();
            return false;
        }
    }

    public Post getPostByID(int id) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Post post = (Post) session.createQuery("FROM Post WHERE id = :id")
                    .setParameter("id", id)
                    .uniqueResult();
            if (post != null) {
                Hibernate.initialize(post.getLikerUsername());
                transaction.commit();
                return post;
            }
            transaction.commit();
            return null;
        }
    }

    public List<Post> search(String word) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM Post p WHERE p.text LIKE :word";
            List<Post> posts = session.createQuery(hql).setParameter("word", "%" + word + "%").getResultList();
            for (Post post : posts) {
                Hibernate.initialize(post.getLikerUsername());
            }
            transaction.commit();
            return posts;
        }
    }

    public List<Comment> getCommentPostByID(int id) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            String hql = "FROM Comment c WHERE c.postID = :id";
            List<Comment> comments = session.createQuery(hql, Comment.class)
                    .setParameter("id", id)
                    .getResultList();
            transaction.commit();
            return  comments;
        }
    }
}
