package chapter13;

import javax.swing.*;
import java.awt.*;

public class GridDrawRect extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cell = 30;
        int startX = 30;
        int startY = 30;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                g.drawRect(startX + col * cell, startY + row * cell, cell, cell);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.13 10x10 Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GridDrawRect());
        frame.setSize(380, 380);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
