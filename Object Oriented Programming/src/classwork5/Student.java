
package classwork5;


public class Student extends Person {
    
    public Student(String name, int age){
        super(name, age);
    }
    

   @Override
   void performDuty() {
       System.out.println("The Student duty is to Study");
   }
   
   @Override
   void displayDetails(){
       System.out.println("Student name: " + name);
       System.out.println("Student age: " + age);
       
   }
}
