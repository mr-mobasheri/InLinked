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
    protected Configuration cfg;

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

}
