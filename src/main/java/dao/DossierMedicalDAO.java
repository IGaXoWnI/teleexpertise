package dao;

import jakarta.persistence.EntityManager;
import model.DossierMedical;
import java.util.List;

public class DossierMedicalDAO {

    public void save(DossierMedical dossierMedical) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(dossierMedical);
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

    public void update(DossierMedical dossierMedical) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(dossierMedical);
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

    public void delete(DossierMedical dossierMedical) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(dossierMedical));
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

    public DossierMedical findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(DossierMedical.class, id);
        } finally {
            em.close();
        }
    }

    public List<DossierMedical> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM DossierMedical", DossierMedical.class).getResultList();
        } finally {
            em.close();
        }
    }
}