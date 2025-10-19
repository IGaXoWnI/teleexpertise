package service;

import dao.PatientDAO;
import dao.VitalSignsDAO;
import model.Patient;
import model.VitalSigns;

import java.time.LocalDateTime;
import java.util.List;

public class VitalSignsService {
    
    private VitalSignsDAO vitalSignsDAO = new VitalSignsDAO();
    private PatientDAO patientDAO = new PatientDAO();
    
    public void addVitalSigns(Long patientId, String tension, String frequenceCardiaque, 
                              Integer temperature, Integer frequenceRespiratoire, 
                              Integer poids, Integer taille) {
        Patient patient = patientDAO.findById(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient introuvable");
        }
        
        VitalSigns vitalSigns = new VitalSigns();
        vitalSigns.setPatient(patient);
        vitalSigns.setTension(tension);
        vitalSigns.setFrequenceCardiaque(frequenceCardiaque);
        vitalSigns.setTemperature(temperature);
        vitalSigns.setFrequenceRespiratoire(frequenceRespiratoire);
        vitalSigns.setPoids(poids);
        vitalSigns.setTaille(taille);
        vitalSigns.setDateMesure(LocalDateTime.now());
        vitalSignsDAO.save(vitalSigns);
    }
    
    public List<VitalSigns> getPatientVitalSigns(Long patientId) {
        return vitalSignsDAO.findByPatientId(patientId);
    }
}