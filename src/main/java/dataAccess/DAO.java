package dataAccess;

import models.Message;
import models.Model;
import models.Post;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import utils.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;


public class DAO {
    private Configuration cfg;

    public DAO() {
        try {
            cfg = new Configuration().configure(DAO.class.getClassLoader().getResource("hibernate.cfg.xml"));
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public void insert(Model model) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(model);
            transaction.commit();
        }
    }

    public User getUser(String username) {
        User user = null;
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            transaction.commit();
        }
        return user;
    }

    public List<Message> getSentMessages(String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
//            Initialize the posts collection if it's lazy-loaded
                Hibernate.initialize(user.getSentMessages());
                transaction.commit();
                return user.getSentMessages();
            }
            transaction.commit();
            return null;
        }
    }

    public List<Message> getMessagesBetweenUsers(String senderUsername, String receiverUsername) {
        if (!isUserExists(senderUsername) || !isUserExists(receiverUsername)) {
            return null;
        }
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "FROM Message m WHERE m.sender.username = :senderUsername AND m.receiver.username = :receiverUsername";
            Query<Message> query = session.createQuery(hql, Message.class);
            query.setParameter("senderUsername", senderUsername);
            query.setParameter("receiverUsername", receiverUsername);
            transaction.commit();
            return query.getResultList();
        }
    }

    public List<Message> getReceivedMessages(String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
//            Initialize the posts collection if it's lazy-loaded
                Hibernate.initialize(user.getReceivedMessages());
                transaction.commit();
                return user.getReceivedMessages();
            }
            transaction.commit();
            return null;
        }
    }


    public void delete(Model model) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(model);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(Model model) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            if (model != null) {
                session.update(model);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = null;
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            users = (ArrayList<User>) session.createQuery("from User", User.class).getResultList();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    public ArrayList<Message> getAllMessages() {
        ArrayList<Message> messages = null;
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            messages = (ArrayList<Message>) session.createQuery("from Message", Message.class).getResultList();

            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return messages;
    }

    public void deleteAllUsers() {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS USER").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void deleteAllMessages() {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery("DROP TABLE IF EXISTS MESSAGE").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Post> getPostsByUsername(String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
//            Initialize the posts collection if it's lazy-loaded
                Hibernate.initialize(user.getPosts());
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

    public void addMessage(Message message, String senderUsername, String receiverUsername) throws UserNotFoundException {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User sender = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", senderUsername)
                    .uniqueResult();

            User receiver = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", receiverUsername)
                    .uniqueResult();
            if (sender == null || receiver == null) {
                throw new UserNotFoundException();
            }
            message.setSender(sender);
            message.setReceiver(receiver);
            sender.getSentMessages().add(message);
            receiver.getReceivedMessages().add(message);
            session.update(sender);
            session.update(receiver);
            transaction.commit();
        }
    }

    public boolean isUserExists(String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            String hql = "SELECT count(u.id) FROM User u WHERE u.username = :username";
            Long count = (Long) session.createQuery("SELECT count(u.id) FROM User u WHERE u.username = :username")
                    .setParameter("username", username).uniqueResult();
            return count > 0;
        }
    }

}
