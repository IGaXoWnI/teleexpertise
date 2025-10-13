package dao;

import model.Generaliste;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class GeneralisteDAO {

    public void save(Generaliste generaliste) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(generaliste);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(Generaliste generaliste) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(generaliste);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void delete(Generaliste generaliste) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(generaliste);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Generaliste findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.find(Generaliste.class, id);
        } finally {
            session.close();
        }
    }

    public List<Generaliste> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Generaliste", Generaliste.class).getResultList();
        } finally {
            session.close();
        }
    }
}