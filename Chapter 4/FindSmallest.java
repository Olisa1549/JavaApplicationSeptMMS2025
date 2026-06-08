import java.util.Scanner;

public class FindSmallest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the number of values to input: ");
        int count = input.nextInt();

        if (count <= 0) {
            System.out.println("No values to compare.");
        } else {
            System.out.print("Enter first value: ");
            int smallest = input.nextInt(); 

            for (int i = 2; i <= count; i++) {
                System.out.print("Enter next value: ");
                int current = input.nextInt();

                if (current < smallest) {
                    smallest = current;
                }
            }

            System.out.printf("%nThe smallest integer is: %d%n", smallest);
        }
        
        input.close();
    }
}