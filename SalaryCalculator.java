import java.util.Scanner;

public class SalaryCalculator{
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter the employee name: ");
		String empName = scan.nextLine();
		
		System.out.print("Enter the hours worked: ");
		double hoursWorked = scan.nextDouble();
		
		System.out.print("Enter the hourly rate: ");
		byte hourlyRate = scan.nextByte();
		
		double grossSalary = hoursWorked * hourlyRate;
		double tax = (grossSalary / 100 *10);
		double netSalary = grossSalary - tax;
		
		
		System.out.println("===========================");
		
		System.out.println("The gross Salary is: " + grossSalary);
		System.out.println("The tax is: " + tax);
		System.out.println("The net Salary is: " + netSalary);
		
		
	}
}
		
		