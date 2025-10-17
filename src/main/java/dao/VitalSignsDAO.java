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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(VitalSigns vitalSigns) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(vitalSigns);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(VitalSigns vitalSigns) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(vitalSigns));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
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

    public List<VitalSigns> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM VitalSigns", VitalSigns.class).getResultList();
        } finally {
            em.close();
        }
    }
}