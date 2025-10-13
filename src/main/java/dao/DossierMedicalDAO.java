package dao;

import model.DossierMedical;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class DossierMedicalDAO {

    public void save(DossierMedical dossierMedical) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(dossierMedical);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void update(DossierMedical dossierMedical) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(dossierMedical);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void delete(DossierMedical dossierMedical) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.remove(dossierMedical);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public DossierMedical findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.find(DossierMedical.class, id);
        } finally {
            session.close();
        }
    }

    public List<DossierMedical> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("FROM DossierMedical", DossierMedical.class).getResultList();
        } finally {
            session.close();
        }
    }
}