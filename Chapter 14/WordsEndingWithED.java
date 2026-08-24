package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WordsEndingWithED {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a line of text: ");
        String[] words = input.nextLine().split("\\s+");

        for (String word : words) {
            if (word.toUpperCase().endsWith("ED"))
                System.out.println (word);
        }
    }
}
