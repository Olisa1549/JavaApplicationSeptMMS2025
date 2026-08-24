package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CirclesEllipse2D extends JPanel {
    private final double radius;
    private final double centerX;
    private final double centerY;

    public CirclesEllipse2D(double radius, double centerX, double centerY) {
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        double diameter = 2 * radius;
        double circumference = 2 * Math.PI * radius;
        double area = Math.PI * radius * radius;

        g2.draw(new Ellipse2D.Double(
                centerX - radius, centerY - radius, diameter, diameter));

        g2.drawString(String.format("Diameter: %.2f", diameter), 20, 25);
        g2.drawString(String.format("Circumference: %.2f", circumference), 20, 45);
        g2.drawString(String.format("Area: %.2f", area), 20, 65);
    }

    public static void main(String[] args) {
        double radius = Double.parseDouble(
                JOptionPane.showInputDialog("Enter radius:"));
        double x = Double.parseDouble(
                JOptionPane.showInputDialog("Enter center X coordinate:"));
        double y = Double.parseDouble(
                JOptionPane.showInputDialog("Enter center Y coordinate:"));

        JFrame frame = new JFrame("13.17 Circle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CirclesEllipse2D(radius, x, y));
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
