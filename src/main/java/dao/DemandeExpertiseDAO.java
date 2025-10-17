package dao;

import jakarta.persistence.EntityManager;
import model.DemandeExpertise;
import java.util.List;

public class DemandeExpertiseDAO {

    public void save(DemandeExpertise demandeExpertise) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(demandeExpertise);
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

    public void update(DemandeExpertise demandeExpertise) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(demandeExpertise);
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

    public void delete(DemandeExpertise demandeExpertise) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(demandeExpertise));
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

    public DemandeExpertise findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(DemandeExpertise.class, id);
        } finally {
            em.close();
        }
    }

    public List<DemandeExpertise> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM DemandeExpertise", DemandeExpertise.class).getResultList();
        } finally {
            em.close();
        }
    }
}