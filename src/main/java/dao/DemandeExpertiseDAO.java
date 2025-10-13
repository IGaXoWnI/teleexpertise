package dao;

import model.DemandeExpertise;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class DemandeExpertiseDAO {

    public void save(DemandeExpertise demandeExpertise) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(demandeExpertise);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(DemandeExpertise demandeExpertise) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(demandeExpertise);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void delete(DemandeExpertise demandeExpertise) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(demandeExpertise);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public DemandeExpertise findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.find(DemandeExpertise.class, id);
        } finally {
            session.close();
        }
    }

    public List<DemandeExpertise> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM DemandeExpertise", DemandeExpertise.class).getResultList();
        } finally {
            session.close();
        }
    }
}