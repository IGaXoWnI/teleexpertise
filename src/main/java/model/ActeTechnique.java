package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "acte_technique")
public class ActeTechnique {

    @Id
    @SequenceGenerator(
            name = "acte_technique_sequence",
            sequenceName = "acte_technique_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "acte_technique_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "type_acte", nullable = false)
    private String typeActe;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "resultat", columnDefinition = "TEXT")
    private String resultat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    public ActeTechnique() {}

    public ActeTechnique(Long id, String typeActe, LocalDateTime date, String resultat, Consultation consultation) {
        this.id = id;
        this.typeActe = typeActe;
        this.date = date;
        this.resultat = resultat;
        this.consultation = consultation;
    }

    public Long getId() {
        return id;
    }

    public String getTypeActe() {
        return typeActe;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getResultat() {
        return resultat;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTypeActe(String typeActe) {
        this.typeActe = typeActe;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

    @Override
    public String toString() {
        return "ActeTechnique{" +
                "id=" + id +
                ", typeActe='" + typeActe + '\'' +
                ", date=" + date +
                ", resultat='" + resultat + '\'' +
                ", consultation=" + (consultation != null ? consultation.getId() : null) +
                '}';
    }
}