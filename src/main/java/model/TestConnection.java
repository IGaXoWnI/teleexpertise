package model;

import dao.JPAUtil;
import jakarta.persistence.EntityManager;

public class TestConnection {
    public static void main(String[] args) {
        try {
            EntityManager em = JPAUtil.getEntityManager();

            // Test a simple query
            Long count = em.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult();

            em.close();
            // success: exit normally
            return;
        } catch (Exception e) {
            // On failure, attempt rollback if possible, then exit with non-zero status
            try {
                EntityManager em = JPAUtil.getEntityManager();
                if (em != null && em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                if (em != null && em.isOpen()) {
                    em.close();
                }
            } catch (Exception ignored) {
                // ignore secondary errors
            }
            System.exit(1);
        }
    }
}
