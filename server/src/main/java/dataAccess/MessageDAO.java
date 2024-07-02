package dataAccess;

import models.Message;
import models.User;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MessageDAO extends DAO {
    public List<Message> getSentMessages(String username) {
        try (Session session = cfg.buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = (User) session.createQuery("FROM User WHERE username = :username")
                    .setParameter("username", username)
                    .uniqueResult();
            if (user != null) {
//            Initialize the sentMessages collection if it's lazy-loaded
                Hibernate.initialize(user.getSentMessages());
                transaction.commit();
                return user.getSentMessages();
            }
            transaction.commit();
            return null;
        }
    }

    public List<Message> getMessagesBetweenUsers(String senderUsername, String receiverUsername) {
        UserDAO userDAO = new UserDAO();
        if (!userDAO.isUserExists(senderUsername) || !userDAO.isUserExists(receiverUsername)) {
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
//            Initialize the receivedMessages collection if it's lazy-loaded
                Hibernate.initialize(user.getReceivedMessages());
                transaction.commit();
                return user.getReceivedMessages();
            }
            transaction.commit();
            return null;
        }
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
}
