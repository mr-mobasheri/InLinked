package dataAccess;

import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO {
    public User getUserByEmail(String email) {
        User user = null;
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user = (User) session.createQuery("FROM User WHERE email = :email")
                    .setParameter("email", email)
                    .uniqueResult();
            transaction.commit();
        }
        return user;
    }

    public User getUserByUsername(String username) {
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

    public boolean isUserExists(String email) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Long count = (Long) session.createQuery("SELECT count(u.id) FROM User u WHERE u.email = :email")
                    .setParameter("email", email).uniqueResult();
            return count > 0;
        }
    }

    public boolean isUsernameExists(String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Long count = (Long) session.createQuery("SELECT count(u.id) FROM User u WHERE u.username = :username")
                    .setParameter("username", username).uniqueResult();
            return count > 0;
        }
    }

    public List<User> searchUsers(String word){
        try (Session session = cfg.buildSessionFactory().openSession()) {
            String hql = "FROM User WHERE firstName LIKE :searchTerm OR username LIKE :searchTerm OR email LIKE :searchTerm";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("searchTerm", "%" + word + "%");
            return query.getResultList();
        }
    }

}
