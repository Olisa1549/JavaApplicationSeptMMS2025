package chapter13;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ScreenSaver extends JPanel {
    private final Random random = new Random();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 100; i++) {
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawLine(
                    random.nextInt(Math.max(1, getWidth())),
                    random.nextInt(Math.max(1, getHeight())),
                    random.nextInt(Math.max(1, getWidth())),
                    random.nextInt(Math.max(1, getHeight()))
            );
        }

        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.18 Screen Saver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ScreenSaver());
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
