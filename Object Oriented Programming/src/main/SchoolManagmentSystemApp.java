package main;

import java.time.LocalDate;
//import school.Student;
import school.GraduateStudent;
import school.UnderGraduateStudent;
import java.util.Scanner;
import school.Student;


public class SchoolManagmentSystemApp {
    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        
        System.out.println("=================Menu=================");
        System.out.println("Enter 1: For Graduate Student");
        System.out.println("Enter 2: For UnderGraduate Student");
        System.out.println("Enter 3: To pay School Fees");
        System.out.println("===============================");
        
        System.out.println("Enter your choice: ");
        System.out.print("===Choose the following option===: ");
        int choice = scan.nextInt();
        
        switch(choice){
            case 1 ->  {
                System.out.print("Enter your student ID: ");
                int studentID = scan.nextInt();
                scan.nextLine();
                
                System.out.print("Enter First Name: ");
                String firstName = scan.nextLine();
                
                System.out.print("Enter Last Name: ");
                String lastName = scan.nextLine();
                
                System.out.print("Enter Gender: ");
                char gender = scan.next().charAt(0);
                scan.nextLine();
                System.out.print("Enter Date Of Birth(YYY-MM-DD): ");
                String dateOfBirth = scan.nextLine();
                
                System.out.print("Enter Phone Number: ");
                String phoneNumber = scan.nextLine();
                
                System.out.print("Enter Final Year Project: ");
                String finalYearProject = scan.nextLine();
                
                GraduateStudent gStudent = 
                        new GraduateStudent(studentID,firstName,lastName,
                        gender, LocalDate.parse(dateOfBirth), 
                                phoneNumber,finalYearProject);
                System.out.println("==============================");
                gStudent.displayStudentInfo();
                    }
            case 2 ->  {
                System.out.print("Enter your student ID: ");
                int studentID = scan.nextInt();
                scan.nextLine();
                
                System.out.print("Enter First Name: ");
                String firstName = scan.nextLine();
                
                System.out.print("Enter Last Name: ");
                String lastName = scan.nextLine();
   
           
                System.out.print("Enter Gender: ");
                char gender = scan.next().charAt(0);
                scan.nextLine();
                
                System.out.print("Enter Dirth Of Birth(YYY-MM-DD): ");
                String dateOfBirth = scan.nextLine();
                
                System.out.print("Enter Phone Number: ");
                String phoneNumber = scan.nextLine();
                
                System.out.print("Enter Level: ");
                int level = scan.nextInt();
                
                UnderGraduateStudent ugStudent = 
                        new UnderGraduateStudent(studentID,firstName,lastName,
                        gender, LocalDate.parse(dateOfBirth), 
                                phoneNumber,level);
                System.out.println("==============================");
                ugStudent.displayStudentInfo();
        break;
        }
            case 3 -> {
                System.out.println("=====Choose the following option=====\n");
                System.out.println("Enter 1: Amount only");
                System.out.println("Enter 2: Amount, FullName only");
                System.out.println("Enter 3: Amount, FullName,Payment Method only");
                
                System.out.println(" ");
                System.out.println("==========================");
                System.out.print("Enter your option: ");
                int myOption = scan.nextInt();
                
                switch(myOption){
                    case 1:{
                        System.out.print("Enter StudentID: ");
                        int studentID = scan.nextInt();
                     
                        System.out.print("Enter First Name: ");
                        String firstName = scan.nextLine();
                
                        System.out.print("Enter Last Name: ");
                        String lastName = scan.nextLine();
                
                        System.out.print("Enter Gender: ");
                        char gender = scan.next().charAt(0);
                        scan.nextLine();
                        
                        System.out.print("Enter Date Of Birth(YYY-MM-DD): ");
                        String dateOfBirth = scan.nextLine();
                
                        System.out.print("Enter Phone Number: ");
                        String phoneNumber = scan.nextLine();
          
                        
                        Student student = new Student(studentID,firstName,lastName,
                        gender, LocalDate.parse(dateOfBirth), 
                                phoneNumber);
                        
                        System.out.print("Enter amount: ");
                        double amount = scan.nextDouble();
                        student.payFees(amount);
                        scan.nextLine();
                        break;
                    }
                    case 2:{
                        System.out.print("Enter StudentID: ");
                        int studentID = scan.nextInt();
                     
                        System.out.print("Enter First Name: ");
                        String firstName = scan.nextLine();
                        scan.nextLine();
                
                        System.out.print("Enter Last Name: ");
                        String lastName = scan.nextLine();
                
                        System.out.print("Enter Gender: ");
                        char gender = scan.next().charAt(0);
                        scan.nextLine();
                        
                        System.out.print("Enter Date Of Birth(YYY-MM-DD): ");
                        String dateOfBirth = scan.nextLine();
                
                        System.out.print("Enter Phone Number: ");
                        String phoneNumber = scan.nextLine();
          
                        
                        Student student = new Student(studentID,firstName,lastName,
                        gender, LocalDate.parse(dateOfBirth), 
                                phoneNumber);
                        
                        System.out.println("========================");
                        System.out.println("Enter details for Payment\n");
                        System.out.print("Enter amount: ");
                        double amount = scan.nextDouble();
                        scan.nextLine();
                        
                        System.out.print("Enter full name:\n ");
                        System.out.println("========================");
                        String fullName = scan.nextLine();
                        student.payFees(amount,fullName);
                        break;
                    }
                    case 3:{
                        System.out.print("Enter StudentID");
                        int studentID = scan.nextInt();
                     
                        System.out.print("Enter First Name: ");
                        String firstName = scan.nextLine();
                
                        System.out.print("Enter Last Name: ");
                        String lastName = scan.nextLine();
                
                        System.out.print("Enter Gender: ");
                        char gender = scan.next().charAt(0);
                        scan.nextLine();
                        
                        System.out.print("Enter Date Of Birth(YYY-MM-DD): ");
                        String dateOfBirth = scan.nextLine();
                
                        System.out.print("Enter Phone Number: ");
                        String phoneNumber = scan.nextLine();
          
                        
                        Student student = new Student(studentID,firstName,lastName,
                        gender, LocalDate.parse(dateOfBirth), 
                                phoneNumber);
                        
                        System.out.print("Enter amount: ");
                        double amount = scan.nextDouble();
                        scan.nextLine();
                        
                        System.out.print("Enter full name: ");
                        String fullName = scan.nextLine();
                        
                        System.out.print("Enter Payment Method: ");
                        String paymentMethod = scan.nextLine();
                        scan.nextLine();
                        
                        
                        student.payFees(amount,fullName,paymentMethod);
                      break;  
                    }
                    default:
                        System.out.println("Invalid Input");
                        
                
                }
                
              break;  
                
            }
            default -> System.out.println("Invalid Input");
        }
    
    }
        
 
    
}