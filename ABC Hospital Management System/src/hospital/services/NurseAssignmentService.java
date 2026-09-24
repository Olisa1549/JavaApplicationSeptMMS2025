package hospital.services;

import hospital.dao.NurseAssignmentDAO;
import hospital.models.NurseAssignment;

import java.util.List;

public class NurseAssignmentService {

    private final NurseAssignmentDAO nurseAssignmentDAO;

    public NurseAssignmentService() {
        nurseAssignmentDAO = new NurseAssignmentDAO();
    }

    public boolean createAssignment(NurseAssignment assignment) {

        if (assignment == null) {
            return false;
        }

        if (assignment.getNurse() == null) {
            System.out.println("Nurse is required.");
            return false;
        }

        if (assignment.getPatient() == null) {
            System.out.println("Patient is required.");
            return false;
        }

        if (assignment.getAssignmentDate() == null) {
            System.out.println("Assignment date is required.");
            return false;
        }

        if (assignment.getShift() == null
                || assignment.getShift().isBlank()) {
            System.out.println("Shift is required.");
            return false;
        }

        if (assignment.getStatus() == null
                || assignment.getStatus().isBlank()) {
            System.out.println("Assignment status is required.");
            return false;
        }

        nurseAssignmentDAO.addNurseAssignment(assignment);
        return true;
    }

    public List<NurseAssignment> getAllAssignments() {
        return nurseAssignmentDAO.findAllNurseAssignments();
    }

    public NurseAssignment getAssignmentById(int id) {
        return nurseAssignmentDAO.findNurseAssignmentById(id);
    }

    public List<NurseAssignment> getAssignmentsByPatient(
            int patientId) {

        return nurseAssignmentDAO
                .findNurseAssignmentsByPatient(patientId);
    }

    public List<NurseAssignment> getAssignmentsByNurse(
            int nurseStaffId) {

        return nurseAssignmentDAO
                .findNurseAssignmentsByNurse(nurseStaffId);
    }

    public boolean updateAssignment(NurseAssignment assignment) {

        if (assignment == null) {
            return false;
        }

        nurseAssignmentDAO.updateNurseAssignment(assignment);
        return true;
    }

    public boolean deleteAssignment(int id) {

        nurseAssignmentDAO.deleteNurseAssignment(id);
        return true;
    }
}