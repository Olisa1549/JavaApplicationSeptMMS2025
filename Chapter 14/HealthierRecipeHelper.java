package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HealthierRecipeHelper extends JFrame {
    JTextArea recipe = new JTextArea(12, 55);
    JTextArea output = new JTextArea(12, 55);

    public HealthierRecipeHelper() {
        super("14.26 Recipe Ingredient Helper");
        JButton check = new JButton("Suggest Substitutions");
        check.addActionListener(e -> suggest());

        output.setEditable(false);
        JPanel top = new JPanel();
        top.add(check);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(recipe), BorderLayout.CENTER);
        add(new JScrollPane(output), BorderLayout.SOUTH);

        setSize(700, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void suggest() {
        String text = recipe.getText().toLowerCase();
        StringBuilder s = new StringBuilder();
        if (text.contains("sour cream")) s.append("Sour cream -> yogurt\n");
        if (text.contains("butter")) s.append("Butter -> margarine or yogurt\n");
        if (text.contains("sugar")) s.append("Sugar -> consider a smaller amount or a suitable substitute\n");
        if (text.contains("milk")) s.append("Milk -> soy milk can be an alternative\n");
        if (text.contains("white bread")) s.append("White bread -> whole-grain bread\n");
        if (s.length() == 0) s.append("No listed substitution found.");
        s.append("\nAlways consult a qualified health professional before making significant dietary changes.");
        output.setText(s.toString());
    }

    public static void main(String[] args) {
        new HealthierRecipeHelper().setVisible(true);
    }
}
