
package chapter12;

import javax.swing.*;
import java.awt.*;

public class EcoFont extends JFrame {

    private JTextArea textArea;
    private JButton increaseButton;
    private JButton decreaseButton;
    private JLabel sizeLabel;

    private int fontSize = 9;

    private Font ecoFont;

    public EcoFont() {

        setTitle("Ecofont");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Find Ecofont
        ecoFont = findEcofont();

        // Text area
        textArea = new JTextArea();

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setText(
            "Ecofont is designed to reduce the amount of ink " +
            "used when printing. Type your own text here."
        );

        textArea.setFont(
            ecoFont.deriveFont((float) fontSize)
        );

        // Label
        sizeLabel = new JLabel(
            "Font Size: " + fontSize + " pt"
        );

        // Buttons
        decreaseButton =
            new JButton("Decrease Font Size");

        increaseButton =
            new JButton("Increase Font Size");

        // Button panel
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(decreaseButton);
        buttonPanel.add(sizeLabel);
        buttonPanel.add(increaseButton);

        // Frame layout
        add(
            new JScrollPane(textArea),
            BorderLayout.CENTER
        );

        add(
            buttonPanel,
            BorderLayout.SOUTH
        );

        // Increase button
        increaseButton.addActionListener(e -> {

            fontSize++;

            updateFont();
        });

        // Decrease button
        decreaseButton.addActionListener(e -> {

            if (fontSize > 1) {

                fontSize--;

                updateFont();
            }
        });
    }

    // Find the installed Ecofont
    private Font findEcofont() {

        GraphicsEnvironment environment =
            GraphicsEnvironment
                .getLocalGraphicsEnvironment();

        Font[] fonts =
            environment.getAllFonts();

        for (Font font : fonts) {

            String name =
                font.getName().toLowerCase();

            if (name.contains("eco") ||
                name.contains("spranq")) {

                return font;
            }
        }

        // If Ecofont isn't found
        JOptionPane.showMessageDialog(
            this,
            "Ecofont was not found.\n" +
            "Please install Spranq_eco_sans_regular.ttf.",
            "Font Not Found",
            JOptionPane.WARNING_MESSAGE
        );

        // Fallback font
        return new Font(
            "SansSerif",
            Font.PLAIN,
            fontSize
        );
    }

    // Update font size
    private void updateFont() {

        textArea.setFont(
            ecoFont.deriveFont((float) fontSize)
        );

        sizeLabel.setText(
            "Font Size: " + fontSize + " pt"
        );
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            EcoFont gui =
                new EcoFont();

            gui.setVisible(true);
        });
    }
}
