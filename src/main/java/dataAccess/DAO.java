package dataAccess;

import models.Message;
import models.Model;
import models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import java.util.ArrayList;
import java.util.Formatter;

public class DAO {
    private Configuration cfg;

    public DAO() {
        try {
            cfg = new Configuration().configure(DAO.class.getClassLoader().getResource("hibernate.cfg.xml"));
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


//    public static void main(String[] args) {
//        User user = new User();
//        user.setUsername("mobash");
//        insertUser(user);
//        Message message = new Message("hi this is a message just for Me!");
//        insertUser(message);
//        ArrayList<User> users = getAllUsers();
//        for (User user1 : users) {
//            System.out.println(user1.getUsername());
//        }
//        for (Message msg : getAllMessages()) {
//            System.out.println(msg.getText());
//        }
//    }

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
            String hql = "FROM User WHERE username = :username";
            Query query = session.createQuery(hql);
            query.setParameter("username", username);
            user = (User) query.uniqueResult();
            transaction.commit();
        }
        return user;
    }

    public void delete(Model model) {
        Transaction transaction = null;
        try (Session session = cfg.buildSessionFactory().getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete((User) model);
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


}