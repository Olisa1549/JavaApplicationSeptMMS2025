package hospital.models;

import java.time.LocalDate;

public class Staff extends Person {
    private int staffID;
    private LocalDate employmentDate;
    private double salary;
    private Department department;
    
    public Staff(){ 
        
    }

    public Staff( String firstName, String lastName, char gender, LocalDate dateOfBirth, String phone, String email, String street, String city, String country,int staffID, LocalDate employmentDate, double salary,Department department) {
        super(firstName, lastName, gender, dateOfBirth, phone, email, street, city, country);
        this.staffID = staffID;
        this.employmentDate = employmentDate;
        this.salary = salary;
        this.department = department;
    }

    public int getStaffID() {
        return staffID;
    }
    
    public void setStaffID(int staffID) {
        this.staffID = staffID;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    
    
}
