package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class ConcentricCirclesEllipse2D extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (int i = 0; i < 8; i++) {
            double diameter = 20 + i * 20;
            double x = centerX - diameter / 2;
            double y = centerY - diameter / 2;
            g2.draw(new Ellipse2D.Double(x, y, diameter, diameter));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.7 Concentric Circles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ConcentricCirclesEllipse2D());
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
