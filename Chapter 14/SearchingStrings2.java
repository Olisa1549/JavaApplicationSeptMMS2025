package chapter14;

import java.util.Scanner;

public class SearchingStrings2 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.print("Enter a line of text: ");
        String sentence = input.nextLine();

        sentence = sentence.toLowerCase();

        int[] letterCount = new int[26];

        for (int i = 0; i < 26; i++) {

            char letter = (char) ('a' + i);

            int position = sentence.indexOf(letter);

            while (position != -1) {

                letterCount[i]++;

                position = sentence.indexOf(letter, position + 1);
            }
        }

        System.out.println("\nLetter\tOccurrences");

        for (int i = 0; i < 26; i++) {

            char letter = (char) ('a' + i);

            System.out.println(letter + "\t" + letterCount[i]);
        }
    }
}