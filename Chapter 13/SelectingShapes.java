package chapter13;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SelectingShapes extends JPanel {
    private String selectedShape = "Rectangle";
    private final Random random = new Random();

    public SelectingShapes() {
        JComboBox<String> comboBox = new JComboBox<>(
                new String[]{"Rectangle", "Oval", "Line"});
        comboBox.addActionListener(e -> {
            selectedShape = (String) comboBox.getSelectedItem();
            repaint();
        });
        add(comboBox);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(Math.max(1, getWidth()));
            int y = 40 + random.nextInt(Math.max(1, getHeight() - 40));
            int w = 20 + random.nextInt(100);
            int h = 20 + random.nextInt(100);

            if ("Rectangle".equals(selectedShape)) {
                g.drawRect(x, y, w, h);
            } else if ("Oval".equals(selectedShape)) {
                g.drawOval(x, y, w, h);
            } else {
                g.drawLine(x, y, x + w, y + h);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.28 Selecting Shapes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SelectingShapes());
        frame.setSize(700, 550);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
