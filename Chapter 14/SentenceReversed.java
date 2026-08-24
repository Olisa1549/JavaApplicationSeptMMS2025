package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SentenceReversed {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a sentence: ");
        String[] words = input.nextLine().trim().split("\\s+");

        System.out.print("Reversed: ");
        for (int i = words.length - 1; i >= 0; i--) {
            System.out.print(words[i] + (i == 0 ? "" : " "));
        }
        System.out.println();
    }
}
