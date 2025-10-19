package dao;

import jakarta.persistence.EntityManager;
import model.Patient;
import java.util.List;

public class PatientDAO {

    public void save(Patient patient) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(patient);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void update(Patient patient) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(patient);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Patient patient) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.merge(patient));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Patient findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Patient.class, id);
        } finally {
            em.close();
        }
    }

    public List<Patient> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Patient", Patient.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Patient findByPhone(String phone) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Patient p WHERE p.phoneNumber = :phone", Patient.class)
                    .setParameter("phone", phone)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public Patient findBySsn(String ssn) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Patient p WHERE p.socialSecurityNumber = :ssn", Patient.class)
                    .setParameter("ssn", ssn)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

    public List<Patient> searchPatients(String query) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("FROM Patient p WHERE LOWER(p.firstName) LIKE LOWER(:partialQuery) OR LOWER(p.lastName) LIKE LOWER(:partialQuery) OR p.socialSecurityNumber = :exactQuery OR p.phoneNumber LIKE :partialQuery", Patient.class)
                    .setParameter("partialQuery", "%" + query + "%")
                    .setParameter("exactQuery", query)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}