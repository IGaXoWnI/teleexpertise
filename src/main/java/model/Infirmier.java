package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import model.enums.Role;

@Entity
public class Infirmier extends Employee
{

    public Infirmier() {
        super();
        this.setRole(Role.INFIRMIER);
    }

    public Infirmier(Long id, String nom, String prenom, String email, String password) {
        super(null, nom, prenom, email, password, Role.INFIRMIER);
    }

    @Override
    public String toString() {
        return "Infirmier{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", role=" + getRole() +
                '}';
    }
}