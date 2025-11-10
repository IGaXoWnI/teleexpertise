package service;

import model.Generaliste;

public interface IConsultationService {
    void createConsultation(Long patientId, Generaliste generaliste, String motif, 
                           String observations, String diagnostic, String traitement, Double cout);
}
