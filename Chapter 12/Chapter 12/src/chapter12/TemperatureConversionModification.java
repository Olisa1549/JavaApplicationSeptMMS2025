
package chapter12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TemperatureConversionModification extends JFrame {

    private JTextField temperatureField;
    private JLabel resultLabel;
    private JComboBox<String> fromScale;
    private JComboBox<String> toScale;

    public TemperatureConversionModification() {
        setTitle("Temperature Conversion");
        setLayout(new FlowLayout());

        // Input temperature
        add(new JLabel("Temperature:"));
        temperatureField = new JTextField(10);
        add(temperatureField);

        // Source scale
        add(new JLabel("From:"));
        String[] scales = {"Fahrenheit", "Celsius", "Kelvin"};
        fromScale = new JComboBox<>(scales);
        add(fromScale);

        // Destination scale
        add(new JLabel("To:"));
        toScale = new JComboBox<>(scales);
        add(toScale);

        // Convert button
        JButton convertButton = new JButton("Convert");
        add(convertButton);

        // Result
        resultLabel = new JLabel("Result: ");
        add(resultLabel);

        // Convert when button is clicked
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double temperature =
                        Double.parseDouble(temperatureField.getText());

                    String from = (String) fromScale.getSelectedItem();
                    String to = (String) toScale.getSelectedItem();

                    double celsius;

                    // First convert the input to Celsius
                    if (from.equals("Fahrenheit")) {
                        celsius = (5.0 / 9.0) * (temperature - 32);
                    } else if (from.equals("Kelvin")) {
                        celsius = temperature - 273.15;
                    } else {
                        celsius = temperature;
                    }

                    // Then convert Celsius to the requested scale
                    double result;

                    if (to.equals("Fahrenheit")) {
                        result = (9.0 / 5.0) * celsius + 32;
                    } else if (to.equals("Kelvin")) {
                        result = celsius + 273.15;
                    } else {
                        result = celsius;
                    }

                    resultLabel.setText(
                        String.format("Result: %.2f %s", result, to)
                    );

                } catch (NumberFormatException ex) {
                    resultLabel.setText("Enter a valid temperature.");
                }
            }
        });

        setSize(450, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TemperatureConversionModification();
    }
}
