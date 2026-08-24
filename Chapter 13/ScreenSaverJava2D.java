package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ScreenSaverJava2D extends JPanel implements ActionListener {
    private final Random random = new Random();
    private final Timer timer = new Timer(1000, this);

    public ScreenSaverJava2D() {
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(Math.max(1, getWidth()));
            int y = random.nextInt(Math.max(1, getHeight()));
            int w = 30 + random.nextInt(100);
            int h = 30 + random.nextInt(100);

            Color c1 = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            Color c2 = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));

            g2.setPaint(new GradientPaint(x, y, c1, x + w, y + h, c2, true));

            if (random.nextBoolean()) {
                g2.fillRect(x, y, w, h);
            } else {
                g2.fillOval(x, y, w, h);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.22 Screen Saver Java 2D");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ScreenSaverJava2D());
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
