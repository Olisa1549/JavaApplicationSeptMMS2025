
package classwork5;


public class MainApp {
    public static void main (String[] args) {
        
        Student student = new Student("Obi", 50);
        
        Teacher teacher = new Teacher("Sam",20);
        
        System.out.println("========Teacher's Details========");
        teacher.displayDetails();
        teacher.performDuty();
        
        System.out.println("\n");
        
        System.out.println("========Student's Details========");
        student.displayDetails();
        student.performDuty();
        

    }
}
