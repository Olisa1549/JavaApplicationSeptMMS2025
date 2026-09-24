package hospital.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {
    private int id;
    private Patient patient;
    private LocalDate createdDate;
    
    private List<Diagnosis> diagnoses = new ArrayList<>();
    private List<Treatment> treatments = new ArrayList<>();
    private List<LaboratoryTest> laboratoryTests = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>();
    private List<Admission> addmissions = new ArrayList<>();
    
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

    public List<Diagnosis> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(List<Diagnosis> diagnoses) {
        this.diagnoses = diagnoses;
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

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<Admission> getAddmissions() {
        return addmissions;
    }

    public void setAddmissions(List<Admission> addmissions) {
        this.addmissions = addmissions;
    }
    
    
}
