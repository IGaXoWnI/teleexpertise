package service;

import dao.PatientDAO;
import model.Patient;

public class InfirmierService {
    private final PatientDAO patientDAO = new PatientDAO();

    /**
     * Save a new patient. Throws RuntimeException on failure with a friendly message.
     */
    public Patient addPatient(Patient p) {
        try {
            // check duplicate by phone
            if (p.getPhoneNumber() != null && !p.getPhoneNumber().trim().isEmpty()) {
                Patient existing = patientDAO.findByPhone(p.getPhoneNumber().trim());
                if (existing != null) {
                    throw new RuntimeException("Un patient avec ce numéro de téléphone existe déjà (id=" + existing.getId() + ").");
                }
            }
            patientDAO.save(p);
            return p;
        } catch (RuntimeException re) {
            // rethrow known runtime exceptions directly
            throw re;
        } catch (Exception e) {
            // Bubble a friendly message while keeping the original exception available
            throw new RuntimeException("Impossible d'enregistrer le patient: " + e.getMessage(), e);
        }
    }
}
