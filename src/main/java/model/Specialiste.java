package model;

import jakarta.persistence.*;
import model.enums.Role;
import model.enums.Specialite;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Specialiste extends Employee {

    @Enumerated(EnumType.STRING)
    @Column(name = "specialite", nullable = false)
    private Specialite specialite;

    @Column(name = "tarif", nullable = false)
    private Double tarif;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "specialiste_id")
    private List<Creneau> creneaux = new ArrayList<>();

    public Specialiste() {
        super();
    }

    public Specialiste(String nom, String prenom, String email, String password, Specialite specialite, Double tarif) {
        super(null, nom, prenom, email, password, Role.SPECIALISTE);
        this.specialite = specialite;
        this.tarif = tarif;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public Double getTarif() {
        return tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }

    public List<Creneau> getCreneaux() {
        return creneaux;
    }

    public void setCreneaux(List<Creneau> creneaux) {
        this.creneaux = creneaux;
    }

    public void addCreneau(Creneau creneau) {
        this.creneaux.add(creneau);
    }

    @Override
    public String toString() {
        return "Specialiste{" + super.toString() +
                ", specialite=" + specialite +
                ", tarif=" + tarif +
                '}';
    }
}