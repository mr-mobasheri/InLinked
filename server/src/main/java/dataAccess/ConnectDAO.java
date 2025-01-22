package dataAccess;

import models.Connect;
import models.Post;
import models.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class ConnectDAO extends DAO {
    public List<User> getConnectRequests(String username) {
        Transaction transaction = null;
        UserDAO userDAO = new UserDAO();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Connect> connects = session.createQuery("FROM Connect c WHERE c.secondUsername = :username AND c.isConnected IS FALSE", Connect.class)
                    .setParameter("username", username)
                    .getResultList();
            List<User> users = new ArrayList<>();
            for (Connect connect : connects) {
                users.add(userDAO.getUserByUsername(connect.getFirstUsername()));
            }
            transaction.commit();
            return users;
        }
    }

    public List<User> getMyRequests(String username) {
        Transaction transaction = null;
        UserDAO userDAO = new UserDAO();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Connect> connects = session.createQuery("FROM Connect c WHERE c.firstUsername = :username AND c.isConnected IS FALSE", Connect.class)
                    .setParameter("username", username)
                    .getResultList();
            List<User> users = new ArrayList<>();
            for (Connect connect : connects) {
                users.add(userDAO.getUserByUsername(connect.getSecondUsername()));
            }
            transaction.commit();
            return users;
        }
    }

    public void requestToConnect(String myUsername, String conUsername) {
        Connect connect = new Connect();
        connect.setFirstUsername(myUsername);
        connect.setSecondUsername(conUsername);
        this.insert(connect);
    }

    public List<User> getConnects(String myUsername) {
        Transaction transaction = null;
        UserDAO userDAO = new UserDAO();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Connect> connects = session.createQuery("FROM Connect c WHERE c.firstUsername = :me AND c.isConnected IS TRUE", Connect.class)
                    .setParameter("me", myUsername)
                    .getResultList();
            List<User> users = new ArrayList<>();
            for (Connect connect : connects) {
                users.add(userDAO.getUserByUsername(connect.getSecondUsername()));
            }
            connects = session.createQuery("FROM Connect c WHERE c.secondUsername = :me AND c.isConnected IS TRUE", Connect.class)
                    .setParameter("me", myUsername)
                    .getResultList();
            for (Connect connect : connects) {
                users.add(userDAO.getUserByUsername(connect.getFirstUsername()));
            }
            transaction.commit();
            return users;
        }
    }

    public void accept(String myUsername, String conUsername) {
        Transaction transaction = null;
        UserDAO userDAO = new UserDAO();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Connect connect = session.createQuery("FROM Connect c WHERE c.secondUsername = :me AND c.firstUsername = :con AND c.isConnected IS FALSE", Connect.class)
                    .setParameter("me", myUsername).setParameter("con", conUsername)
                    .uniqueResult();
            connect.setConnected(true);
            session.update(connect);
            transaction.commit();
        }
    }

    public void reject(String myUsername, String conUsername) {
        Transaction transaction = null;
        UserDAO userDAO = new UserDAO();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Connect connect = session.createQuery("FROM Connect c WHERE c.secondUsername = :me AND c.firstUsername = :con AND c.isConnected IS FALSE", Connect.class)
                    .setParameter("me", myUsername).setParameter("con", conUsername)
                    .uniqueResult();
            session.delete(connect);
            transaction.commit();
        }
    }

    public void deleteRequest(String myUsername, String conUsername) {
        Transaction transaction = null;
        UserDAO userDAO = new UserDAO();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Connect connect = session.createQuery("FROM Connect c WHERE c.firstUsername = :me AND c.secondUsername = :con AND c.isConnected IS FALSE", Connect.class)
                    .setParameter("me", myUsername).setParameter("con", conUsername)
                    .uniqueResult();
            if(connect != null) {
                session.delete(connect);
            }
            transaction.commit();
        }
    }

    public void disConnect(String myUsername, String conUsername) {
        Transaction transaction = null;
        UserDAO userDAO = new UserDAO();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Connect connect = session.createQuery("FROM Connect c WHERE c.secondUsername = :me AND c.firstUsername = :con AND c.isConnected IS TRUE", Connect.class)
                    .setParameter("me", myUsername).setParameter("con", conUsername)
                    .uniqueResult();
            if (connect != null)
                session.delete(connect);
            else {
                connect = session.createQuery("FROM Connect c WHERE c.firstUsername = :me AND c.secondUsername = :con AND c.isConnected IS TRUE", Connect.class)
                        .setParameter("me", myUsername).setParameter("con", conUsername)
                        .uniqueResult();
                session.delete(connect);
            }
            transaction.commit();
        }
    }

    public int isConnected(String myUsername, String conUsername) {
        Transaction transaction = null;
        UserDAO userDAO = new UserDAO();
        try (Session session = cfg.buildSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Connect connect = session.createQuery("FROM Connect c WHERE c.secondUsername = :me AND c.firstUsername = :con AND c.isConnected IS TRUE", Connect.class)
                    .setParameter("me", myUsername).setParameter("con", conUsername)
                    .uniqueResult();
            if (connect != null){
                return 1;
            }
            connect = session.createQuery("FROM Connect c WHERE c.firstUsername = :me AND c.secondUsername = :con AND c.isConnected IS TRUE", Connect.class)
                    .setParameter("me", myUsername).setParameter("con", conUsername)
                    .uniqueResult();
            if (connect != null) {
                return 1;
            }
            connect = session.createQuery("FROM Connect c WHERE c.firstUsername = :me AND c.secondUsername = :con AND c.isConnected IS FALSE", Connect.class)
                    .setParameter("me", myUsername).setParameter("con", conUsername)
                    .uniqueResult();
            if(connect != null) {
                return 2;
            }
            connect = session.createQuery("FROM Connect c WHERE c.secondUsername = :me AND c.firstUsername = :con AND c.isConnected IS FALSE", Connect.class)
                    .setParameter("me", myUsername).setParameter("con", conUsername)
                    .uniqueResult();
            if(connect != null) {
                return 3;
            }
            return  0;
        }
    }
}
