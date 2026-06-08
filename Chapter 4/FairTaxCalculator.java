import java.util.Scanner;

public class FairTaxCalculator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter annual housing expenses: ");
        double housing = input.nextDouble();

        System.out.print("Enter annual food expenses: ");
        double food = input.nextDouble();

        System.out.print("Enter annual clothing expenses: ");
        double clothing = input.nextDouble();

        System.out.print("Enter annual transportation expenses: ");
        double transportation = input.nextDouble();

        System.out.print("Enter annual education expenses: ");
        double education = input.nextDouble();

        System.out.print("Enter annual health care expenses: ");
        double healthCare = input.nextDouble();

        System.out.print("Enter annual vacation expenses: ");
        double vacations = input.nextDouble();

        double totalExpenses = housing + food + clothing + transportation + education +
                               healthCare + vacations;

        double fairTax23 = totalExpenses * 0.23; // 23% tax-inclusive
        double fairTax30 = totalExpenses * 0.30; // 30% tax-exclusive equivalent

        System.out.printf("%nTotal Annual Expenses: $%.2f%n", totalExpenses);
        System.out.printf("Estimated FairTax (23%%): $%.2f%n", fairTax23);
        System.out.printf("Estimated FairTax (30%%): $%.2f%n", fairTax30);

        input.close();
    }
}