package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class GridRectangle2D extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double cell = 30;
        double startX = 30;
        double startY = 30;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                g2.draw(new Rectangle2D.Double(
                        startX + col * cell, startY + row * cell, cell, cell));
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.14 10x10 Grid");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GridRectangle2D());
        frame.setSize(380, 380);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
