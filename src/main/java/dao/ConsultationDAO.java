package dao;

import model.Consultation;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ConsultationDAO {

    public void save(Consultation consultation) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(consultation);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(Consultation consultation) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(consultation);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void delete(Consultation consultation) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(consultation);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Consultation findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.find(Consultation.class, id);
        } finally {
            session.close();
        }
    }

    public List<Consultation> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM Consultation", Consultation.class).getResultList();
        } finally {
            session.close();
        }
    }
}