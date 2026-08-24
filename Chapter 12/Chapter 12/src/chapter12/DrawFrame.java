
package chapter12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawFrame extends JFrame {

    private DrawPanel drawPanel;

    private final Color[] colors = {
        Color.BLACK,
        Color.BLUE,
        Color.CYAN,
        Color.DARK_GRAY,
        Color.GRAY,
        Color.GREEN,
        Color.LIGHT_GRAY,
        Color.MAGENTA,
        Color.ORANGE,
        Color.PINK,
        Color.RED,
        Color.WHITE,
        Color.YELLOW
    };

    private final String[] colorNames = {
        "Black",
        "Blue",
        "Cyan",
        "Dark Gray",
        "Gray",
        "Green",
        "Light Gray",
        "Magenta",
        "Orange",
        "Pink",
        "Red",
        "White",
        "Yellow"
    };

    private final String[] shapeNames = {
        "Line",
        "Rectangle",
        "Oval"
    };

    public DrawFrame() {

        setTitle("Interactive Drawing Application");

        setLayout(new BorderLayout());

        // ---------------------------------
        // Status bar
        // ---------------------------------

        JLabel statusLabel =
            new JLabel("Coordinates: (0, 0)");

        // ---------------------------------
        // Drawing panel
        // ---------------------------------

        drawPanel = new DrawPanel(statusLabel);

        // ---------------------------------
        // Top control panel
        // ---------------------------------

        JPanel controlPanel = new JPanel();

        // Undo button
        JButton undoButton =
            new JButton("Undo");

        undoButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                drawPanel.clearLastShape();
            }
        });

        controlPanel.add(undoButton);

        // Clear button
        JButton clearButton =
            new JButton("Clear");

        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                drawPanel.clearDrawing();
            }
        });

        controlPanel.add(clearButton);

        // Color combo box
        controlPanel.add(new JLabel("Color:"));

        JComboBox<String> colorComboBox =
            new JComboBox<>(colorNames);

        colorComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                int index =
                    colorComboBox.getSelectedIndex();

                drawPanel.setCurrentColor(colors[index]);
            }
        });

        controlPanel.add(colorComboBox);

        // Shape combo box
        controlPanel.add(new JLabel("Shape:"));

        JComboBox<String> shapeComboBox =
            new JComboBox<>(shapeNames);

        shapeComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                int index =
                    shapeComboBox.getSelectedIndex();

                drawPanel.setShapeType(index);
            }
        });

        controlPanel.add(shapeComboBox);

        // Filled checkbox
        JCheckBox filledCheckBox =
            new JCheckBox("Filled");

        filledCheckBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent event) {

                boolean filled =
                    event.getStateChange()
                    == ItemEvent.SELECTED;

                drawPanel.setFilledShape(filled);
            }
        });

        controlPanel.add(filledCheckBox);

        // ---------------------------------
        // Add components to frame
        // ---------------------------------

        add(controlPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setSize(900, 650);

        setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);
    }
}
