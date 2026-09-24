package hospital.models;

import java.time.LocalDate;

public class Nurse extends Staff{
    private String nursingLicense;
    private String qualification;
    
    public Nurse(){
    }

    public Nurse(String firstName, String lastName, char gender, LocalDate dateOfBirth, String phone, String email, String street, String city, String country, int staffID, LocalDate employmentDate, double salary,Department department,String nursingLicense, String qualification) {
        super( firstName, lastName, gender, dateOfBirth, phone, email, street, city, country,staffID, employmentDate, salary,department);
        this.nursingLicense = nursingLicense;
        this.qualification = qualification;
    }

    public String getNursingLicense() {
        return nursingLicense;
    }

    public void setNursingLicense(String nursingLicense) {
        this.nursingLicense = nursingLicense;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
}
