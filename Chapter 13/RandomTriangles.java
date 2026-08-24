package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.Random;

public class RandomTriangles extends JPanel {
    private final Random random = new Random();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < 10; i++) {
            GeneralPath triangle = new GeneralPath();
            triangle.moveTo(random.nextInt(getWidth()), random.nextInt(getHeight()));
            triangle.lineTo(random.nextInt(getWidth()), random.nextInt(getHeight()));
            triangle.lineTo(random.nextInt(getWidth()), random.nextInt(getHeight()));
            triangle.closePath();

            g2.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g2.fill(triangle);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.9 Random Triangles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RandomTriangles());
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
