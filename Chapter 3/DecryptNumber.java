import java.util.Scanner;

public class DecryptNumber {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter encrypted 4-digit integer: ");
        int number = input.nextInt();

        int d1 = number / 1000;
        int d2 = (number / 100) % 10;
        int d3 = (number / 10) % 10;
        int d4 = number % 10;

        // Step 1: swap back
        int temp;

        temp = d1;
        d1 = d3;
        d3 = temp;

        temp = d2;
        d2 = d4;
        d4 = temp;

        // Step 2: reverse (digit + 7) % 10 using +3
        d1 = (d1 + 3) % 10;
        d2 = (d2 + 3) % 10;
        d3 = (d3 + 3) % 10;
        d4 = (d4 + 3) % 10;

        System.out.println("Decrypted number: " +
                d1 + d2 + d3 + d4);

        input.close();
    }
}
