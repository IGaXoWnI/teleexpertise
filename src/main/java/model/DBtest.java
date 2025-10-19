package model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;


public class DBtest {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    private static synchronized void initializeConnection() {
        if (emf == null) {
            try {
                emf = Persistence.createEntityManagerFactory("default");
                em = emf.createEntityManager();
            } catch (Exception e) {
                throw new RuntimeException("Cannot initialize database connection", e);
            }
        }
    }
    
    public static boolean testConnection() {
        try {
            initializeConnection();
            em.getTransaction().begin();
            Query query = em.createNativeQuery("SELECT 1");
            query.getSingleResult();
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }
    
    public static void createSchema() {
        try {
            initializeConnection();
            // Force Hibernate to create tables by accessing metadata
            em.getMetamodel().getEntities().forEach(entityType -> {
                // no debug output
            });
            
            em.getTransaction().begin();
            em.createNativeQuery("SELECT 1").getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
    
    /**
     * Insert sample data for testing
     */
    public static void insertSampleData() {
        try {
            initializeConnection();
            em.getTransaction().begin();
            
            // Sample data will be inserted here if needed

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
    
    /**
     * Replace existing schema - drops all tables and recreates them
     */
    public static void replaceSchema() {
        try {
            initializeConnection();
            em.getTransaction().begin();
            
            // Drop all sequences and tables
            em.createNativeQuery("DROP SCHEMA public CASCADE").executeUpdate();
            em.createNativeQuery("CREATE SCHEMA public").executeUpdate();
            
            em.getTransaction().commit();

            // Close current EntityManager and recreate to force Hibernate to recreate tables
            if (em != null) em.close();
            if (emf != null) emf.close();

            // Reinitialize with create-drop to force table creation
            System.setProperty("hibernate.hbm2ddl.auto", "create");
            emf = Persistence.createEntityManagerFactory("default");
            em = emf.createEntityManager();
            
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
    
    /**
     * Clean database (for testing purposes)
     */
    public static void cleanDatabase() {
        try {
            initializeConnection();
            em.getTransaction().begin();
            
            em.createNativeQuery("TRUNCATE TABLE consultation CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE demande_expertise CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE dossier_medical CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE vital_signs CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE acte_technique CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE creneau CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE employee CASCADE").executeUpdate();
            em.createNativeQuery("TRUNCATE TABLE patient CASCADE").executeUpdate();
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
    
    /**
     * Run all migration tasks
     */
    public static void migrate() {
        if (testConnection()) {
            createSchema();
        } else {
            // no-op on failure
        }
    }
    
    /**
     * Main method for running migrations
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "test":
                    testConnection();
                    break;
                case "migrate":
                    migrate();
                    break;
                case "clean":
                    cleanDatabase();
                    break;
                case "sample":
                    insertSampleData();
                    break;
                case "replace":
                    replaceSchema();
                    break;
                default:
                    // usage info removed to avoid debug prints
            }
        } else {
            migrate();
        }
        
        // Close resources
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}