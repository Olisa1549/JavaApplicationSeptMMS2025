
package chapter12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GuessNumberGame extends JFrame {

    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel resultLabel;
    private JButton newGameButton;

    private int number;
    private int previousDistance;

    public GuessNumberGame() {
        setTitle("Guess the Number");
        setLayout(new FlowLayout());

        // Instructions
        messageLabel = new JLabel(
            "<html>I have a number between 1 and 1000.<br>" +
            "Can you guess my number?<br>" +
            "Please enter your first guess.</html>"
        );
        add(messageLabel);

        // Guess input
        guessField = new JTextField(10);
        add(guessField);

        // Result message
        resultLabel = new JLabel(" ");
        add(resultLabel);

        // New Game button
        newGameButton = new JButton("Play Again");
        add(newGameButton);

        // Start the first game
        startNewGame();

        // Process guesses when Enter is pressed
        guessField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        // Start a new game when button is clicked
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });

        setSize(400, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startNewGame() {
        Random random = new Random();

        // Generate a number from 1 to 1000
        number = 1 + random.nextInt(1000);

        // No previous guess yet
        previousDistance = Integer.MAX_VALUE;

        // Reset the input field
        guessField.setText("");
        guessField.setEditable(true);
        guessField.setBackground(Color.WHITE);
        guessField.requestFocusInWindow();

        resultLabel.setText(" ");
        messageLabel.setText(
            "<html>I have a number between 1 and 1000.<br>" +
            "Can you guess my number?<br>" +
            "Please enter your first guess.</html>"
        );
    }

    private void checkGuess() {
        if (!guessField.isEditable()) {
            return;
        }

        try {
            int guess = Integer.parseInt(guessField.getText());

            if (guess < 1 || guess > 1000) {
                resultLabel.setText("Enter a number from 1 to 1000.");
                return;
            }

            // Correct answer
            if (guess == number) {
                resultLabel.setText("Correct!");
                guessField.setEditable(false);
                guessField.setBackground(Color.GREEN);
                return;
            }

            // Tell the user whether the guess is high or low
            if (guess > number) {
                resultLabel.setText("Too High");
            } else {
                resultLabel.setText("Too Low");
            }

            // Calculate distance from the correct answer
            int currentDistance = Math.abs(number - guess);

            // First guess
            if (previousDistance == Integer.MAX_VALUE) {
                guessField.setBackground(Color.RED);
            }
            // Getting closer = warmer
            else if (currentDistance < previousDistance) {
                guessField.setBackground(Color.RED);
            }
            // Getting farther away = colder
            else {
                guessField.setBackground(Color.BLUE);
            }

            previousDistance = currentDistance;

        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter a valid integer.");
        }
    }

    public static void main(String[] args) {
        new GuessNumberGame();
    }
}

