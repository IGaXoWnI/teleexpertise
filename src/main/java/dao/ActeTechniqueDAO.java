package dao;

import model.ActeTechnique;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ActeTechniqueDAO {

    public void save(ActeTechnique acteTechnique) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(acteTechnique);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(ActeTechnique acteTechnique) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(acteTechnique);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void delete(ActeTechnique acteTechnique) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(acteTechnique);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public ActeTechnique findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.find(ActeTechnique.class, id);
        } finally {
            session.close();
        }
    }

    public List<ActeTechnique> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM ActeTechnique", ActeTechnique.class).getResultList();
        } finally {
            session.close();
        }
    }
}