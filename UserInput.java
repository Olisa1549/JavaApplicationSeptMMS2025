import java.util.Scanner;

public class UserInput{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter your name: ");
		String name = input.nextLine();
		
		System.out.printf("Enter your gender: ");
		String gender = input.next();
		
		System.out.print("Enter your age: ");
		byte age = input.nextByte();
		
		System.out.printf("Enter the number of student in your class: ");
		short numberOfStudent = input.nextShort();
		
		System.out.print("Enter the number of student in your class: ");
		int totalNumberOfStudent = input.nextInt();
		
		System.out.print("Enter your grade: ");
		char grade = input.next().charAt(0);
		
		System.out.print("Do you love learning java(true/false): ");
		boolean lovejava = input.nextBoolean();
		
		
		System.out.println("");
		System.out.printf("Information about %s%n",name);
		System.out.println("=============================================");
		
		System.out.printf("Hello %s, you are welcome to NIIT%n",name);
		System.out.printf("You are a %s and you are %d years old%n", gender,age);
		System.out.printf("There are %d students in your class%n",numberOfStudent);
		System.out.printf("There total number of students in your school is %,d%n",totalNumberOfStudent);
		System.out.printf("Your grade is %c%n", grade);
		System.out.printf("Do you love java %b%n",lovejava);
	
	}
}
