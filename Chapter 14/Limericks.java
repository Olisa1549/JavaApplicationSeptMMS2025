
package chapter14;

import java.util.Random;

public class Limericks {

    public static void main(String[] args) {

        String[] line1 = {
            "There once was a boy from the town",
            "There once was a girl with a crown",
            "There once was a dog in the bay"
        };

        String[] line2 = {
            "Who loved to go walking around",
            "Who liked to dance all around",
            "Who always went out to play"
        };

        String[] line3 = {
            "He jumped over a light",
            "She ran through the night",
            "The dog took a flight"
        };

        String[] line4 = {
            "And came home before night",
            "Then disappeared from sight",
            "And landed just right"
        };

        String[] line5 = {
            "And everyone laughed at the sound",
            "And everyone smiled at the crown",
            "And everyone cheered at the play"
        };

        Random random = new Random();

        for (int i = 0; i < 5; i++) {

            System.out.println(line1[random.nextInt(line1.length)]);
            System.out.println(line2[random.nextInt(line2.length)]);
            System.out.println(line3[random.nextInt(line3.length)]);
            System.out.println(line4[random.nextInt(line4.length)]);
            System.out.println(line5[random.nextInt(line5.length)]);

            System.out.println();
        }
    }
}