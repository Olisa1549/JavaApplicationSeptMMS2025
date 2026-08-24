package chapter13;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class DrawingCubes extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        GeneralPath cube = new GeneralPath();

        cube.moveTo(100, 120);
        cube.lineTo(220, 120);
        cube.lineTo(220, 240);
        cube.lineTo(100, 240);
        cube.closePath();

        cube.moveTo(100, 120);
        cube.lineTo(150, 70);
        cube.lineTo(270, 70);
        cube.lineTo(220, 120);

        cube.moveTo(220, 120);
        cube.lineTo(270, 70);
        cube.lineTo(270, 190);
        cube.lineTo(220, 240);

        g2.draw(cube);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("13.16 Cube");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DrawingCubes());
        frame.setSize(380, 330);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
