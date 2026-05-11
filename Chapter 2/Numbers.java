// 2.17
import java.util.Scanner;

public class Numbers {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int x, y, z;

        System.out.print("Enter first integer: ");
        x = input.nextInt();

        System.out.print("Enter second integer: ");
        y = input.nextInt();

        System.out.print("Enter third integer: ");
        z = input.nextInt();

        int sum = x + y + z;
        int average = sum / 3;
        int product = x * y * z;

        int smallest = x;
        int largest = x;

        if (y < smallest)
            smallest = y;

        if (z < smallest)
            smallest = z;

        if (y > largest)
            largest = y;

        if (z > largest)
            largest = z;

        System.out.printf("Sum = %d%n", sum);
        System.out.printf("Average = %d%n", average);
        System.out.printf("Product = %d%n", product);
        System.out.printf("Smallest = %d%n", smallest);
        System.out.printf("Largest = %d%n", largest);
    }
}