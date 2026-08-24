
package runtime;

import java.util.InputMismatchException;
import java.util.Scanner;


public class InputMismatchExceptionHandling {
    public static void main(String args[]){
       Scanner input = new Scanner(System.in);
       try{
        System.out.print("Enter your age: ");
        int age = input.nextInt();
        
        System.out.printf("You are %d years old", age);
       }
       
       catch(InputMismatchException e){
           System.out.println("Age must be a number");
       }
    }
}
