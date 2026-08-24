
package chapter12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawPanel extends JPanel {

    public static final int LINE = 0;
    public static final int RECTANGLE = 1;
    public static final int OVAL = 2;

    private MyShape[] shapes;
    private int shapeCount;
    private int shapeType;
    private MyShape currentShape;
    private Color currentColor;
    private boolean filledShape;
    private JLabel statusLabel;

    public DrawPanel(JLabel statusLabel) {

        this.statusLabel = statusLabel;

        shapes = new MyShape[100];
        shapeCount = 0;
        shapeType = LINE;
        currentShape = null;
        currentColor = Color.BLACK;
        filledShape = false;

        setBackground(Color.WHITE);

        MouseHandler handler = new MouseHandler();

        addMouseListener(handler);
        addMouseMotionListener(handler);
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        // Draw all completed shapes
        for (int i = 0; i < shapeCount; i++) {

            if (shapes[i] != null) {
                shapes[i].draw(g);
            }
        }

        // Draw the shape currently being dragged
        if (currentShape != null) {
            currentShape.draw(g);
        }
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
    }

    public void setFilledShape(boolean filledShape) {
        this.filledShape = filledShape;
    }

    public void clearLastShape() {

        if (shapeCount > 0) {
            shapeCount--;
            shapes[shapeCount] = null;
        }

        repaint();
    }

    public void clearDrawing() {

        for (int i = 0; i < shapeCount; i++) {
            shapes[i] = null;
        }

        shapeCount = 0;

        repaint();
    }

    private class MouseHandler extends MouseAdapter
            implements MouseMotionListener {

        @Override
        public void mousePressed(MouseEvent event) {

            int x = event.getX();
            int y = event.getY();

            switch (shapeType) {

                case LINE:

                    currentShape = new MyLine(
                        x, y, x, y, currentColor
                    );

                    break;

                case RECTANGLE:

                    currentShape = new MyRectangle(
                        x, y, x, y,
                        currentColor,
                        filledShape
                    );

                    break;

                case OVAL:

                    currentShape = new MyOval(
                        x, y, x, y,
                        currentColor,
                        filledShape
                    );

                    break;
            }
        }

        @Override
        public void mouseReleased(MouseEvent event) {

            if (currentShape == null) {
                return;
            }

            currentShape.setX2(event.getX());
            currentShape.setY2(event.getY());

            if (shapeCount < shapes.length) {

                shapes[shapeCount] = currentShape;
                shapeCount++;
            }

            currentShape = null;

            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent event) {

            if (currentShape != null) {

                currentShape.setX2(event.getX());
                currentShape.setY2(event.getY());

                statusLabel.setText(
                    "Coordinates: (" +
                    event.getX() + ", " +
                    event.getY() + ")"
                );

                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {

            statusLabel.setText(
                "Coordinates: (" +
                event.getX() + ", " +
                event.getY() + ")"
            );
        }
    }
}

