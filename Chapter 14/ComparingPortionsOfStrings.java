import java.util.Scanner;

public class ComparingPortionsOfStrings {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter first string: ");
        String input1 = input.nextLine();

        System.out.print("Enter second string: ");
        String input2 = input.nextLine();

        System.out.print("Enter starting index: ");
        int startIndex = input.nextInt();

        System.out.print("Enter number of characters to compare: ");
        int numberOfCharacters = input.nextInt();

        boolean result = input1.regionMatches(
                true,
                startIndex,
                input2,
                startIndex,
                numberOfCharacters
        );

        if (result) {
            System.out.println("The portions of the strings are equal.");
        } else {
            System.out.println("The portions of the strings are not equal.");
        }
    }
}