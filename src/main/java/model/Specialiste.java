package model;

import jakarta.persistence.*;
import model.enums.Role;
import model.enums.Specialite;

@Entity
public class Specialiste extends Employee {

    @Enumerated(EnumType.STRING)
    @Column(name = "specialite", nullable = false)
    private Specialite specialite;

    public Specialiste() {
        super();
    }

    public Specialiste(String nom, String prenom, String email, String password , Specialite specialite) {
        super(null, nom, prenom, email, password, Role.SPECIALISTE);
        this.specialite = specialite ;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    @Override
    public String toString() {
        return "Specialiste{" + super.toString() +
                ", specialite=" + specialite +
                '}';
    }
}