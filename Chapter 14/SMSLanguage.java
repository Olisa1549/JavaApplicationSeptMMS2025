package chapter14;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SMSLanguage extends JFrame {
    JTextArea input = new JTextArea(8, 45);
    JTextArea output = new JTextArea(8, 45);

    Map<String,String> sms = new LinkedHashMap<>();
    {
        sms.put("imo","in my opinion");
        sms.put("brb","be right back");
        sms.put("btw","by the way");
        sms.put("idk","I don't know");
        sms.put("lol","laughing out loud");
        sms.put("omg","oh my goodness");
        sms.put("ttyl","talk to you later");
        sms.put("pls","please");
        sms.put("u","you");
        sms.put("r","are");
    }

    public SMSLanguage() {
        super("14.28 SMS Language");
        JButton toEnglish = new JButton("SMS -> English");
        JButton toSMS = new JButton("English -> SMS");
        toEnglish.addActionListener(e -> translate(true));
        toSMS.addActionListener(e -> translate(false));

        JPanel buttons = new JPanel();
        buttons.add(toEnglish); buttons.add(toSMS);
        output.setEditable(false);

        add(buttons, BorderLayout.NORTH);
        add(new JScrollPane(input), BorderLayout.CENTER);
        add(new JScrollPane(output), BorderLayout.SOUTH);

        setSize(650, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    void translate(boolean english) {
        String text = input.getText();
        if (english) {
            for (Map.Entry<String,String> e : sms.entrySet())
                text = text.replaceAll("(?i)\\b" + java.util.regex.Pattern.quote(e.getKey()) + "\\b", e.getValue());
        } else {
            for (Map.Entry<String,String> e : sms.entrySet())
                text = text.replaceAll("(?i)\\b" + java.util.regex.Pattern.quote(e.getValue()) + "\\b", e.getKey());
        }
        output.setText(text);
    }

    public static void main(String[] args) {
        new SMSLanguage().setVisible(true);
    }
}
