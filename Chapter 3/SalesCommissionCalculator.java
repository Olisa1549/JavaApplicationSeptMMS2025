import java.util.Scanner;

public class SalesCommissionCalculator {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Item values
        double item1 = 239.99;
        double item2 = 129.75;
        double item3 = 99.95;
        double item4 = 350.89;

        double totalSales = 0.0;
        int itemNumber;

        System.out.println("Items Available:");
        System.out.println("1 - $239.99");
        System.out.println("2 - $129.75");
        System.out.println("3 - $99.95");
        System.out.println("4 - $350.89");

        System.out.print("\nEnter item number sold (1-4) or -1 to finish: ");
        itemNumber = input.nextInt();

        // Sentinel-controlled loop
        while (itemNumber != -1) {

            switch (itemNumber) {

                case 1:
                    totalSales += item1;
                    break;

                case 2:
                    totalSales += item2;
                    break;

                case 3:
                    totalSales += item3;
                    break;

                case 4:
                    totalSales += item4;
                    break;

                default:
                    System.out.println("Invalid item number.");
            }

            System.out.print("Enter item number sold (1-4) or -1 to finish: ");
            itemNumber = input.nextInt();
        }

        // Calculate earnings
        double earnings = 200 + (0.09 * totalSales);

        // Display results
        System.out.printf("%nTotal Sales: $%.2f%n", totalSales);
        System.out.printf("Weekly Earnings: $%.2f%n", earnings);

        input.close();
    }
}
