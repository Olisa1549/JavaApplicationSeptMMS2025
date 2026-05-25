import java.util.Scanner;

public class MileageCalculator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int milesDriven;
        int gallonsUsed;

        int totalMiles = 0;
        int totalGallons = 0;

        System.out.println("Enter miles driven (-1 to quit): ");
        milesDriven = input.nextInt();

        // Sentinel-controlled loop
        while (milesDriven != -1) {

            System.out.println("Enter gallons used: ");
            gallonsUsed = input.nextInt();

            // Calculate MPG for current trip
            double mpg = (double) milesDriven / gallonsUsed;

            // Update totals
            totalMiles += milesDriven;
            totalGallons += gallonsUsed;

            // Calculate combined MPG
            double combinedMpg = (double) totalMiles / totalGallons;

            // Display results
            System.out.printf("Miles per gallon for this trip: %.2f%n", mpg);
            System.out.printf("Combined miles per gallon: %.2f%n%n", combinedMpg);

            // Input next trip
            System.out.println("Enter miles driven (-1 to quit): ");
            milesDriven = input.nextInt();
        }

        System.out.println("Program ended.");
        input.close();
    }
}
