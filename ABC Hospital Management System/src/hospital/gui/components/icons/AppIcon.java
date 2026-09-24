package hospital.gui.components.icons;

import hospital.gui.theme.Theme;
import hospital.gui.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class AppIcon extends JComponent
        implements ThemeManager.ThemeChangeListener {

    public enum IconType {
        DASHBOARD,
        PATIENTS,
        DOCTORS,
        APPOINTMENTS,
        RECORDS,
        PHARMACY,
        FINANCE,
        REPORTS,
        SETTINGS,
        LOGOUT,
        SEARCH,
        NOTIFICATION,
        USER,
        PLUS
    }

    private final IconType type;

    private Color iconColor;

    private int iconSize = 22;

    public AppIcon(IconType type) {

        this.type = type;

        setOpaque(false);

        setPreferredSize(
                new Dimension(
                        iconSize,
                        iconSize
                )
        );

        setMinimumSize(
                new Dimension(
                        iconSize,
                        iconSize
                )
        );

        setMaximumSize(
                new Dimension(
                        iconSize,
                        iconSize
                )
        );

        iconColor =
                ThemeManager
                        .getCurrentTheme()
                        .getTextColor();

        ThemeManager.addThemeChangeListener(this);
    }

    // =========================================================
    // PAINT
    // =========================================================

    @Override
    protected void paintComponent(
            Graphics graphics) {

        super.paintComponent(graphics);

        Graphics2D g =
                (Graphics2D) graphics.create();

        // -----------------------------------------------------
        // Smooth rendering
        // -----------------------------------------------------

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g.setRenderingHint(
                RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE
        );

        g.setStroke(
                new BasicStroke(
                        2f,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                )
        );

        g.setColor(iconColor);

        int width = getWidth();
        int height = getHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        switch (type) {

            case DASHBOARD:
                drawDashboard(
                        g,
                        centerX,
                        centerY
                );
                break;

            case PATIENTS:
                drawPatients(
                        g,
                        centerX,
                        centerY
                );
                break;

            case DOCTORS:
                drawDoctors(
                        g,
                        centerX,
                        centerY
                );
                break;

            case APPOINTMENTS:
                drawAppointments(
                        g,
                        centerX,
                        centerY
                );
                break;

            case RECORDS:
                drawRecords(
                        g,
                        centerX,
                        centerY
                );
                break;

            case PHARMACY:
                drawPharmacy(
                        g,
                        centerX,
                        centerY
                );
                break;

            case FINANCE:
                drawFinance(
                        g,
                        centerX,
                        centerY
                );
                break;

            case REPORTS:
                drawReports(
                        g,
                        centerX,
                        centerY
                );
                break;

            case SETTINGS:
                drawSettings(
                        g,
                        centerX,
                        centerY
                );
                break;

            case LOGOUT:
                drawLogout(
                        g,
                        centerX,
                        centerY
                );
                break;

            case SEARCH:
                drawSearch(
                        g,
                        centerX,
                        centerY
                );
                break;

            case NOTIFICATION:
                drawNotification(
                        g,
                        centerX,
                        centerY
                );
                break;

            case USER:
                drawUser(
                        g,
                        centerX,
                        centerY
                );
                break;

            case PLUS:
                drawPlus(
                        g,
                        centerX,
                        centerY
                );
                break;
        }

        g.dispose();
    }

    // =========================================================
    // DASHBOARD ICON
    // =========================================================

    private void drawDashboard(
            Graphics2D g,
            int x,
            int y) {

        g.drawRoundRect(
                x - 8,
                y - 8,
                7,
                7,
                2,
                2
        );

        g.drawRoundRect(
                x + 1,
                y - 8,
                7,
                7,
                2,
                2
        );

        g.drawRoundRect(
                x - 8,
                y + 1,
                7,
                7,
                2,
                2
        );

        g.drawRoundRect(
                x + 1,
                y + 1,
                7,
                7,
                2,
                2
        );
    }

    // =========================================================
    // PATIENT ICON
    // =========================================================

    private void drawPatients(
            Graphics2D g,
            int x,
            int y) {

        g.drawOval(
                x - 4,
                y - 9,
                8,
                8
        );

        g.drawArc(
                x - 8,
                y,
                16,
                13,
                0,
                180
        );

        g.drawOval(
                x + 5,
                y - 5,
                6,
                6
        );

        g.drawArc(
                x + 2,
                y + 2,
                12,
                9,
                0,
                180
        );
    }

    // =========================================================
    // DOCTOR ICON
    // =========================================================

    private void drawDoctors(
            Graphics2D g,
            int x,
            int y) {

        g.drawOval(
                x - 4,
                y - 9,
                8,
                8
        );

        g.drawArc(
                x - 9,
                y,
                18,
                14,
                0,
                180
        );

        // Medical cross
        g.drawLine(
                x + 6,
                y - 3,
                x + 6,
                y + 5
        );

        g.drawLine(
                x + 2,
                y + 1,
                x + 10,
                y + 1
        );
    }

    // =========================================================
    // APPOINTMENT ICON
    // =========================================================

    private void drawAppointments(
            Graphics2D g,
            int x,
            int y) {

        g.drawRoundRect(
                x - 9,
                y - 7,
                18,
                16,
                3,
                3
        );

        g.drawLine(
                x - 5,
                y - 10,
                x - 5,
                y - 4
        );

        g.drawLine(
                x + 5,
                y - 10,
                x + 5,
                y - 4
        );

        g.drawLine(
                x - 8,
                y - 1,
                x + 8,
                y - 1
        );

        g.drawOval(
                x - 2,
                y + 2,
                4,
                4
        );
    }

    // =========================================================
    // MEDICAL RECORD ICON
    // =========================================================

    private void drawRecords(
            Graphics2D g,
            int x,
            int y) {

        g.drawRoundRect(
                x - 8,
                y - 10,
                16,
                20,
                2,
                2
        );

        g.drawLine(
                x - 4,
                y - 5,
                x + 4,
                y - 5
        );

        g.drawLine(
                x - 4,
                y,
                x + 4,
                y
        );

        g.drawLine(
                x - 4,
                y + 5,
                x + 2,
                y + 5
        );
    }

    // =========================================================
    // PHARMACY ICON
    // =========================================================

    private void drawPharmacy(
            Graphics2D g,
            int x,
            int y) {

        g.drawRoundRect(
                x - 9,
                y - 9,
                18,
                18,
                4,
                4
        );

        g.drawLine(
                x,
                y - 5,
                x,
                y + 5
        );

        g.drawLine(
                x - 5,
                y,
                x + 5,
                y
        );
    }

    // =========================================================
    // FINANCE ICON
    // =========================================================

    private void drawFinance(
            Graphics2D g,
            int x,
            int y) {

        g.drawOval(
                x - 8,
                y - 8,
                16,
                16
        );

        g.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        FontMetrics metrics =
                g.getFontMetrics();

        String symbol = "₦";

        int textWidth =
                metrics.stringWidth(symbol);

        int textHeight =
                metrics.getAscent();

        g.drawString(
                symbol,
                x - textWidth / 2,
                y + textHeight / 2 - 1
        );
    }

    // =========================================================
    // REPORT ICON
    // =========================================================

    private void drawReports(
            Graphics2D g,
            int x,
            int y) {

        g.drawRect(
                x - 8,
                y - 9,
                16,
                18
        );

        g.drawLine(
                x - 4,
                y + 5,
                x - 4,
                y
        );

        g.drawLine(
                x,
                y + 5,
                x,
                y - 3
        );

        g.drawLine(
                x + 4,
                y + 5,
                x + 4,
                y - 6
        );
    }

    // =========================================================
    // SETTINGS ICON
    // =========================================================

    private void drawSettings(
            Graphics2D g,
            int x,
            int y) {

        g.drawOval(
                x - 7,
                y - 7,
                14,
                14
        );

        g.drawOval(
                x - 2,
                y - 2,
                4,
                4
        );

        // Gear teeth
        for (int i = 0; i < 8; i++) {

            double angle =
                    i * Math.PI / 4;

            int x1 =
                    x + (int)
                            (Math.cos(angle) * 9);

            int y1 =
                    y + (int)
                            (Math.sin(angle) * 9);

            int x2 =
                    x + (int)
                            (Math.cos(angle) * 11);

            int y2 =
                    y + (int)
                            (Math.sin(angle) * 11);

            g.drawLine(
                    x1,
                    y1,
                    x2,
                    y2
            );
        }
    }

    // =========================================================
    // LOGOUT ICON
    // =========================================================

    private void drawLogout(
            Graphics2D g,
            int x,
            int y) {

        // Door
        g.drawRoundRect(
                x - 8,
                y - 9,
                8,
                18,
                2,
                2
        );

        // Arrow
        g.drawLine(
                x - 1,
                y,
                x + 9,
                y
        );

        g.drawLine(
                x + 5,
                y - 4,
                x + 9,
                y
        );

        g.drawLine(
                x + 5,
                y + 4,
                x + 9,
                y
        );
    }

    // =========================================================
    // SEARCH ICON
    // =========================================================

    private void drawSearch(
            Graphics2D g,
            int x,
            int y) {

        g.drawOval(
                x - 8,
                y - 8,
                12,
                12
        );

        g.drawLine(
                x + 2,
                y + 2,
                x + 9,
                y + 9
        );
    }

    // =========================================================
    // NOTIFICATION ICON
    // =========================================================

    private void drawNotification(
            Graphics2D g,
            int x,
            int y) {

        g.drawArc(
                x - 7,
                y - 8,
                14,
                16,
                0,
                180
        );

        g.drawLine(
                x - 7,
                y,
                x - 7,
                y + 5
        );

        g.drawLine(
                x + 7,
                y,
                x + 7,
                y + 5
        );

        g.drawLine(
                x - 9,
                y + 5,
                x + 9,
                y + 5
        );

        g.drawArc(
                x - 3,
                y + 5,
                6,
                5,
                180,
                180
        );
    }

    // =========================================================
    // USER ICON
    // =========================================================

    private void drawUser(
            Graphics2D g,
            int x,
            int y) {

        g.drawOval(
                x - 4,
                y - 9,
                8,
                8
        );

        g.drawArc(
                x - 8,
                y,
                16,
                13,
                0,
                180
        );
    }

    // =========================================================
    // PLUS ICON
    // =========================================================

    private void drawPlus(
            Graphics2D g,
            int x,
            int y) {

        g.drawLine(
                x - 7,
                y,
                x + 7,
                y
        );

        g.drawLine(
                x,
                y - 7,
                x,
                y + 7
        );
    }

    // =========================================================
    // THEME
    // =========================================================

    public void setIconColor(
            Color color) {

        if (color != null) {
            iconColor = color;
            repaint();
        }
    }

    @Override
    public void themeChanged(
            Theme newTheme) {

        if (newTheme != null) {

            iconColor =
                    newTheme.getTextColor();

            repaint();
        }
    }

    // =========================================================
    // CLEANUP
    // =========================================================

    public void dispose() {

        ThemeManager.removeThemeChangeListener(
                this
        );
    }
}