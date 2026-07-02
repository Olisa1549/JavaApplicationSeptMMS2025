import java.util.Scanner;

public class Pj {
	public static void main (String[] args) {
		Scanner Scan = new Scanner (System.in);
	
	System.out.print("Enter a number from 1 - 3:  ");
	int digit = Scan.nextInt();
	
	switch(digit){
			case 1:
			System.out.printf("The number %d is okay",digit);
			break;
			
			case 2:
			System.out.printf("The number %d is okay",digit);
			break;
			
			case 3:
			System.out.printf("The number %d is okay",digit);
			break;
			
			default:
			System.out.print("Try again boss!");
			
		}
	}
}

	
		