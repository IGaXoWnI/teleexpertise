package model;

import jakarta.persistence.*;
import model.enums.Role;

@Entity
public class Generaliste extends Employee {

    public Generaliste() {
        super();
    }

    public Generaliste(String nom, String prenom, String email, String password) {
        super(null, nom, prenom, email, password, Role.GENERALISTE);
    }



}