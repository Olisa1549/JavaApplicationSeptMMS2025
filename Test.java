import java.util.Scanner;

public class Test {
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		
		String name;
		int age;
		boolean isStudent;
		
		System.out.print("Enter your name: ");
		name = scan.nextLine();
		
		System.out.print("Enter your age: ");
		age = scan.nextInt();
		
		System.out.print("Are you a student (true/false): ");
		isStudent = scan.nextBoolean();
		
		
		if(name.isEmpty()){
			System.out.println("You didn't enter your name...");
			
		}
		else{
			System.out.println("Hello " + name + "!");
		}
		
		
		if(age <= 0){
			System.out.println("You are not yet born");
		}
		else if(age <= 20){
			System.out.println("You are still young");
		}
		else if(age <= 40){
			System.out.println("Your age is Okay");
		}
		else if(age <= 79){
			System.out.println("You're an adult");
		}
		else{
			System.out.println("It seems your age is not suitable for this position");
		}
		
		
		if(isStudent){
				System.out.print("You are a student");
		}
		else{
			System.out.print("Go and enroll as a Student and try again");
		}
	}
}
		
		