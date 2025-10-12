package model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "dossier_medical")
public class DossierMedical {

    @Id
    @SequenceGenerator(
            name = "dossier_medical_sequence",
            sequenceName = "dossier_medical_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "dossier_medical_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "antecedents", columnDefinition = "TEXT")
    private String antecedents;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "traitements_en_cours", columnDefinition = "TEXT")
    private String traitementsEnCours;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private Patient patient;

    public DossierMedical() {}

    public DossierMedical(Long id, String antecedents, String allergies, String traitementsEnCours, Patient patient) {
        this.id = id;
        this.antecedents = antecedents;
        this.allergies = allergies;
        this.traitementsEnCours = traitementsEnCours;
        this.patient = patient;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getAntecedents() { return antecedents; }
    public String getAllergies() { return allergies; }
    public String getTraitementsEnCours() { return traitementsEnCours; }
    public Patient getPatient() { return patient; }

    public void setId(Long id) { this.id = id; }
    public void setAntecedents(String antecedents) { this.antecedents = antecedents; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public void setTraitementsEnCours(String traitementsEnCours) { this.traitementsEnCours = traitementsEnCours; }
    public void setPatient(Patient patient) { this.patient = patient; }

    @Override
    public String toString() {
        return "DossierMedical{" +
                "id=" + id +
                ", antecedents='" + antecedents + '\'' +
                ", allergies='" + allergies + '\'' +
                ", traitementsEnCours='" + traitementsEnCours + '\'' +
                ", patient=" + (patient != null ? patient.getId() : null) +
                '}';
    }
}