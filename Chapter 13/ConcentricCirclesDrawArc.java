package chapter13;

import javax.swing.*;
import java.awt.*;

public class ConcentricCirclesDrawArc extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        for (int i = 0; i < 8; i++) {
            int diameter = 20 + i * 20;
            int x = centerX - diameter / 2;
            int y = centerY - diameter / 2;
            g.drawArc(x, y, diameter, diameter, 0, 360);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.6 Concentric Circles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ConcentricCirclesDrawArc());
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
