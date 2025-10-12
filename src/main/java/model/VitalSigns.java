package model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import static jakarta.persistence.GenerationType.*;

@Entity(name = "VitalSigns")
@Table(name = "vital_signs")
public class VitalSigns {
    @Id
    @SequenceGenerator(
            name = "VitalSignsSequence",
            sequenceName = "VitalSignsSequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "VitalSignsSequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id" , nullable = false)
    private Patient patient ;

    @Column(
            name = "tension" ,
            nullable = false
    )
    private String tension ;
    @Column(
            name = "frequence_cardiaque" ,
            nullable = false
    )
    private String frequenceCardiaque ;
    @Column(
            name = "temperature" ,
            nullable = false
    )
    private Integer temperature ;
    @Column(
            name = "frequence_respiratoire" ,
            nullable = false
    )
    private Integer frequenceRespiratoire ;

    @Column(
            name = "poids" ,
            nullable = false
    )
    private Integer poids ;

    @Column(
            name = "taille" ,
            nullable = false
    )
    private Integer taille ;
    @Column(
            name = "date_mesure" ,
            nullable = false
    )
    private LocalDateTime dateMesure ;



    public VitalSigns() {

    }

    public VitalSigns(Long id, Patient patient, String tension, String frequenceCardiaque, Integer temperature, Integer frequenceRespiratoire, Integer poids, Integer taille, LocalDateTime dateMesure) {
        this.id = id;
        this.patient = patient;
        this.tension = tension;
        this.frequenceCardiaque = frequenceCardiaque;
        this.temperature = temperature;
        this.frequenceRespiratoire = frequenceRespiratoire;
        this.poids = poids;
        this.taille = taille;
        this.dateMesure = dateMesure;
    }

    public Long getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getTension() {
        return tension;
    }

    public String getFrequenceCardiaque() {
        return frequenceCardiaque;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public Integer getFrequenceRespiratoire() {
        return frequenceRespiratoire;
    }

    public Integer getPoids() {
        return poids;
    }

    public Integer getTaille() {
        return taille;
    }

    public LocalDateTime getDateMesure() {
        return dateMesure;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setTension(String tension) {
        this.tension = tension;
    }

    public void setFrequenceCardiaque(String frequenceCardiaque) {
        this.frequenceCardiaque = frequenceCardiaque;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public void setFrequenceRespiratoire(Integer frequenceRespiratoire) {
        this.frequenceRespiratoire = frequenceRespiratoire;
    }

    public void setPoids(Integer poids) {
        this.poids = poids;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public void setDateMesure(LocalDateTime dateMesure) {
        this.dateMesure = dateMesure;
    }


    @Override
    public String toString() {
        return "VitalSigns{" +
                "id=" + id +
                ", patient=" + patient +
                ", tension='" + tension + '\'' +
                ", frequenceCardiaque='" + frequenceCardiaque + '\'' +
                ", temperature=" + temperature +
                ", frequenceRespiratoire=" + frequenceRespiratoire +
                ", poids=" + poids +
                ", taille=" + taille +
                ", dateMesure=" + dateMesure +
                '}';
    }
}
