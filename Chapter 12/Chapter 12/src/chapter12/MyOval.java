
package chapter12;

import java.awt.Color;
import java.awt.Graphics;

public class MyOval extends MyShape {

    private boolean filled;

    public MyOval() {
        this(0, 0, 0, 0, Color.BLACK, false);
    }

    public MyOval(
            int x1,
            int y1,
            int x2,
            int y2,
            Color color,
            boolean filled) {

        super(x1, y1, x2, y2, color);
        this.filled = filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public boolean isFilled() {
        return filled;
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(getColor());

        int x = Math.min(getX1(), getX2());
        int y = Math.min(getY1(), getY2());

        int width = Math.abs(getX2() - getX1());
        int height = Math.abs(getY2() - getY1());

        if (filled) {
            g.fillOval(x, y, width, height);
        } else {
            g.drawOval(x, y, width, height);
        }
    }
}
