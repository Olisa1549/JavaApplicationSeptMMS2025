package chapter13;

import javax.swing.*;
import java.awt.*;

public class GridDrawLine extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int size = 320;
        int cell = size / 8;
        int startX = 40;
        int startY = 40;

        for (int i = 0; i <= 8; i++) {
            g.drawLine(startX, startY + i * cell, startX + size, startY + i * cell);
            g.drawLine(startX + i * cell, startY, startX + i * cell, startY + size);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.11 8x8 Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GridDrawLine());
        frame.setSize(420, 420);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
