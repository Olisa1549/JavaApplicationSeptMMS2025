package chapter14;

import java.util.Scanner;

public class ComparingStrings {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Input first word: ");
        String input1 = input.nextLine();

        System.out.print("Input second word: ");
        String input2 = input.nextLine();
        
        if (input1.compareTo(input2) < 0) {
            System.out.println("The first string is less than the second string");
        } else if (input1.compareTo(input2) == 0) {
            System.out.println("The first string is equal to the second string");
        } else {
            System.out.println("The first string is greater than the second string");
        
        }

    }
}
