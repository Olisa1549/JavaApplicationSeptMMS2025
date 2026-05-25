import java.util.Scanner;

public class GrossPayCalculator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        final int NUMBER_OF_EMPLOYEES = 3;

        for (int i = 1; i <= NUMBER_OF_EMPLOYEES; i++) {

            System.out.println("Employee " + i);

            System.out.print("Enter hours worked: ");
            double hoursWorked = input.nextDouble();

            System.out.print("Enter hourly rate: ");
            double hourlyRate = input.nextDouble();

            double grossPay;

            // Calculate gross pay
            if (hoursWorked <= 40) {
                grossPay = hoursWorked * hourlyRate;
            } else {
                double overtimeHours = hoursWorked - 40;
                grossPay = (40 * hourlyRate) +
                           (overtimeHours * hourlyRate * 1.5);
            }

            // Display gross pay
            System.out.printf("Gross pay for employee %d: $%.2f%n%n",
                    i, grossPay);
        }

        input.close();
    }
}
