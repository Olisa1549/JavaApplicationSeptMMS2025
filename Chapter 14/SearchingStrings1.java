package chapter14;

import java.util.Scanner;

public class SearchingStrings1 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter a line of text: ");
        String sentence = input.nextLine();

        System.out.print("Enter the character to search for: ");
        String search = input.nextLine();

        int count = 0;
        int position = sentence.indexOf(search);

        while (position != -1) {

            count++;

            position = sentence.indexOf(search, position + 1);
        }

        System.out.println("The character occurs " + count + " times.");
    }
}

