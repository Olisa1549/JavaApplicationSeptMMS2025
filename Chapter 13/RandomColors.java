package chapter13;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomColors extends JPanel {
    private final Random random = new Random();

    private final Color[] colors = {
        Color.BLACK, Color.BLUE, Color.CYAN, Color.DARK_GRAY,
        Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA,
        Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(Math.max(1, getWidth()));
            int y = random.nextInt(Math.max(1, getHeight()));
            int w = 20 + random.nextInt(100);
            int h = 20 + random.nextInt(100);

            g.setColor(colors[random.nextInt(colors.length)]);
            g.fillRect(x, y, w, h);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.29 Random Colors");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RandomColors());
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
