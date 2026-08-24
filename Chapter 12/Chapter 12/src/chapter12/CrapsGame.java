
package chapter12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class CrapsGame extends JFrame implements ActionListener {

    private JLabel die1Label;
    private JLabel die2Label;
    private JLabel sumLabel;
    private JLabel pointLabel;

    private JTextField die1Field;
    private JTextField die2Field;
    private JTextField sumField;
    private JTextField pointField;

    private JButton rollButton;
    private JLabel statusLabel;

    private Random random = new Random();

    private int point = 0;
    private boolean firstRoll = true;
    private boolean gameOver = false;

    public CrapsGame() {
        setTitle("Craps Game");
        setLayout(new FlowLayout());

        // Labels
        die1Label = new JLabel("Die 1:");
        die2Label = new JLabel("Die 2:");
        sumLabel = new JLabel("Sum:");
        pointLabel = new JLabel("Point:");

        // Text fields
        die1Field = new JTextField(5);
        die2Field = new JTextField(5);
        sumField = new JTextField(5);
        pointField = new JTextField(5);

        // Make fields display-only
        die1Field.setEditable(false);
        die2Field.setEditable(false);
        sumField.setEditable(false);
        pointField.setEditable(false);

        // Add components
        add(die1Label);
        add(die1Field);

        add(die2Label);
        add(die2Field);

        add(sumLabel);
        add(sumField);

        add(pointLabel);
        add(pointField);

        // Roll button
        rollButton = new JButton("Roll Dice");
        rollButton.addActionListener(this);
        add(rollButton);

        // Status
        statusLabel = new JLabel("Click Roll Dice to begin.");
        add(statusLabel);

        setSize(450, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        // Don't roll after the game is over
        if (gameOver) {
            return;
        }

        // Roll both dice
        int die1 = 1 + random.nextInt(6);
        int die2 = 1 + random.nextInt(6);
        int sum = die1 + die2;

        // Display dice values and sum
        die1Field.setText(String.valueOf(die1));
        die2Field.setText(String.valueOf(die2));
        sumField.setText(String.valueOf(sum));

        // First roll
        if (firstRoll) {

            firstRoll = false;

            if (sum == 7 || sum == 11) {
                // Natural -- player wins
                statusLabel.setText("You Win!");
                gameOver = true;
                rollButton.setEnabled(false);

            } else if (sum == 2 || sum == 3 || sum == 12) {
                // Craps -- player loses
                statusLabel.setText("Craps! You Lose.");
                gameOver = true;
                rollButton.setEnabled(false);

            } else {
                // Establish the point
                point = sum;
                pointField.setText(String.valueOf(point));
                statusLabel.setText("Point established. Roll again.");
            }

        } else {
            // Subsequent rolls

            if (sum == point) {
                // Player made the point
                statusLabel.setText("You Win!");
                gameOver = true;
                rollButton.setEnabled(false);

            } else if (sum == 7) {
                // Seven-out
                statusLabel.setText("Seven-out. You Lose.");
                gameOver = true;
                rollButton.setEnabled(false);

            } else {
                // Continue playing
                statusLabel.setText("Roll again.");
            }
        }
    }

    public static void main(String[] args) {
        new CrapsGame();
    }
}
