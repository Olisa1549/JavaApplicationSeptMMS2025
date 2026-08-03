
package school;

import java.time.LocalDate;


public class Student {
    // Properties of Student
    private int studentID;
    private String firstName;
    private String lastName;
    private char gender;
    private LocalDate doB;
    private String phoneNumber;

    public Student(int studentID, String firstName, String lastName, 
            char gender, LocalDate doB, String phoneNumber) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.doB = doB;
        this.phoneNumber = phoneNumber;
    }
    
    

    public int getStudentID() {
        return studentID;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public LocalDate getDoB() {
        return doB;
    }

    public void setDoB(LocalDate doB) {
        this.doB = doB;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void payFees(double amount) {
        System.out.println("Amount Paid " + amount);
    }
    
    public void payFees(double amount, String fullName) {
        System.out.println("Amount Paid " + amount);
        System.out.println("Student fullname  " + fullName);
    }
    
    public void payFees(double amount, String fullName, String paymentMethod) {
        System.out.println("Amount Paid " + amount);
        System.out.println("Student fullname  " + fullName);
        System.out.println("Payment Method  " + paymentMethod);
    }
    
    public void displayStudentInfo(){
        System.out.println("StudentID: " + studentID);
        System.out.println("First Name: " + firstName + " " + lastName);
        //System.out.println("Last Name: " + lastName);
        System.out.println("Gender: " + gender);
        System.out.println("Date of Birth: " + doB);
        System.out.println("Phone Number: " + phoneNumber);
    }
}
