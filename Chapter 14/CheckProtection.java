package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckProtection {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter dollar amount: ");
        double amount = input.nextDouble();

        String value = String.format("%.2f", amount);
        if (value.length() > 9) {
            System.out.println("Amount is too large.");
        } else {
            System.out.println(String.format("%9s", value).replace(' ', '*'));
        }
    }
}
