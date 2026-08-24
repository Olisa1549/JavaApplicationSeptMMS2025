package inheritance;

import java.util.Scanner;
import inheritance.Person;
import inheritance.Student;
import inheritance.Teacher;


public class ManagmentApp {
        public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("===========================");

        System.out.println("Enter 1 for Student");
        System.out.println("Enter 2 for Teacher");
        

        System.out.println("==========================");

        System.out.print("Enter your choice: ");
        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter your Name: ");
                String name = scan.nextLine();
                
                System.out.print("Enter Student Age: ");
                int age = scan.nextInt();
                scan.nextLine();

                System.out.print("Enter Your Course: ");
                String course = scan.nextLine();
                
                System.out.print("Enter Your Level: ");
                int level = scan.nextInt();
                Student student
                        = new Student(name,age,course,level);
                student.displayPersonInfo();
                
            
            }
            
            case 2 -> {
                System.out.print("Enter your Name: ");
                String name = scan.nextLine();
                
                System.out.print("Enter Your Age: ");
                int age = scan.nextInt();
                scan.nextLine();

                System.out.print("Enter Your Department: ");
                String department = scan.nextLine();
                
                System.out.print("Enter Your Salary: ");
                int salary = scan.nextInt();
                Teacher teacher
                        = new Teacher(department,salary,name,age);
                teacher.displayPersonInfo();
                
            
            }
            default ->
                System.out.println("Invalid input");
        }        
        }
}