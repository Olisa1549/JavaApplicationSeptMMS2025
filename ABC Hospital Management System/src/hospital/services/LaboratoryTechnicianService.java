package hospital.services;

import hospital.dao.LaboratoryTechnicianDAO;
import hospital.models.LaboratoryTechnician;

import java.util.List;

public class LaboratoryTechnicianService {

    private final LaboratoryTechnicianDAO laboratoryTechnicianDAO;

    public LaboratoryTechnicianService() {
        laboratoryTechnicianDAO = new LaboratoryTechnicianDAO();
    }

    public boolean registerLaboratoryTechnician(
            LaboratoryTechnician technician) {

        if (technician == null) {
            return false;
        }

        if (technician.getFirstName() == null
                || technician.getFirstName().isBlank()) {
            System.out.println("First name is required.");
            return false;
        }

        if (technician.getLastName() == null
                || technician.getLastName().isBlank()) {
            System.out.println("Last name is required.");
            return false;
        }

        if (technician.getDateOfBirth() == null) {
            System.out.println("Date of birth is required.");
            return false;
        }

        if (technician.getEmploymentDate() == null) {
            System.out.println("Employment date is required.");
            return false;
        }

        if (technician.getDepartment() == null) {
            System.out.println("Department is required.");
            return false;
        }

        if (technician.getQualification() == null
                || technician.getQualification().isBlank()) {
            System.out.println("Qualification is required.");
            return false;
        }

        if (technician.getLicenseNumber() == null
                || technician.getLicenseNumber().isBlank()) {
            System.out.println("License number is required.");
            return false;
        }

        return laboratoryTechnicianDAO
                .addLaboratoryTechnician(technician);
    }

    public List<LaboratoryTechnician> getAllLaboratoryTechnicians() {
        return laboratoryTechnicianDAO.findAllLaboratoryTechnicians();
    }

    public LaboratoryTechnician getLaboratoryTechnicianById(int staffId) {
        return laboratoryTechnicianDAO
                .findLaboratoryTechnicianById(staffId);
    }

    public boolean updateLaboratoryTechnician(
            LaboratoryTechnician technician) {

        if (technician == null) {
            return false;
        }

        return laboratoryTechnicianDAO.update(technician);
    }

    public boolean deleteLaboratoryTechnician(int staffId) {
        return laboratoryTechnicianDAO.delete(staffId);
    }
}