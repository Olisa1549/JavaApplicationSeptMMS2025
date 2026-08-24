package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class GridLine2D extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int size = 320;
        int cell = size / 8;
        int startX = 40;
        int startY = 40;

        for (int i = 0; i <= 8; i++) {
            g2.draw(new Line2D.Double(startX, startY + i * cell, startX + size, startY + i * cell));
            g2.draw(new Line2D.Double(startX + i * cell, startY, startX + i * cell, startY + size));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.12 8x8 Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GridLine2D());
        frame.setSize(420, 420);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
