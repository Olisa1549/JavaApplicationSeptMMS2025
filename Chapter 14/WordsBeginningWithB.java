package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WordsBeginningWithB {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a line of text: ");
        String[] words = input.nextLine().split("\\s+");

        for (String word : words) {
            if (!word.isEmpty() && word.toLowerCase().startsWith("b"))
                System.out.println(word);
        }
    }
}
