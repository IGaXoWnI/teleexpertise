package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static volatile EntityManagerFactory emf;

    private static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("default");
            } catch (Exception e) {
                System.err.println("Failed to create EntityManagerFactory: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Cannot connect to database", e);
            }
        }
        return emf;
    }

    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
    
    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}