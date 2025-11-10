package service;

import dao.ConsultationDAO;
import dao.PatientDAO;
import model.Consultation;
import model.Generaliste;
import model.Patient;
import model.enums.ConsultationStatus;
import service.servicesInterfaces.IConsultationService;

import java.time.LocalDateTime;

public class ConsultationService implements IConsultationService {
    
    private ConsultationDAO consultationDAO = new ConsultationDAO();
    private PatientDAO patientDAO = new PatientDAO();
    
    public void createConsultation(Long patientId, Generaliste generaliste, String motif, 
                                   String observations, String diagnostic, String traitement, Double cout) {
        Patient patient = patientDAO.findById(patientId);
        if (patient == null) {
            throw new IllegalArgumentException("Patient introuvable");
        }
        
        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setGeneraliste(generaliste);
        consultation.setDateConsultation(LocalDateTime.now());
        consultation.setMotif(motif);
        consultation.setObservations(observations);
        consultation.setDiagnostic(diagnostic);
        consultation.setTraitement(traitement);
        consultation.setCout(cout);
        consultation.setConsultationStatus(ConsultationStatus.TERMINEE);
        
        consultationDAO.save(consultation);
    }
}
