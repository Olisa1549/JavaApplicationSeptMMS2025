package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrintingDates {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter date (MM/DD/YYYY): ");
        String[] parts = input.nextLine().split("/");

        if (parts.length != 3) {
            System.out.println("Invalid date.");
            return;
        }

        String[] months = {"", "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        int month = Integer.parseInt(parts[0]);
        if (month < 1 || month > 12) {
            System.out.println("Invalid month.");
            return;
        }

        System.out.println(months[month] + " " + Integer.parseInt(parts[1]) + ", " + parts[2]);
    }
}
