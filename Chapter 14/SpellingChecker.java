package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SpellingChecker {
    static String[] wordList = {"default","computer","program","java","library","student","keyboard","school","software","database"};

    static boolean contains(String word) {
        for (String w : wordList) if (w.equalsIgnoreCase(word)) return true;
        return false;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a word: ");
        String word = input.nextLine();

        if (contains(word)) {
            System.out.println("Word is spelled correctly.");
            return;
        }

        System.out.println("Word is not spelled correctly.");
        for (int i = 0; i < word.length()-1; i++) {
            char[] chars = word.toCharArray();
            char temp = chars[i]; chars[i] = chars[i+1]; chars[i+1] = temp;
            String suggestion = new String(chars);
            if (contains(suggestion))
                System.out.println("Did you mean \"" + suggestion + "\"?");
        }
    }
}
