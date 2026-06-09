import java.util.Random;

public class PasswordGenerator {
    public static void main(String[] args) {
        Random random = new Random();

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789" +
                "!@#$%^&*";

        String password = "";

        for (int i = 0; i < 15; i++) {
            int index = random.nextInt(characters.length());
            password += characters.charAt(index);
        }

        System.out.printf("The generated Password is: %s%n", password);
    }
}