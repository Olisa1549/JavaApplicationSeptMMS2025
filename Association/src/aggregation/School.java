
package aggregation;

import java.util.ArrayList;
import java.util.List;


public class School {
    private String schoolName;
    private List<Student> students;

    public School(String schoolName, ArrayList<Student> students) {
        this.schoolName = schoolName;
        this.students = students;
    }
    
    public void displaySchoolDetails(){
        System.out.println();
        System.out.println("       SCHOOL INFORMATION");
        System.out.println("================================");
        
        System.out.printf("School Name: %s%n",schoolName);
        System.out.println("Number of Students: " + students.size());
        
        System.out.println("================================");
        
        for(Student student : students) {
            student.displayStudentDetails();
        }
    }
}
