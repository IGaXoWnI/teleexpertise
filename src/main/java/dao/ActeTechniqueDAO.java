package dao;

import jakarta.persistence.EntityManager;
import model.ActeTechnique;
import java.util.List;

public class ActeTechniqueDAO {

    public void save(ActeTechnique acteTechnique) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(acteTechnique);
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

    public void update(ActeTechnique acteTechnique) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(acteTechnique);
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

    public void delete(ActeTechnique acteTechnique) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(acteTechnique));
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

    public ActeTechnique findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(ActeTechnique.class, id);
        } finally {
            em.close();
        }
    }

    public List<ActeTechnique> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM ActeTechnique", ActeTechnique.class).getResultList();
        } finally {
            em.close();
        }
    }
}