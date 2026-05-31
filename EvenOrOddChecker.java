import java.util.Scanner;

public class EvenOrOddChecker{
	public static void main (String[] args){
		Scanner scan = new Scanner(System.in);
		char option;
		do{
			System.out.print("Enter any number: ");
			int num = scan.nextInt();
			
			if(num % 2 == 0){
				System.out.printf("The %d is an even number",num);
				System.out.println(" ");
				System.out.println("========================");
			}
			else{
				System.out.printf("The %d is an odd number",num);System.out.println(" ");
				System.out.println(" ");
				System.out.println("========================");	
			}
			System.out.printf("Do you want to run the program again(Y/N): ");
			option = scan.next().charAt(0);
		}while(option == 'Y' || option == 'y');
		System.out.println("Goodbye.............");
	}
	
}