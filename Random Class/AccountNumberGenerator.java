import java.util.Random;

public class AccountNumberGenerator {
    public static void main(String[] args) {
        Random random = new Random();

        String accountNumber = "000";

        for (int i = 0; i < 7; i++) {
            accountNumber += random.nextInt(10); 
        }

        System.out.printf("The generated account Number: %s%n", accountNumber);
    }
}