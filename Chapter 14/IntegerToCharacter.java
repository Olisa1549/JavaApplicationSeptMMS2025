package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IntegerToCharacter {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter an integer character code: ");
        int code = input.nextInt();

        if (code >= 0 && code <= 255)
            System.out.println("Character: " + (char) code);
        else
            System.out.println("Code must be from 0 to 255.");

        System.out.println("\nCodes 000 through 255:");
        for (int i = 0; i <= 255; i++)
            System.out.printf("%03d = %c%n", i, (char)i);
    }
}
