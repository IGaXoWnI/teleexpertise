package model;

import jakarta.persistence.*;
import model.enums.DemandePriorite;
import model.enums.DemandeStatus;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "demande_expertise")
public class DemandeExpertise {

    @Id
    @SequenceGenerator(
            name = "demande_expertise_sequence",
            sequenceName = "demande_expertise_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "demande_expertise_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "question", columnDefinition = "TEXT", nullable = false)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "demande_status", nullable = false)
    private DemandeStatus demandeStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "demande_priorite", nullable = false)
    private DemandePriorite demandePriorite;

    @Column(name = "date_demande", nullable = false)
    private LocalDateTime dateDemande;

    @Column(name = "avis_medecin", columnDefinition = "TEXT")
    private String avisMedecin;

    @Column(name = "recommandations", columnDefinition = "TEXT")
    private String recommandations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialiste_id", nullable = false)
    private Specialiste specialiste;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    public DemandeExpertise() {}

    public DemandeExpertise(Long id, String question, DemandeStatus demandeStatus,
                            DemandePriorite demandePriorite, LocalDateTime dateDemande,
                            String avisMedecin, String recommandations,
                            Specialiste specialiste, Consultation consultation) {
        this.id = id;
        this.question = question;
        this.demandeStatus = demandeStatus;
        this.demandePriorite = demandePriorite;
        this.dateDemande = dateDemande;
        this.avisMedecin = avisMedecin;
        this.recommandations = recommandations;
        this.specialiste = specialiste;
        this.consultation = consultation;
    }


    public Long getId() { return id; }
    public String getQuestion() { return question; }
    public DemandeStatus getDemandeStatus() { return demandeStatus; }
    public DemandePriorite getDemandePriorite() { return demandePriorite; }
    public LocalDateTime getDateDemande() { return dateDemande; }
    public String getAvisMedecin() { return avisMedecin; }
    public String getRecommandations() { return recommandations; }
    public Specialiste getSpecialiste() { return specialiste; }
    public Consultation getConsultation() { return consultation; }

    public void setId(Long id) { this.id = id; }
    public void setQuestion(String question) { this.question = question; }
    public void setDemandeStatus(DemandeStatus demandeStatus) { this.demandeStatus = demandeStatus; }
    public void setDemandePriorite(DemandePriorite demandePriorite) { this.demandePriorite = demandePriorite; }
    public void setDateDemande(LocalDateTime dateDemande) { this.dateDemande = dateDemande; }
    public void setAvisMedecin(String avisMedecin) { this.avisMedecin = avisMedecin; }
    public void setRecommandations(String recommandations) { this.recommandations = recommandations; }
    public void setSpecialiste(Specialiste specialiste) { this.specialiste = specialiste; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }

    @Override
    public String toString() {
        return "DemandeExpertise{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", demandeStatus=" + demandeStatus +
                ", demandePriorite=" + demandePriorite +
                ", dateDemande=" + dateDemande +
                ", avisMedecin='" + avisMedecin + '\'' +
                ", recommandations='" + recommandations + '\'' +
                ", specialiste=" + (specialiste != null ? specialiste.getId() : null) +
                ", consultation=" + (consultation != null ? consultation.getId() : null) +
                '}';
    }
}