package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CheckAmountInWords {
    static final String[] ones = {"ZERO","ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE",
            "TEN","ELEVEN","TWELVE","THIRTEEN","FOURTEEN","FIFTEEN","SIXTEEN","SEVENTEEN","EIGHTEEN","NINETEEN"};
    static final String[] tens = {"","","TWENTY","THIRTY","FORTY","FIFTY","SIXTY","SEVENTY","EIGHTY","NINETY"};

    static String words(int n) {
        if (n < 20) return ones[n];
        if (n < 100) return tens[n/10] + (n%10 == 0 ? "" : " " + ones[n%10]);
        return ones[n/100] + " HUNDRED" + (n%100 == 0 ? "" : " " + words(n%100));
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter amount less than $1000 (e.g. 112.43): ");
        double amount = input.nextDouble();

        if (amount < 0 || amount >= 1000) {
            System.out.println("Amount must be from $0.00 to $999.99.");
            return;
        }

        int dollars = (int) amount;
        int cents = (int)Math.round((amount - dollars) * 100);

        if (cents == 100) { dollars++; cents = 0; }

        System.out.printf("%s and %02d/100%n", words(dollars), cents);
    }
}
