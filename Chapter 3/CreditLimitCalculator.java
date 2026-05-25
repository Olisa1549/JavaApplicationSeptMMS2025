import java.util.Scanner;

public class CreditLimitCalculator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int accountNumber;
        int beginningBalance;
        int totalCharges;
        int totalCredits;
        int creditLimit;
        int newBalance;

        System.out.print("Enter account number (-1 to quit): ");
        accountNumber = input.nextInt();

        // Sentinel-controlled loop
        while (accountNumber != -1) {

            System.out.print("Enter beginning balance: ");
            beginningBalance = input.nextInt();

            System.out.print("Enter total charges: ");
            totalCharges = input.nextInt();

            System.out.print("Enter total credits: ");
            totalCredits = input.nextInt();

            System.out.print("Enter credit limit: ");
            creditLimit = input.nextInt();

            // Calculate new balance
            newBalance = beginningBalance + totalCharges - totalCredits;

            // Display account information
            System.out.println("\nAccount Number: " + accountNumber);
            System.out.println("Credit Limit: " + creditLimit);
            System.out.println("New Balance: " + newBalance);

            // Check if credit limit is exceeded
            if (newBalance > creditLimit) {
                System.out.println("Credit limit exceeded");
            }

            System.out.println();

            // Input next customer
            System.out.print("Enter account number (-1 to quit): ");
            accountNumber = input.nextInt();
        }

        System.out.println("Program ended.");
        input.close();
    }
}
