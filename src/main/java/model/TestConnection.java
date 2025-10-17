package model;

import dao.JPAUtil;
import jakarta.persistence.EntityManager;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        try {
            EntityManager em = JPAUtil.getEntityManager();
            System.out.println("✓ EntityManager created successfully");

            // Test a simple query
            Long count = em.createQuery("SELECT COUNT(e) FROM Employee e", Long.class).getSingleResult();
            System.out.println("✓ Query executed successfully");
            System.out.println("✓ Employee count: " + count);

            em.close();
            System.out.println("✓ Connection test PASSED");
        } catch (Exception e) {
            System.err.println("✗ Connection test FAILED");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

