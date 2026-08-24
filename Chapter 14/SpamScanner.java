package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SpamScanner extends JFrame {
    JTextArea message = new JTextArea(12, 55);
    JLabel result = new JLabel("Spam score: 0");
    String[] keywords = {
        "free money","winner","congratulations","click here","limited time",
        "act now","cash prize","you have won","claim now","risk free",
        "guaranteed","urgent","bonus","earn money","cheap","offer",
        "buy now","no obligation","million dollars","credit","loan",
        "investment","work from home","viagra","unsubscribe","selected",
        "prize","promotion","special offer","deal"
    };

    public SpamScanner() {
        super("14.27 Spam Scanner");
        JButton scan = new JButton("Scan");
        scan.addActionListener(e -> scan());

        JPanel top = new JPanel();
        top.add(scan); top.add(result);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(message), BorderLayout.CENTER);

        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void scan() {
        String text = message.getText().toLowerCase();
        int score = 0;
        for (String k : keywords) {
            int p = text.indexOf(k);
            while (p != -1) {
                score++;
                p = text.indexOf(k, p + k.length());
            }
        }

        String rating = score >= 6 ? "Likely spam" : score >= 3 ? "Possibly spam" : "Low spam likelihood";
        result.setText("Spam score: " + score + " - " + rating);
    }

    public static void main(String[] args) {
        new SpamScanner().setVisible(true);
    }
}
