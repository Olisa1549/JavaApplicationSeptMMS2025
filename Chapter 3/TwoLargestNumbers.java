 import java.util.Scanner;

public class TwoLargestNumbers {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int number;
        int largest = Integer.MIN_VALUE;
        int secondLargest = Integer.MIN_VALUE;

        int counter = 1;

        while (counter <= 10) {

            System.out.print("Enter integer #" + counter + ": ");
            number = input.nextInt();

            // If new number is greater than largest
            if (number > largest) {
                secondLargest = largest; // old largest becomes second largest
                largest = number;
            }
            // If number is between largest and second largest
            else if (number > secondLargest) {
                secondLargest = number;
            }

            counter++;
        }

        System.out.println("\nLargest number: " + largest);
        System.out.println("Second largest number: " + secondLargest);

        input.close();
    }
}
