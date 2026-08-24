package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;

public class PieChart extends JPanel {
    private final double[] values;

    public PieChart(double[] values) {
        this.values = values;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double total = 0;
        for (double value : values) {
            total += value;
        }

        double startAngle = 0;
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};

        for (int i = 0; i < values.length; i++) {
            double angle = values[i] / total * 360;

            g2.setColor(colors[i % colors.length]);
            g2.fill(new Arc2D.Double(100, 80, 300, 300,
                    startAngle, angle, Arc2D.PIE));

            startAngle += angle;
        }
    }

    public static void main(String[] args) {
        double[] values = new double[4];

        for (int i = 0; i < 4; i++) {
            values[i] = Double.parseDouble(
                    JOptionPane.showInputDialog("Enter number " + (i + 1) + ":"));
        }

        JFrame frame = new JFrame("13.27 Pie Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PieChart(values));
        frame.setSize(520, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
