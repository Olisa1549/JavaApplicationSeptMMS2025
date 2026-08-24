package chapter13;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JColorChooserShapes extends JPanel {
    private Color selectedColor = Color.BLUE;
    private final Random random = new Random();

    public JColorChooserShapes() {
        JButton button = new JButton("Choose Color");
        button.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choose Shape Color", selectedColor);
            if (color != null) {
                selectedColor = color;
                repaint();
            }
        });
        add(button);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(selectedColor);

        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(Math.max(1, getWidth()));
            int y = 50 + random.nextInt(Math.max(1, getHeight() - 50));
            int w = 20 + random.nextInt(100);
            int h = 20 + random.nextInt(100);
            g.drawRect(x, y, w, h);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.30 JColorChooser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JColorChooserShapes());
        frame.setSize(700, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
