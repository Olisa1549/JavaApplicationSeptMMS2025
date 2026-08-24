
package chapter14;

    import java.util.Scanner;

public class TokenizingTelephoneNumbers {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter telephone number in the form (555) 555-5555: ");
        String phoneNumber = input.nextLine();

        String[] parts = phoneNumber.split("[() -]+");

        String areaCode = parts[0];
        String firstThree = parts[1];
        String lastFour = parts[2];

        String sevenDigits = firstThree + lastFour;

        System.out.println("Area code: " + areaCode);
        System.out.println("Phone number: " + sevenDigits);
    }
}

