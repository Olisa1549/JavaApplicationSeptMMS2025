package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextAnalysis {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a line of text: ");
        String text = input.nextLine();

        // Part A
        int[] letters = new int[26];
        for (char c : text.toLowerCase().toCharArray())
            if (c >= 'a' && c <= 'z') letters[c - 'a']++;

        System.out.println("\nA) Letter counts");
        for (int i = 0; i < 26; i++)
            System.out.printf("%c: %d%n", (char)('a' + i), letters[i]);

        // Part B
        String[] words = text.split("\\s+");
        Map<Integer, Integer> lengths = new TreeMap<>();
        for (String w : words) {
            if (!w.isEmpty()) {
                int n = w.replaceAll("[^A-Za-z']", "").length();
                lengths.put(n, lengths.getOrDefault(n, 0) + 1);
            }
        }

        System.out.println("\nB) Word lengths");
        for (Map.Entry<Integer,Integer> e : lengths.entrySet())
            System.out.println(e.getKey() + " letters: " + e.getValue());

        // Part C
        Map<String,Integer> wordCounts = new LinkedHashMap<>();
        for (String w : words) {
            String clean = w.toLowerCase().replaceAll("[^a-z']", "");
            if (!clean.isEmpty())
                wordCounts.put(clean, wordCounts.getOrDefault(clean, 0) + 1);
        }

        System.out.println("\nC) Word occurrences");
        for (Map.Entry<String,Integer> e : wordCounts.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue());
    }
}
