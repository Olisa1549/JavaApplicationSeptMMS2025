package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CrosswordPuzzleGenerator {
    static final int SIZE = 15;

    public static void main(String[] args) {
        char[][] grid = new char[SIZE][SIZE];
        for (char[] row : grid) Arrays.fill(row, '.');

        String[] words = {"JAVA","CODE","CLASS","STRING","METHOD","ARRAY","OBJECT","LOOP"};
        int row = 1;
        for (String word : words) {
            if (row >= SIZE) break;
            if (word.length() < SIZE) {
                for (int col = 0; col < word.length(); col++)
                    grid[row][col] = word.charAt(col);
                row += 2;
            }
        }

        for (char[] r : grid)
            System.out.println(new String(r));
    }
}
