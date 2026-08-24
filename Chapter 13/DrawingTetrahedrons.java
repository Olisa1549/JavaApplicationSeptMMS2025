package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class DrawingTetrahedrons extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        GeneralPath tetrahedron = new GeneralPath();
        tetrahedron.moveTo(200, 60);
        tetrahedron.lineTo(100, 280);
        tetrahedron.lineTo(300, 280);
        tetrahedron.closePath();

        tetrahedron.moveTo(200, 60);
        tetrahedron.lineTo(200, 190);
        tetrahedron.moveTo(100, 280);
        tetrahedron.lineTo(200, 190);
        tetrahedron.lineTo(300, 280);

        g2.draw(tetrahedron);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.15 Tetrahedron");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DrawingTetrahedrons());
        frame.setSize(400, 380);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
