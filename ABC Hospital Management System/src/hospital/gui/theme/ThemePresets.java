package hospital.gui.theme;

import java.awt.Color;

/**
 * Predefined themes available in the application.
 */
public final class ThemePresets {

    private ThemePresets() {
        // Prevent creating instances.
    }

    /**
     * Ocean Blue
     * Clean, professional medical appearance.
     */
    public static Theme oceanBlue() {

        return new Theme(
                "Ocean Blue",

                new Color(30, 90, 150),      // Primary
                new Color(24, 70, 120),      // Secondary
                new Color(45, 125, 210),     // Accent

                new Color(245, 247, 250),    // Background
                Color.WHITE,                 // Surface
                Color.WHITE,                 // Card

                new Color(35, 45, 55),       // Text
                new Color(100, 110, 120),    // Secondary text

                new Color(220, 225, 230),    // Border

                new Color(40, 167, 69),      // Success
                new Color(245, 166, 35),     // Warning
                new Color(220, 53, 69),      // Error
                new Color(23, 162, 184)      // Info
        );
    }

    /**
     * Emerald Care
     * Fresh healthcare-oriented theme.
     */
    public static Theme emeraldCare() {

        return new Theme(
                "Emerald Care",

                new Color(25, 120, 95),
                new Color(20, 90, 72),
                new Color(40, 180, 140),

                new Color(245, 249, 247),
                Color.WHITE,
                Color.WHITE,

                new Color(35, 50, 45),
                new Color(100, 115, 108),

                new Color(215, 225, 220),

                new Color(40, 167, 69),
                new Color(245, 166, 35),
                new Color(220, 53, 69),
                new Color(23, 162, 184)
        );
    }

    /**
     * Teal Horizon
     * Modern and calm.
     */
    public static Theme tealHorizon() {

        return new Theme(
                "Teal Horizon",

                new Color(20, 110, 125),
                new Color(15, 80, 95),
                new Color(35, 170, 185),

                new Color(244, 249, 250),
                Color.WHITE,
                Color.WHITE,

                new Color(30, 50, 55),
                new Color(95, 115, 120),

                new Color(215, 225, 228),

                new Color(40, 167, 69),
                new Color(245, 166, 35),
                new Color(220, 53, 69),
                new Color(23, 162, 184)
        );
    }

    /**
     * Royal Violet
     * Premium and distinctive.
     */
    public static Theme royalViolet() {

        return new Theme(
                "Royal Violet",

                new Color(92, 60, 150),
                new Color(70, 45, 115),
                new Color(125, 85, 200),

                new Color(247, 245, 250),
                Color.WHITE,
                Color.WHITE,

                new Color(45, 40, 55),
                new Color(105, 100, 115),

                new Color(225, 220, 230),

                new Color(40, 167, 69),
                new Color(245, 166, 35),
                new Color(220, 53, 69),
                new Color(23, 162, 184)
        );
    }

    /**
     * Midnight Slate
     * Dark professional theme.
     */
    public static Theme midnightSlate() {

        return new Theme(
                "Midnight Slate",

                new Color(45, 55, 72),
                new Color(30, 38, 52),
                new Color(75, 145, 210),

                new Color(25, 29, 36),
                new Color(32, 37, 46),
                new Color(38, 44, 54),

                new Color(240, 243, 247),
                new Color(165, 175, 190),

                new Color(65, 73, 85),

                new Color(55, 190, 105),
                new Color(245, 175, 55),
                new Color(235, 80, 90),
                new Color(50, 175, 200)
        );
    }
}