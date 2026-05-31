import java.util.Scanner;

public class EncryptNumber {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter a 4-digit integer: ");
        int number = input.nextInt();

        int d1 = number / 1000;
        int d2 = (number / 100) % 10;
        int d3 = (number / 10) % 10;
        int d4 = number % 10;

        // Step 1: add 7 and mod 10
        d1 = (d1 + 7) % 10;
        d2 = (d2 + 7) % 10;
        d3 = (d3 + 7) % 10;
        d4 = (d4 + 7) % 10;

        // Step 2: swap 1st <-> 3rd, 2nd <-> 4th
        int temp;

        temp = d1;
        d1 = d3;
        d3 = temp;

        temp = d2;
        d2 = d4;
        d4 = temp;

        System.out.println("Encrypted number: " +
                d1 + d2 + d3 + d4);

        input.close();
    }
}

