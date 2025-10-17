package dao;

import jakarta.persistence.EntityManager;
import model.Specialiste;
import java.util.List;

public class SpecialisteDAO {

    public void save(Specialiste specialiste) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(specialiste);
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

    public void update(Specialiste specialiste) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(specialiste);
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

    public void delete(Specialiste specialiste) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(specialiste));
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

    public Specialiste findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Specialiste.class, id);
        } finally {
            em.close();
        }
    }

    public List<Specialiste> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Specialiste", Specialiste.class).getResultList();
        } finally {
            em.close();
        }
    }
}