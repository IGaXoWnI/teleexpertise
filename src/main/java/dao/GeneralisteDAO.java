package dao;

import jakarta.persistence.EntityManager;
import model.Generaliste;
import java.util.List;

public class GeneralisteDAO {

    public void save(Generaliste generaliste) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(generaliste);
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

    public void update(Generaliste generaliste) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(generaliste);
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

    public void delete(Generaliste generaliste) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(generaliste));
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

    public Generaliste findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Generaliste.class, id);
        } finally {
            em.close();
        }
    }

    public List<Generaliste> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Generaliste", Generaliste.class).getResultList();
        } finally {
            em.close();
        }
    }
}