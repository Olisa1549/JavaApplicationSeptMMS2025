import java.util.Scanner;

public class EvenChecker{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a number: ");
		int num1= input.nextInt();
		
		boolean isEven = (num1)% 2 == 0;
		System.out.printf("Is num1 an Even number? %b",isEven);
		
	}
}
		
		
		
		