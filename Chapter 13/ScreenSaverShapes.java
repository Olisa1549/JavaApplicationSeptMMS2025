package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ScreenSaverShapes extends JPanel implements ActionListener {
    private final Random random = new Random();
    private final Timer timer = new Timer(1000, this);

    public ScreenSaverShapes() {
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 25; i++) {
            int x = random.nextInt(Math.max(1, getWidth()));
            int y = random.nextInt(Math.max(1, getHeight()));
            int w = 20 + random.nextInt(100);
            int h = 20 + random.nextInt(100);

            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

            if (random.nextBoolean()) {
                g.drawRect(x, y, w, h);
            } else {
                g.drawOval(x, y, w, h);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.21 Screen Saver with Shapes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ScreenSaverShapes());
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
