package dao;

import model.VitalSigns;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class VitalSignsDAO {

    public void save(VitalSigns vitalSigns) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(vitalSigns);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(VitalSigns vitalSigns) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(vitalSigns);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void delete(VitalSigns vitalSigns) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(vitalSigns);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public VitalSigns findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.find(VitalSigns.class, id);
        } finally {
            session.close();
        }
    }

    public List<VitalSigns> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM VitalSigns", VitalSigns.class).getResultList();
        } finally {
            session.close();
        }
    }
}