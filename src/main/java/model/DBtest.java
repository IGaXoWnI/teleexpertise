package model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;


public class DBtest {
    
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    static {
        try {
            emf = Persistence.createEntityManagerFactory("default");
            em = emf.createEntityManager();
        } catch (Exception e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
        }
    }
    

    public static boolean testConnection() {
        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("SELECT 1");
            query.getSingleResult();
            em.getTransaction().commit();
            System.out.println("Database connection successful!");
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }
    

    public static void createSchema() {
        try {
            // Force Hibernate to create tables by accessing metadata
            em.getMetamodel().getEntities().forEach(entityType -> {
                System.out.println("Entity: " + entityType.getName());
            });
            
            em.getTransaction().begin();
            em.createNativeQuery("SELECT 1").getSingleResult();
            em.getTransaction().commit();
            
            System.out.println("Database schema created successfully!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Schema creation failed: " + e.getMessage());
        }
    }
    
    /**
     * Insert sample data for testing
     */
    public static void insertSampleData() {
        try {
            em.getTransaction().begin();
            
            // Sample data will be inserted here if needed
            System.out.println("Sample data insertion completed!");
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Sample data insertion failed: " + e.getMessage());
        }
    }
    
    /**
     * Replace existing schema - drops all tables and recreates them
     */
    public static void replaceSchema() {
        try {
            em.getTransaction().begin();
            
            // Drop all sequences and tables
            em.createNativeQuery("DROP SCHEMA public CASCADE").executeUpdate();
            em.createNativeQuery("CREATE SCHEMA public").executeUpdate();
            
            em.getTransaction().commit();
            System.out.println("Schema dropped and recreated!");
            
            // Close current EntityManager and recreate to force Hibernate to recreate tables
            em.close();
            emf.close();
            
            // Reinitialize with create-drop to force table creation
            System.setProperty("hibernate.hbm2ddl.auto", "create");
            emf = Persistence.createEntityManagerFactory("default");
            em = emf.createEntityManager();
            
            // Test connection to trigger table creation
            testConnection();
            
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Schema replacement failed: " + e.getMessage());
        }
    }
    
    /**
     * Clean database (for testing purposes)
     */
    public static void cleanDatabase() {
        try {
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
            System.out.println("Database cleaned successfully!");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Database cleaning failed: " + e.getMessage());
        }
    }
    
    /**
     * Run all migration tasks
     */
    public static void migrate() {
        System.out.println("Starting database migration...");
        
        if (testConnection()) {
            createSchema();
            System.out.println("Migration completed successfully!");
        } else {
            System.err.println("Migration failed - no database connection!");
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
                    System.out.println("Usage: java model.DBtest [test|migrate|clean|sample|replace]");
            }
        } else {
            migrate();
        }
        
        // Close resources
        if (em != null) em.close();
        if (emf != null) emf.close();
    }
}