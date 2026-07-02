import java.util.Scanner;

public class Test3 {
	public static void main(String[] args) {
		 Scanner scan = new Scanner(System.in);
		
		/* System.out.print("Enter the length of side A: ");
		double a = scan.nextDouble();
		
		System.out.print("Enter the length of side B: ");
		double b = scan.nextDouble();
		
		double c = Math.sqrt(Math.pow(a,2) + Math.pow(b,2));
		
		System.out.println("The hypotenuse (side c) is: " + c + "cm"); */
	
		System.out.print("Enter the raidus: ");
		double raidus = scan.nextDouble();
		
		double circumference = 2 * Math.PI * raidus;
		double area = Math.PI * Math.pow(raidus, 2);
		double volume = (4 / 3) * Math.PI * Math.pow(raidus, 3);
		
		System.out.printf("The circumference is: %.1fcm\n ",circumference);
		System.out.printf("The area is: %.1fcm\n ",area);
		System.out.printf("The volume is: %.1fcm\n ",volume);
	}
}