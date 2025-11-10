package service;

import model.VitalSigns;
import java.util.List;

public interface IVitalSignsService {
    void addVitalSigns(Long patientId, String tension, String frequenceCardiaque, 
                      Integer temperature, Integer frequenceRespiratoire, 
                      Integer poids, Integer taille);
    List<VitalSigns> getPatientVitalSigns(Long patientId);
}
