package hospital.gui.components;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/** Branded clinical-tech backdrop used by the LifeSaver sign-in experience. */
public class LifeSaverBrandPanel extends JPanel {
    private static final Color NAVY = new Color(8, 30, 58);
    private static final Color TEAL = new Color(9, 113, 128);
    private static final Color CYAN = new Color(71, 211, 211);

    public LifeSaverBrandPanel() {
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        g.setPaint(new GradientPaint(0, 0, NAVY, width, height, TEAL));
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(255, 255, 255, 18));
        for (int x = -height; x < width + height; x += 42) {
            g.drawLine(x, height, x + height, 0);
        }
        g.setColor(new Color(CYAN.getRed(), CYAN.getGreen(), CYAN.getBlue(), 32));
        g.fillOval(width - 220, -100, 330, 330);
        g.fillOval(width - 80, height - 170, 240, 240);
        g.setColor(new Color(255, 255, 255, 34));
        g.drawRoundRect(38, 38, Math.max(100, width - 76), Math.max(100, height - 76), 28, 28);
        g.dispose();
    }
}
