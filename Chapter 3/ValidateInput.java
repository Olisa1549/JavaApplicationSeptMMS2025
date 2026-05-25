import java.util.Scanner;

public class ValidateInput {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int userInput;

        System.out.print("Enter a value (1 or 2): ");
        userInput = input.nextInt();

        // Validate input
        while (userInput != 1 && userInput != 2) {
            System.out.println("Invalid input. Please enter 1 or 2 only.");

            System.out.print("Enter a value (1 or 2): ");
            userInput = input.nextInt();
        }

        System.out.println("You entered a valid value: " + userInput);

        input.close();
    }
}
