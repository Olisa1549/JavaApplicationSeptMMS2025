
package chapter12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TemperatureConversion extends JFrame {

    private JTextField fahrenheitField;
    private JLabel celsiusLabel;

    public TemperatureConversion() {
        setTitle("Temperature Conversion");
        setLayout(new FlowLayout());

        // Fahrenheit input
        add(new JLabel("Fahrenheit:"));
        fahrenheitField = new JTextField(10);
        add(fahrenheitField);

        // Convert button
        JButton convertButton = new JButton("Convert");
        add(convertButton);

        // Celsius output
        celsiusLabel = new JLabel("Celsius: ");
        add(celsiusLabel);

        // Handle button click
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double fahrenheit =
                        Double.parseDouble(fahrenheitField.getText());

                    double celsius = (5.0 / 9.0) * (fahrenheit - 32);

                    celsiusLabel.setText(
                        String.format("Celsius: %.2f", celsius)
                    );
                } catch (NumberFormatException ex) {
                    celsiusLabel.setText("Enter a valid temperature.");
                }
            }
        });

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TemperatureConversion();
    }
}
