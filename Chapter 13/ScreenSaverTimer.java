package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ScreenSaverTimer extends JPanel implements ActionListener {
    private final Random random = new Random();
    private final Timer timer = new Timer(1000, this);

    public ScreenSaverTimer() {
        timer.start();
    }

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.19 Screen Saver Using Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ScreenSaverTimer());
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
