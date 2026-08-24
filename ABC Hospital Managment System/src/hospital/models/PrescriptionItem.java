
package hospital.models;


public class PrescriptionItem {
    private int id;
    private PrescriptionItem prescription;
    private Medication medication;
    private String dosage;
    private String frequency;
    private int duration;
    private String distrubutionUnit;
    private String instructions;
    
    public PrescriptionItem(){
        
    }

    public int getId() {
        return id;
    }


    public PrescriptionItem getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionItem prescription) {
        this.prescription = prescription;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDistrubutionUnit() {
        return distrubutionUnit;
    }

    public void setDistrubutionUnit(String distrubutionUnit) {
        this.distrubutionUnit = distrubutionUnit;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
}
