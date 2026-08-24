
package hospital.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MedicalRecord {
    private int id;
    private Patient patient;
    private LocalDate createdDate;
    
    private List<Diagnosis> diagnosis = new ArrayList<>(); 
    private List<Treatment> treatments = new ArrayList<>(); 
    private List<LaboratoryTest> laboratoryTests = new ArrayList<>(); 
    private List<Prescription> prescription = new ArrayList<>(); 
    private List<Admission> admissions = new ArrayList<>(); 
    
    public MedicalRecord(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public List<Diagnosis> getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(List<Diagnosis> diagnosis) {
        this.diagnosis = diagnosis;
    }

    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatments) {
        this.treatments = treatments;
    }

    public List<LaboratoryTest> getLaboratoryTests() {
        return laboratoryTests;
    }

    public void setLaboratoryTests(List<LaboratoryTest> laboratoryTests) {
        this.laboratoryTests = laboratoryTests;
    }

    public List<Prescription> getPrescription() {
        return prescription;
    }

    public void setPrescription(List<Prescription> prescription) {
        this.prescription = prescription;
    }

    public List<Admission> getAdmissions() {
        return admissions;
    }

    public void setAdmissions(List<Admission> admissions) {
        this.admissions = admissions;
    }
   
}
