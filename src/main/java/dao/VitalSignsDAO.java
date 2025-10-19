package dao;

import jakarta.persistence.EntityManager;
import model.VitalSigns;
import java.util.List;

public class VitalSignsDAO {

    public void save(VitalSigns vitalSigns) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(vitalSigns);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public VitalSigns findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(VitalSigns.class, id);
        } finally {
            em.close();
        }
    }

    public List<VitalSigns> findByPatientId(Long patientId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM VitalSigns v WHERE v.patient.id = :patientId ORDER BY v.dateMesure DESC", VitalSigns.class)
                    .setParameter("patientId", patientId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<VitalSigns> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM VitalSigns", VitalSigns.class).getResultList();
        } finally {
            em.close();
        }
    }
}