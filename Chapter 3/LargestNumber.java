import java.util.Scanner;

public class LargestNumber {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int counter = 1;
        int number;
        int largest = Integer.MIN_VALUE;

        while (counter <= 10) {

            System.out.print("Enter integer #" + counter + ": ");
            number = input.nextInt();

            // Check if current number is larger
            if (number > largest) {
                largest = number;
            }

            counter++;
        }

        System.out.println("\nLargest number is: " + largest);

        input.close();
    }
}

