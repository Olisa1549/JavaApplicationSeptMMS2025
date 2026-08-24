
package chapter14;

 import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class PigLatin {

    public static void main(String[] args) {

        String sentence = JOptionPane.showInputDialog(
                "Enter an English sentence:"
        );

        String[] words = sentence.split(" ");

        JTextArea output = new JTextArea();
        output.setText("Pig Latin:\n");

        for (String word : words) {
            output.append(printLatinWord(word) + " ");
        }

        JOptionPane.showMessageDialog(null, output);
    }

    public static String printLatinWord(String word) {

        String pigLatinWord = word.substring(1)
                + word.charAt(0)
                + "ay";

        return pigLatinWord;
    }
}   
