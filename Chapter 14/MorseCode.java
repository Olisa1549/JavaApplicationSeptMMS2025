package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MorseCode {
    static final String[] CODES = {
        ".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--",
        "-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--..",
        "-----",".----","..---","...--","....-",".....","-....","--...","---..","----."
    };

    static String encode(String text) {
        StringBuilder result = new StringBuilder();
        for (String word : text.toUpperCase().split("\\s+")) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                int index = c >= 'A' && c <= 'Z' ? c-'A' : c >= '0' && c <= '9' ? 26+c-'0' : -1;
                if (index >= 0) {
                    if (result.length() > 0 && result.charAt(result.length()-1) != ' ') result.append(' ');
                    result.append(CODES[index]);
                }
            }
            result.append("   ");
        }
        return result.toString().trim();
    }

    static String decode(String code) {
        StringBuilder result = new StringBuilder();
        for (String word : code.trim().split(" {3}")) {
            for (String token : word.trim().split(" +")) {
                for (int i = 0; i < CODES.length; i++)
                    if (CODES[i].equals(token)) {
                        result.append(i < 26 ? (char)('A'+i) : (char)('0'+i-26));
                        break;
                    }
            }
            result.append(' ');
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("1. English -> Morse");
        System.out.println("2. Morse -> English");
        System.out.print("Choose: ");
        int choice = input.nextInt(); input.nextLine();

        System.out.print("Enter phrase: ");
        String text = input.nextLine();

        if (choice == 1) System.out.println(encode(text));
        else System.out.println(decode(text));
    }
}
