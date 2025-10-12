package model;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DBInit {
    public static void main(String[] args) {
        try {
            // "jeePU" matches your persistence.xml
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
            emf.close();
            System.out.println("✅ Tables created successfully in PostgreSQL!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Table creation failed!");
        }
    }
}