package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Random;

public class RandomLines extends JPanel {
    private final Random random = new Random();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < 20; i++) {
            g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g2.setStroke(new BasicStroke(1 + random.nextInt(6)));

            double x1 = random.nextInt(Math.max(1, getWidth()));
            double y1 = random.nextInt(Math.max(1, getHeight()));
            double x2 = random.nextInt(Math.max(1, getWidth()));
            double y2 = random.nextInt(Math.max(1, getHeight()));

            g2.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.8 Random Lines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RandomLines());
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
