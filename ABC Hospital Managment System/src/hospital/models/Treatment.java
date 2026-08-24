
package hospital.models;

import java.time.LocalDate;


public class Treatment {
    private int id;
    private Patient patient;
    private Doctor doctor;
    private Diagnosis diagnosis;
    private LocalDate treatmentDate;
    private String treatmentName;
    private String description;
    private String notes;
    private String status;
}
