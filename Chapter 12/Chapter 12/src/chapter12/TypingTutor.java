
package chapter12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class TypingTutor extends JFrame {

    private final JTextArea textArea;
    private final JLabel targetLabel;
    private final JLabel statsLabel;

    private final Map<Integer, JButton> keyButtons = new HashMap<>();
    private final Map<Integer, Color> originalColors = new HashMap<>();

    private int correct = 0;
    private int incorrect = 0;
    private int targetPosition = 0;

    // Practice phrase
    private final String targetText =
            "The quick brown fox jumped over a lazy dog.";

    public TypingTutor() {
        super("Typing Application");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        // -------------------------------------------------
        // Top panel
        // -------------------------------------------------

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));

        JLabel instruction = new JLabel(
                "<html>Type some text using your keyboard. "
                + "The keys you press will be highlighted and "
                + "the text will be displayed.<br>"
                + "Note: Clicking the buttons with your mouse "
                + "will not perform any action.</html>"
        );

        topPanel.add(instruction, BorderLayout.NORTH);

        targetLabel = new JLabel(
                "Practice: " + targetText
        );

        targetLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        topPanel.add(targetLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // -------------------------------------------------
        // Text area
        // -------------------------------------------------

        textArea = new JTextArea(6, 50);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);

        add(scrollPane, BorderLayout.CENTER);

        // -------------------------------------------------
        // Keyboard
        // -------------------------------------------------

        JPanel keyboard = createKeyboard();

        add(keyboard, BorderLayout.SOUTH);

        // -------------------------------------------------
        // Statistics
        // -------------------------------------------------

        statsLabel = new JLabel(
                "Correct: 0    Incorrect: 0    Accuracy: 100%"
        );

        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statsLabel, BorderLayout.EAST);

        // Give the text area focus
        SwingUtilities.invokeLater(() -> textArea.requestFocusInWindow());

        // Listen for real keyboard input
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(this::handleKeyEvent);
    }

    // =====================================================
    // Create the virtual keyboard
    // =====================================================

    private JPanel createKeyboard() {

        JPanel keyboard = new JPanel();
        keyboard.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.fill = GridBagConstraints.BOTH;

        // Row 1
        addKey(keyboard, "~", KeyEvent.VK_BACK_QUOTE, 0, 0, 1);
        addKey(keyboard, "1", KeyEvent.VK_1, 1, 0, 1);
        addKey(keyboard, "2", KeyEvent.VK_2, 2, 0, 1);
        addKey(keyboard, "3", KeyEvent.VK_3, 3, 0, 1);
        addKey(keyboard, "4", KeyEvent.VK_4, 4, 0, 1);
        addKey(keyboard, "5", KeyEvent.VK_5, 5, 0, 1);
        addKey(keyboard, "6", KeyEvent.VK_6, 6, 0, 1);
        addKey(keyboard, "7", KeyEvent.VK_7, 7, 0, 1);
        addKey(keyboard, "8", KeyEvent.VK_8, 8, 0, 1);
        addKey(keyboard, "9", KeyEvent.VK_9, 9, 0, 1);
        addKey(keyboard, "0", KeyEvent.VK_0, 10, 0, 1);
        addKey(keyboard, "-", KeyEvent.VK_MINUS, 11, 0, 1);
        addKey(keyboard, "+", KeyEvent.VK_EQUALS, 12, 0, 1);
        addKey(keyboard, "Backspace", KeyEvent.VK_BACK_SPACE, 13, 0, 2);

        // Row 2
        addKey(keyboard, "Tab", KeyEvent.VK_TAB, 0, 1, 2);
        addKey(keyboard, "Q", KeyEvent.VK_Q, 2, 1, 1);
        addKey(keyboard, "W", KeyEvent.VK_W, 3, 1, 1);
        addKey(keyboard, "E", KeyEvent.VK_E, 4, 1, 1);
        addKey(keyboard, "R", KeyEvent.VK_R, 5, 1, 1);
        addKey(keyboard, "T", KeyEvent.VK_T, 6, 1, 1);
        addKey(keyboard, "Y", KeyEvent.VK_Y, 7, 1, 1);
        addKey(keyboard, "U", KeyEvent.VK_U, 8, 1, 1);
        addKey(keyboard, "I", KeyEvent.VK_I, 9, 1, 1);
        addKey(keyboard, "O", KeyEvent.VK_O, 10, 1, 1);
        addKey(keyboard, "P", KeyEvent.VK_P, 11, 1, 1);
        addKey(keyboard, "[", KeyEvent.VK_OPEN_BRACKET, 12, 1, 1);
        addKey(keyboard, "]", KeyEvent.VK_CLOSE_BRACKET, 13, 1, 1);
        addKey(keyboard, "\\", KeyEvent.VK_BACK_SLASH, 14, 1, 1);

        // Row 3
        addKey(keyboard, "Caps", KeyEvent.VK_CAPS_LOCK, 0, 2, 2);
        addKey(keyboard, "A", KeyEvent.VK_A, 2, 2, 1);
        addKey(keyboard, "S", KeyEvent.VK_S, 3, 2, 1);
        addKey(keyboard, "D", KeyEvent.VK_D, 4, 2, 1);
        addKey(keyboard, "F", KeyEvent.VK_F, 5, 2, 1);
        addKey(keyboard, "G", KeyEvent.VK_G, 6, 2, 1);
        addKey(keyboard, "H", KeyEvent.VK_H, 7, 2, 1);
        addKey(keyboard, "J", KeyEvent.VK_J, 8, 2, 1);
        addKey(keyboard, "K", KeyEvent.VK_K, 9, 2, 1);
        addKey(keyboard, "L", KeyEvent.VK_L, 10, 2, 1);
        addKey(keyboard, ";", KeyEvent.VK_SEMICOLON, 11, 2, 1);
        addKey(keyboard, "'", KeyEvent.VK_QUOTE, 12, 2, 1);
        addKey(keyboard, "Enter", KeyEvent.VK_ENTER, 13, 2, 2);

        // Row 4
        addKey(keyboard, "Shift", KeyEvent.VK_SHIFT, 0, 3, 2);
        addKey(keyboard, "Z", KeyEvent.VK_Z, 2, 3, 1);
        addKey(keyboard, "X", KeyEvent.VK_X, 3, 3, 1);
        addKey(keyboard, "C", KeyEvent.VK_C, 4, 3, 1);
        addKey(keyboard, "V", KeyEvent.VK_V, 5, 3, 1);
        addKey(keyboard, "B", KeyEvent.VK_B, 6, 3, 1);
        addKey(keyboard, "N", KeyEvent.VK_N, 7, 3, 1);
        addKey(keyboard, "M", KeyEvent.VK_M, 8, 3, 1);
        addKey(keyboard, ",", KeyEvent.VK_COMMA, 9, 3, 1);
        addKey(keyboard, ".", KeyEvent.VK_PERIOD, 10, 3, 1);
        addKey(keyboard, "?", KeyEvent.VK_SLASH, 11, 3, 1);
        addKey(keyboard, "^", KeyEvent.VK_UP, 12, 3, 1);
        addKey(keyboard, "Shift", KeyEvent.VK_SHIFT, 13, 3, 2);

        // Space
        addKey(keyboard, "Space", KeyEvent.VK_SPACE, 4, 4, 7);

        // Arrow keys
        addKey(keyboard, "<", KeyEvent.VK_LEFT, 11, 4, 1);
        addKey(keyboard, "v", KeyEvent.VK_DOWN, 12, 4, 1);
        addKey(keyboard, ">", KeyEvent.VK_RIGHT, 13, 4, 1);

        return keyboard;
    }

    // =====================================================
    // Add a key to the keyboard
    // =====================================================

    private void addKey(
            JPanel panel,
            String text,
            int keyCode,
            int x,
            int y,
            int width
    ) {

        JButton button = new JButton(text);

        button.setFocusable(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));

        // Save original color
        originalColors.put(keyCode, button.getBackground());

        // Store button
        keyButtons.put(keyCode, button);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = 1;

        gbc.weightx = width;
        gbc.weighty = 1;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(3, 3, 3, 3);

        panel.add(button, gbc);

        // There is intentionally NO ActionListener.
        // Clicking the button therefore does nothing.
    }

    // =====================================================
    // Handle keyboard events
    // =====================================================

    private boolean handleKeyEvent(KeyEvent event) {

        // Only handle events while our window is active
        if (!isActiveWindow()) {
            return false;
        }

        int keyCode = event.getKeyCode();

        // -------------------------------------------------
        // Key pressed
        // -------------------------------------------------

        if (event.getID() == KeyEvent.KEY_PRESSED) {

            JButton button = keyButtons.get(keyCode);

            if (button != null) {
                button.setBackground(Color.YELLOW);
            }

            // Backspace needs special handling because
            // we are manually controlling the text area.
            if (keyCode == KeyEvent.VK_BACK_SPACE) {

                String current = textArea.getText();

                if (!current.isEmpty()) {
                    textArea.setText(
                            current.substring(0, current.length() - 1)
                    );

                    if (targetPosition > 0) {
                        targetPosition--;
                    }
                }

                return true;
            }

            // Enter
            if (keyCode == KeyEvent.VK_ENTER) {
                textArea.append("\n");
                checkCharacter('\n');
                return true;
            }

            // Tab
            if (keyCode == KeyEvent.VK_TAB) {
                textArea.append("\t");
                checkCharacter('\t');
                return true;
            }

            // Arrow keys don't add text
            if (keyCode == KeyEvent.VK_LEFT ||
                keyCode == KeyEvent.VK_RIGHT ||
                keyCode == KeyEvent.VK_UP ||
                keyCode == KeyEvent.VK_DOWN) {

                return true;
            }
        }

        // -------------------------------------------------
        // Key released
        // -------------------------------------------------

        if (event.getID() == KeyEvent.KEY_RELEASED) {

            JButton button = keyButtons.get(keyCode);

            if (button != null) {
                button.setBackground(originalColors.get(keyCode));
            }
        }

        // -------------------------------------------------
        // Key typed
        // -------------------------------------------------

        if (event.getID() == KeyEvent.KEY_TYPED) {

            char character = event.getKeyChar();

            // Ignore control characters except normal typing
            if (!Character.isISOControl(character)) {

                textArea.append(String.valueOf(character));

                checkCharacter(character);

                return true;
            }
        }

        return false;
    }

    // =====================================================
    // Check typing accuracy
    // =====================================================

    private void checkCharacter(char character) {

        if (targetPosition < targetText.length()) {

            char expected = targetText.charAt(targetPosition);

            if (character == expected) {
                correct++;
            } else {
                incorrect++;
            }

            targetPosition++;

            updateStatistics();

            if (targetPosition == targetText.length()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Practice complete!\n\n"
                        + "Correct: " + correct
                        + "\nIncorrect: " + incorrect
                        + "\nAccuracy: "
                        + String.format("%.1f", getAccuracy())
                        + "%",
                        "Results",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    // =====================================================
    // Update statistics
    // =====================================================

    private void updateStatistics() {

        statsLabel.setText(
                "Correct: " + correct
                + "    Incorrect: " + incorrect
                + "    Accuracy: "
                + String.format("%.1f", getAccuracy())
                + "%"
        );
    }

    private double getAccuracy() {

        int total = correct + incorrect;

        if (total == 0) {
            return 100.0;
        }

        return (correct * 100.0) / total;
    }

    // =====================================================
    // Main method
    // =====================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TypingTutor tutor = new TypingTutor();

            tutor.setVisible(true);
        });
    }

    private boolean isActiveWindow() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
