package model;

import jakarta.persistence.*;
import model.enums.CreneauStatus;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "creneau")
public class Creneau {

    @Id
    @SequenceGenerator(
            name = "creneau_sequence",
            sequenceName = "creneau_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "creneau_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "date_heure_debut", nullable = false)
    private LocalDateTime dateHeureDebut;

    @Column(name = "date_heure_fin", nullable = false)
    private LocalDateTime dateHeureFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "creneau_status", nullable = false)
    private CreneauStatus creneauStatus;

    public Creneau() {}

    public Creneau(Long id, LocalDateTime dateHeureDebut, LocalDateTime dateHeureFin, CreneauStatus creneauStatus) {
        this.id = id;
        this.dateHeureDebut = dateHeureDebut;
        this.dateHeureFin = dateHeureFin;
        this.creneauStatus = creneauStatus;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateHeureDebut() {
        return dateHeureDebut;
    }

    public LocalDateTime getDateHeureFin() {
        return dateHeureFin;
    }

    public CreneauStatus getCreneauStatus() {
        return creneauStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDateHeureDebut(LocalDateTime dateHeureDebut) {
        this.dateHeureDebut = dateHeureDebut;
    }

    public void setDateHeureFin(LocalDateTime dateHeureFin) {
        this.dateHeureFin = dateHeureFin;
    }

    public void setCreneauStatus(CreneauStatus creneauStatus) {
        this.creneauStatus = creneauStatus;
    }

    @Override
    public String toString() {
        return "Creneau{" +
                "id=" + id +
                ", dateHeureDebut=" + dateHeureDebut +
                ", dateHeureFin=" + dateHeureFin +
                ", creneauStatus=" + creneauStatus +
                '}';
    }
}