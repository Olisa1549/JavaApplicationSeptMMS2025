package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomStringSearch {
    public static int myIndexOf(String text, char target) {
        for (int i = 0; i < text.length(); i++)
            if (text.charAt(i) == target) return i;
        return -1;
    }

    public static int myIndexOf(String text, String target) {
        if (target.length() == 0) return 0;
        for (int i = 0; i <= text.length() - target.length(); i++) {
            int j = 0;
            while (j < target.length() && text.charAt(i + j) == target.charAt(j))
                j++;
            if (j == target.length()) return i;
        }
        return -1;
    }

    public static int myLastIndexOf(String text, char target) {
        for (int i = text.length() - 1; i >= 0; i--)
            if (text.charAt(i) == target) return i;
        return -1;
    }

    public static int myLastIndexOf(String text, String target) {
        if (target.length() == 0) return text.length();
        for (int i = text.length() - target.length(); i >= 0; i--) {
            int j = 0;
            while (j < target.length() && text.charAt(i + j) == target.charAt(j))
                j++;
            if (j == target.length()) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter text: ");
        String text = input.nextLine();
        System.out.print("Enter search text: ");
        String target = input.nextLine();

        System.out.println("Our indexOf: " + myIndexOf(text, target));
        System.out.println("Our lastIndexOf: " + myLastIndexOf(text, target));
    }
}
