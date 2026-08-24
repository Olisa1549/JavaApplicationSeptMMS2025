
package aggregation;

import java.time.LocalDate;
import java.util.ArrayList;


public class SchoolManagmentSystem {
    public static void main(String[] args){
        
        //Student Object Created
        Student s1 = new Student(101,"Henry","John",'M');
        Student s2 = new Student(102,"Joy","Jack",'F');
        Student s3 = new Student(103,"Franklin","Williams",'M');
        Student s4 = new Student(104,"Toby","Emmanuel",'M');
        Student s5 = new Student(105,"Lucy","Andy",'F');
        Student s6 = new Student(106,"Lucky","Anthony",'M');
        Student s7 = new Student(107,"Lucy","Victor",'F');
        
        s1.makePayment(300000.00, LocalDate.of(2026, 8, 10), 
                "Transfer", "Paid for Data Analytics");
        
        s3.makePayment(500000.00, LocalDate.of(2026, 8, 8), 
                "POS", "Paid for Java");
        
        s5.makePayment(350000.00, LocalDate.of(2026, 8, 5), 
                "Transfer", "Paid for MMS");
        
        //School Object Created
        ArrayList<Student> students = new ArrayList<>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.add(s5);
        
        School school = new School("Green Land International School",students);
        
        school.displaySchoolDetails();
    }
}
