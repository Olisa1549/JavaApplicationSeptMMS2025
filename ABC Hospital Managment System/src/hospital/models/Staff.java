
package hospital.models;

import java.time.LocalDate;


public abstract class Staff extends Person {
    private String staffID;
    private LocalDate employmentDate;
    private double salary;
    private Department department;
   
    
    public Staff(){
        
    }

    public Staff(String firstName, String lastName, char gender, LocalDate dateOfBirth, String phone, String email, String street,  
            String city, String country, String staffID, LocalDate employmentDate, double salary, Department department) {
        super(firstName, lastName, gender, dateOfBirth, phone, email, street, city, country);
        this.staffID = staffID;
        this.employmentDate = employmentDate;
        this.salary = salary;
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getStaffID() {
        return staffID;
    }


    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
}
