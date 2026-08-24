package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class DrawingSpiral extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        List<Point> points = new ArrayList<>();
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;

        for (double angle = 0; angle < 12 * Math.PI; angle += 0.08) {
            double radius = 4 + angle * 4;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            points.add(new Point(x, y));
        }

        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];

        for (int i = 0; i < points.size(); i++) {
            xPoints[i] = points.get(i).x;
            yPoints[i] = points.get(i).y;
        }

        g.drawPolyline(xPoints, yPoints, points.size());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.26 Spiral");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DrawingSpiral());
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
