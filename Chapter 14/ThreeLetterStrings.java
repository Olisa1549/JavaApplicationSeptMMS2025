package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThreeLetterStrings {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a five-letter word: ");
        String word = input.nextLine();

        if (word.length() != 5) {
            System.out.println("The word must contain exactly five letters.");
            return;
        }

        Set<String> results = new TreeSet<>();
        for (int a = 0; a < 5; a++)
            for (int b = 0; b < 5; b++)
                for (int c = 0; c < 5; c++)
                    if (a != b && a != c && b != c)
                        results.add("" + word.charAt(a) + word.charAt(b) + word.charAt(c));

        for (String result : results) System.out.println(result);
    }
}
