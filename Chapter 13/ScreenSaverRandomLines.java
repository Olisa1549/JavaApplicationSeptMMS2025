package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class ScreenSaverRandomLines extends JPanel implements ActionListener {
    private final Random random = new Random();
    private final Timer timer = new Timer(1000, this);
    private int lineCount = 100;
    private final JTextField lineField = new JTextField("100", 8);

    public ScreenSaverRandomLines() {
        timer.start();
        lineField.addActionListener(e -> {
            try {
                lineCount = Math.max(1, Integer.parseInt(lineField.getText()));
            } catch (NumberFormatException ex) {
                lineField.setText(String.valueOf(lineCount));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < lineCount; i++) {
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
        JFrame frame = new JFrame("13.20 Screen Saver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ScreenSaverRandomLines panel = new ScreenSaverRandomLines();
        frame.add(panel, BorderLayout.CENTER);
        frame.add(panel.lineField, BorderLayout.SOUTH);

        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
