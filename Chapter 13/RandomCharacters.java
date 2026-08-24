package chapter13;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class RandomCharacters extends JPanel {
    private final Random random = new Random();
    private final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 50; i++) {
            char character = characters.charAt(random.nextInt(characters.length()));
            int size = 12 + random.nextInt(40);
            String[] fonts = {"Serif", "SansSerif", "Monospaced"};
            int style = random.nextInt(3);
            int fontStyle = random.nextInt(4);

            g.setFont(new Font(fonts[style], fontStyle, size));
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

            int x = random.nextInt(Math.max(1, getWidth()));
            int y = random.nextInt(Math.max(30, getHeight()));
            g.drawString(String.valueOf(character), x, y);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.10 Random Characters");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new RandomCharacters());
        frame.setSize(600, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
