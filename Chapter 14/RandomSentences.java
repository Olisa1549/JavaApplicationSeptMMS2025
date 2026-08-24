import java.util.Random;

public class RandomSentences {

    public static void main(String[] args) {

        String[] article = {"the", "a", "one", "some", "any"};
        String[] noun = {"boy", "girl", "dog", "town", "car"};
        String[] verb = {"drove", "jumped", "ran", "walked", "skipped"};
        String[] preposition = {"to", "from", "over", "under", "on"};

        Random random = new Random();

        for (int i = 0; i < 20; i++) {

            String sentence = article[random.nextInt(article.length)] + " "
                    + noun[random.nextInt(noun.length)] + " "
                    + verb[random.nextInt(verb.length)] + " "
                    + preposition[random.nextInt(preposition.length)] + " "
                    + article[random.nextInt(article.length)] + " "
                    + noun[random.nextInt(noun.length)] + ".";

            sentence = Character.toUpperCase(sentence.charAt(0))
                    + sentence.substring(1);

            System.out.println(sentence);
        }
    }
}