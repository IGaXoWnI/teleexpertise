package model;

import jakarta.persistence.*;
import model.enums.ConsultationStatus;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "consultation")
public class Consultation {

    @Id
    @SequenceGenerator(
            name = "consultation_sequence",
            sequenceName = "consultation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "consultation_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generaliste_id", nullable = false)
    private Generaliste generaliste;

    @Column(name = "date_consultation", nullable = false)
    private LocalDateTime dateConsultation;

    @Column(name = "motif", nullable = false)
    private String motif;

    @Column(name = "observations", columnDefinition = "TEXT", nullable = false)
    private String observations;

    @Enumerated(EnumType.STRING)
    @Column(name = "consultation_status", nullable = false)
    private ConsultationStatus consultationStatus;

    @Column(name = "diagnostic", columnDefinition = "TEXT")
    private String diagnostic;

    @Column(name = "traitement", columnDefinition = "TEXT")
    private String traitement;

    @Column(name = "cout", nullable = false)
    private Double cout;

    public Consultation() {}

    public Consultation(Long id, Patient patient, Generaliste generaliste,
                        LocalDateTime dateConsultation, String motif,
                        String observations, ConsultationStatus consultationStatus,
                        String diagnostic, String traitement, Double cout) {
        this.id = id;
        this.patient = patient;
        this.generaliste = generaliste;
        this.dateConsultation = dateConsultation;
        this.motif = motif;
        this.observations = observations;
        this.consultationStatus = consultationStatus;
        this.diagnostic = diagnostic;
        this.traitement = traitement;
        this.cout = cout;
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public Generaliste getGeneraliste() {
        return generaliste;
    }

    public LocalDateTime getDateConsultation() {
        return dateConsultation;
    }

    public String getMotif() {
        return motif;
    }

    public String getObservations() {
        return observations;
    }

    public ConsultationStatus getConsultationStatus() {
        return consultationStatus;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public String getTraitement() {
        return traitement;
    }

    public Double getCout() {
        return cout;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setGeneraliste(Generaliste generaliste) {
        this.generaliste = generaliste;
    }

    public void setDateConsultation(LocalDateTime dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public void setConsultationStatus(ConsultationStatus consultationStatus) {
        this.consultationStatus = consultationStatus;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }

    public void setCout(Double cout) {
        this.cout = cout;
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "id=" + id +
                ", patient=" + patient +
                ", generaliste=" + generaliste +
                ", dateConsultation=" + dateConsultation +
                ", motif='" + motif + '\'' +
                ", observations='" + observations + '\'' +
                ", consultationStatus=" + consultationStatus +
                ", diagnostic='" + diagnostic + '\'' +
                ", traitement='" + traitement + '\'' +
                ", cout=" + cout +
                '}';
    }
}