package hospital.services;

import hospital.dao.PharmacistDAO;
import hospital.models.Pharmacist;

import java.util.List;

public class PharmacistService {

    private final PharmacistDAO pharmacistDAO;

    public PharmacistService() {
        pharmacistDAO = new PharmacistDAO();
    }

    public boolean registerPharmacist(Pharmacist pharmacist) {

        if (pharmacist == null) {
            return false;
        }

        if (pharmacist.getFirstName() == null
                || pharmacist.getFirstName().isBlank()) {
            System.out.println("First name is required.");
            return false;
        }

        if (pharmacist.getLastName() == null
                || pharmacist.getLastName().isBlank()) {
            System.out.println("Last name is required.");
            return false;
        }

        if (pharmacist.getDateOfBirth() == null) {
            System.out.println("Date of birth is required.");
            return false;
        }

        if (pharmacist.getEmploymentDate() == null) {
            System.out.println("Employment date is required.");
            return false;
        }

        if (pharmacist.getDepartment() == null) {
            System.out.println("Department is required.");
            return false;
        }

        if (pharmacist.getQualification() == null
                || pharmacist.getQualification().isBlank()) {
            System.out.println("Qualification is required.");
            return false;
        }

        if (pharmacist.getLicenseNumber() == null
                || pharmacist.getLicenseNumber().isBlank()) {
            System.out.println("License number is required.");
            return false;
        }

        return pharmacistDAO.addPharmacist(pharmacist);
    }

    public List<Pharmacist> getAllPharmacists() {
        return pharmacistDAO.findAllPharmacists();
    }

    public Pharmacist getPharmacistById(int staffId) {
        return pharmacistDAO.findPharmacistById(staffId);
    }

    public boolean updatePharmacist(Pharmacist pharmacist) {

        if (pharmacist == null) {
            return false;
        }

        return pharmacistDAO.update(pharmacist);
    }

    public boolean deletePharmacist(int staffId) {
        return pharmacistDAO.delete(staffId);
    }
}