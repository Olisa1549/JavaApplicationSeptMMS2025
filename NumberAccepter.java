import java.util.Scanner;

public class NumberAccepter{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter the first number: ");
		int number1 = input.nextInt();
		
		System.out.print("Enter the second number: ");
		int number2 = input.nextInt();
		
		System.out.print("Enter the third number: ");
		int number3 = input.nextInt();
		
		System.out.print("Enter the fourth number: ");
		int number4 = input.nextInt();
		
		System.out.print("Enter the fifth number: ");
		int number5 = input.nextInt();
		
		int addition = number1 + number2 + number3 + number4 + number5;
		int multiplication = number1 * number2 * number3 * number4 * number5;
		int average = number1 + number2 + number3 + number4 + number5 / 5;
		
		System.out.printf("%d + %d + %d + %d + %d = %d%n", number1,number2,number3,number4,number5,addition);
		System.out.printf("%d x %d x %d x %d x %d = %d%n",number1,number2,number3,number4,number5,multiplication);
		System.out.printf("%d + %d + %d + %d + %d / 5 = %d%n", number1,number2,number3,number4,number5,average);
		
		
		
		
		
	}
}