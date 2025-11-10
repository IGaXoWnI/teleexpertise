package dao;

import jakarta.persistence.EntityManager;
import model.Consultation;
import java.util.List;

public class ConsultationDAO {

    public void save(Consultation consultation) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(consultation);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Consultation findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Consultation.class, id);
        } finally {
            em.close();
        }
    }

    public List<Consultation> findByPatientId(Long patientId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Consultation c WHERE c.patient.id = :patientId ORDER BY c.dateConsultation DESC", Consultation.class)
                    .setParameter("patientId", patientId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Consultation> findByGeneralisteId(Long generalisteId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Consultation c WHERE c.generaliste.id = :generalisteId ORDER BY c.dateConsultation DESC", Consultation.class)
                    .setParameter("generalisteId", generalisteId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Consultation> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Consultation ORDER BY dateConsultation DESC", Consultation.class).getResultList();
        } finally {
            em.close();
        }
    }
}
